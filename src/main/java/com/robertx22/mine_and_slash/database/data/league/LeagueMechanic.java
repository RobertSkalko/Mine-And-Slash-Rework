package com.robertx22.mine_and_slash.database.data.league;

import com.robertx22.mine_and_slash.database.data.spells.map_fields.MapField;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.maps.LeagueData;
import com.robertx22.mine_and_slash.maps.MapData;
import com.robertx22.mine_and_slash.maps.MapItemData;
import com.robertx22.mine_and_slash.mechanics.base.LeagueBlockData;
import com.robertx22.mine_and_slash.mechanics.base.LeagueControlBlockEntity;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
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
