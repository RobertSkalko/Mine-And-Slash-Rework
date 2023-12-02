package com.robertx22.age_of_exile.gui.screens.spell;

import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.spell_school.AscendancyClass;
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

public class AscendancyClassScreen extends BaseScreen implements INamedScreen, ILeftRight {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(SlashRef.MODID, "textures/gui/asc_classes/background.png");

    static int sizeX = 250;
    static int sizeY = 233;

    Minecraft mc = Minecraft.getInstance();

    public List<AscendancyClass> schoolsInOrder = ExileDB.SpellSchools()
            .getList();
    public int currentIndex = 0;
    public int maxIndex = ExileDB.SpellSchools()
            .getSize() - 1;

    public AscendancyClass currentSchool() {
        return schoolsInOrder.get(currentIndex);
    }

    public AscendancyClassScreen() {
        super(sizeX, sizeY);
    }

    @Override
    public ResourceLocation iconLocation() {
        return new ResourceLocation(SlashRef.MODID, "textures/gui/main_hub/icons/spells.png");
    }

    @Override
    public Words screenName() {
        return Words.AscClasses;
    }

    static int SLOT_SPACING = 21;

    @Override
    public void init() {
        super.init();

        try {
            this.clearWidgets();

            addRenderableWidget(new LeftRightButton(this, guiLeft + 100 - LeftRightButton.xSize - 5, guiTop + 25 - LeftRightButton.ySize / 2, true));
            addRenderableWidget(new LeftRightButton(this, guiLeft + 150 + 5, guiTop + 25 - LeftRightButton.ySize / 2, false));

            currentSchool().perks.entrySet()
                    .forEach(e -> {

                        PointData point = e.getValue();
                        Perk perk = ExileDB.Perks()
                                .get(e.getKey());

                        if (perk != null) {
                            int x = this.guiLeft + 12 + (point.x * SLOT_SPACING);
                            int y = this.guiTop + 178 - (point.y * SLOT_SPACING);

                            this.addRenderableWidget(new LearnClassPointButton(this, perk, x, y));

                            // todo add a differently shaped button for passive stats
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
            gui.blit(currentSchool().getIconLoc(), guiLeft + 107, guiTop + 8, 36, 36, 36, 36, 36, 36);

            // background
            gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);

            gui.blit(currentSchool().getBackgroundLoc(), guiLeft + 7, guiTop + 8, 93, 36, 93, 36, 93, 36);
            gui.blit(currentSchool().getBackgroundLoc(), guiLeft + 150, guiTop + 8, 93, 36, 93, 36, 93, 36);

            super.render(gui, x, y, ticks);

            String txt = "Spell Points: " + Load.player(mc.player).ascClass.getFreeSpellPoints(mc.player);
            GuiUtils.renderScaledText(gui, guiLeft + 48, guiTop + 216, 1, txt, ChatFormatting.WHITE);

            String tx2 = "Passive Points: " + Load.player(mc.player).ascClass.getFreePassivePoints(mc.player);
            GuiUtils.renderScaledText(gui, guiLeft + 195, guiTop + 216, 1, tx2, ChatFormatting.WHITE);

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