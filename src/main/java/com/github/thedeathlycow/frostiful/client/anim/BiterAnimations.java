package com.github.thedeathlycow.frostiful.client.anim;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

@Environment(EnvType.CLIENT)
public class BiterAnimations {
    public static final AnimationDefinition BITE = AnimationDefinition.Builder.withLength(0.225f)
            .addAnimation("head",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1125f, KeyframeAnimations.posVec(0f, 2f, -4f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.225f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("bottom",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1125f, KeyframeAnimations.degreeVec(27.5f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.225f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("top",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.1125f, KeyframeAnimations.degreeVec(-75f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.225f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR))).build();
}
