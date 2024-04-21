package com.robertx22.age_of_exile.database.data.league;

import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.maps.LeagueData;
import com.robertx22.age_of_exile.maps.MapData;
import com.robertx22.age_of_exile.mechanics.base.LeagueBlockData;
import com.robertx22.age_of_exile.mechanics.base.LeagueControlBlockEntity;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class LeagueMechanics {

    public static String HARVEST_ID = "harvest";

    public static LeagueMechanic NONE = new LeagueMechanic() {


        @Override
        public AutoLocGroup locNameGroup() {
            return AutoLocGroup.LEAGUE;
        }

        @Override
        public ChatFormatting getTextColor() {
            return ChatFormatting.WHITE;
        }

        @Override
        public String locNameLangFileGUID() {
            return SlashRef.MODID + ".league." + GUID();
        }

        @Override
        public String locNameForLangFile() {
            return "empty";
        }

        @Override
        public LeagueStructure getStructure() {
            return LeagueStructure.EMPTY;
        }

        @Override
        public int getDefaultSpawns() {
            return 0;
        }

        @Override
        public void onMapStartSetup(LeagueData data) {

        }

        @Override
        public void onKillMob(MapData map, LootInfo info) {

        }

        @Override
        public void spawnMechanicInMap(ServerLevel level, BlockPos pos) {


        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public void onTick(MapData map, ServerLevel level, BlockPos pos, LeagueControlBlockEntity be, LeagueBlockData data) {

        }


        @Override
        public String GUID() {
            return "none";
        }

        @Override
        public int Weight() {
            return 0;
        }
    };

    public static LeagueMechanic HARVEST = new HarvestLeague();
    public static LeagueMechanic PROPHECY = new ProphecyLeague();


    public static void init() {
        HARVEST.registerToExileRegistry();
        PROPHECY.registerToExileRegistry();
    }
}
