// Made with Blockbench 5.1.6
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package com.nekoadventure.entity.model.missile;

import com.nekoadventure.entity.missile.MissileEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class BoomerangEntityModel extends SinglePartEntityModel<MissileEntity> {
	private final ModelPart main;
	public BoomerangEntityModel(ModelPart root) {
		this.main = root.getChild("main");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.of(-1.3F, 24.0F, 1.5F, 0.0F, 2.7053F, 0.0F));

		ModelPartData cube_r1 = main.addChild("cube_r1", ModelPartBuilder.create().uv(0, 3).cuboid(-7.06F, -2.001F, -0.97F, 8.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(1.35F, 1.0F, 0.25F, 0.0F, 1.5708F, 0.0F));

		ModelPartData cube_r2 = main.addChild("cube_r2", ModelPartBuilder.create().uv(0, 0).cuboid(-9.0F, -2.0F, -1.0F, 10.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 1.0F, -1.0F, 0.0F, 0.5672F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
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
		return this.main;
	}

	@Override
	public void setAngles(MissileEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
	}
}