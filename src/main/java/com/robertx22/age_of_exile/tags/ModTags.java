package com.robertx22.age_of_exile.tags;

import com.robertx22.age_of_exile.tags.all.EffectTags;
import com.robertx22.age_of_exile.tags.all.MobTags;
import com.robertx22.age_of_exile.tags.all.SlotTags;
import com.robertx22.age_of_exile.tags.all.SpellTags;

public class ModTags {

    public static EffectTags EFFECT = new EffectTags();
    public static MobTags MOB = new MobTags();
    public static SpellTags SPELL = new SpellTags();
    public static SlotTags GEAR_SLOT = new SlotTags();

    public static void init() {

        EffectTags.init();
        MobTags.init();
        SlotTags.init();
        SpellTags.init();

    }


}
