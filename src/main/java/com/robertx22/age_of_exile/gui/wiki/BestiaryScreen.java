package com.robertx22.age_of_exile.gui.wiki;

import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.gui.screens.character_screen.MainHubScreen;
import com.robertx22.age_of_exile.gui.wiki.all.CurrencyBestiary;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.MathHelper;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.utils.RenderUtils;
import com.robertx22.library_of_exile.utils.TextUTIL;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class BestiaryScreen extends BaseScreen implements INamedScreen {

    ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(SlashRef.MODID, "textures/gui/bestiary/bestiary.png");
    ResourceLocation BUTTON_TEXTURE = new ResourceLocation(SlashRef.MODID, "textures/gui/bestiary/buttons.png");
    ResourceLocation SPLITTER_BUTTON_TEXTURE = new ResourceLocation(SlashRef.MODID, "textures/gui/bestiary/split.png");
    ResourceLocation GROUP_BUTTON_TEXTURE = new ResourceLocation(SlashRef.MODID, "textures/gui/bestiary/bestiary_group_buttons.png");

    public Minecraft mc;

    public static int entryButtonX = 235;
    public static int entryButtonY = 24;

    public static int groupButtonX = 20;
    public static int groupButtonY = 20;

    Scrollbar scrollbar;

    static int x = 276;
    static int y = 200;

    public BestiaryGroup currentBestiaryGroup = new CurrencyBestiary();

    int currentElement = 0;

    public List<BestiaryEntry> entries = new ArrayList<>();

    int level = 1;
    int elementsAmount = 1;

    int entryButtonsAtOnce = 7;

    public BestiaryScreen() {
        super(x, y);
        this.mc = Minecraft.getInstance();

    }

    @Override
    protected void init() {
        super.init();
        this.children().clear();
        this.renderables.clear();

        addRenderableWidget(new BackButton(guiLeft, guiTop - BackButton.ySize));

        this.level = Load.Unit(mc.player)
                .getLevel();

        initEntries();

        setupEntryButtons();
        setupGroupButtons();
        setupScrollbar();

    }

    public void setupScrollbar() {

        int sliderXSize = 10;
        int sliderYSize = 30;

        int sliderX = guiLeft + 262;
        int sliderY = guiTop + 18;

        scrollbar = addRenderableWidget(new Scrollbar(sliderX, sliderY, 174));

        // AbstractSlider
    }

    public void initEntries() {
        this.entries = currentBestiaryGroup.getAll(1);
        this.elementsAmount = entries.size();
    }

    public void setupGroupButtons() {

        int gx = guiLeft + 5;
        int gy = guiTop + 18;

        for (BestiaryGroup bestiaryGroup : BestiaryGroup.getAll()) {
            addRenderableWidget(new GroupButton(this, bestiaryGroup, gx, gy));
            gy += groupButtonY;
        }

    }

    public void setupEntryButtons() {


        int x = this.guiLeft + 27;
        int y = this.guiTop + 19;

        for (int i = currentElement; i < currentElement + entryButtonsAtOnce; i++) {
            if (i >= elementsAmount) {
                continue;
            } else {

                BestiaryEntry entry = entries.get(i);

                addRenderableWidget(new EntryButton((BestiaryEntry) entry, x, y));
                y += entryButtonY + 0;
            }

        }

    }

    @Override
    public void render(GuiGraphics gui, int x, int y, float ticks) {

        try {
            gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            gui.blit(BACKGROUND_TEXTURE, mc.getWindow()
                            .getGuiScaledWidth() / 2 - sizeX / 2,
                    mc.getWindow()
                            .getGuiScaledHeight() / 2 - sizeY / 2, 0, 0, sizeX, sizeY, 512, 256
            );


            super.render(gui, x, y, ticks);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public ResourceLocation iconLocation() {
        return new ResourceLocation(SlashRef.MODID, "textures/gui/main_hub/icons/wiki.png");
    }

    @Override
    public Words screenName() {
        return Words.WIKI;
    }

    @Override
    public boolean mouseScrolled(double num1, double num2, double num3) {

        this.setCurrentElement((int) (currentElement - num3));

        return super.mouseScrolled(num1, num2, num3);

    }

    public void setCurrentElement(int element) {

        this.currentElement = MathHelper.clamp(element, 0, elementsAmount);

        scrollbar.setValueFromElement(currentElement, elementsAmount);

        init();

    }

    @Override
    public boolean mouseReleased(double m1, double m2, int m3) {
        this.setDragging(false);

        /*
        buttons.stream()
                .filter(x -> x.isMouseOver(m1, m2))
                .findFirst()
                .ifPresent(x -> x.onClick(m1, m2));

         */

        return super.mouseReleased(m1, m2, m3);

    }

    public void setGroup(BestiaryGroup group) {
        this.currentBestiaryGroup = group;

        initEntries();

        this.setCurrentElement(0);

        init();

    }

    class Scrollbar extends BaseScrollbar {

        protected Scrollbar(int xpos, int ypos, int scrollbarTotalHeight) {
            super(xpos, ypos, scrollbarTotalHeight);
        }

        @Override
        protected void applyValue() {
            BestiaryScreen.this.setCurrentElement((int) (this.value * BestiaryScreen.this.elementsAmount));
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

        }
    }

    class GroupButton extends ImageButton {
        BestiaryGroup group;
        BestiaryScreen screen;

        public GroupButton(BestiaryScreen screen, BestiaryGroup group, int xPos, int yPos) {
            super(xPos, yPos, groupButtonX, groupButtonY, 0, 0, groupButtonY, GROUP_BUTTON_TEXTURE, (button) -> {
            });

            this.screen = screen;
            this.group = group;

        }

        @Override
        public void onPress() {
            super.onPress();

            screen.setGroup(group);

        }


        @Override
        public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
            setModTooltip();
            super.render(gui, mouseX, mouseY, delta);
            RenderUtils.render16Icon(gui, group.getTextureLoc(), this.getX() + 2, this.getY() + 2);

        }

        public void setModTooltip() {

            List<Component> tooltip = new ArrayList<>();

            tooltip.add(Component.literal(ChatFormatting.BLUE + "" + ChatFormatting.BOLD + CLOC.translate(group.getName())));

            this.setTooltip(Tooltip.create(TextUTIL.mergeList(tooltip)));


        }

    }

    class EntryButton extends ImageButton {
        BestiaryEntry item;

        public EntryButton(BestiaryEntry item, int xPos, int yPos) {
            super(xPos, yPos, entryButtonX, entryButtonY, 0, 0, entryButtonY, BUTTON_TEXTURE, (button) -> {
            });

            this.item = item;

        }

        int getStackY() {
            return this.getY() + 4;
        }

        int getStackX() {
            return this.getX() + 13;
        }

        @Override
        public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
            if (isHovered()) {
                setModTooltip();
            }
            super.render(gui, mouseX, mouseY, delta);

            gui.renderFakeItem(item.stack, getStackX(), getStackY());

            int xp = (int) (this.getX() + 35);
            int yp = (int) (this.getY() + entryButtonY / 2) - mc.font.lineHeight / 2;

            gui.drawString(mc.font, item.getName(), xp, yp, ChatFormatting.GREEN.getColor());


        }

        public void setModTooltip() {

            List<Component> tooltip = new ArrayList<>();
            tooltip.addAll(item.getTooltip());
            this.setTooltip(Tooltip.create(TextUTIL.mergeList(tooltip)));


        }

    }


    static class BackButton extends ImageButton {
        static ResourceLocation BACK_BUTTON = new ResourceLocation(
                SlashRef.MODID, "textures/gui/back_button.png");

        public static int xSize = 26;
        public static int ySize = 16;

        public BackButton(int xPos, int yPos) {
            super(xPos, yPos, xSize, ySize, 0, 0, ySize + 1, BACK_BUTTON, (button) -> {
                Minecraft.getInstance().setScreen(new MainHubScreen());

            });
        }
    }

}

