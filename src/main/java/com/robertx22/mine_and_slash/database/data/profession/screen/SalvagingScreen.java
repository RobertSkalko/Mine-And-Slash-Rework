package com.robertx22.mine_and_slash.database.data.profession.screen;

import com.robertx22.mine_and_slash.database.data.profession.all.Professions;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SalvagingScreen extends CraftingStationScreen {
    public SalvagingScreen(CraftingStationMenu pMenu, Inventory pPlayerInventory, Component txt) {
        super(Professions.SALVAGING, pMenu, pPlayerInventory, txt);
        BACKGROUND_LOCATION = new ResourceLocation(SlashRef.MODID, "textures/gui/salvage_station.png");
    }
}
