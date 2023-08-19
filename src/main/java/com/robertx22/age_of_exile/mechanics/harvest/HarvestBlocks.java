package com.robertx22.age_of_exile.mechanics.harvest;

import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;

public class HarvestBlocks {

    public static RegObj<HarvestPlantBlock> BLUE = Def.block("harvest/blue", () -> new HarvestPlantBlock());
    public static RegObj<HarvestPlantBlock> PURPLE = Def.block("harvest/purple", () -> new HarvestPlantBlock());
    public static RegObj<HarvestPlantBlock> GREEN = Def.block("harvest/green", () -> new HarvestPlantBlock());

    public static void init() {

    }
}
