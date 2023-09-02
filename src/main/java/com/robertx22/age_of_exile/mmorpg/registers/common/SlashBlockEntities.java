package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.database.data.profession.ProfessionBlockEntity;
import com.robertx22.age_of_exile.maps.MapBlockEntity;
import com.robertx22.age_of_exile.mechanics.base.LeagueControlBlockEntity;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class SlashBlockEntities {

    public static void init() {

    }

    public static RegObj<BlockEntityType<MapBlockEntity>> MAP = Def.blockEntity("map", () -> BlockEntityType.Builder.of(MapBlockEntity::new, SlashBlocks.MAP.get()).build(null));
    public static RegObj<BlockEntityType<LeagueControlBlockEntity>> LEAGUE = Def.blockEntity("league", () -> BlockEntityType.Builder.of(LeagueControlBlockEntity::new, SlashBlocks.LEAGUE_CONTROL.get()).build(null));

    public static RegObj<BlockEntityType<ProfessionBlockEntity>> PROFESSION = Def.blockEntity("profession", () -> {

        return BlockEntityType.Builder.of(ProfessionBlockEntity::new,
                SlashBlocks.COOKING_STATION.get(),
                SlashBlocks.ALCHEMY_STATION.get(),
                SlashBlocks.ENCHANTING_STATION.get()).build(null);
    });

}
