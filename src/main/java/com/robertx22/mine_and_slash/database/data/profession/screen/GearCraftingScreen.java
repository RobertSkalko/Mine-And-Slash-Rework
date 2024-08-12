package com.robertx22.mine_and_slash.database.data.profession.screen;

import com.robertx22.mine_and_slash.database.data.profession.all.Professions;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GearCraftingScreen extends CraftingStationScreen {
    public GearCraftingScreen(CraftingStationMenu pMenu, Inventory pPlayerInventory, Component txt) {
        super(Professions.GEAR_CRAFTING, pMenu, pPlayerInventory, txt);
    }
}
