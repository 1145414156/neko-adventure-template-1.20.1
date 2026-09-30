package com.nekoadventure.entity.entityRender.missile;


import com.nekoadventure.NekoAdventure;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.entity.missile.MissileModelType;
import com.nekoadventure.entity.ModEntityModelLayers;
import com.nekoadventure.entity.model.missile.BoomerangEntityModel;
import com.nekoadventure.entity.model.missile.BrimstoneEntityModel;
import com.nekoadventure.entity.model.missile.JoyeuseEntityModel;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;

public class MissileEntityRenderer extends EntityRenderer<MissileEntity> {

    private static final Identifier TEXTURE_DEFAULT =
            new Identifier(NekoAdventure.MOD_ID, "textures/entity/missile/bullet.png");
    private static final Identifier TEXTURE_BRIMSTONE =
            new Identifier(NekoAdventure.MOD_ID, "textures/entity/missile/brimstone.png");
    private static final Identifier TEXTURE_JOYEUSE =
            new Identifier(NekoAdventure.MOD_ID, "textures/entity/missile/joyeuse.png");
    private static final Identifier TEXTURE_BOOMERANG=
            new Identifier(NekoAdventure.MOD_ID, "textures/entity/missile/boomerang.png");

    private final BrimstoneEntityModel modelBrimstone;
    private final JoyeuseEntityModel modelJoyeuse;
    private final BoomerangEntityModel modelBoomerang;
    public MissileEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.modelBrimstone = new BrimstoneEntityModel(ctx.getPart(ModEntityModelLayers.BRIMSTONE));
        this.modelJoyeuse=new JoyeuseEntityModel(ctx.getPart(ModEntityModelLayers.JOYEUSE));
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
        float green = renderRed ? 0.5f : 1.0f;
        float blue = renderRed ? 0.5f : 1.0f;

        if (entity.getMissileType() == MissileModelType.BRIMSTONE) {
            this.modelBrimstone.setAngles(entity, 0, 0, entity.age, yaw, entity.getPitch());
            if (entity.getWorld() instanceof ClientWorld clientWorld) {
                //qwq
                if (clientWorld.random.nextFloat() < 0.1f) {
                    clientWorld.addParticle(
                            ParticleTypes.FLAME,
                            entity.getX(), entity.getY(), entity.getZ(),
                            0, 0, 0
                    );
                }
            }
            var vertexConsumer = vertexConsumers.getBuffer(
                    modelBrimstone.getLayer(TEXTURE_BRIMSTONE));
            modelBrimstone.render(matrices, vertexConsumer, light,
                    OverlayTexture.DEFAULT_UV, red, green, blue, 0.5f);
        }
        else if (entity.getMissileType() == MissileModelType.JOYEUSE) {
            this.modelJoyeuse.setAngles(entity, 0, 0, entity.age, yaw, entity.getPitch());
            var vertexConsumer = vertexConsumers.getBuffer(
                    modelJoyeuse.getLayer(TEXTURE_JOYEUSE));
            modelJoyeuse.render(matrices, vertexConsumer, light,
                    OverlayTexture.DEFAULT_UV, red, green, blue, 1.0f);
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
                    RenderLayer.getEntityCutoutNoCull(TEXTURE_DEFAULT));
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
    }

    @Override
    public Identifier getTexture(MissileEntity entity) {
        return switch (entity.getMissileType()) {
            case BRIMSTONE -> TEXTURE_BRIMSTONE;
            case JOYEUSE -> TEXTURE_JOYEUSE;
            case BOOMERANG -> TEXTURE_BOOMERANG;
            default -> TEXTURE_DEFAULT;
        };
    }
}
