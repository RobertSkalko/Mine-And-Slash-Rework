package com.robertx22.mine_and_slash.aoe_data.database.perks;

import com.robertx22.mine_and_slash.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.mine_and_slash.database.OptScaleExactStat;
import com.robertx22.mine_and_slash.database.data.perks.Perk;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.resources.ResourceLocation;

public class StartPerksAddtl implements ExileRegistryInit {

    public static String ROGUE = "rogue";
    public static String DUELIST = "duelist";


    @Override
    public void registerAll() {

        of(ROGUE, "Rogue",
                new OptScaleExactStat(7, DatapackStats.INT, ModType.FLAT),
                new OptScaleExactStat(5, DatapackStats.STR, ModType.FLAT),
                new OptScaleExactStat(7, DatapackStats.DEX, ModType.FLAT)
        );
        of(DUELIST, "Duelist",
                new OptScaleExactStat(5, DatapackStats.INT, ModType.FLAT),
                new OptScaleExactStat(7, DatapackStats.STR, ModType.FLAT),
                new OptScaleExactStat(7, DatapackStats.DEX, ModType.FLAT)
        );

    }

    Perk of(String id, String locname, OptScaleExactStat... stats) {

        Perk perk = PerkBuilder.bigStat(id, locname, stats);
        perk.is_entry = true;
        perk.type = Perk.PerkType.START;
        perk.one_kind = "start";
        perk.icon = new ResourceLocation(SlashRef.MODID, "textures/gui/stat_icons/start/" + id + ".png")
                .toString();
        return perk;
    }
}
