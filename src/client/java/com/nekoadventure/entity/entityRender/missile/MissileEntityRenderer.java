package com.nekoadventure.entity.entityRender.missile;


import com.nekoadventure.NekoAdventure;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.entity.missile.MissileModelType;
import com.nekoadventure.entity.ModEntityModelLayers;
import com.nekoadventure.entity.model.missile.BoomerangEntityModel;
import com.nekoadventure.item.ModItems;
import com.nekoadventure.particle.ModParticleTypes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class MissileEntityRenderer extends EntityRenderer<MissileEntity> {

    private static final Identifier TEXTURE_BULLET =
            new Identifier(NekoAdventure.MOD_ID, "textures/entity/missile/bullet.png");
    private static final Identifier TEXTURE_BRIMSTONE =
            new Identifier(NekoAdventure.MOD_ID, "textures/entity/missile/brimstone.png");
    private static final Identifier TEXTURE_JOYEUSE =
            new Identifier(NekoAdventure.MOD_ID, "textures/entity/missile/joyeuse.png");
    private static final Identifier TEXTURE_BOOMERANG=
            new Identifier(NekoAdventure.MOD_ID, "textures/entity/missile/boomerang.png");
    //zenith弹幕剑以物品模型渲染，需要一个非空的载体堆（实际模型通过参数显式指定）
    private static final ItemStack ZENITH_STACK = new ItemStack(ModItems.ZENITH);
    private static final ItemStack JOYEUSE_STACK = new ItemStack(ModItems.JOYEUSE);

    //joyeuse物品贴图里剑身沿 45° 对角线绘制（左下剑柄、右上剑尖），模型空间中剑尖方向为归一化的 (1,1,0)
    private static final Vector3f ITEM_BLADE_AXIS = new Vector3f(0.70710678f, 0.70710678f, 0.0f);

    private final BoomerangEntityModel modelBoomerang;
    public MissileEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.modelBoomerang=new BoomerangEntityModel(ctx.getPart(ModEntityModelLayers.BOOMERANG));
    }

    @Override
    public void render(MissileEntity entity, float yaw, float tickDelta,
                       MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        boolean renderRed;
        if(entity.getOwner() != null && entity.getOwner() instanceof PlayerEntity){
            renderRed=false;
        } else renderRed= entity.getOwner() == null || !(entity.getOwner() instanceof MissileEntity) || !((((MissileEntity) entity.getOwner()).getOwner()) instanceof PlayerEntity);

        float red =1.0f;
        float green = renderRed ? 0.0f : 1.0f;
        float blue = renderRed ? 0.0f : 1.0f;

        //brimstone分支是用AI做的
        //为了格式统一，所以说一股脑塞进一个分支里面了，观感可能有点差...
        if (entity.getMissileType() == MissileModelType.BRIMSTONE) {
            if (entity.getFlag()<=0){
                if (entity.getWorld() instanceof ClientWorld clientWorld) {
                    double x = MathHelper.lerp(tickDelta, entity.prevX, entity.getX());
                    double y = MathHelper.lerp(tickDelta, entity.prevY, entity.getY());
                    double z = MathHelper.lerp(tickDelta, entity.prevZ, entity.getZ());
                    DustParticleEffect ash = new DustParticleEffect(
                            new Vector3f(1.0f, 0, 0), 0.75f);
                    clientWorld.addParticle(ash,
                                    x , y , z ,
                                    0.0, 0.0, 0.0);

                }
            }
            else {
                Matrix3f baseNormal = new Matrix3f(matrices.peek().getNormalMatrix());
                int duration=entity.getAliveDuration();
                int time=entity.getTime();
                float progress = (float) duration / Math.max(1, time);
                float eased = (float) Math.pow(progress,10);
                Vec3d look = entity.getRotationVec(tickDelta);
                float length = 1.5f;
                matrices.push();
                matrices.translate(-look.x, -look.y, -look.z);
                float yawRad = (float) Math.atan2(look.z, look.x);
                float pitchRad = (float) Math.acos(MathHelper.clamp(look.y, -1.0, 1.0));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((1.5707964f - yawRad) * 57.295776f));
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(pitchRad * 57.295776f));
                float roll;
                {
                    Vec3d toCamera = this.dispatcher.camera.getPos().subtract(entity.getLerpedPos(tickDelta));
                    Vec3d perpendicular = toCamera.subtract(look.multiply(toCamera.dotProduct(look)));
                    if (perpendicular.lengthSquared() < 1.0E-4) {
                        roll = 0.0f;
                    } else {
                        float ryAngle = 1.5707964f - yawRad;
                        double u = Math.cos(ryAngle) * perpendicular.x - Math.sin(ryAngle) * perpendicular.z;
                        double w = Math.cos(pitchRad) * (Math.sin(ryAngle) * perpendicular.x + Math.cos(ryAngle) * perpendicular.z)
                                - Math.sin(pitchRad) * perpendicular.y;
                        roll = (float) Math.atan2(-u, w);
                    }
                }

                float vStart = 0.0f;
                float vEnd = 1.0f;
                float uMid = 0.999f;
                float inner = 0.6f * (1.0f - eased);
                float ix1 = MathHelper.cos(roll + 3.1415927f) * inner;
                float iz1 = MathHelper.sin(roll + 3.1415927f) * inner;
                float ix2 = MathHelper.cos(roll) * inner;
                float iz2 = MathHelper.sin(roll) * inner;
                float jx1 = MathHelper.cos(roll + 4.712389f) * inner;
                float jz1 = MathHelper.sin(roll + 4.712389f) * inner;
                float jx2 = MathHelper.cos(roll + 1.5707964f) * inner;
                float jz2 = MathHelper.sin(roll + 1.5707964f) * inner;
                int t= entity.getAliveDuration() %5/2;
                Identifier texture=new Identifier(NekoAdventure.MOD_ID, "textures/entity/missile/brimstone"+t+".png");
                if (entity.getFlag()==1){
                   texture  = new Identifier(NekoAdventure.MOD_ID, "textures/entity/missile/brimstone_start"+t+".png");
                } else if (entity.getFlag()==entity.getRange()) {
                    texture  = new Identifier(NekoAdventure.MOD_ID, "textures/entity/missile/brimstone_end"+t+".png");
                }

                VertexConsumer consumer = vertexConsumers.getBuffer(
                        RenderLayer.getEntityTranslucentEmissive(texture));
                MatrixStack.Entry entry = matrices.peek();
                java.util.function.Consumer<float[]> beamVertex = v -> consumer.vertex(entry.getPositionMatrix(), v[0], v[1], v[2])
                        .color(255, 255, 255, 255)
                        .texture(v[3], v[4])
                        .overlay(OverlayTexture.DEFAULT_UV)
                        .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                        .normal(baseNormal, 0.0f, 1.0f, 0.0f)
                        .next();
                beamVertex.accept(new float[]{ix1, length, iz1, uMid, vEnd});
                beamVertex.accept(new float[]{ix1, 0.0f,   iz1, uMid, vStart});
                beamVertex.accept(new float[]{ix2, 0.0f,   iz2, 0.0f, vStart});
                beamVertex.accept(new float[]{ix2, length, iz2, 0.0f, vEnd});
                beamVertex.accept(new float[]{jx1, length, jz1, uMid, vEnd});
                beamVertex.accept(new float[]{jx1, 0.0f,   jz1, uMid, vStart});
                beamVertex.accept(new float[]{jx2, 0.0f,   jz2, 0.0f, vStart});
                beamVertex.accept(new float[]{jx2, length, jz2, 0.0f, vEnd});
                matrices.pop();
               if (entity.getWorld() instanceof ClientWorld clientWorld
                        && (entity.getFlag() > 2 || entity.getFlag() < 1)
                        ) {
                   double px = MathHelper.lerp(tickDelta, entity.prevX, entity.getX()) - look.x;
                   double py = MathHelper.lerp(tickDelta, entity.prevY, entity.getY()) - look.y;
                   double pz = MathHelper.lerp(tickDelta, entity.prevZ, entity.getZ()) - look.z;
                   double speed = 1.0;
                   double spread = 0.3;

                   double vx = look.x * speed + (clientWorld.random.nextDouble() - 0.5) * spread;
                   double vy = look.y * speed + (clientWorld.random.nextDouble() - 0.5) * spread;
                   double vz = look.z * speed + (clientWorld.random.nextDouble() - 0.5) * spread;
                   if (clientWorld.random.nextDouble() > 0.99) {
                       switch (clientWorld.random.nextInt(3)) {
                           case 0 -> clientWorld.addParticle(ModParticleTypes.BRIMSTONE_ATTACK1, px, py, pz, vx, vy, vz);
                           case 1 -> clientWorld.addParticle(ModParticleTypes.BRIMSTONE_ATTACK2, px, py, pz, vx, vy, vz);
                           default -> clientWorld.addParticle(
                                   new DustParticleEffect(new Vector3f(1.0f, 0.0f, 0.0f), 1.0f),
                                   px, py, pz, vx, vy, vz);
                       }
                   }
                }
            }
        }

        else if (entity.getMissileType() == MissileModelType.JOYEUSE) {
            BakedModel model = MinecraftClient.getInstance().getItemRenderer()
                    .getModel(JOYEUSE_STACK, entity.getWorld(), null, entity.getId());
            if (model != null) {
                //物品贴图是单层平面，必须让贴图里 45° 的剑身轴线对准弹幕朝向，
                //再绕剑轴 roll 到尽量正对镜头，否则平面与视线共面会被压成一条线（表现为不渲染）
                Vec3d look = entity.getRotationVec(tickDelta);
                Vec3d toCamera = this.dispatcher.camera.getPos().subtract(entity.getLerpedPos(tickDelta));
                Vec3d faceDir = toCamera.subtract(look.multiply(toCamera.dotProduct(look)));
                if (faceDir.lengthSquared() < 1.0E-4) {
                    //视线几乎与剑轴共线时退化，用竖直平面兜底
                    faceDir = new Vec3d(0.0, 1.0, 0.0).crossProduct(look);
                }
                faceDir = faceDir.normalize();

                Vector3f lookVec = new Vector3f((float) look.x, (float) look.y, (float) look.z);
                Quaternionf orientation = new Quaternionf().rotationTo(ITEM_BLADE_AXIS, lookVec);
                Vector3f normal = new Vector3f(0.0f, 0.0f, 1.0f).rotate(orientation);
                Vector3f faceVec = new Vector3f((float) faceDir.x, (float) faceDir.y, (float) faceDir.z);
                Vector3f cross = normal.cross(faceVec, new Vector3f());
                float roll = (float) Math.atan2(cross.dot(lookVec), normal.dot(faceVec));
                orientation.premul(new Quaternionf().setAngleAxis(roll, lookVec.x, lookVec.y, lookVec.z));

                matrices.push();
                matrices.multiply(orientation);
                float size = 1.5f;
                matrices.scale(size, size, size);
                MinecraftClient.getInstance().getItemRenderer().renderItem(
                        JOYEUSE_STACK,
                        ModelTransformationMode.NONE,
                        false,
                        matrices,
                        vertexConsumers,
                        LightmapTextureManager.MAX_LIGHT_COORDINATE,
                        OverlayTexture.DEFAULT_UV,
                        model
                );
                matrices.pop();
            }
        }

        else if (entity.getMissileType() == MissileModelType.BOOMERANG) {
            matrices.push();
            this.modelBoomerang.setAngles(entity, 0, 0, entity.age, yaw, entity.getPitch());
            var vertexConsumer = vertexConsumers.getBuffer(
                    modelBoomerang.getLayer(TEXTURE_BOOMERANG));
            float rotation = (entity.age + tickDelta) * 20.0f;
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotation));
            modelBoomerang.render(matrices, vertexConsumer, light,
                    OverlayTexture.DEFAULT_UV, red, green, blue, 1.0f);
            matrices.pop();
        }

        else if (entity.getMissileType()==MissileModelType.BULLET){
            matrices.push();
            matrices.multiply(this.dispatcher.getRotation());
            matrices.translate(0, 0.1, 0);
            float size = 0.2f;
            float half = size / 2.0f;
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(
                    RenderLayer.getEntityCutoutNoCull(TEXTURE_BULLET));
            Matrix4f mat = matrices.peek().getPositionMatrix();
            vertexConsumer.vertex(mat, -half, -half, 0)
                    .color(red, green, blue, 1.0f)
                    .texture(0.0f, 1.0f)
                    .overlay(OverlayTexture.DEFAULT_UV)
                    .light(light)
                    .normal(0.0f, 0.0f, 1.0f)
                    .next();
            vertexConsumer.vertex(mat, half, -half, 0)
                    .color(red, green, blue, 1.0f)
                    .texture(1.0f, 1.0f)
                    .overlay(OverlayTexture.DEFAULT_UV)
                    .light(light)
                    .normal(0.0f, 0.0f, 1.0f)
                    .next();
            vertexConsumer.vertex(mat, half, half, 0)
                    .color(red, green, blue, 1.0f)
                    .texture(1.0f, 0.0f)
                    .overlay(OverlayTexture.DEFAULT_UV)
                    .light(light)
                    .normal(0.0f, 0.0f, 1.0f)
                    .next();
            vertexConsumer.vertex(mat, -half, half, 0)
                    .color(red, green, blue, 1.0f)
                    .texture(0.0f, 0.0f)
                    .overlay(OverlayTexture.DEFAULT_UV)
                    .light(light)
                    .normal(0.0f, 0.0f, 1.0f)
                    .next();
            matrices.pop();
        }
        else if (entity.getMissileType() == MissileModelType.ZENITH) {
            boolean isLeft = entity.getFlag() < 11;

            ZenithSlashRenderer.render(entity, matrices, vertexConsumers, tickDelta);
            Identifier modelId = new Identifier(NekoAdventure.MOD_ID,
                    "item/zenith_sword_" + MathHelper.clamp(entity.getFlag(), 0, 20));
            BakedModel model = MinecraftClient.getInstance().getBakedModelManager()
                    .getModel(modelId);
            if (model != null) {
                float size = 1.5f;
                matrices.push();
                if (isLeft) {
                    matrices.scale( size, size, size);
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-entity.getYaw()));
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(entity.getPitch() + 90f));
                    matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(30f));
                }
                else {
                    matrices.scale( size, size, -1*size);
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getYaw()));
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-entity.getPitch() - 90f));
                    matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(210f));
                }
                MinecraftClient.getInstance().getItemRenderer().renderItem(
                        ZENITH_STACK, ModelTransformationMode.NONE, false, matrices, vertexConsumers,
                        LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV, model);
                matrices.pop();
            }
        }
    }

    @Override
    public Identifier getTexture(MissileEntity entity) {
        return switch (entity.getMissileType()) {
            case BRIMSTONE -> TEXTURE_BRIMSTONE;
            case JOYEUSE -> TEXTURE_JOYEUSE;
            case BOOMERANG -> TEXTURE_BOOMERANG;
            case BULLET -> TEXTURE_BULLET;
            default -> null;
        };
    }
}
