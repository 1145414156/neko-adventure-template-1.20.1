// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

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
        modelPartData.addChild("main", ModelPartBuilder.create().uv(1, 13).cuboid(-8.0F, -2.0F, -1.0F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(12, 12).cuboid(-3.0F, -3.0F, -1.0F, 2.0F, 5.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.0F, 0.0F, -1.0F, 14.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 8).cuboid(-1.0F, 0.0F, -1.0F, 14.0F, 0.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 10).cuboid(-1.0F, -1.0F, 0.0F, 14.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 19).cuboid(-6.0F, -1.0F, 0.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 4).cuboid(-1.0F, -2.0F, -1.0F, 14.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0F, 8.0F, 0.0F));
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