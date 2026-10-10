package com.nekoadventure.entity.model.particle;

import com.nekoadventure.entity.particle.AbstractParticleEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class ParticleTextEntityModel extends SinglePartEntityModel<AbstractParticleEntity> {
	private final ModelPart main;
	public ParticleTextEntityModel(ModelPart root) {
		this.main = root.getChild("main");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, 0.0F, -8.0F, 8.0F, 8.0F, 16.0F, new Dilation(0.0F))
		.uv(0, 24).cuboid(-4.0F, 0.0F, -8.0F, 8.0F, 8.0F, 16.0F, new Dilation(-0.1F)), ModelTransform.pivot(0.0F, 16.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		matrices.push();
		matrices.translate(0, -1.5, 0);
		main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		matrices.pop();
	}

	@Override
	public ModelPart getPart() {
		return main;
	}

	@Override
	public void setAngles(AbstractParticleEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.main.yaw = -headYaw * (float) (Math.PI / 180.0);
		this.main.pitch = headPitch * (float) (Math.PI / 180.0);
	}
}