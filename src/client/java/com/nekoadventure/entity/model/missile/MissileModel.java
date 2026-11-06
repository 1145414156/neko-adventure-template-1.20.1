package com.nekoadventure.entity.model.missile;

import com.nekoadventure.entity.missile.MissileEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class MissileModel extends SinglePartEntityModel<MissileEntity> {
	private final ModelPart main;
	private final ModelPart missile;
	private final ModelPart missile_bone;
	private final ModelPart seeker;
	private final ModelPart octagon;
	public MissileModel(ModelPart root) {
		this.main = root.getChild("main");
		this.missile = this.main.getChild("missile");
		this.missile_bone = this.missile.getChild("missile_bone");
		this.seeker = this.missile_bone.getChild("seeker");
		this.octagon = this.main.getChild("octagon");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData missile = main.addChild("missile", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData missile_bone = missile.addChild("missile_bone", ModelPartBuilder.create().uv(40, 28).cuboid(0.0F, -6.0F, -2.0F, 1.0F, 5.0F, 4.0F, new Dilation(0.0F))
		.uv(0, 28).cuboid(0.0F, -28.0F, -2.0F, 1.0F, 9.0F, 4.0F, new Dilation(0.0F))
		.uv(10, 28).cuboid(0.0F, -28.0F, 2.0F, 1.0F, 9.0F, 4.0F, new Dilation(0.0F))
		.uv(40, 37).cuboid(0.0F, -6.0F, 2.0F, 1.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.5F, -0.5F, -2.0F));

		ModelPartData cube_r1 = missile_bone.addChild("cube_r1", ModelPartBuilder.create().uv(20, 50).cuboid(0.0F, -5.0F, -3.0F, 1.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(2.7793F, -25.5425F, 1.5F, -0.9599F, -1.5708F, 0.0F));

		ModelPartData cube_r2 = missile_bone.addChild("cube_r2", ModelPartBuilder.create().uv(30, 41).cuboid(0.0F, -5.0F, -3.0F, 1.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(2.7793F, -3.5425F, 1.5F, -0.9599F, -1.5708F, 0.0F));

		ModelPartData cube_r3 = missile_bone.addChild("cube_r3", ModelPartBuilder.create().uv(50, 28).cuboid(0.0F, -5.0F, -3.0F, 1.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, -27.1808F, 1.4264F, 0.9599F, -1.5708F, 0.0F));

		ModelPartData cube_r4 = missile_bone.addChild("cube_r4", ModelPartBuilder.create().uv(20, 41).cuboid(0.0F, -5.0F, -3.0F, 1.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, -5.1809F, 1.4264F, 0.9599F, -1.5708F, 0.0F));

		ModelPartData cube_r5 = missile_bone.addChild("cube_r5", ModelPartBuilder.create().uv(10, 50).cuboid(0.0F, -5.0F, -3.0F, 1.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -27.1808F, 5.4264F, 0.9599F, 0.0F, 0.0F));

		ModelPartData cube_r6 = missile_bone.addChild("cube_r6", ModelPartBuilder.create().uv(10, 41).cuboid(0.0F, -5.0F, -3.0F, 1.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -5.1809F, 5.4264F, 0.9599F, 0.0F, 0.0F));

		ModelPartData cube_r7 = missile_bone.addChild("cube_r7", ModelPartBuilder.create().uv(30, 50).cuboid(0.0F, -5.0F, -3.0F, 1.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -25.5425F, -0.2793F, -0.9599F, 0.0F, 0.0F));

		ModelPartData cube_r8 = missile_bone.addChild("cube_r8", ModelPartBuilder.create().uv(0, 41).cuboid(0.0F, -5.0F, -3.0F, 1.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.5425F, -0.2793F, -0.9599F, 0.0F, 0.0F));

		ModelPartData cube_r9 = missile_bone.addChild("cube_r9", ModelPartBuilder.create().uv(0, 50).cuboid(0.0F, -5.0F, -3.0F, 1.0F, 5.0F, 4.0F, new Dilation(0.0F))
		.uv(20, 28).cuboid(0.0F, -27.0F, -3.0F, 1.0F, 9.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -1.0F, 2.5F, 0.0F, 1.5708F, 0.0F));

		ModelPartData cube_r10 = missile_bone.addChild("cube_r10", ModelPartBuilder.create().uv(40, 46).cuboid(0.0F, -5.0F, -3.0F, 1.0F, 5.0F, 4.0F, new Dilation(0.0F))
		.uv(30, 28).cuboid(0.0F, -27.0F, -3.0F, 1.0F, 9.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.5F, -1.0F, 2.5F, 0.0F, 1.5708F, 0.0F));

		ModelPartData seeker = missile_bone.addChild("seeker", ModelPartBuilder.create(), ModelTransform.pivot(0.5F, -2.5F, 2.0F));

		ModelPartData octagon = main.addChild("octagon", ModelPartBuilder.create().uv(0, 7).cuboid(-40.5F, -1.0355F, -2.5F, 41.0F, 2.0711F, 5.0F, new Dilation(0.0F))
		.uv(0, 21).cuboid(-40.5F, -2.5F, -1.0355F, 41.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		ModelPartData cube_r11 = octagon.addChild("cube_r11", ModelPartBuilder.create().uv(50, 41).cuboid(-5.8605F, -2.0F, -0.3706F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-38.1252F, 0.9645F, 1.8095F, 0.0F, -0.4189F, 0.0F));

		ModelPartData cube_r12 = octagon.addChild("cube_r12", ModelPartBuilder.create().uv(50, 49).cuboid(-3.7193F, 0.0F, -0.6816F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-40.0111F, -0.5405F, 0.2829F, 3.1416F, 0.0F, -0.384F));

		ModelPartData cube_r13 = octagon.addChild("cube_r13", ModelPartBuilder.create().uv(50, 45).cuboid(-3.7193F, 0.0F, -0.6816F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-40.7544F, 2.3814F, 0.2829F, 3.1416F, 0.0F, 0.384F));

		ModelPartData cube_r14 = octagon.addChild("cube_r14", ModelPartBuilder.create().uv(50, 37).cuboid(-4.4705F, -2.0F, -1.8779F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-39.28F, 0.9645F, -0.9969F, 0.0F, 0.4102F, 0.0F));

		ModelPartData octagon_r1 = octagon.addChild("octagon_r1", ModelPartBuilder.create().uv(0, 14).cuboid(-40.5F, -2.5F, -1.0355F, 41.0F, 5.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-40.5F, -1.0355F, -2.5F, 41.0F, 2.0711F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public ModelPart getPart() {
		return this.main;
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		matrices.push();
		matrices.translate(0.0, -0.6, 0.0);
		this.main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		matrices.pop();
	}

	@Override
	public void setAngles(MissileEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
	}
}