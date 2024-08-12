package com.robertx22.mine_and_slash.database.data.profession.screen;

import com.robertx22.mine_and_slash.database.data.profession.all.ProfessionMatItems;
import com.robertx22.mine_and_slash.database.data.profession.all.Professions;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GearCraftingScreen extends CraftingStationScreen {
    public GearCraftingScreen(CraftingStationMenu pMenu, Inventory pPlayerInventory, Component txt) {
        super(Professions.GEAR_CRAFTING, pMenu, pPlayerInventory, txt);

        this.primaryTier = new PrimaryMatInfoButton.InfoData(
                Words.PRIMARY_TIER_MAT,
                ProfessionMatItems.TIERED_MAIN_MATS.get(Professions.MINING).get(SkillItemTier.TIER0).get(),
                ProfessionMatItems.TIERED_MAIN_MATS.get(Professions.MINING).values().stream().map(x -> x.get()).toList()
        );
    }
}
