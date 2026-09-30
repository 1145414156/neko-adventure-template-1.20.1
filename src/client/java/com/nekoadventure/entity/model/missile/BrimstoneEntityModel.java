
package com.nekoadventure.entity.model.missile;

import com.nekoadventure.entity.missile.MissileEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleTypes;


//其实这个是有一个模型的，但是我觉得他变成透明的还挺不错，所以就保留了
public class BrimstoneEntityModel extends SinglePartEntityModel<MissileEntity> {
	private final ModelPart main;
	public BrimstoneEntityModel(ModelPart root) {
		this.main = root.getChild("main");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("main", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}


	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return this.main;
	}

	@Override
	public void setAngles(MissileEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.main.yaw = entity.getYaw();
		this.main.pitch = entity.getPitch();
	}
}