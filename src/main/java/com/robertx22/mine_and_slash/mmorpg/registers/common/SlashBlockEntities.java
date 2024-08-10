package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.mine_and_slash.database.data.profession.ProfessionBlock;
import com.robertx22.mine_and_slash.database.data.profession.ProfessionBlockEntity;
import com.robertx22.mine_and_slash.maps.MapBlockEntity;
import com.robertx22.mine_and_slash.mechanics.base.LeagueControlBlockEntity;
import com.robertx22.mine_and_slash.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.mine_and_slash.mmorpg.registers.deferred_wrapper.RegObj;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.stream.Collectors;

public class SlashBlockEntities {

    public static void init() {

    }

    public static RegObj<BlockEntityType<MapBlockEntity>> MAP = Def.blockEntity("map", () -> BlockEntityType.Builder.of(MapBlockEntity::new, SlashBlocks.MAP.get()).build(null));
    public static RegObj<BlockEntityType<LeagueControlBlockEntity>> LEAGUE = Def.blockEntity("league", () -> BlockEntityType.Builder.of(LeagueControlBlockEntity::new, SlashBlocks.LEAGUE_CONTROL.get()).build(null));

    public static RegObj<BlockEntityType<ProfessionBlockEntity>> PROFESSION = Def.blockEntity("profession", () -> {
        return BlockEntityType.Builder.of(ProfessionBlockEntity::new,
                SlashBlocks.STATIONS.values().stream().map(x -> x.get()).collect(Collectors.toList()).toArray(new ProfessionBlock[SlashBlocks.STATIONS.size()])
        ).build(null);
    });

}
