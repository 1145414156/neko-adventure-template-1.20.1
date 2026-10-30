package com.nekoadventure.entity.entityRender.missile;


import com.nekoadventure.NekoAdventure;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.other.mobApart.MissileModelType;
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
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;


//全部弹幕的渲染总类
public class MissileEntityRenderer extends EntityRenderer<MissileEntity> {

    private static final Identifier TEXTURE_BULLET =
            new Identifier(NekoAdventure.MOD_ID, "textures/entity/missile/bullet.png");
    private static final Identifier TEXTURE_BRIMSTONE =
            new Identifier(NekoAdventure.MOD_ID, "textures/entity/missile/brimstone.png");
    private static final Identifier TEXTURE_JOYEUSE =
            new Identifier(NekoAdventure.MOD_ID, "textures/entity/missile/joyeuse.png");
    private static final Identifier TEXTURE_BOOMERANG=
            new Identifier(NekoAdventure.MOD_ID, "textures/entity/missile/boomerang.png");
    private static final ItemStack ZENITH_STACK = new ItemStack(ModItems.ZENITH);
    private static final ItemStack JOYEUSE_STACK = new ItemStack(ModItems.JOYEUSE);

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

        boolean isPlayer = !(entity.getRealOwner() instanceof PlayerEntity);
        float red =1.0f;
        float green = isPlayer ? 0.0f : 1.0f;
        float blue = isPlayer ? 0.0f : 1.0f;

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
                float uMid = 0.999f;
                float inner = 0.6f * (1.0f - eased);
                int t= entity.getAliveDuration() %5/2;
                Identifier texture=new Identifier(NekoAdventure.MOD_ID, "textures/entity/missile/brimstone"+t+".png");
                if (entity.getFlag()==1){
                   texture  = new Identifier(NekoAdventure.MOD_ID, "textures/entity/missile/brimstone_start"+t+".png");
                } else if (entity.getFlag()==entity.getRange()) {
                    texture  = new Identifier(NekoAdventure.MOD_ID, "textures/entity/missile/brimstone_end"+t+".png");
                }

                VertexConsumer consumer = vertexConsumers.getBuffer(
                        RenderLayer.getEntityTranslucentEmissive(texture));
                MissileEntity next = BrimstoneBeamRenderer.findNext(entity);
                BrimstoneBeamRenderer.render(entity, next, look, tickDelta, inner, baseNormal, matrices,
                        consumer, uMid, this.dispatcher.camera.getPos());
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
                Vec3d look = entity.getRotationVec(tickDelta);
                Vec3d toCamera = this.dispatcher.camera.getPos().subtract(entity.getLerpedPos(tickDelta));
                Vec3d faceDir = toCamera.subtract(look.multiply(toCamera.dotProduct(look)));
                if (faceDir.lengthSquared() < 1.0E-4) {
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
            float size = 0.4f;
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
