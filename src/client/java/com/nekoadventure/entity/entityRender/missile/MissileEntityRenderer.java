package com.nekoadventure.entity.entityRender.missile;


import com.nekoadventure.NekoAdventure;
import com.nekoadventure.entity.missile.MissileEntity;
import com.nekoadventure.entity.missile.MissileModelType;
import com.nekoadventure.entity.ModEntityModelLayers;
import com.nekoadventure.entity.model.missile.BoomerangEntityModel;
import com.nekoadventure.entity.model.missile.BrimstoneEntityModel;
import com.nekoadventure.entity.model.missile.BulletEntityModel;
import com.nekoadventure.entity.model.missile.JoyeuseEntityModel;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class MissileEntityRenderer extends EntityRenderer<MissileEntity> {

    private static final Identifier TEXTURE_DEFAULT =
            new Identifier(NekoAdventure.MOD_ID, "textures/entity/missile/bullet.png");
    private static final Identifier TEXTURE_BRIMSTONE =
            new Identifier(NekoAdventure.MOD_ID, "textures/entity/missile/brimstone.png");
    private static final Identifier TEXTURE_JOYEUSE =
            new Identifier(NekoAdventure.MOD_ID, "textures/entity/missile/joyeuse.png");
    private static final Identifier TEXTURE_BOOMERANG=
            new Identifier(NekoAdventure.MOD_ID, "textures/entity/missile/boomerang.png");

    private final BulletEntityModel modelDefault;
    private final BrimstoneEntityModel modelBrimstone;
    private final JoyeuseEntityModel modelJoyeuse;
    private final BoomerangEntityModel modelBoomerang;
    public MissileEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.modelDefault = new BulletEntityModel(ctx.getPart(ModEntityModelLayers.BULLET));
        this.modelBrimstone = new BrimstoneEntityModel(ctx.getPart(ModEntityModelLayers.BRIMSTONE));
        this.modelJoyeuse=new JoyeuseEntityModel(ctx.getPart(ModEntityModelLayers.JOYEUSE));
        this.modelBoomerang=new BoomerangEntityModel(ctx.getPart(ModEntityModelLayers.BOOMERANG));
    }

    @Override
    public void render(MissileEntity entity, float yaw, float tickDelta,
                       MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        boolean renderRed = (entity.getOwner() != null && !(entity.getOwner() instanceof PlayerEntity))||
                (entity.getOwner()!=null&&(entity.getOwner() instanceof MissileEntity)&&(!(((MissileEntity)entity.getOwner()).getOwner() instanceof PlayerEntity)));
        float red =1.0f;
        float green = renderRed ? 0.0f : 1.0f;
        float blue = renderRed ? 0.0f : 1.0f;

        if (entity.getMissileType() == MissileModelType.BRIMSTONE) {
            this.modelBrimstone.setAngles(entity, 0, 0, entity.age, yaw, entity.getPitch());
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
        else {
            this.modelDefault.setAngles(entity, 0, 0, entity.age, yaw, entity.getPitch());
            var vertexConsumer = vertexConsumers.getBuffer(
                    modelDefault.getLayer(TEXTURE_DEFAULT));
            modelDefault.render(matrices, vertexConsumer, light,
                    OverlayTexture.DEFAULT_UV, red, green, blue, 1.0f);
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
