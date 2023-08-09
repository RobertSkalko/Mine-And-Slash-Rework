package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.maps.MapBlockEntity;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class SlashBlockEntities {

    public static void init() {

    }

    public static RegObj<BlockEntityType<MapBlockEntity>> MAP = Def.blockEntity("map", () -> BlockEntityType.Builder.of(MapBlockEntity::new, SlashBlocks.MAP.get()).build(null));

}
