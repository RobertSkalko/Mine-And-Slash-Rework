package com.robertx22.mine_and_slash.characters.reworked_gui;

import com.robertx22.mine_and_slash.gui.bases.INamedScreen;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.stream.Collectors;

public class ToonScreen extends Screen implements INamedScreen {


    protected EditBox searchBox;

    private ToonList list;

    public ToonScreen() {
        super(Words.Characters.locName().withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD));
    }

    public ToonData selectedEntry = null;


    private void refreshFilterButtons() {

        this.children().stream().filter(x -> x instanceof ToonActionButton).collect(Collectors.toList()).forEach(e -> this.removeWidget(e));

        int x = width - ToonActionButton.WIDTH - 25;
        int y = 55;

        int spacing = ToonActionButton.HEIGHT + 5;

        for (ToonActionButton.Action action : ToonActionButton.Action.values()) {
            this.addRenderableWidget(new ToonActionButton(this, action, x, y));
            y += spacing;
        }

    }

    @Override
    public void renderBackground(GuiGraphics pGuiGraphics) {
        super.renderBackground(pGuiGraphics);
        /*
        if (this.minecraft.level != null) {
            pGuiGraphics.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.ScreenEvent.BackgroundRendered(this, pGuiGraphics));
        } else {
            this.renderDirtBackground(pGuiGraphics);
        }

         */

    }

    @Override
    public void tick() {
        this.searchBox.tick();
    }

    //  public Checkbox searchTooltipsCheckbox;

    // gotta call add filter button first or it cant be clicked for some reason
    @Override
    protected void init() {


        this.clearWidgets();

        refreshFilterButtons();

        this.searchBox = new EditBox(this.font, this.width / 2 - 100, 22, 200, 20, this.searchBox, Component.translatable("selectWorld.search"));
        this.searchBox.setResponder((p_232980_) -> {

        });

        this.list = new ToonList(this, this.minecraft, this.width, this.height, 48, this.height - 64, 36);


        this.addWidget(this.searchBox);

        this.addWidget(this.list);


    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        return super.keyPressed(pKeyCode, pScanCode, pModifiers) ? true : this.searchBox.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public void onClose() {
        super.onClose();
    }

    @Override
    public boolean charTyped(char pCodePoint, int pModifiers) {
        return this.searchBox.charTyped(pCodePoint, pModifiers);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.list.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.searchBox.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        pGuiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 8, 16777215);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }


    @Override
    public void removed() {
        if (this.list != null) {
            //  this.list.children().forEach(WorldSelectionList.Entry::close);
        }

    }

    @Override
    public ResourceLocation iconLocation() {
        return SlashRef.guiId("main_hub/icons/chars");
    }

    @Override
    public Words screenName() {
        return Words.Characters;
    }
}