
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
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -0.5F, -8.0F, 2.0F, 1.0F, 10.0F, new Dilation(0.0F))
				.uv(12, 11).cuboid(-2.0F, -1.0F, 2.0F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 11).cuboid(-1.0F, -0.5F, 4.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}


	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		matrices.push();
		matrices.translate(0, -1.3, 0);
		main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		matrices.pop();
	}

	@Override
	public ModelPart getPart() {
		return this.main;
	}

	@Override
	public void setAngles(MissileEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
	}
}