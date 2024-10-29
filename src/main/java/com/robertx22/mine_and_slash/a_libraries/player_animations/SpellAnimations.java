package com.robertx22.mine_and_slash.a_libraries.player_animations;

import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;

public class SpellAnimations {

    public static HashMap<String, AnimationHolder> MAP = new HashMap<>();

    public static ResourceLocation ANIMATION_RESOURCE = new ResourceLocation(SlashRef.MODID, "animation");

    // mine
    public static final AnimationHolder SPIN = of("sword_spin");

    public static final AnimationHolder STEADY_CAST = of("steady_cast").hidesOffhand();
    public static final AnimationHolder HAND_UP_CAST = of("hand_up_cast");
    public static final AnimationHolder CAST_FINISH = of("cast_finish");
    public static final AnimationHolder STAFF_CAST_WAVE_LOOP = of("staff_cast_loop");
    public static final AnimationHolder STAFF_CAST_FINISH = of("staff_cast_shoot");
    public static final AnimationHolder MELEE_SLASH = of("sword_slash_double");
    public static final AnimationHolder SINGLE_MELEE_SLASH = of("sword_slash_single");

    public static final AnimationHolder SHOOT_ARROW_FAST = of("spell_arrow").hidesOffhand();
    public static final AnimationHolder SINGLE_ARROW_SHOT = of("single_arrow_shot").hidesOffhand(); // needs reworking
    public static final AnimationHolder TAUNT = of("spell_taunt");
    public static final AnimationHolder PULL = of("spell_pull");
    public static final AnimationHolder TOUCH_GROUND = of("staff_ground");
    public static final AnimationHolder CHARGE = of("spell_charge");
    public static final AnimationHolder THROW = of("cast_throwable");


    private static AnimationHolder of(String id) {
        var o = new AnimationHolder(id);
        MAP.put(id, o);
        return o;
    }

}
