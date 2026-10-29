package com.nekoadventure.entity.entityRender.missile;

import com.nekoadventure.NekoAdventure;
import com.nekoadventure.entity.missile.MissileEntity;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

/**
 * 天顶剑刀光拖尾渲染。
 * 与 AttackTypes.zenithMovement 使用同一套椭圆轨迹参数（半轴 a、b、角速度 omega、
 * 左右方向 sign），把剑已经扫过的椭圆弧解析重建为一条带状光刃：
 * 内弧与外弧由椭圆单位法线向两侧偏移得到，两条边线始终与轨迹等距、连续平滑。
 * 贴图整图沿弧长只拉伸一次（u: 尾端0 -> 剑身1，v: 内弧0 -> 外弧1）。
 * 拖尾跨度在起手与收尾阶段平滑收敛：起手渐长刻意放缓，刀光由短到长逐步展开。
 */
public final class ZenithSlashRenderer {

    private static final Identifier SLASH_TEXTURE =
            new Identifier(NekoAdventure.MOD_ID, "textures/particle/zenith_particle.png");
    /** 各变色剑的刀光颜色 */
    private static final int[] WEAPON_COLORS = {
            0xEBA687, 0x7A42BF, 0x384ED2, 0xB2FFB4, 0xED1C24,
            0xEC3EC0, 0x5B9EE8, 0xFFE745, 0x6BCB00, 0xFE9E23,
            0xB336C9, 0xB336C9, 0xECC813, 0xECC813, 0xFC5F04,
            0x8FD71D, 0x50DE7A, 0x54EAF5, 0xED3F85, 0xFEC2FA,
            0xB2FFB4
    };
    /** 弧带细分段数 */
    private static final int SEGMENTS = 32;
    /** 拖尾最大角度跨度（弧度） */
    private static final double MAX_SPAN = Math.toRadians(150.0);
    /** 小于该跨度时不再渲染刀光 */
    private static final double MIN_SPAN = 1.0E-3;
    /** 起手渐长所占整圈进度（越大起手渐长越慢） */
    private static final double FADE_IN = 0.4;
    /** 收尾收窄所占整圈进度 */
    private static final double FADE_OUT = 0.18;
    /** 拖尾末端由尖收窄到全宽所占的弧长比例 */
    private static final float TAIL_FADE = 0.45f;
    /** 刀光外弧半宽 */
    private static final float OUTER_WIDTH = 0.75f;
    /** 刀光内弧半宽 */
    private static final float INNER_WIDTH = 0.75f;

    private ZenithSlashRenderer() {
    }

