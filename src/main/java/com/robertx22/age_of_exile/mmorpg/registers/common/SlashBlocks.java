package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.database.data.league.LeagueMechanics;
import com.robertx22.age_of_exile.database.data.profession.ProfessionBlock;
import com.robertx22.age_of_exile.database.data.profession.all.Professions;
import com.robertx22.age_of_exile.maps.MapBlock;
import com.robertx22.age_of_exile.mechanics.base.LeagueControlBlock;
import com.robertx22.age_of_exile.mechanics.base.LeagueTeleportBlock;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.vanilla_mc.blocks.BlackHoleBlock;
import com.robertx22.age_of_exile.vanilla_mc.blocks.TotemBlock;

import java.util.HashMap;

public class SlashBlocks {

    public static void init() {

        for (String p : Professions.STATION_PROFESSIONS) {
            STATIONS.put(p, Def.block(p + "_station", () -> new ProfessionBlock(p)));
        }
    }

    public static RegObj<BlackHoleBlock> BLACK_HOLE = Def.block("black_hole", () -> new BlackHoleBlock());
    public static RegObj<TotemBlock> BLUE_TOTEM = Def.block("blue_totem", () -> new TotemBlock());
    public static RegObj<TotemBlock> GREEN_TOTEM = Def.block("green_totem", () -> new TotemBlock());
    public static RegObj<TotemBlock> GUARD_TOTEM = Def.block("guard_totem", () -> new TotemBlock());
    public static RegObj<TotemBlock> PROJECTILE_TOTEM = Def.block("attack_totem", () -> new TotemBlock());
    public static RegObj<TotemBlock> THORN_BUSH = Def.block("thorn_bush", () -> new TotemBlock());
    public static RegObj<TotemBlock> MAGMA_FLOWER = Def.block("magma_flower", () -> new TotemBlock());
    public static RegObj<TotemBlock> FROST_FLOWER = Def.block("frost_flower", () -> new TotemBlock());
    public static RegObj<TotemBlock> TRAP = Def.block("trap", () -> new TotemBlock());
    public static RegObj<TotemBlock> GLYPH = Def.block("glyph", () -> new TotemBlock());
    public static RegObj<MapBlock> MAP = Def.block("teleporter", () -> new MapBlock());
    public static RegObj<LeagueControlBlock> LEAGUE_CONTROL = Def.block("league", () -> new LeagueControlBlock());
    public static RegObj<LeagueTeleportBlock> HARVEST_TELEPORT = Def.block("harvest_teleport", () -> new LeagueTeleportBlock(LeagueMechanics.HARVEST_ID));


    public static HashMap<String, RegObj<ProfessionBlock>> STATIONS = new HashMap<>();


}
