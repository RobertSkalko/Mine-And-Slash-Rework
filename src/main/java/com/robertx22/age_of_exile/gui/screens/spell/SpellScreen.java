package com.robertx22.age_of_exile.gui.screens.spell;

import com.robertx22.age_of_exile.database.data.spell_school.SpellSchool;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.gui.screens.ILeftRight;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class SpellScreen extends BaseScreen implements INamedScreen, ILeftRight {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(SlashRef.MODID, "textures/gui/spells/spell_school_background.png");

    static int sizeX = 250;
    static int sizeY = 233;
    public static int NUMBER_OF_HOTBAR_FOR_PICKING = 0;

    Minecraft mc = Minecraft.getInstance();

    public List<SpellSchool> schoolsInOrder = ExileDB.SpellSchools()
            .getList();
    public int currentIndex = 0;
    public int maxIndex = ExileDB.SpellSchools()
            .getSize() - 1;

    public SpellSchool currentSchool() {
        return schoolsInOrder.get(currentIndex);
    }

    public SpellScreen() {
        super(sizeX, sizeY);
    }

    @Override
    public ResourceLocation iconLocation() {
        return new ResourceLocation(SlashRef.MODID, "textures/gui/main_hub/icons/spells.png");
    }

    @Override
    public Words screenName() {
        return Words.Skills;
    }

    static int SLOT_SPACING = 21;

    @Override
    public void init() {
        super.init();

        try {
            this.clearWidgets();

            addRenderableWidget(new LeftRightButton(this, guiLeft + 100 - LeftRightButton.xSize - 5, guiTop + 25 - LeftRightButton.ySize / 2, true));
            addRenderableWidget(new LeftRightButton(this, guiLeft + 150 + 5, guiTop + 25 - LeftRightButton.ySize / 2, false));

            currentSchool().spells.entrySet()
                    .forEach(e -> {

                        PointData point = e.getValue();
                        Spell spell = ExileDB.Spells()
                                .get(e.getKey());

                        if (spell != null) {
                            int x = this.guiLeft + 12 + (point.x * SLOT_SPACING);
                            int y = this.guiTop + 177 - (point.y * SLOT_SPACING);

                            this.addRenderableWidget(new LearnSpellButton(this, spell, x, y));
                        }
                    });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics gui, int x, int y, float ticks) {

        try {
            gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            gui.blit(BACKGROUND, mc.getWindow()
                            .getGuiScaledWidth() / 2 - sizeX / 2,
                    mc.getWindow()
                            .getGuiScaledHeight() / 2 - sizeY / 2, 0, 0, sizeX, sizeY
            );

            gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            gui.blit(currentSchool().getIconLoc(), guiLeft + 108, guiTop + 8, 34, 34, 34, 34, 34, 34);

            // background
            gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);

            gui.blit(currentSchool().getBackgroundLoc(), guiLeft + 7, guiTop + 7, 93, 36, 93, 36, 93, 36);
            gui.blit(currentSchool().getBackgroundLoc(), guiLeft + 150, guiTop + 7, 93, 36, 93, 36, 93, 36);

            super.render(gui, x, y, ticks);

            String txt = "Points: " + Load.spells(mc.player)
                    .getFreeSpellPoints();
            GuiUtils.renderScaledText(gui, guiLeft + 125, guiTop + 215, 1, txt, ChatFormatting.GREEN);

            //buttons.forEach(b -> b.renderToolTip(matrix, x, y));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void goLeft() {
        this.currentIndex--;
        if (currentIndex < 0) {
            currentIndex = maxIndex;
        }
        init();
    }

    @Override
    public void goRight() {
        currentIndex++;
        if (currentIndex > maxIndex) {
            currentIndex = 0;
        }
        init();
    }
}