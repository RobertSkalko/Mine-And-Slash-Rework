package com.robertx22.age_of_exile.database.data.league;

import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.maps.LeagueData;
import com.robertx22.age_of_exile.maps.MapData;
import com.robertx22.age_of_exile.mechanics.base.LeagueBlockData;
import com.robertx22.age_of_exile.mechanics.base.LeagueControlBlockEntity;
import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public abstract class LeagueMechanic implements ExileRegistry<LeagueMechanic> {
    public static MapField<String> STRUCTURE = new MapField<>("structure");


    public abstract LeagueStructure getStructure();

    public float getBaseSpawnChance() {
        return 100;
    }

    public abstract int getDefaultSpawns();

    public final void onMapStartSetupBase(LeagueData data) {

        data.remainingSpawns = getDefaultSpawns();

        if (!getStructure().getPieces().list.isEmpty()) {
            data.map.put(STRUCTURE, RandomUtils.weightedRandom(getStructure().getPieces().list).folder);
        }
    }

    public abstract void onMapStartSetup(LeagueData data);

    public abstract void onKillMob(MapData map, LootInfo info);


    public abstract void spawnMechanicInMap(ServerLevel level, BlockPos pos);


    public boolean isEmpty() {
        return false;
    }

    public abstract void onTick(MapData map, ServerLevel level, BlockPos pos, LeagueControlBlockEntity be, LeagueBlockData data);


    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.LEAGUE_MECHANIC;
    }
}
