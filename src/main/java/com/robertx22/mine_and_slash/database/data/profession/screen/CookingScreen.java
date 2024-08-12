package com.robertx22.mine_and_slash.database.data.profession.screen;

import com.robertx22.mine_and_slash.database.data.profession.all.ProfessionMatItems;
import com.robertx22.mine_and_slash.database.data.profession.all.Professions;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;

import java.util.ArrayList;

public class CookingScreen extends CraftingStationScreen {
    public CookingScreen(CraftingStationMenu pMenu, Inventory pPlayerInventory, Component txt) {

        super(Professions.COOKING, pMenu, pPlayerInventory, txt);

        var list = new ArrayList<Item>();

        list.addAll(ProfessionMatItems.TIERED_MAIN_MATS.get(Professions.FARMING).values().stream().map(x -> x.get()).toList());
        list.addAll(ProfessionMatItems.TIERED_MAIN_MATS.get(Professions.FISHING).values().stream().map(x -> x.get()).toList());
        list.addAll(ProfessionMatItems.TIERED_MAIN_MATS.get(Professions.HUSBANDRY).values().stream().map(x -> x.get()).toList());

        this.primaryTier = new PrimaryMatInfoButton.InfoData(
                Words.PRIMARY_TIER_MAT,
                ProfessionMatItems.TIERED_MAIN_MATS.get(Professions.HUSBANDRY).get(SkillItemTier.TIER0).get(),
                list
        );
    }
}
