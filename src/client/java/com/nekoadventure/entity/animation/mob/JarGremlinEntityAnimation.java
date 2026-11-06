package com.nekoadventure.entity.animation.mob;// Save this class in your mod and generate all required imports

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
public class JarGremlinEntityAnimation {
	public static final Animation move = Animation.Builder.create(1.0F).looping()
			.addBoneAnimation("main", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.125F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.375F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.625F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.875F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0.0F, 5.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2917F, AnimationHelper.createRotationalVector(0.0F, -5.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(0.0F, 5.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.7917F, AnimationHelper.createRotationalVector(0.0F, -5.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("arm_left", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(45.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.75F, AnimationHelper.createRotationalVector(-40.0021F, 0.1287F, -0.1495F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("arm_right", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(-40.0021F, 0.1287F, -0.1495F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.75F, AnimationHelper.createRotationalVector(45.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("leg_left", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(-30.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.75F, AnimationHelper.createRotationalVector(30.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("leg_right", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(30.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.75F, AnimationHelper.createRotationalVector(-30.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.build();

	public static final Animation attack = Animation.Builder.create(0.5F).looping()
			.addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-12.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(-12.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(12.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-5.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("arm_left", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-170.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(-170.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-25.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-7.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("arm_left", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("arm_right", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-170.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(-170.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-25.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167F, AnimationHelper.createRotationalVector(5.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("leg_left", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-40.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-40.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-12.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-5.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("leg_right", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(22.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createRotationalVector(22.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createRotationalVector(20.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167F, AnimationHelper.createRotationalVector(5.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("leg_right", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.build();

	public static final Animation shoot = Animation.Builder.create(0.5F).looping()
			.addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-35.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("arm_left", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833F, AnimationHelper.createRotationalVector(-97.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(62.1F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("arm_right", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833F, AnimationHelper.createRotationalVector(-97.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(62.1F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("jar", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833F, AnimationHelper.createRotationalVector(-30.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.build();
}