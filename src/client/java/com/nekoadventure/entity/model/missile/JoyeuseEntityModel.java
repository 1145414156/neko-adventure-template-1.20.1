
package com.nekoadventure.entity.model.missile;

import com.nekoadventure.entity.missile.MissileEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class JoyeuseEntityModel extends SinglePartEntityModel<MissileEntity> {
	private final ModelPart main;
	public JoyeuseEntityModel(ModelPart root) {
		this.main = root.getChild("main");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create().uv(0, 19).cuboid(-5.0F, -1.0F, 0.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(2, 6).cuboid(-1.7F, -1.0F, 0.0F, 14.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 24.0F, 0.0F));
		ModelPartData cube_r1 = main.addChild("cube_r1", ModelPartBuilder.create().uv(0, 19).cuboid(-1.0F, -1.01F, 0.0F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -0.5F, 0.5F, 0.0F, -0.6109F, 0.0F));
		ModelPartData cube_r2 = main.addChild("cube_r2", ModelPartBuilder.create().uv(0, 19).cuboid(-1.0F, -1.0099F, -1.0F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -0.5F, 0.5F, 0.0F, 0.6109F, 0.0F));
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

        this.main.yaw = headYaw * (float) (Math.PI / 180.0);
		this.main.pitch = headPitch * (float) (Math.PI / 180.0);
	}
}