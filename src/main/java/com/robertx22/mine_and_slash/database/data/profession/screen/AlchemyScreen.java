package com.robertx22.mine_and_slash.database.data.profession.screen;

import com.robertx22.mine_and_slash.database.data.profession.all.Professions;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class AlchemyScreen extends CraftingStationScreen {
    public AlchemyScreen(CraftingStationMenu pMenu, Inventory pPlayerInventory, Component txt) {
        super(Professions.ALCHEMY, pMenu, pPlayerInventory, txt);
    }
}
