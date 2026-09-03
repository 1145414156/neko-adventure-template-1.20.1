package com.nekoadventure.entity.model.boss;


import com.nekoadventure.entity.animation.boss.GrandKnightEntityModelAnimation;
import com.nekoadventure.entity.animation.boss.HugeSlimeEntityModelAnimation;
import com.nekoadventure.entity.animation.mob.MinderAnimation;
import com.nekoadventure.entity.animation.mob.ShooterAnimation;
import com.nekoadventure.entity.boss.GrandKnightEntity;
import com.nekoadventure.entity.boss.HugeSlimeEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class GrandKnightEntityModel extends SinglePartEntityModel<GrandKnightEntity> {
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
		private final ModelPart waist;
		private final ModelPart head;
		private final ModelPart headLayer;
		private final ModelPart layer;
		private final ModelPart rightHandItem;
		public GrandKnightEntityModel(ModelPart root) {
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
			this.waist = this.body.getChild("waist");
			this.head = this.waist.getChild("head");
			this.headLayer = this.head.getChild("headLayer");
			this.layer = this.waist.getChild("layer");
			this.rightHandItem = this.waist.getChild("rightHandItem");
		}
		public static TexturedModelData getTexturedModelData() {
			ModelData modelData = new ModelData();
			ModelPartData modelPartData = modelData.getRoot();
			ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

			ModelPartData a = main.addChild("a", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

			ModelPartData leg = a.addChild("leg", ModelPartBuilder.create().uv(146, 77).cuboid(-7.0F, -0.95F, -3.5F, 14.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -24.0F, -1.0F));

			ModelPartData rightLeg = leg.addChild("rightLeg", ModelPartBuilder.create().uv(166, 53).cuboid(-2.425F, 0.0F, -3.5F, 7.0F, 9.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, 2.0F, 0.5F));

			ModelPartData rightLeg1 = rightLeg.addChild("rightLeg1", ModelPartBuilder.create().uv(28, 178).cuboid(-2.0F, -1.0F, -2.5F, 5.0F, 10.0F, 5.0F, new Dilation(0.0F))
					.uv(168, 87).cuboid(-2.0F, 10.0F, -2.25F, 5.0F, 2.0F, 10.0F, new Dilation(0.0F))
					.uv(146, 87).cuboid(-2.0F, 9.0F, -2.5F, 5.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 10.0F, 0.0F));

			ModelPartData cube_r1 = rightLeg1.addChild("cube_r1", ModelPartBuilder.create().uv(188, 77).cuboid(-1.999F, -2.0F, -2.0F, 5.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 11.15F, 2.85F, -0.2618F, 0.0F, 0.0F));

			ModelPartData cube_r2 = rightLeg1.addChild("cube_r2", ModelPartBuilder.create().uv(176, 49).cuboid(-2.999F, -0.5F, -2.0F, 5.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 10.25F, -2.25F, -1.2217F, 0.0F, 0.0F));

			ModelPartData leftLeg = leg.addChild("leftLeg", ModelPartBuilder.create().uv(60, 166).cuboid(-4.575F, 0.0F, -3.5F, 7.0F, 9.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 2.0F, 0.5F));

			ModelPartData leftLeg1 = leftLeg.addChild("leftLeg1", ModelPartBuilder.create().uv(180, 17).cuboid(-3.0F, -1.0F, -2.5F, 5.0F, 10.0F, 5.0F, new Dilation(0.0F))
					.uv(168, 99).cuboid(-3.0F, 10.0F, -2.25F, 5.0F, 2.0F, 10.0F, new Dilation(0.0F))
					.uv(176, 144).cuboid(-3.0F, 9.0F, -2.5F, 5.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 10.0F, 0.0F));

			ModelPartData cube_r3 = leftLeg1.addChild("cube_r3", ModelPartBuilder.create().uv(86, 189).cuboid(-3.001F, -2.0F, -2.0F, 5.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 11.15F, 2.85F, -0.2618F, 0.0F, 0.0F));

			ModelPartData cube_r4 = leftLeg1.addChild("cube_r4", ModelPartBuilder.create().uv(192, 49).cuboid(-2.001F, -0.5F, -2.0F, 5.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 10.25F, -2.25F, -1.2217F, 0.0F, 0.0F));

			ModelPartData body = a.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -22.4F, -1.0F));

			ModelPartData hand = body.addChild("hand", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 4.0F, 0.0F));

			ModelPartData leftHand = hand.addChild("leftHand", ModelPartBuilder.create(), ModelTransform.of(-15.75F, -27.0F, 0.5F, 0.9581F, 0.3644F, -0.1582F));

			ModelPartData cube_r5 = leftHand.addChild("cube_r5", ModelPartBuilder.create().uv(140, 17).cuboid(-2.13F, -6.9886F, -2.0086F, 10.0F, 8.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(-0.49F, 10.3066F, -3.0F, 0.0283F, 0.0405F, -1.4406F));

			ModelPartData cube_r6 = leftHand.addChild("cube_r6", ModelPartBuilder.create().uv(106, 189).cuboid(0.5F, -0.5F, -3.01F, 2.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-7.375F, -5.125F, 0.5F, 0.0F, 0.0F, 0.0873F));

			ModelPartData cube_r7 = leftHand.addChild("cube_r7", ModelPartBuilder.create().uv(184, 175).cuboid(-2.5F, -0.5F, -3.01F, 5.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -1.2F, 0.5F, 0.0F, 0.0F, -0.0436F));

			ModelPartData cube_r8 = leftHand.addChild("cube_r8", ModelPartBuilder.create().uv(122, 92).cuboid(-9.0F, 0.0F, -6.0F, 11.0F, 9.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -0.7F, -0.3F, 0.0F, 0.0F, 0.3491F));

			ModelPartData leftHand1 = leftHand.addChild("leftHand1", ModelPartBuilder.create().uv(122, 113).cuboid(-3.7367F, -3.2984F, -1.7123F, 1.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.1164F, 13.5362F, 0.2F, 0.1055F, 0.3306F, -0.4772F));

			ModelPartData cube_r9 = leftHand1.addChild("cube_r9", ModelPartBuilder.create().uv(184, 164).cuboid(1.9225F, -2.7707F, -1.4506F, 5.0F, 6.0F, 5.0F, new Dilation(0.0F))
					.uv(172, 150).cuboid(-1.9325F, -3.5043F, -2.8434F, 5.0F, 6.0F, 8.0F, new Dilation(0.0F))
					.uv(128, 169).cuboid(-0.9325F, -8.5043F, -1.8434F, 5.0F, 14.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-3.281F, 8.12F, -0.8698F, 0.0071F, -0.0466F, -0.2132F));

			ModelPartData cube_r10 = leftHand1.addChild("cube_r10", ModelPartBuilder.create().uv(68, 182).cuboid(0.6442F, -6.4885F, -0.8998F, 5.0F, 10.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-3.281F, 8.12F, -0.8698F, -0.004F, -0.047F, 0.0227F));

			ModelPartData leftHandItem = leftHand1.addChild("leftHandItem", ModelPartBuilder.create(), ModelTransform.of(0.0F, 11.0F, 0.5F, 0.0F, 0.0F, -0.4363F));

			ModelPartData cube_r11 = leftHandItem.addChild("cube_r11", ModelPartBuilder.create().uv(34, 193).cuboid(4.7434F, 1.7457F, -2.9325F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-3.281F, -2.88F, -1.3698F, 0.1502F, 1.5237F, -0.0629F));

			ModelPartData cube_r12 = leftHandItem.addChild("cube_r12", ModelPartBuilder.create().uv(30, 155).cuboid(6.1566F, 1.7457F, -2.0675F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-3.281F, -2.88F, -1.3698F, 2.9914F, -1.5237F, 3.0787F));

			ModelPartData cube_r13 = leftHandItem.addChild("cube_r13", ModelPartBuilder.create().uv(150, 169).cuboid(-0.5F, -17.0F, -1.0F, 1.0F, 34.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-6.4913F, 0.449F, 11.2577F, 0.014F, 1.0442F, -0.2007F));

			ModelPartData cube_r14 = leftHandItem.addChild("cube_r14", ModelPartBuilder.create().uv(70, 0).cuboid(-11.7233F, -3.9325F, -17.8434F, 1.0F, 2.0F, 34.0F, new Dilation(0.0F)), ModelTransform.of(-1.656F, 4.67F, -4.7948F, 0.0466F, 0.007F, -1.7837F));

			ModelPartData cube_r15 = leftHandItem.addChild("cube_r15", ModelPartBuilder.create().uv(0, 66).cuboid(-6.2543F, 1.9325F, -17.8434F, 1.0F, 2.0F, 34.0F, new Dilation(0.0F)), ModelTransform.of(-4.981F, -10.605F, -4.7948F, -0.0466F, -0.007F, 1.3579F));

			ModelPartData cube_r16 = leftHandItem.addChild("cube_r16", ModelPartBuilder.create().uv(48, 121).cuboid(-3.929F, 16.04F, -12.1096F, 2.0F, 1.0F, 23.0F, new Dilation(0.0F)), ModelTransform.of(-2.681F, -3.305F, -18.3697F, -0.7783F, -0.0466F, -0.2132F));

			ModelPartData cube_r17 = leftHandItem.addChild("cube_r17", ModelPartBuilder.create().uv(116, 53).cuboid(-3.939F, -12.0975F, -16.2521F, 2.0F, 1.0F, 23.0F, new Dilation(0.0F)), ModelTransform.of(-2.781F, -3.705F, -18.7697F, 0.7925F, -0.0466F, -0.2132F));

			ModelPartData cube_r18 = leftHandItem.addChild("cube_r18", ModelPartBuilder.create().uv(70, 36).cuboid(-3.9278F, -14.8975F, -18.9521F, 1.0F, 22.0F, 22.0F, new Dilation(0.0F)), ModelTransform.of(-2.956F, -3.305F, -14.3697F, 0.7925F, -0.0466F, -0.2132F));

			ModelPartData cube_r19 = leftHandItem.addChild("cube_r19", ModelPartBuilder.create().uv(0, 0).cuboid(-3.9325F, -13.2543F, -17.8434F, 1.0F, 32.0F, 34.0F, new Dilation(0.0F)), ModelTransform.of(-3.281F, -2.88F, -4.7948F, 0.0071F, -0.0466F, -0.2132F));

			ModelPartData cube_r20 = leftHandItem.addChild("cube_r20", ModelPartBuilder.create().uv(176, 131).cuboid(0.0675F, 1.7457F, -4.8434F, 2.0F, 2.0F, 11.0F, new Dilation(0.0F)), ModelTransform.of(-3.281F, -2.88F, -1.3698F, 0.0071F, -0.0466F, -0.2132F));

			ModelPartData rightHand = hand.addChild("rightHand", ModelPartBuilder.create(), ModelTransform.of(12.8488F, -25.1F, 1.9514F, 0.0F, 0.0F, 0.0873F));

			ModelPartData cube_r21 = rightHand.addChild("cube_r21", ModelPartBuilder.create().uv(122, 189).cuboid(-2.5F, -0.5F, -3.01F, 2.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(9.9357F, -7.5248F, -0.9514F, 0.0F, 0.0F, -0.1745F));

			ModelPartData cube_r22 = rightHand.addChild("cube_r22", ModelPartBuilder.create().uv(46, 145).cuboid(-7.87F, -6.9886F, -2.0086F, 10.0F, 8.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(3.3257F, 7.7318F, -4.4514F, 0.0283F, -0.0405F, 1.4406F));

			ModelPartData cube_r23 = rightHand.addChild("cube_r23", ModelPartBuilder.create().uv(0, 185).cuboid(-2.5F, -0.5F, -3.01F, 5.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(2.3357F, -3.0748F, -0.9514F, 0.0F, 0.0F, 0.0436F));

			ModelPartData cube_r24 = rightHand.addChild("cube_r24", ModelPartBuilder.create().uv(0, 134).cuboid(-2.0F, 0.0F, -6.0F, 11.0F, 9.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.8357F, -2.8498F, -1.7514F, 0.0F, 0.0F, -0.3491F));

			ModelPartData rightHand1 = rightHand.addChild("rightHand1", ModelPartBuilder.create().uv(86, 145).cuboid(-0.1669F, 0.2399F, -1.8088F, 1.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(5.1823F, 8.7896F, -1.7314F, 0.218F, 0.0094F, -0.1299F));

			ModelPartData cube_r25 = rightHand1.addChild("cube_r25", ModelPartBuilder.create().uv(174, 183).cuboid(-6.9225F, -2.7707F, -1.4506F, 5.0F, 6.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.6774F, 10.3834F, -0.9662F, 0.0071F, 0.0466F, 0.2132F));

			ModelPartData cube_r26 = rightHand1.addChild("cube_r26", ModelPartBuilder.create().uv(176, 35).cuboid(-3.0675F, -3.5043F, -2.8434F, 5.0F, 6.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.3774F, 10.8084F, -0.9662F, 0.0071F, 0.0466F, 0.2132F));

			ModelPartData cube_r27 = rightHand1.addChild("cube_r27", ModelPartBuilder.create().uv(156, 183).cuboid(-5.6442F, -6.4885F, -0.8998F, 5.0F, 10.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.3774F, 11.2834F, -0.9662F, -0.004F, 0.047F, -0.0227F));

			ModelPartData cube_r28 = rightHand1.addChild("cube_r28", ModelPartBuilder.create().uv(174, 111).cuboid(-4.0675F, -8.5043F, -1.8434F, 5.0F, 14.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.3774F, 11.3334F, -0.9662F, 0.0071F, 0.0466F, 0.2132F));

			ModelPartData waist = body.addChild("waist", ModelPartBuilder.create().uv(134, 113).cuboid(-6.0F, -14.0F, -4.01F, 12.0F, 12.0F, 8.0F, new Dilation(0.0F))
					.uv(116, 36).cuboid(-11.0F, -22.8F, -4.0F, 22.0F, 9.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

			ModelPartData cube_r29 = waist.addChild("cube_r29", ModelPartBuilder.create().uv(0, 193).cuboid(-1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(4.561F, -6.5932F, 2.9664F, 0.2657F, -0.8696F, -0.3421F));

			ModelPartData cube_r30 = waist.addChild("cube_r30", ModelPartBuilder.create().uv(138, 189).cuboid(-1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-4.561F, -6.5932F, 2.9664F, 0.2657F, 0.8696F, 0.3421F));

			ModelPartData cube_r31 = waist.addChild("cube_r31", ModelPartBuilder.create().uv(48, 102).cuboid(-1.0F, -4.5F, -5.0F, 2.0F, 5.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(-0.439F, 2.1805F, 2.5F, -1.5708F, -1.0908F, 1.5708F));

			ModelPartData cube_r32 = waist.addChild("cube_r32", ModelPartBuilder.create().uv(108, 166).cuboid(0.0F, -4.5F, -4.0F, 1.0F, 14.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(-0.439F, -7.5695F, -2.875F, -1.5708F, 1.3526F, -1.5708F));

			ModelPartData cube_r33 = waist.addChild("cube_r33", ModelPartBuilder.create().uv(88, 166).cuboid(0.0F, -4.5F, -5.0F, 1.0F, 14.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(-0.439F, -7.5695F, 4.0F, 1.5708F, -1.3526F, -1.5708F));

			ModelPartData cube_r34 = waist.addChild("cube_r34", ModelPartBuilder.create().uv(0, 171).cuboid(-5.5017F, -2.8344F, -4.0F, 6.0F, 6.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(8.6F, -11.25F, 0.0F, 0.0F, 0.0F, 0.3054F));

			ModelPartData cube_r35 = waist.addChild("cube_r35", ModelPartBuilder.create().uv(156, 169).cuboid(-0.4983F, -2.8344F, -4.0F, 6.0F, 6.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-8.6F, -11.25F, 0.0F, 0.0F, 0.0F, -0.3054F));

			ModelPartData cube_r36 = waist.addChild("cube_r36", ModelPartBuilder.create().uv(48, 182).cuboid(-0.5F, 1.0F, -4.0F, 2.0F, 7.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-5.75F, -9.25F, 0.0F, 0.0F, 0.0F, 0.1745F));

			ModelPartData cube_r37 = waist.addChild("cube_r37", ModelPartBuilder.create().uv(182, 194).cuboid(-0.5F, -5.5F, 0.5F, 1.0F, 8.0F, 3.0F, new Dilation(0.0F))
					.uv(194, 183).cuboid(-0.5F, -5.5F, -3.4F, 1.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(8.639F, 2.0185F, -0.5F, 0.0F, 0.0F, -0.3491F));

			ModelPartData cube_r38 = waist.addChild("cube_r38", ModelPartBuilder.create().uv(174, 194).cuboid(-0.5F, -5.5F, 0.5F, 1.0F, 8.0F, 3.0F, new Dilation(0.0F))
					.uv(194, 53).cuboid(-0.5F, -5.5F, -3.4F, 1.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-8.639F, 2.0185F, -0.5F, 0.0F, 0.0F, 0.3491F));

			ModelPartData cube_r39 = waist.addChild("cube_r39", ModelPartBuilder.create().uv(182, 0).cuboid(-1.5F, 1.0F, -4.0F, 2.0F, 7.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(5.75F, -9.25F, 0.0F, 0.0F, 0.0F, -0.1745F));

			ModelPartData cube_r40 = waist.addChild("cube_r40", ModelPartBuilder.create().uv(166, 73).cuboid(-1.5F, -4.9F, -0.9F, 13.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.75F, -20.8F, 3.9F, -1.153F, 1.0515F, -0.6902F));

			ModelPartData cube_r41 = waist.addChild("cube_r41", ModelPartBuilder.create().uv(116, 77).cuboid(-4.9926F, -0.8604F, -0.3272F, 10.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-4.575F, -23.625F, 4.275F, -1.58F, -0.0962F, 0.1888F));

			ModelPartData cube_r42 = waist.addChild("cube_r42", ModelPartBuilder.create().uv(48, 116).cuboid(-5.0074F, -0.8604F, -0.3272F, 10.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(4.8F, -23.625F, 4.275F, -1.58F, 0.0962F, -0.1888F));

			ModelPartData cube_r43 = waist.addChild("cube_r43", ModelPartBuilder.create().uv(166, 69).cuboid(-12.5F, -4.9F, -0.9F, 14.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-3.75F, -20.8F, 3.9F, -1.153F, -1.0515F, 0.6902F));

			ModelPartData cube_r44 = waist.addChild("cube_r44", ModelPartBuilder.create().uv(70, 80).cuboid(-15.0F, -2.0F, -5.0F, 29.0F, 3.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(0.25F, -23.725F, 0.475F, -0.3054F, 0.0F, 0.0F));

			ModelPartData cube_r45 = waist.addChild("cube_r45", ModelPartBuilder.create().uv(86, 154).cuboid(-8.0F, -6.5F, -1.0F, 16.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -18.3F, -4.0F, 0.1745F, 0.0F, 0.0F));

			ModelPartData cube_r46 = waist.addChild("cube_r46", ModelPartBuilder.create().uv(140, 0).cuboid(0.0F, -4.5F, -4.0F, 13.0F, 9.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-0.275F, -18.3F, 3.05F, -0.0946F, 0.1022F, -0.139F));

			ModelPartData cube_r47 = waist.addChild("cube_r47", ModelPartBuilder.create().uv(134, 133).cuboid(-13.0F, -4.5F, -4.0F, 13.0F, 9.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.05F, -18.3F, 3.05F, -0.0946F, -0.1022F, 0.139F));

			ModelPartData head = waist.addChild("head", ModelPartBuilder.create().uv(0, 155).cuboid(-4.0F, -9.0F, -2.6F, 8.0F, 9.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -24.9F, 0.6F));

			ModelPartData headLayer = head.addChild("headLayer", ModelPartBuilder.create().uv(134, 150).cuboid(-5.0F, -9.99F, -4.99F, 10.0F, 10.0F, 9.0F, new Dilation(0.0F))
					.uv(22, 185).cuboid(-1.0F, -7.0F, -6.0F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.4F));

			ModelPartData cube_r48 = headLayer.addChild("cube_r48", ModelPartBuilder.create().uv(194, 144).cuboid(-0.9621F, -0.85F, -3.4995F, 4.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-3.7779F, 0.5F, 3.6075F, -1.5708F, -0.3927F, 0.0F));

			ModelPartData cube_r49 = headLayer.addChild("cube_r49", ModelPartBuilder.create().uv(194, 64).cuboid(-3.0379F, -0.85F, -3.4995F, 4.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(3.7779F, 0.5F, 3.6075F, -1.5708F, 0.3927F, 0.0F));

			ModelPartData cube_r50 = headLayer.addChild("cube_r50", ModelPartBuilder.create().uv(48, 197).cuboid(-1.01F, -2.5F, -0.5F, 2.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.25F, 4.26F, 0.3054F, 0.0F, 0.0F));

			ModelPartData cube_r51 = headLayer.addChild("cube_r51", ModelPartBuilder.create().uv(190, 194).cuboid(0.2101F, -2.0F, -6.1985F, 4.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-0.05F, -7.3F, -1.44F, 2.0252F, 0.8785F, -1.0053F));

			ModelPartData cube_r52 = headLayer.addChild("cube_r52", ModelPartBuilder.create().uv(196, 119).cuboid(-1.8755F, -7.3851F, -7.095F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.05F, -7.3F, -1.44F, 1.3578F, -0.9519F, -1.4414F));

			ModelPartData cube_r53 = headLayer.addChild("cube_r53", ModelPartBuilder.create().uv(54, 118).cuboid(6.3851F, 0.1145F, -7.095F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.05F, -7.3F, -1.44F, 1.4213F, 0.6028F, -1.701F));

			ModelPartData cube_r54 = headLayer.addChild("cube_r54", ModelPartBuilder.create().uv(172, 164).cuboid(-2.5F, -2.0F, -1.5F, 4.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.95F, -7.3F, -1.44F, 2.0252F, -0.8785F, 1.0053F));

			ModelPartData cube_r55 = headLayer.addChild("cube_r55", ModelPartBuilder.create().uv(48, 118).cuboid(-3.5F, 1.0F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-5.9F, -11.075F, -5.74F, 1.4213F, -0.6028F, 1.701F));

			ModelPartData cube_r56 = headLayer.addChild("cube_r56", ModelPartBuilder.create().uv(196, 111).cuboid(-1.01F, -2.5F, -0.5F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-5.9F, -11.075F, -5.74F, 1.3578F, 0.9519F, 1.4414F));

			ModelPartData layer = waist.addChild("layer", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -23.0F, -4.0F));

			ModelPartData cube_r57 = layer.addChild("cube_r57", ModelPartBuilder.create().uv(98, 121).cuboid(-9.0F, -29.5F, -1.0F, 18.0F, 33.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.8F, -3.25F, 2.9671F, 0.0F, 0.0F));

			ModelPartData rightHandItem = waist.addChild("rightHandItem", ModelPartBuilder.create().uv(30, 163).cuboid(-0.5062F, -1.0054F, -19.7736F, 1.0F, 2.0F, 13.0F, new Dilation(0.0F))
					.uv(68, 196).cuboid(-1.4067F, -2.0054F, -21.2487F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F))
					.uv(78, 196).cuboid(-1.0067F, -3.0054F, -8.0486F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F))
					.uv(70, 92).cuboid(-0.5067F, -2.0054F, -6.0486F, 1.0F, 4.0F, 25.0F, new Dilation(0.0F))
					.uv(0, 102).cuboid(-0.0202F, -4.0054F, -6.0486F, 0.0F, 8.0F, 24.0F, new Dilation(0.0F))
					.uv(86, 196).cuboid(-0.5067F, -5.0054F, -7.7986F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F))
					.uv(96, 196).cuboid(-0.5067F, 3.9946F, -7.7986F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.9165F, -9.1241F, -5.5954F, 0.0F, 1.5708F, 2.2253F));

			ModelPartData cube_r58 = rightHandItem.addChild("cube_r58", ModelPartBuilder.create().uv(54, 178).cuboid(-0.9999F, -4.9109F, -3.65F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.0067F, 0.5946F, -5.8487F, -0.5236F, 0.0F, 0.0F));

			ModelPartData cube_r59 = rightHandItem.addChild("cube_r59", ModelPartBuilder.create().uv(48, 178).cuboid(-0.9999F, -4.9109F, 1.682F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.0067F, 8.0946F, -7.0486F, 0.5236F, 0.0F, 0.0F));

			ModelPartData cube_r60 = rightHandItem.addChild("cube_r60", ModelPartBuilder.create().uv(22, 193).cuboid(0.5239F, -3.1867F, -21.4677F, 0.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-0.5067F, 13.3946F, 30.8514F, -0.7854F, 0.0F, 0.0F));

			ModelPartData cube_r61 = rightHandItem.addChild("cube_r61", ModelPartBuilder.create().uv(10, 193).cuboid(-0.0397F, -3.4235F, -3.3342F, 0.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-0.0067F, 0.2946F, 17.8464F, -0.7854F, 0.0F, 0.0F));

			ModelPartData cube_r62 = rightHandItem.addChild("cube_r62", ModelPartBuilder.create().uv(122, 154).cuboid(0.0087F, -3.0003F, -3.0488F, 0.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-0.0067F, -0.0054F, 17.7014F, -0.7854F, 0.0F, 0.0F));
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
		this.updateAnimation(entity.STAGE_2_START_APART_ANI, GrandKnightEntityModelAnimation.levelTwo,animationProgress,1.0f);
		this.updateAnimation(entity.GRAB_ANI, GrandKnightEntityModelAnimation.grab,animationProgress,1.0f);
		this.updateAnimation(entity.SHIELD_SMASH_ANI, GrandKnightEntityModelAnimation.shieldSmash,animationProgress,1.0f);
		this.updateAnimation(entity.SHIELD_SLAP_ANI, GrandKnightEntityModelAnimation.shieldSlap,animationProgress,1.0f);
		this.updateAnimation(entity.EARTH_SHAKER_ANI, GrandKnightEntityModelAnimation.earthshaker,animationProgress,1.0f);

		this.head.yaw = headYaw * (float) (Math.PI / 180.0);
		this.head.pitch = -headPitch * (float) (Math.PI / 180.0);
	}
}