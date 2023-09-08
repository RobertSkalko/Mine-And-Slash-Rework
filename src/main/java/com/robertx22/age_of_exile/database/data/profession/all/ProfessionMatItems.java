package com.robertx22.age_of_exile.database.data.profession.all;

import com.robertx22.age_of_exile.database.data.profession.all.misc_drops.*;
import com.robertx22.age_of_exile.database.data.profession.items.MaterialItem;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.world.item.Item;

import java.util.HashMap;

public class ProfessionMatItems {

    public static HashMap<String, HashMap<SkillItemTier, RegObj<Item>>> TIERED_MAIN_MATS = new HashMap<>();

    public static Essences ESSENCES = null;
    public static RareEssences RARE_ESSENCES = null;
    public static RareFarming RARE_FARMING = null;
    public static FarmingCommon COMMON_FARMING = null;
    public static MiningCommon COMMON_MINING = null;
    public static MiningRare RARE_MINING = null;

    public static void init() {

        ESSENCES = new Essences();
        RARE_ESSENCES = new RareEssences();
        RARE_FARMING = new RareFarming();
        COMMON_FARMING = new FarmingCommon();
        COMMON_MINING = new MiningCommon();
        RARE_MINING = new MiningRare();

        for (String prof : Professions.ALL) {
            TIERED_MAIN_MATS.put(prof, new HashMap<>());
        }

        for (SkillItemTier tier : SkillItemTier.values()) {

            TIERED_MAIN_MATS.get(Professions.MINING).put(tier, Def.item("material/mining/" + tier.tier, () -> new MaterialItem(tier, "Ore")));
            TIERED_MAIN_MATS.get(Professions.FARMING).put(tier, Def.item("material/farming/" + tier.tier, () -> new MaterialItem(tier, "Produce")));
            TIERED_MAIN_MATS.get(Professions.HUSBANDRY).put(tier, Def.item("material/meat/" + tier.tier, () -> new MaterialItem(tier, "Raw Meat")));
            TIERED_MAIN_MATS.get(Professions.FISHING).put(tier, Def.item("material/fishing/" + tier.tier, () -> new MaterialItem(tier, "Raw Fish")));
        }

    }
}
