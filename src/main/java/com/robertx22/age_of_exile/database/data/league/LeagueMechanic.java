package com.robertx22.age_of_exile.database.data.league;

import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.maps.LeagueData;
import com.robertx22.age_of_exile.maps.MapData;
import com.robertx22.age_of_exile.maps.MapItemData;
import com.robertx22.age_of_exile.mechanics.base.LeagueBlockData;
import com.robertx22.age_of_exile.mechanics.base.LeagueControlBlockEntity;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;

public abstract class LeagueMechanic implements ExileRegistry<LeagueMechanic>, IAutoLocName {
  
    public abstract LeagueStructure getStructure(MapItemData map);

    public float getBaseSpawnChance() {
        return 100;
    }

    public abstract int getDefaultSpawns();

    public MapField<String> getStructureId() {
        return new MapField<>(GUID() + "_structure");
    }

    public final void onMapStartSetupBase(MapItemData map, LeagueData data) {

        data.remainingSpawns = getDefaultSpawns();

        if (!getStructure(map).getPieces(map).list.isEmpty()) {
            data.map.put(getStructureId(), RandomUtils.weightedRandom(getStructure(map).getPieces(map).list).folder);
        }
    }

    public abstract void onMapStartSetup(LeagueData data);

    public abstract void onKillMob(MapData map, LootInfo info);


    public abstract void spawnMechanicInMap(ServerLevel level, BlockPos pos);


    public boolean isEmpty() {
        return false;
    }

    public abstract void onTick(MapData map, ServerLevel level, BlockPos pos, LeagueControlBlockEntity be, LeagueBlockData data);


    public abstract Block getTeleportBlock();

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.LEAGUE;
    }

    public abstract ChatFormatting getTextColor();

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".league." + GUID();
    }


    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.LEAGUE_MECHANIC;
    }
}
