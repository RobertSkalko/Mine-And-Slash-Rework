package com.robertx22.mine_and_slash.aoe_data.database.mob_rarities;

import com.robertx22.mine_and_slash.database.data.rarities.MobRarity;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.ChatFormatting;

public class MobRarities implements ExileRegistryInit {


    @Override
    public void registerAll() {


        MobRarity.of(IRarity.COMMON_ID, "Common", 5000, 0, 0, 0, ChatFormatting.GRAY, false);
        MobRarity.of(IRarity.UNCOMMON, "Uncommon", 1000, 0, 0.25F, 1, ChatFormatting.GREEN, false);
        MobRarity.of(IRarity.RARE_ID, "Rare", 500, 3, 1, 1, ChatFormatting.AQUA, false);
        MobRarity.of(IRarity.EPIC_ID, "Epic", 250, 20, 2, 2, ChatFormatting.LIGHT_PURPLE, false);
        MobRarity.of(IRarity.LEGENDARY_ID, "Legendary", 50, 40, 4, 3, ChatFormatting.GOLD, false);
        MobRarity.of(IRarity.MYTHIC_ID, "Mythic", 25, 50, 8, 3, ChatFormatting.DARK_PURPLE, false);
        MobRarity.of(IRarity.UBER, "Uber", 0, 1, 15, 0, ChatFormatting.RED, false).setForceCustomHP(20);


        MobRarity.of(IRarity.SUMMON_ID, "Summon", 0, 0, 1, 0, ChatFormatting.YELLOW, true);

    }
}
