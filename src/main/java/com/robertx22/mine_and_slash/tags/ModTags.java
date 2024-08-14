package com.robertx22.mine_and_slash.tags;

import com.robertx22.mine_and_slash.tags.all.*;

public class ModTags {

    public static EffectTags EFFECT = new EffectTags();
    public static MobTags MOB = new MobTags();
    public static SpellTags SPELL = new SpellTags();
    public static SlotTags GEAR_SLOT = new SlotTags();
    public static ElementTags ELEMENT = new ElementTags();
    public static DungeonTags DUNGEON = new DungeonTags();
    public static MapAffixTags MAP_AFFIX = new MapAffixTags();

    public static void init() {

        EffectTags.init();
        MobTags.init();
        SlotTags.init();
        SpellTags.init();
        ElementTags.init();
        DungeonTags.init();
        MapAffixTags.init();
    }


}
