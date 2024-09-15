package com.robertx22.mine_and_slash.a_libraries.player_animations;

import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import net.minecraft.resources.ResourceLocation;

public class SpellAnimations {
    public static ResourceLocation ANIMATION_RESOURCE = new ResourceLocation(SlashRef.MODID, "animation");

    // mine
    public static final AnimationHolder SPIN = new AnimationHolder("sword_spin");

    public static final AnimationHolder STEADY_CAST = new AnimationHolder("steady_cast");
    public static final AnimationHolder HAND_UP_CAST = new AnimationHolder("hand_up_cast");
    public static final AnimationHolder CAST_FINISH = new AnimationHolder("cast_finish");
    public static final AnimationHolder STAFF_CAST_WAVE_LOOP = new AnimationHolder("staff_cast_loop");
    public static final AnimationHolder STAFF_CAST_FINISH = new AnimationHolder("staff_cast_shoot");
    public static final AnimationHolder MELEE_SLASH = new AnimationHolder("sword_slash_double");


    public static final AnimationHolder SHOOT_ARROW_FAST = new AnimationHolder("spell_arrow");
    public static final AnimationHolder SINGLE_ARROW_SHOT = new AnimationHolder("single_arrow_shot"); // needs reworking
    public static final AnimationHolder TAUNT = new AnimationHolder("spell_taunt");
    public static final AnimationHolder PULL = new AnimationHolder("spell_pull");
    public static final AnimationHolder TOUCH_GROUND = new AnimationHolder("staff_ground");
    public static final AnimationHolder CHARGE = new AnimationHolder("spell_charge");
    public static final AnimationHolder THROW = new AnimationHolder("cast_throwable");


}
