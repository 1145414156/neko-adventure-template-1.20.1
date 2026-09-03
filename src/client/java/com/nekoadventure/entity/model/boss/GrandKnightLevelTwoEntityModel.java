package com.nekoadventure.entity.model.boss;

import com.nekoadventure.entity.animation.boss.GrandKnightEntityModelAnimation;
import com.nekoadventure.entity.animation.boss.GrandKnightLevelTwoEntityModelAnimation;
import com.nekoadventure.entity.boss.GrandKnightEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class GrandKnightLevelTwoEntityModel extends SinglePartEntityModel<GrandKnightEntity> {
	private final ModelPart main;
	private final ModelPart a;
	private final ModelPart leg;
	private final ModelPart rightLeg;
	private final ModelPart rightLeg1;
	private final ModelPart leftLeg;
	private final ModelPart leftLeg1;
	private final ModelPart body;
	private final ModelPart hand;
	private final ModelPart leftHand;
	private final ModelPart leftHand1;
	private final ModelPart leftHandItem;
	private final ModelPart rightHand;
	private final ModelPart rightHand1;
	private final ModelPart rightHandItem;
	private final ModelPart waist;
	private final ModelPart head;
	private final ModelPart headLayer;
	private final ModelPart layer;
	private final ModelPart chest;
	private final ModelPart leftChest;
	private final ModelPart leftChest1;
	private final ModelPart rightChest;
	private final ModelPart rightChest1;
	public GrandKnightLevelTwoEntityModel(ModelPart root) {
		this.main = root.getChild("main");
		this.a = this.main.getChild("a");
		this.leg = this.a.getChild("leg");
		this.rightLeg = this.leg.getChild("rightLeg");
		this.rightLeg1 = this.rightLeg.getChild("rightLeg1");
		this.leftLeg = this.leg.getChild("leftLeg");
		this.leftLeg1 = this.leftLeg.getChild("leftLeg1");
		this.body = this.a.getChild("body");
		this.hand = this.body.getChild("hand");
		this.leftHand = this.hand.getChild("leftHand");
		this.leftHand1 = this.leftHand.getChild("leftHand1");
		this.leftHandItem = this.leftHand1.getChild("leftHandItem");
		this.rightHand = this.hand.getChild("rightHand");
		this.rightHand1 = this.rightHand.getChild("rightHand1");
		this.rightHandItem = this.rightHand1.getChild("rightHandItem");
		this.waist = this.body.getChild("waist");
		this.head = this.waist.getChild("head");
		this.headLayer = this.head.getChild("headLayer");
		this.layer = this.waist.getChild("layer");
		this.chest = this.waist.getChild("chest");
		this.leftChest = this.chest.getChild("leftChest");
		this.leftChest1 = this.leftChest.getChild("leftChest1");
		this.rightChest = this.chest.getChild("rightChest");
		this.rightChest1 = this.rightChest.getChild("rightChest1");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData a = main.addChild("a", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData leg = a.addChild("leg", ModelPartBuilder.create().uv(140, 18).cuboid(-7.0F, -0.95F, -3.5F, 14.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -24.0F, -1.0F));

		ModelPartData rightLeg = leg.addChild("rightLeg", ModelPartBuilder.create().uv(30, 164).cuboid(-2.425F, 0.0F, -3.5F, 7.0F, 9.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, 2.0F, 0.5F));

		ModelPartData rightLeg1 = rightLeg.addChild("rightLeg1", ModelPartBuilder.create().uv(146, 180).cuboid(-2.0F, -1.0F, -2.5F, 5.0F, 10.0F, 5.0F, new Dilation(0.0F))
				.uv(146, 168).cuboid(-2.0F, 10.0F, -2.25F, 5.0F, 2.0F, 10.0F, new Dilation(0.0F))
				.uv(174, 146).cuboid(-2.0F, 9.0F, -2.5F, 5.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 10.0F, 0.0F));

		ModelPartData cube_r1 = rightLeg1.addChild("cube_r1", ModelPartBuilder.create().uv(194, 62).cuboid(-1.999F, -2.0F, -2.0F, 5.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 11.15F, 2.85F, -0.2618F, 0.0F, 0.0F));

		ModelPartData cube_r2 = rightLeg1.addChild("cube_r2", ModelPartBuilder.create().uv(118, 164).cuboid(-2.999F, -0.5F, -2.0F, 5.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 10.25F, -2.25F, -1.2217F, 0.0F, 0.0F));

		ModelPartData leftLeg = leg.addChild("leftLeg", ModelPartBuilder.create().uv(166, 53).cuboid(-4.575F, 0.0F, -3.5F, 7.0F, 9.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 2.0F, 0.5F));

		ModelPartData leftLeg1 = leftLeg.addChild("leftLeg1", ModelPartBuilder.create().uv(182, 69).cuboid(-3.0F, -1.0F, -2.5F, 5.0F, 10.0F, 5.0F, new Dilation(0.0F))
				.uv(168, 151).cuboid(-3.0F, 10.0F, -2.25F, 5.0F, 2.0F, 10.0F, new Dilation(0.0F))
				.uv(0, 201).cuboid(-3.0F, 9.0F, -2.5F, 5.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 10.0F, 0.0F));

		ModelPartData cube_r3 = leftLeg1.addChild("cube_r3", ModelPartBuilder.create().uv(198, 146).cuboid(-3.001F, -2.0F, -2.0F, 5.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 11.15F, 2.85F, -0.2618F, 0.0F, 0.0F));

		ModelPartData cube_r4 = leftLeg1.addChild("cube_r4", ModelPartBuilder.create().uv(202, 0).cuboid(-2.001F, -0.5F, -2.0F, 5.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 10.25F, -2.25F, -1.2217F, 0.0F, 0.0F));

		ModelPartData body = a.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -22.4F, -1.0F));

		ModelPartData hand = body.addChild("hand", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 4.0F, 0.0F));

		ModelPartData leftHand = hand.addChild("leftHand", ModelPartBuilder.create().uv(122, 92).cuboid(-7.0F, -0.7F, -6.3F, 11.0F, 9.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(-15.75F, -27.0F, 0.5F, 0.9581F, 0.3644F, -0.1582F));

		ModelPartData cube_r5 = leftHand.addChild("cube_r5", ModelPartBuilder.create().uv(134, 133).cuboid(-2.13F, -6.9886F, -2.0086F, 10.0F, 8.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(-0.49F, 10.3066F, -3.0F, 0.0283F, 0.0405F, -1.4406F));

		ModelPartData cube_r6 = leftHand.addChild("cube_r6", ModelPartBuilder.create().uv(118, 154).cuboid(0.5F, -0.5F, -3.01F, 2.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-7.375F, -5.125F, 0.5F, 0.0F, 0.0F, 0.0873F));

		ModelPartData cube_r7 = leftHand.addChild("cube_r7", ModelPartBuilder.create().uv(140, 28).cuboid(-2.5F, -0.5F, -3.01F, 5.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -1.2F, 0.5F, 0.0F, 0.0F, -0.0436F));

		ModelPartData leftHand1 = leftHand.addChild("leftHand1", ModelPartBuilder.create().uv(122, 113).cuboid(-3.7367F, -3.2984F, -1.7123F, 1.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.1164F, 14.2862F, 0.2F, 0.1055F, 0.3306F, -0.4772F));

		ModelPartData cube_r8 = leftHand1.addChild("cube_r8", ModelPartBuilder.create().uv(188, 98).cuboid(1.9225F, -2.7707F, -1.4506F, 5.0F, 6.0F, 5.0F, new Dilation(0.0F))
				.uv(176, 28).cuboid(-1.9325F, -3.5043F, -2.8434F, 5.0F, 6.0F, 8.0F, new Dilation(0.0F))
				.uv(0, 171).cuboid(-0.9325F, -8.5043F, -1.8434F, 5.0F, 14.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-3.281F, 8.12F, -0.8698F, 0.0071F, -0.0466F, -0.2132F));

		ModelPartData cube_r9 = leftHand1.addChild("cube_r9", ModelPartBuilder.create().uv(44, 187).cuboid(0.6442F, -6.4885F, -0.8998F, 5.0F, 10.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-3.281F, 8.12F, -0.8698F, -0.004F, -0.047F, 0.0227F));

		ModelPartData leftHandItem = leftHand1.addChild("leftHandItem", ModelPartBuilder.create(), ModelTransform.of(-2.0F, 11.0F, 0.5F, 0.0F, 0.0F, -0.4363F));

		ModelPartData cube_r10 = leftHandItem.addChild("cube_r10", ModelPartBuilder.create().uv(40, 201).cuboid(4.7434F, 1.7457F, -2.9325F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-1.281F, -2.88F, -1.3698F, 0.1502F, 1.5237F, -0.0629F));

		ModelPartData cube_r11 = leftHandItem.addChild("cube_r11", ModelPartBuilder.create().uv(44, 180).cuboid(6.1566F, 1.7457F, -2.0675F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-1.281F, -2.88F, -1.3698F, 2.9914F, -1.5237F, 3.0787F));

		ModelPartData cube_r12 = leftHandItem.addChild("cube_r12", ModelPartBuilder.create().uv(78, 164).cuboid(-0.5F, -17.0F, -1.0F, 1.0F, 34.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.4913F, 0.449F, 11.2577F, 0.014F, 1.0442F, -0.2007F));

		ModelPartData cube_r13 = leftHandItem.addChild("cube_r13", ModelPartBuilder.create().uv(70, 0).cuboid(-11.7233F, -3.9325F, -17.8434F, 1.0F, 2.0F, 34.0F, new Dilation(0.0F)), ModelTransform.of(0.344F, 4.67F, -4.7948F, 0.0466F, 0.007F, -1.7837F));

		ModelPartData cube_r14 = leftHandItem.addChild("cube_r14", ModelPartBuilder.create().uv(0, 66).cuboid(-6.2543F, 1.9325F, -17.8434F, 1.0F, 2.0F, 34.0F, new Dilation(0.0F)), ModelTransform.of(-2.981F, -10.605F, -4.7948F, -0.0466F, -0.007F, 1.3579F));

		ModelPartData cube_r15 = leftHandItem.addChild("cube_r15", ModelPartBuilder.create().uv(48, 121).cuboid(-3.929F, 16.04F, -12.1096F, 2.0F, 1.0F, 23.0F, new Dilation(0.0F)), ModelTransform.of(-0.681F, -3.305F, -18.3697F, -0.7783F, -0.0466F, -0.2132F));

		ModelPartData cube_r16 = leftHandItem.addChild("cube_r16", ModelPartBuilder.create().uv(116, 53).cuboid(-3.939F, -12.0975F, -16.2521F, 2.0F, 1.0F, 23.0F, new Dilation(0.0F)), ModelTransform.of(-0.781F, -3.705F, -18.7697F, 0.7925F, -0.0466F, -0.2132F));

		ModelPartData cube_r17 = leftHandItem.addChild("cube_r17", ModelPartBuilder.create().uv(70, 36).cuboid(-3.9278F, -14.8975F, -18.9521F, 1.0F, 22.0F, 22.0F, new Dilation(0.0F)), ModelTransform.of(-0.956F, -3.305F, -14.3697F, 0.7925F, -0.0466F, -0.2132F));

		ModelPartData cube_r18 = leftHandItem.addChild("cube_r18", ModelPartBuilder.create().uv(0, 0).cuboid(-3.9325F, -13.2543F, -17.8434F, 1.0F, 32.0F, 34.0F, new Dilation(0.0F)), ModelTransform.of(-1.281F, -2.88F, -4.7948F, 0.0071F, -0.0466F, -0.2132F));

		ModelPartData cube_r19 = leftHandItem.addChild("cube_r19", ModelPartBuilder.create().uv(176, 177).cuboid(0.0675F, 1.7457F, -4.8434F, 2.0F, 2.0F, 11.0F, new Dilation(0.0F)), ModelTransform.of(-1.281F, -2.88F, -1.3698F, 0.0071F, -0.0466F, -0.2132F));

		ModelPartData rightHand = hand.addChild("rightHand", ModelPartBuilder.create(), ModelTransform.of(12.25F, -27.1F, -2.5F, 0.0F, 0.0F, 0.0873F));

		ModelPartData cube_r20 = rightHand.addChild("cube_r20", ModelPartBuilder.create().uv(140, 0).cuboid(-7.87F, -6.9886F, -2.0086F, 10.0F, 8.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(3.9046F, 9.1322F, -3.7731F, 0.0283F, -0.0405F, 1.4406F));

		ModelPartData cube_r21 = rightHand.addChild("cube_r21", ModelPartBuilder.create().uv(166, 190).cuboid(-2.5F, -0.5F, -3.01F, 5.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(2.9146F, -1.6744F, -0.2731F, 0.0F, 0.0F, 0.0436F));

		ModelPartData cube_r22 = rightHand.addChild("cube_r22", ModelPartBuilder.create().uv(0, 134).cuboid(-2.0F, 0.0F, -6.0F, 11.0F, 9.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(1.4146F, -1.4494F, -1.0731F, 0.0F, 0.0F, -0.3491F));

		ModelPartData rightHand1 = rightHand.addChild("rightHand1", ModelPartBuilder.create().uv(166, 180).cuboid(-1.0884F, -2.5161F, -1.6989F, 1.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(7.031F, 12.7618F, -0.5731F, 0.218F, 0.0094F, -0.1299F));

		ModelPartData cube_r23 = rightHand1.addChild("cube_r23", ModelPartBuilder.create().uv(62, 187).cuboid(-1.0F, -2.0F, -5.25F, 2.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(1.9391F, 5.7169F, 0.3034F, 1.5312F, 0.0184F, 0.2614F));

		ModelPartData cube_r24 = rightHand1.addChild("cube_r24", ModelPartBuilder.create().uv(174, 126).cuboid(-2.3766F, -0.6851F, -1.8197F, 5.0F, 14.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-0.5441F, 0.5774F, -0.8564F, 0.0071F, 0.0466F, 0.2132F));

		ModelPartData rightHandItem = rightHand1.addChild("rightHandItem", ModelPartBuilder.create().uv(118, 168).cuboid(0.3635F, -1.7003F, -6.3123F, 1.0F, 2.0F, 13.0F, new Dilation(0.0F))
				.uv(202, 35).cuboid(-0.5369F, -2.7003F, -7.7873F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F))
				.uv(22, 171).cuboid(-0.1369F, -3.7003F, 5.4127F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F))
				.uv(70, 92).cuboid(0.3631F, -2.7003F, 7.4127F, 1.0F, 4.0F, 25.0F, new Dilation(0.0F))
				.uv(0, 102).cuboid(0.8495F, -4.7003F, 7.4127F, 0.0F, 8.0F, 24.0F, new Dilation(0.0F))
				.uv(202, 69).cuboid(0.3631F, -5.7003F, 5.6627F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F))
				.uv(202, 74).cuboid(0.3631F, 3.2997F, 5.6627F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-3.8345F, 9.3701F, 0.6523F, -0.2174F, 0.0189F, 0.1288F));

		ModelPartData cube_r25 = rightHandItem.addChild("cube_r25", ModelPartBuilder.create().uv(182, 84).cuboid(-0.9999F, -4.9109F, -3.65F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.8631F, -0.1003F, 7.6127F, -0.5236F, 0.0F, 0.0F));

		ModelPartData cube_r26 = rightHandItem.addChild("cube_r26", ModelPartBuilder.create().uv(112, 179).cuboid(-0.9999F, -4.9109F, 1.682F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.8631F, 7.3997F, 6.4127F, 0.5236F, 0.0F, 0.0F));

		ModelPartData cube_r27 = rightHandItem.addChild("cube_r27", ModelPartBuilder.create().uv(174, 198).cuboid(0.5239F, -3.1867F, -21.4677F, 0.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.3631F, 12.6997F, 44.3127F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r28 = rightHandItem.addChild("cube_r28", ModelPartBuilder.create().uv(162, 198).cuboid(-0.0397F, -3.4235F, -3.3342F, 0.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.8631F, -0.4003F, 31.3077F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r29 = rightHandItem.addChild("cube_r29", ModelPartBuilder.create().uv(132, 183).cuboid(0.0087F, -3.0003F, -3.0489F, 0.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.8631F, -0.7003F, 31.1627F, -0.7854F, 0.0F, 0.0F));

		ModelPartData waist = body.addChild("waist", ModelPartBuilder.create().uv(134, 113).cuboid(-6.0F, -14.0F, -4.01F, 12.0F, 12.0F, 8.0F, new Dilation(0.0F))
				.uv(116, 36).cuboid(-11.0F, -22.8F, -4.0F, 22.0F, 9.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r30 = waist.addChild("cube_r30", ModelPartBuilder.create().uv(62, 197).cuboid(-1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(4.561F, -6.5932F, 2.9664F, 0.2657F, -0.8696F, -0.3421F));

		ModelPartData cube_r31 = waist.addChild("cube_r31", ModelPartBuilder.create().uv(152, 195).cuboid(-1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-4.561F, -6.5932F, 2.9664F, 0.2657F, 0.8696F, 0.3421F));

		ModelPartData cube_r32 = waist.addChild("cube_r32", ModelPartBuilder.create().uv(22, 180).cuboid(-1.0F, -4.5F, -5.0F, 2.0F, 5.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(-0.439F, 2.1805F, 2.5F, -1.5708F, -1.0908F, 1.5708F));

		ModelPartData cube_r33 = waist.addChild("cube_r33", ModelPartBuilder.create().uv(168, 89).cuboid(0.0F, -4.5F, -4.0F, 1.0F, 14.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(-0.439F, -7.5695F, -2.875F, -1.5708F, 1.3526F, -1.5708F));

		ModelPartData cube_r34 = waist.addChild("cube_r34", ModelPartBuilder.create().uv(58, 164).cuboid(0.0F, -4.5F, -5.0F, 1.0F, 14.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(-0.439F, -7.5695F, 4.0F, 1.5708F, -1.3526F, -1.5708F));

		ModelPartData cube_r35 = waist.addChild("cube_r35", ModelPartBuilder.create().uv(174, 112).cuboid(-5.5017F, -2.8344F, -4.001F, 6.0F, 6.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(8.6F, -11.25F, 0.0F, 0.0F, 0.0F, 0.3054F));

		ModelPartData cube_r36 = waist.addChild("cube_r36", ModelPartBuilder.create().uv(84, 171).cuboid(-0.4983F, -2.8344F, -4.001F, 6.0F, 6.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-8.6F, -11.25F, 0.0F, 0.0F, 0.0F, -0.3054F));

		ModelPartData cube_r37 = waist.addChild("cube_r37", ModelPartBuilder.create().uv(84, 185).cuboid(-0.5F, 1.0F, -4.0F, 2.0F, 7.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-5.75F, -9.25F, 0.0F, 0.0F, 0.0F, 0.1745F));

		ModelPartData cube_r38 = waist.addChild("cube_r38", ModelPartBuilder.create().uv(54, 201).cuboid(-0.5F, -5.5F, 0.5F, 1.0F, 8.0F, 3.0F, new Dilation(0.0F))
				.uv(202, 14).cuboid(-0.5F, -5.5F, -3.4F, 1.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(8.639F, 2.0185F, -0.5F, 0.0F, 0.0F, -0.3491F));

		ModelPartData cube_r39 = waist.addChild("cube_r39", ModelPartBuilder.create().uv(124, 198).cuboid(-0.5F, -5.5F, 0.5F, 1.0F, 8.0F, 3.0F, new Dilation(0.0F))
				.uv(104, 185).cuboid(-0.5F, -5.5F, -3.4F, 1.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-8.639F, 2.0185F, -0.5F, 0.0F, 0.0F, 0.3491F));

		ModelPartData cube_r40 = waist.addChild("cube_r40", ModelPartBuilder.create().uv(112, 183).cuboid(-1.5F, 1.0F, -4.0F, 2.0F, 7.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(5.75F, -9.25F, 0.0F, 0.0F, 0.0F, -0.1745F));

		ModelPartData cube_r41 = waist.addChild("cube_r41", ModelPartBuilder.create().uv(48, 119).mirrored().cuboid(-5.0074F, -0.8604F, -0.3272F, 10.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(4.575F, -23.625F, 4.275F, -1.5812F, 0.4889F, -0.1928F));

		ModelPartData cube_r42 = waist.addChild("cube_r42", ModelPartBuilder.create().uv(48, 119).cuboid(-4.9926F, -0.8604F, -0.3272F, 10.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-4.575F, -23.625F, 4.275F, -1.5812F, -0.4889F, 0.1928F));

		ModelPartData cube_r43 = waist.addChild("cube_r43", ModelPartBuilder.create().uv(70, 80).cuboid(-15.0F, -2.0F, -5.0F, 29.0F, 3.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(0.25F, -23.725F, 0.475F, -0.3054F, 0.0F, 0.0F));

		ModelPartData cube_r44 = waist.addChild("cube_r44", ModelPartBuilder.create().uv(146, 77).cuboid(-8.0F, -6.5F, -1.0F, 16.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -18.3F, -4.0F, 0.1745F, 0.0F, 0.0F));

		ModelPartData cube_r45 = waist.addChild("cube_r45", ModelPartBuilder.create().uv(180, 0).cuboid(2.5F, 2.5F, -6.0F, 3.0F, 9.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-0.05F, -18.3F, 3.05F, 0.0F, 0.0F, 0.1309F));

		ModelPartData cube_r46 = waist.addChild("cube_r46", ModelPartBuilder.create().uv(48, 102).cuboid(-5.5F, 2.5F, -6.0F, 3.0F, 9.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.05F, -18.3F, 3.05F, 0.0F, 0.0F, -0.1309F));

		ModelPartData cube_r47 = waist.addChild("cube_r47", ModelPartBuilder.create().uv(84, 154).cuboid(0.25F, -4.5F, -4.25F, 9.0F, 9.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-0.05F, -18.3F, 3.05F, -0.1069F, 0.4929F, -0.1801F));

		ModelPartData cube_r48 = waist.addChild("cube_r48", ModelPartBuilder.create().uv(134, 151).cuboid(-9.25F, -4.5F, -4.25F, 9.0F, 9.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.05F, -18.3F, 3.05F, -0.1069F, -0.4929F, 0.1801F));

		ModelPartData head = waist.addChild("head", ModelPartBuilder.create().uv(0, 155).cuboid(-4.0F, -9.0F, -2.85F, 8.0F, 9.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -24.9F, 0.6F));

		ModelPartData headLayer = head.addChild("headLayer", ModelPartBuilder.create().uv(46, 145).cuboid(-5.0F, -9.99F, -4.99F, 10.0F, 10.0F, 9.0F, new Dilation(0.0F))
				.uv(112, 171).cuboid(-1.0F, -8.0F, -6.0F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.4F));

		ModelPartData cube_r49 = headLayer.addChild("cube_r49", ModelPartBuilder.create().uv(202, 9).cuboid(-0.9621F, -0.85F, -3.4995F, 4.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-3.7779F, 0.5F, 3.6075F, -1.5708F, -0.3927F, 0.0F));

		ModelPartData cube_r50 = headLayer.addChild("cube_r50", ModelPartBuilder.create().uv(202, 4).cuboid(-3.0379F, -0.85F, -3.4995F, 4.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(3.7779F, 0.5F, 3.6075F, -1.5708F, 0.3927F, 0.0F));

		ModelPartData cube_r51 = headLayer.addChild("cube_r51", ModelPartBuilder.create().uv(168, 163).cuboid(-1.01F, -2.5F, -0.5F, 2.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.25F, 4.76F, 0.48F, 0.0F, 0.0F));

		ModelPartData cube_r52 = headLayer.addChild("cube_r52", ModelPartBuilder.create().uv(202, 30).cuboid(-1.5F, -2.0F, -0.5F, 4.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(4.95F, -7.3F, 1.44F, -2.0252F, -0.8785F, -1.0053F));

		ModelPartData cube_r53 = headLayer.addChild("cube_r53", ModelPartBuilder.create().uv(202, 25).cuboid(-2.5F, -2.0F, -0.5F, 4.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.95F, -7.3F, 1.44F, -2.3642F, 1.1668F, 0.6135F));

		ModelPartData cube_r54 = headLayer.addChild("cube_r54", ModelPartBuilder.create().uv(146, 89).cuboid(-3.25F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(5.6956F, -13.7817F, 6.4067F, -1.4159F, 0.6515F, -1.5214F));

		ModelPartData cube_r55 = headLayer.addChild("cube_r55", ModelPartBuilder.create().uv(202, 117).cuboid(-0.99F, -2.5F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(5.9F, -11.075F, 5.74F, -1.3578F, 0.9519F, -1.4414F));

		ModelPartData cube_r56 = headLayer.addChild("cube_r56", ModelPartBuilder.create().uv(202, 109).cuboid(-0.01F, 0.5F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-7.65F, -10.075F, 5.74F, -1.7527F, -0.7809F, 1.3872F));

		ModelPartData layer = waist.addChild("layer", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -21.0F, -7.0F));

		ModelPartData cube_r57 = layer.addChild("cube_r57", ModelPartBuilder.create().uv(98, 121).cuboid(-9.0F, -29.5F, -1.0F, 18.0F, 33.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.8F, -0.25F, 2.9671F, 0.0F, 0.0F));

		ModelPartData chest = waist.addChild("chest", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -18.0F, 3.0F));

		ModelPartData leftChest = chest.addChild("leftChest", ModelPartBuilder.create(), ModelTransform.pivot(-6.75F, 0.5F, 3.0F));

		ModelPartData cube_r58 = leftChest.addChild("cube_r58", ModelPartBuilder.create().uv(194, 52).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(3.75F, 3.5F, -5.0F, 0.0F, -0.7418F, 0.0F));

		ModelPartData cube_r59 = leftChest.addChild("cube_r59", ModelPartBuilder.create().uv(20, 194).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(2.75F, -1.5F, -3.0F, 0.4375F, -0.6022F, 0.0473F));

		ModelPartData cube_r60 = leftChest.addChild("cube_r60", ModelPartBuilder.create().uv(0, 191).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(4.5F, 5.0F, -6.0F, -0.3742F, -0.5956F, -0.0509F));

		ModelPartData cube_r61 = leftChest.addChild("cube_r61", ModelPartBuilder.create().uv(188, 190).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(2.75F, 1.5F, -3.0F, 0.4004F, -0.8887F, -0.1917F));

		ModelPartData leftChest1 = leftChest.addChild("leftChest1", ModelPartBuilder.create(), ModelTransform.pivot(-0.25F, 1.5F, 1.0F));

		ModelPartData cube_r62 = leftChest1.addChild("cube_r62", ModelPartBuilder.create().uv(166, 69).cuboid(-1.9448F, -1.25F, -1.5658F, 2.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(1.2815F, 5.3147F, -2.3328F, 2.4789F, 1.4408F, 2.3494F));

		ModelPartData cube_r63 = leftChest1.addChild("cube_r63", ModelPartBuilder.create().uv(30, 155).cuboid(-2.1948F, -2.75F, -3.3158F, 2.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(1.9516F, -5.0466F, 0.9565F, 0.4275F, 1.3272F, 0.5007F));

		ModelPartData cube_r64 = leftChest1.addChild("cube_r64", ModelPartBuilder.create().uv(88, 200).cuboid(-2.9448F, -4.25F, -0.8158F, 2.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-0.9465F, 1.0F, -1.4237F, 0.2416F, 0.8625F, 0.202F));

		ModelPartData cube_r65 = leftChest1.addChild("cube_r65", ModelPartBuilder.create().uv(84, 145).cuboid(-0.4448F, -1.0F, 0.2842F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-0.9465F, 1.0F, -1.4237F, 0.0F, 0.8727F, 0.0F));

		ModelPartData rightChest = chest.addChild("rightChest", ModelPartBuilder.create(), ModelTransform.pivot(9.75F, -0.5F, 3.0F));

		ModelPartData cube_r66 = rightChest.addChild("cube_r66", ModelPartBuilder.create().uv(104, 198).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-6.75F, 4.5F, -5.0F, 0.0F, 0.7418F, 0.0F));

		ModelPartData cube_r67 = rightChest.addChild("cube_r67", ModelPartBuilder.create().uv(196, 136).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-5.75F, -0.5F, -3.0F, 0.4375F, 0.6022F, -0.0473F));

		ModelPartData cube_r68 = rightChest.addChild("cube_r68", ModelPartBuilder.create().uv(196, 126).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-7.5F, 6.0F, -6.0F, -0.3742F, 0.5956F, 0.0509F));

		ModelPartData cube_r69 = rightChest.addChild("cube_r69", ModelPartBuilder.create().uv(132, 195).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-5.75F, 2.5F, -3.0F, 0.4004F, 0.8887F, 0.1917F));

		ModelPartData rightChest1 = rightChest.addChild("rightChest1", ModelPartBuilder.create(), ModelTransform.pivot(-3.75F, 2.5F, 1.0F));

		ModelPartData cube_r70 = rightChest1.addChild("cube_r70", ModelPartBuilder.create().uv(72, 200).cuboid(-0.0552F, -1.25F, -1.5658F, 2.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-0.2815F, 5.3147F, -2.3328F, 2.4789F, -1.4408F, -2.3494F));

		ModelPartData cube_r71 = rightChest1.addChild("cube_r71", ModelPartBuilder.create().uv(198, 153).cuboid(0.1948F, -2.75F, -3.3158F, 2.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-0.9516F, -5.0466F, 0.9565F, 0.4275F, -1.3272F, -0.5007F));

		ModelPartData cube_r72 = rightChest1.addChild("cube_r72", ModelPartBuilder.create().uv(186, 200).cuboid(0.9448F, -4.25F, -0.8158F, 2.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(1.9465F, 1.0F, -1.4237F, 0.2416F, -0.8625F, -0.202F));

		ModelPartData cube_r73 = rightChest1.addChild("cube_r73", ModelPartBuilder.create().uv(162, 28).cuboid(-1.5552F, -1.0F, 0.2842F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(1.9465F, 1.0F, -1.4237F, 0.0F, -0.8727F, 0.0F));
		return TexturedModelData.of(modelData, 256, 256);
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
	public void setAngles(GrandKnightEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.animateMovement(GrandKnightEntityModelAnimation.walk,limbAngle,limbDistance,2f,2.5f);
		this.updateAnimation(entity.STAGE_2_STOP_APART_ANI, GrandKnightLevelTwoEntityModelAnimation.levelTwoSummon,animationProgress,1.0f);
		this.updateAnimation(entity.SHIELD_SMASH_ANI, GrandKnightEntityModelAnimation.shieldSmash,animationProgress,1.0f);
		this.updateAnimation(entity.SHIELD_SLAP_ANI, GrandKnightEntityModelAnimation.shieldSlap,animationProgress,1.0f);
		this.updateAnimation(entity.EARTH_SHAKER_ANI, GrandKnightEntityModelAnimation.earthshaker,animationProgress,1.0f);
		this.updateAnimation(entity.BRIMSTONE_ANI, GrandKnightLevelTwoEntityModelAnimation.brimStone,animationProgress,1.0f);
		this.updateAnimation(entity.RAPID_SLASHES_ANI, GrandKnightLevelTwoEntityModelAnimation.rapidSlashes,animationProgress,1.0f);
		this.updateAnimation(entity.DELAY_BULLET_ANI, GrandKnightLevelTwoEntityModelAnimation.delayBullet,animationProgress,1.0f);
		this.updateAnimation(entity.ATTACK_ANI, GrandKnightLevelTwoEntityModelAnimation.attack,animationProgress,1.0f);


		this.head.yaw = headYaw * (float) (Math.PI / 180.0);
		this.head.pitch = -headPitch * (float) (Math.PI / 180.0);
	}
}