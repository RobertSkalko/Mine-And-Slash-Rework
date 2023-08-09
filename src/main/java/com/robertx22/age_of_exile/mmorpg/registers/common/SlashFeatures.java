package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.maps.feature.DungeonFeature;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import net.minecraft.world.level.levelgen.feature.Feature;

public class SlashFeatures {

    public static RegObj<Feature<?>> DUNGEON = Def.feature("dungeon", () -> new DungeonFeature());

    public static void init() {
       
    }
}