    /** 渲染一帧天顶剑刀光 */
    public static void render(MissileEntity entity, MatrixStack matrices,
                              VertexConsumerProvider vertexConsumers, float tickDelta) {
        double time = Math.max(1, entity.getTime());
        double a = entity.getRange() / 2.0;
        double b = 3.0;
        double omega = 2.0 * Math.PI / time;
        double sign = entity.getFlag() < 11 ? 1.0 : -1.0;
        int alive = entity.getAliveDuration();
        double yawTheta = omega * Math.max(0, alive - 1);
        double phi = Math.atan2(b * Math.cos(yawTheta), -a * Math.sin(yawTheta) * sign);
        double baseYaw = Math.toRadians(entity.getYaw()) - phi;
        Vec3d forward = new Vec3d(-Math.sin(baseYaw), 0.0, Math.cos(baseYaw));
        Vec3d right = new Vec3d(-forward.z, 0.0, forward.x);
        Vec3d renderPos = entity.getLerpedPos(tickDelta);
        Vec3d center = entity.getPos().subtract(renderPos)
                .subtract(ellipseOffset(forward, right, a, b, sign, omega * alive));
        double thetaNominal = omega * Math.max(0.0, alive - 1 + tickDelta);
        Vec3d rel = center.multiply(-1.0);
        double thetaReal = Math.atan2(rel.dotProduct(right) / b, rel.dotProduct(forward) / (a * sign));
        double theta = thetaReal
                + Math.round((thetaNominal - thetaReal) / (2.0 * Math.PI)) * (2.0 * Math.PI);
        double progress = MathHelper.clamp(theta / (2.0 * Math.PI), 0.0, 1.0);
        double fadeProgress = progress / FADE_IN;
        double span = MAX_SPAN
                * smoothStep(fadeProgress * fadeProgress)
                * smoothStep((1.0 - progress) / FADE_OUT);
        double thetaStart = Math.max(0.0, theta - span);
        if (theta - thetaStart < MIN_SPAN) return;
        int color = WEAPON_COLORS[MathHelper.clamp(entity.getFlag(), 0, WEAPON_COLORS.length - 1)];
        float cr = ((color >> 16) & 0xFF) / 255.0F;
        float cg = ((color >> 8) & 0xFF) / 255.0F;
        float cb = (color & 0xFF) / 255.0F;

        VertexConsumer vc = vertexConsumers.getBuffer(
                RenderLayer.getEntityTranslucentEmissive(SLASH_TEXTURE));
        MatrixStack.Entry entry = matrices.peek();
        Matrix4f mat = entry.getPositionMatrix();
        Matrix3f normalMat = entry.getNormalMatrix();
        for (int i = 0; i < SEGMENTS; i++) {
            double s0 = i / (double) SEGMENTS;
            double s1 = (i + 1) / (double) SEGMENTS;
            double angle0 = thetaStart + (theta - thetaStart) * s0;
            double angle1 = thetaStart + (theta - thetaStart) * s1;
            float width0 = (float) smoothStep(s0 / TAIL_FADE);
            float width1 = (float) smoothStep(s1 / TAIL_FADE);
            Vec3d point0 = center.add(ellipseOffset(forward, right, a, b, sign, angle0));
            Vec3d point1 = center.add(ellipseOffset(forward, right, a, b, sign, angle1));
            Vec3d normal0 = ellipseNormal(forward, right, a, b, sign, angle0);
            Vec3d normal1 = ellipseNormal(forward, right, a, b, sign, angle1);
            Vec3d inner0 = point0.subtract(normal0.multiply(INNER_WIDTH * width0));
            Vec3d outer0 = point0.add(normal0.multiply(OUTER_WIDTH * width0));
            Vec3d inner1 = point1.subtract(normal1.multiply(INNER_WIDTH * width1));
            Vec3d outer1 = point1.add(normal1.multiply(OUTER_WIDTH * width1));
            float u0 = (float) s0;
            float u1 = (float) s1;
            slashVertex(vc, mat, normalMat, inner0, u0, 0.0f, cr, cg, cb);
            slashVertex(vc, mat, normalMat, outer0, u0, 1.0f, cr, cg, cb);
            slashVertex(vc, mat, normalMat, outer1, u1, 1.0f, cr, cg, cb);
            slashVertex(vc, mat, normalMat, inner1, u1, 0.0f, cr, cg, cb);
        }
    }
    private static Vec3d ellipseOffset(Vec3d forward, Vec3d right, double a, double b, double sign, double theta) {
        return forward.multiply(a * sign * Math.cos(theta))
                .add(right.multiply(b * Math.sin(theta)));
    }
    private static Vec3d ellipseNormal(Vec3d forward, Vec3d right, double a, double b, double sign, double theta) {
        return forward.multiply(b * Math.cos(theta))
                .add(right.multiply(a * sign * Math.sin(theta)))
                .normalize();
    }
    private static void slashVertex(VertexConsumer vc, Matrix4f mat, Matrix3f normalMat, Vec3d pos,
                                    float u, float v, float cr, float cg, float cb) {
        vc.vertex(mat, (float) pos.x, (float) pos.y, (float) pos.z)
                .color(cr, cg, cb, 1.0f)
                .texture(u, v)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .normal(normalMat, 0.0f, 1.0f, 0.0f)
                .next();
    }
    private static double smoothStep(double x) {
        x = MathHelper.clamp(x, 0.0, 1.0);
        return x * x * (3.0 - 2.0 * x);
    }
}
