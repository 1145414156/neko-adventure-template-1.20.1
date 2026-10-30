package com.nekoadventure.entity.model.boss;


import com.nekoadventure.entity.animation.boss.GrandKnightEntityModelAnimation;
import com.nekoadventure.entity.boss.GrandKnightEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class GrandKnightEntityModel extends SinglePartEntityModel<GrandKnightEntity> {
	private final ModelPart main;
    private final ModelPart head;
    public GrandKnightEntityModel(ModelPart root) {
		this.main = root.getChild("main");
        ModelPart a = this.main.getChild("a");
        ModelPart leg = a.getChild("leg");
        ModelPart rightLeg = leg.getChild("rightLeg");
        rightLeg.getChild("rightLeg1");
        ModelPart leftLeg = leg.getChild("leftLeg");
        leftLeg.getChild("leftLeg1");
        ModelPart body = a.getChild("body");
        ModelPart hand = body.getChild("hand");
        ModelPart leftHand = hand.getChild("leftHand");
        ModelPart leftHand1 = leftHand.getChild("leftHand1");
        leftHand1.getChild("leftHandItem");
        ModelPart rightHand = hand.getChild("rightHand");
        rightHand.getChild("rightHand1");
        ModelPart waist = body.getChild("waist");
		this.head = waist.getChild("head");
        this.head.getChild("headLayer");
        waist.getChild("layer");
        waist.getChild("rightHandItem");
    }
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData a = main.addChild("a", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData leg = a.addChild("leg", ModelPartBuilder.create().uv(142, 21).cuboid(-7.0F, -0.95F, -3.5F, 14.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -24.0F, -1.0F));

		ModelPartData rightLeg = leg.addChild("rightLeg", ModelPartBuilder.create().uv(169, 55).cuboid(-2.425F, 0.0F, -3.5F, 7.0F, 9.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, 2.0F, 0.5F));

		ModelPartData rightLeg1 = rightLeg.addChild("rightLeg1", ModelPartBuilder.create().uv(49, 104).cuboid(-2.0F, -1.0F, -2.5F, 5.0F, 10.0F, 5.0F, new Dilation(0.0F))
				.uv(161, 140).cuboid(-2.0F, 10.0F, -2.25F, 5.0F, 2.0F, 10.0F, new Dilation(0.0F))
				.uv(200, 125).cuboid(-2.0F, 9.0F, -2.5F, 5.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 10.0F, 0.0F));

        rightLeg1.addChild("cube_r1", ModelPartBuilder.create().uv(101, 196).cuboid(-1.999F, -2.0F, -2.0F, 5.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 11.15F, 2.85F, -0.2618F, 0.0F, 0.0F));

        rightLeg1.addChild("cube_r2", ModelPartBuilder.create().uv(49, 120).cuboid(-2.999F, -0.5F, -2.0F, 5.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 10.25F, -2.25F, -1.2217F, 0.0F, 0.0F));

        ModelPartData leftLeg = leg.addChild("leftLeg", ModelPartBuilder.create().uv(72, 172).cuboid(-4.575F, 0.0F, -3.5F, 7.0F, 9.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 2.0F, 0.5F));

		ModelPartData leftLeg1 = leftLeg.addChild("leftLeg1", ModelPartBuilder.create().uv(185, 14).cuboid(-3.0F, -1.0F, -2.5F, 5.0F, 10.0F, 5.0F, new Dilation(0.0F))
				.uv(151, 173).cuboid(-3.0F, 10.0F, -2.25F, 5.0F, 2.0F, 10.0F, new Dilation(0.0F))
				.uv(200, 131).cuboid(-3.0F, 9.0F, -2.5F, 5.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 10.0F, 0.0F));

        leftLeg1.addChild("cube_r3", ModelPartBuilder.create().uv(198, 53).cuboid(-3.001F, -2.0F, -2.0F, 5.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 11.15F, 2.85F, -0.2618F, 0.0F, 0.0F));

        leftLeg1.addChild("cube_r4", ModelPartBuilder.create().uv(72, 165).cuboid(-2.001F, -0.5F, -2.0F, 5.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 10.25F, -2.25F, -1.2217F, 0.0F, 0.0F));

        ModelPartData body = a.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -22.4F, -1.0F));

		ModelPartData hand = body.addChild("hand", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 4.0F, 0.0F));

		ModelPartData leftHand = hand.addChild("leftHand", ModelPartBuilder.create(), ModelTransform.of(-15.75F, -27.0F, 0.5F, 0.9581F, 0.3644F, -0.1582F));

        leftHand.addChild("cube_r5", ModelPartBuilder.create().uv(0, 159).mirrored().cuboid(-2.13F, -6.9886F, -2.0086F, 10.0F, 8.0F, 10.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.49F, 10.3066F, -3.0F, 0.0283F, 0.0405F, -1.4406F));

        leftHand.addChild("cube_r6", ModelPartBuilder.create().uv(198, 88).mirrored().cuboid(0.5F, -0.5F, -3.01F, 2.0F, 4.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-7.375F, -5.125F, 0.5F, 0.0F, 0.0F, 0.0873F));

        leftHand.addChild("cube_r7", ModelPartBuilder.create().uv(7, 193).cuboid(-2.5F, -0.5F, -3.01F, 5.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -1.2F, 0.5F, 0.0F, 0.0F, -0.0436F));

        leftHand.addChild("cube_r8", ModelPartBuilder.create().uv(0, 137).mirrored().cuboid(-9.0F, 0.0F, -6.0F, 11.0F, 9.0F, 12.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(2.0F, -0.7F, -0.3F, 0.0F, 0.0F, 0.3491F));

        ModelPartData leftHand1 = leftHand.addChild("leftHand1", ModelPartBuilder.create().uv(157, 202).cuboid(-3.7367F, -3.2984F, -1.7123F, 1.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.1164F, 13.5362F, 0.2F, 0.1055F, 0.3306F, -0.4772F));

        leftHand1.addChild("cube_r9", ModelPartBuilder.create().uv(192, 140).mirrored().cuboid(1.9225F, -2.7707F, -1.4506F, 5.0F, 6.0F, 5.0F, new Dilation(0.0F)).mirrored(false)
                .uv(182, 168).mirrored().cuboid(-1.9325F, -3.5043F, -2.8434F, 5.0F, 6.0F, 8.0F, new Dilation(0.0F)).mirrored(false)
                .uv(179, 32).mirrored().cuboid(-0.9325F, -8.5043F, -1.8434F, 5.0F, 14.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.281F, 8.12F, -0.8698F, 0.0071F, -0.0466F, -0.2132F));

        leftHand1.addChild("cube_r10", ModelPartBuilder.create().uv(82, 189).mirrored().cuboid(0.6442F, -6.4885F, -0.8998F, 5.0F, 10.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.281F, 8.12F, -0.8698F, -0.004F, -0.047F, 0.0227F));

        ModelPartData leftHandItem = leftHand1.addChild("leftHandItem", ModelPartBuilder.create(), ModelTransform.of(0.0F, 11.0F, 0.5F, 0.0F, 0.0F, -0.4363F));

        leftHandItem.addChild("cube_r11", ModelPartBuilder.create().uv(122, 201).cuboid(4.7434F, 1.7457F, -2.9325F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-3.281F, -2.88F, -1.3698F, 0.1502F, 1.5237F, -0.0629F));

        leftHandItem.addChild("cube_r12", ModelPartBuilder.create().uv(169, 72).cuboid(6.1566F, 1.7457F, -2.0675F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-3.281F, -2.88F, -1.3698F, 2.9914F, -1.5237F, 3.0787F));

        leftHandItem.addChild("cube_r13", ModelPartBuilder.create().uv(0, 178).cuboid(-0.5F, -17.0F, -1.0F, 1.0F, 34.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-6.4913F, 0.449F, 11.2577F, 0.014F, 1.0442F, -0.2007F));

        leftHandItem.addChild("cube_r14", ModelPartBuilder.create().uv(71, 0).cuboid(-11.7233F, -3.9325F, -17.8434F, 1.0F, 2.0F, 34.0F, new Dilation(0.0F)), ModelTransform.of(-1.656F, 4.67F, -4.7948F, 0.0466F, 0.007F, -1.7837F));

        leftHandItem.addChild("cube_r15", ModelPartBuilder.create().uv(0, 67).cuboid(-6.2543F, 1.9325F, -17.8434F, 1.0F, 2.0F, 34.0F, new Dilation(0.0F)), ModelTransform.of(-4.981F, -10.605F, -4.7948F, -0.0466F, -0.007F, 1.3579F));

        leftHandItem.addChild("cube_r16", ModelPartBuilder.create().uv(124, 95).cuboid(-3.929F, 16.04F, -12.1096F, 2.0F, 1.0F, 23.0F, new Dilation(0.0F)), ModelTransform.of(-2.681F, -3.305F, -18.3697F, -0.7783F, -0.0466F, -0.2132F));

        leftHandItem.addChild("cube_r17", ModelPartBuilder.create().uv(118, 55).cuboid(-3.939F, -12.0975F, -16.2521F, 2.0F, 1.0F, 23.0F, new Dilation(0.0F)), ModelTransform.of(-2.781F, -3.705F, -18.7697F, 0.7925F, -0.0466F, -0.2132F));

        leftHandItem.addChild("cube_r18", ModelPartBuilder.create().uv(71, 37).cuboid(-3.9278F, -14.8975F, -18.9521F, 1.0F, 22.0F, 22.0F, new Dilation(0.0F)), ModelTransform.of(-2.656F, -3.305F, -14.3697F, 0.7925F, -0.0466F, -0.2132F));

        leftHandItem.addChild("cube_r19", ModelPartBuilder.create().uv(0, 0).cuboid(-3.9325F, -13.2543F, -17.8434F, 1.0F, 32.0F, 34.0F, new Dilation(0.0F)), ModelTransform.of(-3.281F, -2.88F, -4.7948F, 0.0071F, -0.0466F, -0.2132F));

        leftHandItem.addChild("cube_r20", ModelPartBuilder.create().uv(183, 0).cuboid(0.0675F, 1.7457F, -4.8434F, 2.0F, 2.0F, 11.0F, new Dilation(0.0F)), ModelTransform.of(-3.281F, -2.88F, -1.3698F, 0.0071F, -0.0466F, -0.2132F));

        ModelPartData rightHand = hand.addChild("rightHand", ModelPartBuilder.create(), ModelTransform.of(12.8488F, -27.1F, 1.9514F, 0.0F, 0.0F, 0.0873F));

        rightHand.addChild("cube_r21", ModelPartBuilder.create().uv(198, 88).cuboid(-2.5F, -0.5F, -3.01F, 2.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(9.7777F, -9.3306F, -1.7966F, 0.0F, 0.0F, -0.1745F));

        rightHand.addChild("cube_r22", ModelPartBuilder.create().uv(0, 159).cuboid(-7.87F, -6.9886F, -2.0086F, 10.0F, 8.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(3.1677F, 5.9261F, -5.2966F, 0.0283F, -0.0405F, 1.4406F));

        rightHand.addChild("cube_r23", ModelPartBuilder.create().uv(30, 197).cuboid(-2.5F, -0.5F, -3.01F, 5.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(2.1777F, -4.8805F, -1.7966F, 0.0F, 0.0F, 0.0436F));

        rightHand.addChild("cube_r24", ModelPartBuilder.create().uv(0, 137).cuboid(-2.0F, 0.0F, -6.0F, 11.0F, 9.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.6777F, -4.6555F, -2.5966F, 0.0F, 0.0F, -0.3491F));

        ModelPartData rightHand1 = rightHand.addChild("rightHand1", ModelPartBuilder.create().uv(146, 202).cuboid(-0.1669F, 0.2399F, -1.8088F, 1.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(5.0243F, 6.9839F, -2.5766F, 0.218F, 0.0094F, -0.1299F));

        rightHand1.addChild("cube_r25", ModelPartBuilder.create().uv(192, 140).cuboid(-6.9225F, -2.7707F, -1.4506F, 5.0F, 6.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.6774F, 10.3834F, -0.9662F, 0.0071F, 0.0466F, 0.2132F));

        rightHand1.addChild("cube_r26", ModelPartBuilder.create().uv(182, 168).cuboid(-3.0675F, -3.5043F, -2.8434F, 5.0F, 6.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.3774F, 10.8084F, -0.9662F, 0.0071F, 0.0466F, 0.2132F));

        rightHand1.addChild("cube_r27", ModelPartBuilder.create().uv(82, 189).cuboid(-5.6442F, -6.4885F, -0.8998F, 5.0F, 10.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.3774F, 11.2834F, -0.9662F, -0.004F, 0.047F, -0.0227F));

        rightHand1.addChild("cube_r28", ModelPartBuilder.create().uv(179, 32).cuboid(-4.0675F, -8.5043F, -1.8434F, 5.0F, 14.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.3774F, 11.3334F, -0.9662F, 0.0071F, 0.0466F, 0.2132F));

        ModelPartData waist = body.addChild("waist", ModelPartBuilder.create().uv(142, 0).cuboid(-6.0F, -14.0F, -4.01F, 12.0F, 12.0F, 8.0F, new Dilation(0.0F))
				.uv(118, 37).cuboid(-11.0F, -22.8F, -4.0F, 22.0F, 9.0F, 8.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        waist.addChild("cube_r29", ModelPartBuilder.create().uv(196, 198).cuboid(-1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(4.561F, -6.5932F, 2.9664F, 0.2657F, -0.8696F, -0.3421F));

        waist.addChild("cube_r30", ModelPartBuilder.create().uv(185, 198).cuboid(-1.0F, -5.5F, -1.0F, 2.0F, 11.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-4.561F, -6.5932F, 2.9664F, 0.2657F, 0.8696F, 0.3421F));

        waist.addChild("cube_r31", ModelPartBuilder.create().uv(182, 183).cuboid(-1.0F, -4.5F, -5.0F, 2.0F, 5.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(-0.439F, 2.1805F, 2.5F, -1.5708F, -1.0908F, 1.5708F));

        waist.addChild("cube_r32", ModelPartBuilder.create().uv(101, 172).cuboid(0.0F, -4.5F, -4.0F, 1.0F, 14.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(-0.439F, -7.5695F, -2.875F, -1.5708F, 1.3526F, -1.5708F));

        waist.addChild("cube_r33", ModelPartBuilder.create().uv(96, 125).cuboid(0.0F, -4.5F, -5.0F, 1.0F, 14.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(-0.439F, -7.5695F, 4.0F, 1.5708F, -1.3526F, -1.5708F));

        waist.addChild("cube_r34", ModelPartBuilder.create().uv(7, 178).cuboid(-5.5017F, -2.8344F, -4.0F, 6.0F, 6.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(8.6F, -11.25F, 0.0F, 0.0F, 0.0F, 0.3054F));

        waist.addChild("cube_r35", ModelPartBuilder.create().uv(174, 153).cuboid(-0.4983F, -2.8344F, -4.0F, 6.0F, 6.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-8.6F, -11.25F, 0.0F, 0.0F, 0.0F, -0.3054F));

        waist.addChild("cube_r36", ModelPartBuilder.create().uv(151, 186).cuboid(-0.5F, 1.0F, -4.0F, 2.0F, 7.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-5.75F, -9.25F, 0.0F, 0.0F, 0.0F, 0.1745F));

        waist.addChild("cube_r37", ModelPartBuilder.create().uv(137, 201).cuboid(-0.5F, -5.5F, 0.5F, 1.0F, 8.0F, 3.0F, new Dilation(0.0F))
                .uv(202, 36).cuboid(-0.5F, -5.5F, -3.4F, 1.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(8.639F, 2.0185F, -0.5F, 0.0F, 0.0F, -0.3491F));

        waist.addChild("cube_r38", ModelPartBuilder.create().uv(53, 197).cuboid(-0.5F, -5.5F, 0.5F, 1.0F, 8.0F, 3.0F, new Dilation(0.0F))
                .uv(172, 186).cuboid(-0.5F, -5.5F, -3.4F, 1.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-8.639F, 2.0185F, -0.5F, 0.0F, 0.0F, 0.3491F));

        waist.addChild("cube_r39", ModelPartBuilder.create().uv(185, 72).cuboid(-1.5F, 1.0F, -4.0F, 2.0F, 7.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(5.75F, -9.25F, 0.0F, 0.0F, 0.0F, -0.1745F));

        waist.addChild("cube_r40", ModelPartBuilder.create().uv(90, 149).cuboid(-1.5F, -4.9F, -0.9F, 13.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.75F, -20.8F, 3.9F, -1.153F, 1.0515F, -0.6902F));

        waist.addChild("cube_r41", ModelPartBuilder.create().uv(175, 117).cuboid(-4.9926F, -0.8604F, -0.3272F, 10.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-4.575F, -23.625F, 4.275F, -1.58F, -0.0962F, 0.1888F));

        waist.addChild("cube_r42", ModelPartBuilder.create().uv(175, 114).cuboid(-5.0074F, -0.8604F, -0.3272F, 10.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(4.8F, -23.625F, 4.275F, -1.58F, 0.0962F, -0.1888F));

        waist.addChild("cube_r43", ModelPartBuilder.create().uv(142, 32).cuboid(-12.5F, -4.9F, -0.9F, 14.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-3.75F, -20.8F, 3.9F, -1.153F, -1.0515F, 0.6902F));

        waist.addChild("cube_r44", ModelPartBuilder.create().uv(71, 82).cuboid(-15.0F, -2.0F, -5.0F, 29.0F, 3.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(0.25F, -23.725F, 0.475F, -0.3054F, 0.0F, 0.0F));

        waist.addChild("cube_r45", ModelPartBuilder.create().uv(148, 80).cuboid(-8.0F, -6.5F, -1.0F, 16.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -18.3F, -4.0F, 0.1745F, 0.0F, 0.0F));

        waist.addChild("cube_r46", ModelPartBuilder.create().uv(90, 154).mirrored().cuboid(-13.0F, -4.5F, -4.0F, 13.0F, 9.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.275F, -18.3F, 3.05F, -0.0946F, -0.1022F, 0.139F));

        waist.addChild("cube_r47", ModelPartBuilder.create().uv(90, 154).cuboid(0.0F, -4.5F, -4.0F, 13.0F, 9.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-0.275F, -18.3F, 3.05F, -0.0946F, 0.1022F, -0.139F));

        ModelPartData head = waist.addChild("head", ModelPartBuilder.create().uv(41, 165).cuboid(-4.0F, -9.0F, -2.6F, 8.0F, 9.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -24.9F, 0.6F));

		ModelPartData headLayer = head.addChild("headLayer", ModelPartBuilder.create().uv(161, 120).cuboid(-5.0F, -9.99F, -4.99F, 10.0F, 10.0F, 9.0F, new Dilation(0.0F))
				.uv(117, 125).cuboid(-1.0F, -7.0F, -6.0F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.4F));

        headLayer.addChild("cube_r48", ModelPartBuilder.create().uv(202, 30).cuboid(-0.9621F, -0.85F, -3.4995F, 4.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-3.7779F, 0.5F, 3.6075F, -1.5708F, -0.3927F, 0.0F));

        headLayer.addChild("cube_r49", ModelPartBuilder.create().uv(7, 202).cuboid(-3.0379F, -0.85F, -3.4995F, 4.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(3.7779F, 0.5F, 3.6075F, -1.5708F, 0.3927F, 0.0F));

        headLayer.addChild("cube_r50", ModelPartBuilder.create().uv(117, 143).cuboid(-1.01F, -2.5F, -0.5F, 2.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.25F, 4.26F, 0.3054F, 0.0F, 0.0F));

        headLayer.addChild("cube_r51", ModelPartBuilder.create().uv(203, 158).cuboid(0.2101F, -2.0F, -6.1985F, 4.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-0.05F, -7.3F, -1.44F, 2.0252F, 0.8785F, -1.0053F));

        headLayer.addChild("cube_r52", ModelPartBuilder.create().uv(143, 189).cuboid(-1.8755F, -7.3851F, -7.095F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.05F, -7.3F, -1.44F, 1.3578F, -0.9519F, -1.4414F));

        headLayer.addChild("cube_r53", ModelPartBuilder.create().uv(143, 198).cuboid(6.3851F, 0.1145F, -7.095F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.05F, -7.3F, -1.44F, 1.4213F, 0.6028F, -1.701F));

        headLayer.addChild("cube_r54", ModelPartBuilder.create().uv(203, 152).cuboid(-2.5F, -2.0F, -1.5F, 4.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.95F, -7.3F, -1.44F, 2.0252F, -0.8785F, 1.0053F));

        headLayer.addChild("cube_r55", ModelPartBuilder.create().uv(185, 88).cuboid(-3.5F, 1.0F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-5.9F, -11.075F, -5.74F, 1.4213F, -0.6028F, 1.701F));

        headLayer.addChild("cube_r56", ModelPartBuilder.create().uv(117, 134).cuboid(-1.01F, -2.5F, -0.5F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-5.9F, -11.075F, -5.74F, 1.3578F, 0.9519F, 1.4414F));

        ModelPartData layer = waist.addChild("layer", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -23.0F, -4.0F));

        layer.addChild("cube_r57", ModelPartBuilder.create().uv(124, 120).cuboid(-9.0F, -29.5F, -1.0F, 18.0F, 33.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.8F, -3.25F, 2.9671F, 0.0F, 0.0F));

        ModelPartData rightHandItem = waist.addChild("rightHandItem", ModelPartBuilder.create().uv(122, 173).cuboid(-0.5062F, -1.0054F, -19.7736F, 1.0F, 2.0F, 13.0F, new Dilation(0.0F))
				.uv(62, 204).cuboid(-1.4067F, -2.0054F, -21.2487F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F))
				.uv(73, 204).cuboid(-1.0067F, -3.0054F, -8.0486F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F))
				.uv(71, 95).cuboid(-0.5067F, -2.0054F, -6.0486F, 1.0F, 4.0F, 25.0F, new Dilation(0.0F))
				.uv(206, 182).cuboid(-0.0202F, -4.0054F, -6.0486F, 0.0F, 8.0F, 34.0F, new Dilation(0.0F))
				.uv(82, 204).cuboid(-0.5067F, -5.0054F, -7.7986F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F))
				.uv(93, 204).cuboid(-0.5067F, 3.9946F, -7.7986F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.9165F, -9.1241F, -5.5954F, 0.0F, 1.5708F, 2.2253F));

        rightHandItem.addChild("cube_r58", ModelPartBuilder.create().uv(63, 182).cuboid(-0.9999F, -4.9109F, -3.65F, 2.0F, 3.0F, 1.0F, new Dilation(0.01F)), ModelTransform.of(-0.0067F, 0.5946F, -5.8487F, -0.5236F, 0.0F, 0.0F));

        rightHandItem.addChild("cube_r59", ModelPartBuilder.create().uv(174, 168).cuboid(-0.9999F, -4.9109F, 1.682F, 2.0F, 3.0F, 1.0F, new Dilation(0.01F)), ModelTransform.of(-0.0067F, 8.0946F, -7.0486F, 0.5236F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 512, 512);
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
		this.updateAnimation(entity.SUMMON_ANI,GrandKnightEntityModelAnimation.summon,animationProgress,1.0f);
		this.updateAnimation(entity.STAGE_2_START_APART_ANI, GrandKnightEntityModelAnimation.levelTwo,animationProgress,1.0f);
		this.updateAnimation(entity.GRAB_ANI, GrandKnightEntityModelAnimation.grab,animationProgress,1.0f);
		this.updateAnimation(entity.SHIELD_SMASH_ANI, GrandKnightEntityModelAnimation.shieldSmash,animationProgress,1.0f);
		this.updateAnimation(entity.SHIELD_SLAP_ANI, GrandKnightEntityModelAnimation.shieldSlap,animationProgress,1.0f);
		this.updateAnimation(entity.EARTH_SHAKER_ANI, GrandKnightEntityModelAnimation.earthshaker,animationProgress,1.0f);

		this.head.yaw = headYaw * (float) (Math.PI / 180.0);
		this.head.pitch = -headPitch * (float) (Math.PI / 180.0);
	}
}