package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.vanilla_mc.blocks.runeword_station.RuneWordStationTile;
import net.minecraft.tileentity.TileEntityType;

public class SlashBlockEntities {

    public static void init() {

    }

    public static RegObj<TileEntityType<RuneWordStationTile>> RUNEWORD = Def.blockEntity("runeword", () -> TileEntityType.Builder.of(RuneWordStationTile::new, SlashBlocks.RUNEWORD.get())
            .build(null));
 
}
