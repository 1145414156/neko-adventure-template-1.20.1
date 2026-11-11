package com.nekoadventure.entity.model.boss;

import com.nekoadventure.entity.boss.SandWormBodyEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class SandWormBodyEntityModel extends SinglePartEntityModel<SandWormBodyEntity> {
	private final ModelPart main;
	public SandWormBodyEntityModel(ModelPart root) {
		this.main = root.getChild("main");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -14.0F, -8.0F, 14.0F, 14.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public ModelPart getPart() {
		return this.main;
	}

	@Override
	public void setAngles(SandWormBodyEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay,
	                   float red, float green, float blue, float alpha) {
		matrices.push();
		matrices.translate(0.0, -2.5, 0.0);
		matrices.scale(3.0f, 3.0f, 3.0f);
		main.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		matrices.pop();
	}
}
