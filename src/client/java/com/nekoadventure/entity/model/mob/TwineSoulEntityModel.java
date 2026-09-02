
package com.nekoadventure.entity.model.mob;

import com.nekoadventure.entity.mob.TwineSoulEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class TwineSoulEntityModel extends SinglePartEntityModel<TwineSoulEntity> {
	private final ModelPart main;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart bone;
	private final ModelPart bone3;
	private final ModelPart bone2;
	private final ModelPart leg;
	public TwineSoulEntityModel(ModelPart root) {
		this.main = root.getChild("main");
		this.head = this.main.getChild("head");
		this.body = this.main.getChild("body");
		this.bone = this.body.getChild("bone");
		this.bone3 = this.body.getChild("bone3");
		this.bone2 = this.body.getChild("bone2");
		this.leg = this.main.getChild("leg");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(-0.5924F, 16.3278F, -0.192F));

		ModelPartData head = main.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.3152F, -14.3445F, -0.384F));

		ModelPartData cube_r1 = head.addChild("cube_r1", ModelPartBuilder.create().uv(0, 18).cuboid(-3.0F, -1.7652F, -4.113F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -2.7175F, 1.5867F, -0.781F, 0.0F, 0.0F));

		ModelPartData body = main.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(-0.1576F, 7.6722F, 0.192F));

		ModelPartData cube_r2 = body.addChild("cube_r2", ModelPartBuilder.create().uv(12, 53).cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0267F, -19.9234F, 2.1251F, 0.2659F, -0.0016F, 0.0046F));

		ModelPartData cube_r3 = body.addChild("cube_r3", ModelPartBuilder.create().uv(20, 30).cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -15.0073F, 2.1186F, -0.2228F, 0.0012F, 0.0047F));

		ModelPartData cube_r4 = body.addChild("cube_r4", ModelPartBuilder.create().uv(0, 53).cuboid(-0.4853F, -3.4631F, -0.446F, 1.0F, 13.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.0461F, -9.5434F, 0.169F, -0.3406F, 0.0012F, 0.0047F));

		ModelPartData cube_r5 = body.addChild("cube_r5", ModelPartBuilder.create().uv(16, 50).cuboid(-0.5309F, -3.3672F, -0.4215F, 1.0F, 13.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -9.6573F, 1.3436F, -0.0352F, -0.0001F, 0.0049F));

		ModelPartData bone = body.addChild("bone", ModelPartBuilder.create(), ModelTransform.pivot(0.4523F, -16.2454F, -0.3017F));

		ModelPartData cube_r6 = bone.addChild("cube_r6", ModelPartBuilder.create().uv(42, 54).cuboid(-0.5F, -0.5F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(3.0434F, 0.043F, 2.1484F, 1.6361F, 1.5659F, -3.1196F));

		ModelPartData cube_r7 = bone.addChild("cube_r7", ModelPartBuilder.create().uv(58, 52).cuboid(-0.5435F, -0.8774F, -0.6138F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.5524F, 0.3679F, -2.111F, 0.6679F, -1.235F, -2.183F));

		ModelPartData cube_r8 = bone.addChild("cube_r8", ModelPartBuilder.create().uv(58, 5).cuboid(-0.5F, -2.5F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(3.4024F, -0.1321F, -0.086F, 1.3897F, -0.431F, -3.075F));

		ModelPartData cube_r9 = bone.addChild("cube_r9", ModelPartBuilder.create().uv(20, 58).cuboid(-0.7297F, -2.5089F, -0.543F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.8274F, 0.0429F, 1.664F, 1.6139F, 0.0389F, 3.1379F));

		ModelPartData cube_r10 = bone.addChild("cube_r10", ModelPartBuilder.create().uv(38, 54).cuboid(-0.5F, -0.5F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-4.198F, 0.443F, 1.4984F, 1.0024F, -1.3267F, -2.5431F));

		ModelPartData cube_r11 = bone.addChild("cube_r11", ModelPartBuilder.create().uv(24, 58).cuboid(-0.5F, -3.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-4.148F, 0.568F, 2.1484F, 1.6256F, 0.0914F, -3.0102F));

		ModelPartData cube_r12 = bone.addChild("cube_r12", ModelPartBuilder.create().uv(28, 58).cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-4.323F, 0.518F, -0.5266F, 1.8147F, 1.038F, -2.8686F));

		ModelPartData bone3 = body.addChild("bone3", ModelPartBuilder.create(), ModelTransform.of(-0.3676F, -20.2279F, 0.7307F, 0.3927F, 0.0F, 0.0F));

		ModelPartData cube_r13 = bone3.addChild("cube_r13", ModelPartBuilder.create().uv(8, 57).cuboid(-0.5672F, 0.4993F, -0.5232F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(3.8633F, 0.5255F, 1.3661F, 0.0791F, 1.4356F, 1.6069F));

		ModelPartData cube_r14 = bone3.addChild("cube_r14", ModelPartBuilder.create().uv(58, 48).cuboid(-0.5127F, -2.4335F, -0.5218F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.8223F, 0.4754F, 2.2067F, 1.4114F, 0.1901F, 3.1184F));

		ModelPartData cube_r15 = bone3.addChild("cube_r15", ModelPartBuilder.create().uv(4, 57).cuboid(-0.4303F, 0.4997F, -0.5143F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.1282F, 0.5005F, 1.3661F, 0.0612F, -1.3047F, -1.5893F));

		ModelPartData cube_r16 = bone3.addChild("cube_r16", ModelPartBuilder.create().uv(58, 44).cuboid(-0.527F, -2.2818F, -0.3674F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.1282F, 0.5005F, 1.3661F, 1.491F, -0.0658F, -3.0098F));

		ModelPartData bone2 = body.addChild("bone2", ModelPartBuilder.create(), ModelTransform.pivot(0.9081F, -12.2454F, -1.1501F));

		ModelPartData cube_r17 = bone2.addChild("cube_r17", ModelPartBuilder.create().uv(58, 52).cuboid(-0.5435F, -0.8774F, -0.6138F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.0034F, 0.2929F, -0.7376F, -2.1304F, -1.3201F, 0.791F));

		ModelPartData cube_r18 = bone2.addChild("cube_r18", ModelPartBuilder.create().uv(58, 52).cuboid(-0.5435F, -0.8774F, -0.6138F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.6466F, 0.6179F, 0.9124F, -1.5353F, 0.0725F, 0.1033F));

		ModelPartData cube_r19 = bone2.addChild("cube_r19", ModelPartBuilder.create().uv(54, 54).cuboid(-0.5F, -0.5F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.5876F, 0.618F, 1.6718F, 2.5203F, 1.2536F, -2.1858F));

		ModelPartData cube_r20 = bone2.addChild("cube_r20", ModelPartBuilder.create().uv(50, 54).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.0381F, 0.528F, -0.9608F, 1.47F, -1.0016F, -2.9719F));

		ModelPartData cube_r21 = bone2.addChild("cube_r21", ModelPartBuilder.create().uv(46, 54).cuboid(-0.5F, -0.5F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-4.4038F, 0.043F, 1.9968F, 2.2388F, -1.5164F, 2.8206F));

		ModelPartData cube_r22 = bone2.addChild("cube_r22", ModelPartBuilder.create().uv(32, 58).cuboid(0.0888F, -1.5781F, -0.9473F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.6538F, 0.693F, 0.7468F, 1.7057F, 0.2735F, -2.8012F));

		ModelPartData leg = main.addChild("leg", ModelPartBuilder.create().uv(24, 28).cuboid(-2.0F, -2.0F, -4.0F, 3.0F, 2.0F, 8.0F, new Dilation(0.0F))
		.uv(40, 44).cuboid(-3.0F, -2.0F, -4.0F, 1.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.1576F, 7.6722F, 0.192F));

		ModelPartData cube_r23 = leg.addChild("cube_r23", ModelPartBuilder.create().uv(4, 53).cuboid(-2.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.0F, 1.0F, 2.5169F, 0.2097F, 0.2811F));

		ModelPartData cube_r24 = leg.addChild("cube_r24", ModelPartBuilder.create().uv(52, 0).cuboid(-2.0F, -2.0F, -2.0F, 5.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(38, 48).cuboid(3.0F, -2.0F, -2.0F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8.0F, 2.0F, 1.986F, 0.3215F, 0.1384F));

		ModelPartData cube_r25 = leg.addChild("cube_r25", ModelPartBuilder.create().uv(50, 8).cuboid(0.0F, -2.0F, -1.0F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8.0F, 2.0F, 1.986F, 0.3215F, 0.7493F));

		ModelPartData cube_r26 = leg.addChild("cube_r26", ModelPartBuilder.create().uv(28, 9).cuboid(-2.0F, -2.0F, -7.0F, 3.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.5F, -2.0F, 3.0084F, 0.2261F, 2.6029F));

		ModelPartData cube_r27 = leg.addChild("cube_r27", ModelPartBuilder.create().uv(1, 41).cuboid(-1.2716F, -0.4959F, -4.219F, 2.0F, 2.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-2.625F, -3.65F, -2.55F, 0.7089F, -0.6642F, 0.4353F));

		ModelPartData cube_r28 = leg.addChild("cube_r28", ModelPartBuilder.create().uv(19, 38).cuboid(-3.0F, -3.0F, -7.0F, 3.0F, 3.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, -3.1F, 3.0F, 0.0F, 0.0F, 0.5236F));

		ModelPartData cube_r29 = leg.addChild("cube_r29", ModelPartBuilder.create().uv(40, 38).cuboid(-2.0F, -2.0F, -6.0F, 7.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.8F, -7.875F, 2.0F, 1.986F, 0.3215F, 0.1384F));

		ModelPartData cube_r30 = leg.addChild("cube_r30", ModelPartBuilder.create().uv(0, 9).cuboid(-2.0F, -2.0F, -6.0F, 7.0F, 2.0F, 7.0F, new Dilation(0.0F))
		.uv(50, 12).cuboid(-2.6F, -2.0F, -6.0F, 7.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.4F, -3.0F, 3.0F, 0.48F, 0.0F, 0.0F));

		ModelPartData cube_r31 = leg.addChild("cube_r31", ModelPartBuilder.create().uv(30, 0).cuboid(1.0F, -2.0F, -6.0F, 4.0F, 1.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(2.575F, -2.225F, -2.6F, 2.373F, 0.5975F, 2.2623F));

		ModelPartData cube_r32 = leg.addChild("cube_r32", ModelPartBuilder.create().uv(52, 3).cuboid(-1.6381F, -0.8233F, -0.3812F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-4.575F, -2.2F, -0.25F, 2.6051F, 0.2751F, 2.4424F));

		ModelPartData cube_r33 = leg.addChild("cube_r33", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -1.0F, -7.0F, 7.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-1.6F, -2.5F, -0.825F, -2.8606F, -0.4478F, 2.5536F));

		ModelPartData cube_r34 = leg.addChild("cube_r34", ModelPartBuilder.create().uv(20, 48).cuboid(0.0F, -2.0F, -7.0F, 1.0F, 2.0F, 8.0F, new Dilation(0.0F))
		.uv(0, 30).cuboid(3.0F, -2.0F, -7.0F, 2.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -2.5F, 3.0F, 0.2546F, 0.4114F, 0.577F));

		ModelPartData cube_r35 = leg.addChild("cube_r35", ModelPartBuilder.create().uv(46, 28).cuboid(1.0F, -2.0F, -7.0F, 1.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(2.8F, -1.5F, 5.0F, -0.1564F, -0.2635F, 0.5444F));

		ModelPartData cube_r36 = leg.addChild("cube_r36", ModelPartBuilder.create().uv(46, 18).cuboid(2.0F, -2.0F, -7.0F, 1.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, -1.5F, 6.0F, -0.4547F, 0.6708F, 0.1969F));

		ModelPartData cube_r37 = leg.addChild("cube_r37", ModelPartBuilder.create().uv(24, 18).cuboid(2.0F, -2.0F, -7.0F, 3.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -2.75F, -0.325F, 0.3927F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
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
	public void setAngles(TwineSoulEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		float yaw = entity.getYaw();
		float pitch = entity.getPitch();

		this.head.yaw = yaw * 0.017453292F;
		this.head.pitch = pitch * 0.017453292F;
	}
}