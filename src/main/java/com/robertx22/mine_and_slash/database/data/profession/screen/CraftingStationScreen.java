package com.robertx22.mine_and_slash.database.data.profession.screen;

import com.robertx22.mine_and_slash.database.data.profession.Profession;
import com.robertx22.mine_and_slash.database.data.profession.StationSyncData;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

// todo sepearate screens for each station
public abstract class CraftingStationScreen extends AbstractContainerScreen<CraftingStationMenu> {
    public ResourceLocation BACKGROUND_LOCATION = new ResourceLocation(SlashRef.MODID, "textures/gui/crafting_table2.png");


    Profession prof;

    public CraftingStationScreen(String prof, CraftingStationMenu pMenu, Inventory pPlayerInventory, Component txt) {
        super(pMenu, pPlayerInventory, ExileDB.Professions().get(prof).locName());
        this.prof = ExileDB.Professions().get(prof);
        this.imageWidth = 176;
        this.imageHeight = 166;

        this.titleLabelX = this.getGuiLeft() + imageWidth / 2;
        this.titleLabelY = -15;
    }


    public StationSyncData getSyncedData() {
        return StationSyncData.SYNCED_DATA;
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(new LockButton(leftPos + 79, topPos + 33, this));
        this.addRenderableWidget(new CraftButton(leftPos + 79, topPos + 51, this));


    }

    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);

    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        pGuiGraphics.drawString(this.font, this.title, this.titleLabelX - font.width(title) / 2, this.titleLabelY, ChatFormatting.YELLOW.getColor(), false);
        //pGuiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, ChatFormatting.WHITE.getColor(), false);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(BACKGROUND_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);

    }

}
