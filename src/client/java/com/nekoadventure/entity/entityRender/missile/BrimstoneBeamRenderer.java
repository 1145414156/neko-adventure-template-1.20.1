package com.nekoadventure.entity.entityRender.missile;

import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.other.mobApart.MissileModelType;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3f;

import java.util.Comparator;
import java.util.List;

/**
 * 为硫磺火光柱制作的专门的弧形段渲染。
 * 每节以自身位置为起点、沿自身视野出发，弯向下一节：终点取下一节视野前方一格，
 */
public final class BrimstoneBeamRenderer {

    //弧线细分数
    private static final int SEGMENTS = 8;
    //查找下一节光柱的半径
    private static final double SEARCH_RADIUS = 3.0;
    //尾段沿自身视野向前收尾的长度
    private static final double TAIL_LENGTH = 1.0;

    private BrimstoneBeamRenderer() {
    }
    @Nullable
    public static MissileEntity findNext(MissileEntity entity) {
        if (entity.getFlag() >= (int) entity.getRange()) {
            return null;
        }
        int nextFlag = entity.getFlag() + 1;
        Entity owner = entity.getOwner();
        List<MissileEntity> candidates = entity.getWorld().getEntitiesByClass(
                MissileEntity.class,
                entity.getBoundingBox().expand(SEARCH_RADIUS),
                e -> e != entity
                        && e.getMissileType() == MissileModelType.BRIMSTONE
                        && e.getFlag() == nextFlag
                        && e.getOwner() == owner
        );
        return candidates.stream()
                .min(Comparator.comparingDouble(e -> e.squaredDistanceTo(entity)))
                .orElse(null);
    }
    public static void render(MissileEntity entity, @Nullable MissileEntity next, Vec3d look, float tickDelta,
                              float inner, Matrix3f baseNormal, MatrixStack matrices,
                              VertexConsumer consumer, float uMid, Vec3d cameraPos) {
        Vec3d origin = entity.getLerpedPos(tickDelta);
        Vec3d endLook;
        Vec3d end;
        if (next != null) {
            endLook = next.getRotationVec(tickDelta);
            Vec3d nextPos = next.getLerpedPos(tickDelta).subtract(origin);
            if (entity.getFlag() == 1 || next.getFlag() >= (int) next.getRange()) {
                end = nextPos;
            } else {
                end = nextPos.add(endLook.multiply(TAIL_LENGTH));
            }
        } else {
            endLook = look;
            end = look.multiply(TAIL_LENGTH);
        }
        double handle = Math.max(end.length(), 0.5);
        Vec3d startHandle = look.multiply(handle);
        Vec3d endHandle = endLook.multiply(handle);

        Vec3d[] points = new Vec3d[SEGMENTS + 1];
        Vec3d[] tangents = new Vec3d[SEGMENTS + 1];
        double[] arc = new double[SEGMENTS + 1];
        for (int i = 0; i <= SEGMENTS; i++) {
            float u = (float) i / SEGMENTS;
            points[i] = hermite(end, startHandle, endHandle, u);
            tangents[i] = hermiteTangent(end, startHandle, endHandle, u).normalize();
            if (i > 0) {
                arc[i] = arc[i - 1] + points[i].distanceTo(points[i - 1]);
            }
        }
        double arcLength = Math.max(arc[SEGMENTS], 1.0E-4);
        Vec3d[] sides = new Vec3d[SEGMENTS + 1];
        Vec3d[] binormals = new Vec3d[SEGMENTS + 1];
        Vec3d prevSide = null;
        for (int i = 0; i <= SEGMENTS; i++) {
            Vec3d tangent = tangents[i];
            Vec3d toCamera = cameraPos.subtract(origin.add(points[i]));
            Vec3d side = toCamera.subtract(tangent.multiply(toCamera.dotProduct(tangent)));
            if (side.lengthSquared() < 1.0E-4 && prevSide != null) {
                side = prevSide.subtract(tangent.multiply(prevSide.dotProduct(tangent)));
            }
            if (side.lengthSquared() < 1.0E-4) {
                side = new Vec3d(0.0, 1.0, 0.0).crossProduct(tangent);
            }
            if (side.lengthSquared() < 1.0E-4) {
                side = new Vec3d(1.0, 0.0, 0.0).crossProduct(tangent);
            }
            if (prevSide != null && side.dotProduct(prevSide) < 0) {
                side = side.negate();
            }
            side = side.normalize();
            sides[i] = side;
            binormals[i] = tangent.crossProduct(side).normalize();
            prevSide = side;
        }

        MatrixStack.Entry entry = matrices.peek();
        for (int i = 0; i < SEGMENTS; i++) {
            float v0 = (float) (arc[i] / arcLength);
            float v1 = (float) (arc[i + 1] / arcLength);
            emitBeamQuad(consumer, entry, baseNormal, points[i], sides[i], points[i + 1], sides[i + 1], inner, uMid, v0, v1);
            emitBeamQuad(consumer, entry, baseNormal, points[i], binormals[i], points[i + 1], binormals[i + 1], inner, uMid, v0, v1);
        }
    }

    private static void emitBeamQuad(VertexConsumer consumer, MatrixStack.Entry entry, Matrix3f normal,
                                     Vec3d startPos, Vec3d startSide, Vec3d endPos, Vec3d endSide,
                                     float inner, float uA, float vStart, float vEnd) {
        beamVertex(consumer, entry, normal, endPos.add(endSide.multiply(inner)), uA, vEnd);
        beamVertex(consumer, entry, normal, startPos.add(startSide.multiply(inner)), uA, vStart);
        beamVertex(consumer, entry, normal, startPos.subtract(startSide.multiply(inner)), (float) 0.0, vStart);
        beamVertex(consumer, entry, normal, endPos.subtract(endSide.multiply(inner)), (float) 0.0, vEnd);
    }

    private static void beamVertex(VertexConsumer consumer, MatrixStack.Entry entry, Matrix3f normal,
                                   Vec3d pos, float u, float v) {
        consumer.vertex(entry.getPositionMatrix(), (float) pos.x, (float) pos.y, (float) pos.z)
                .color(255, 255, 255, 255)
                .texture(u, v)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .normal(normal, 0.0f, 1.0f, 0.0f)
                .next();
    }

    private static Vec3d hermite(Vec3d p1, Vec3d t0, Vec3d t1, float t) {
        float t2 = t * t;
        float t3 = t2 * t;
        return Vec3d.ZERO.multiply(2 * t3 - 3 * t2 + 1)
                .add(t0.multiply(t3 - 2 * t2 + t))
                .add(p1.multiply(-2 * t3 + 3 * t2))
                .add(t1.multiply(t3 - t2));
    }

    private static Vec3d hermiteTangent(Vec3d p1, Vec3d t0, Vec3d t1, float t) {
        float t2 = t * t;
        return Vec3d.ZERO.multiply(6 * t2 - 6 * t)
                .add(t0.multiply(3 * t2 - 4 * t + 1))
                .add(p1.multiply(-6 * t2 + 6 * t))
                .add(t1.multiply(3 * t2 - 2 * t));
    }
}
