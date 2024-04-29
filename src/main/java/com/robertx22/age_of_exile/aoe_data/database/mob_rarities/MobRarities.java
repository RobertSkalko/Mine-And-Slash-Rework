package com.robertx22.age_of_exile.aoe_data.database.mob_rarities;

import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.ChatFormatting;

public class MobRarities implements ExileRegistryInit {


    @Override
    public void registerAll() {


        MobRarity.of(IRarity.COMMON_ID, "Common", 5000, 0, 0, 0, ChatFormatting.GRAY);
        MobRarity.of(IRarity.UNCOMMON, "Uncommon", 1000, 0, 0.25F, 0, ChatFormatting.GREEN);
        MobRarity.of(IRarity.RARE_ID, "Rare", 500, 3, 1, 1, ChatFormatting.AQUA);
        MobRarity.of(IRarity.EPIC_ID, "Epic", 250, 20, 2, 1, ChatFormatting.LIGHT_PURPLE);
        MobRarity.of(IRarity.LEGENDARY_ID, "Legendary", 50, 40, 4, 2, ChatFormatting.GOLD);
        MobRarity.of(IRarity.MYTHIC_ID, "Mythic", 25, 50, 8, 3, ChatFormatting.DARK_PURPLE);
        MobRarity.of(IRarity.UBER, "Uber", 0, 1, 25, 0, ChatFormatting.RED).setForceCustomHP(20);


        MobRarity.of(IRarity.SUMMON_ID, "Summon", 0, 0, 3, 0, ChatFormatting.YELLOW);

    }
}
