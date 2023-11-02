package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.resources.ResourceLocation;

public class StartPerks implements ExileRegistryInit {

    public static String MAGE = "mage";
    public static String WARRIOR = "warrior";
    public static String RANGER = "ranger";
    public static String GUARDIAN = "guardian";


    @Override
    public void registerAll() {


        of(MAGE, "Mage",
                new OptScaleExactStat(9, DatapackStats.INT, ModType.FLAT),
                new OptScaleExactStat(5, DatapackStats.STR, ModType.FLAT),
                new OptScaleExactStat(5, DatapackStats.DEX, ModType.FLAT)
        );
        of(WARRIOR, "Warrior",
                new OptScaleExactStat(5, DatapackStats.INT, ModType.FLAT),
                new OptScaleExactStat(9, DatapackStats.STR, ModType.FLAT),
                new OptScaleExactStat(5, DatapackStats.DEX, ModType.FLAT)
        );
        of(RANGER, "Ranger",
                new OptScaleExactStat(5, DatapackStats.INT, ModType.FLAT),
                new OptScaleExactStat(5, DatapackStats.STR, ModType.FLAT),
                new OptScaleExactStat(9, DatapackStats.DEX, ModType.FLAT)
        );
        of(GUARDIAN, "Guardian",
                new OptScaleExactStat(7, DatapackStats.INT, ModType.FLAT),
                new OptScaleExactStat(7, DatapackStats.STR, ModType.FLAT),
                new OptScaleExactStat(5, DatapackStats.DEX, ModType.FLAT)
        );

        /*
        of(TEMPLAR, "Templar",
            new OptScaleExactStat(23, DatapackStats.INT, ModType.FLAT),
            new OptScaleExactStat(23, DatapackStats.STR, ModType.FLAT),
            new OptScaleExactStat(14, DatapackStats.DEX, ModType.FLAT)
        );

        of(DUELIST, "Duelist",
            new OptScaleExactStat(14, DatapackStats.INT, ModType.FLAT),
            new OptScaleExactStat(23, DatapackStats.STR, ModType.FLAT),
            new OptScaleExactStat(23, DatapackStats.DEX, ModType.FLAT)
        );

        of(BATTLE_MAGE, "Battle Mage",
            new OptScaleExactStat(23, DatapackStats.INT, ModType.FLAT),
            new OptScaleExactStat(14, DatapackStats.STR, ModType.FLAT),
            new OptScaleExactStat(23, DatapackStats.DEX, ModType.FLAT)
        );

        of(SCION, "Scion",
            new OptScaleExactStat(20, DatapackStats.INT, ModType.FLAT),
            new OptScaleExactStat(20, DatapackStats.STR, ModType.FLAT),
            new OptScaleExactStat(20, DatapackStats.DEX, ModType.FLAT)
        );

         */
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
