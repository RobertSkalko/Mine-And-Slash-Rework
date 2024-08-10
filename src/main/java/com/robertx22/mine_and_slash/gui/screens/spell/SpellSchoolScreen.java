package com.robertx22.mine_and_slash.gui.screens.spell;

import com.robertx22.mine_and_slash.database.data.game_balance_config.PlayerPointsType;
import com.robertx22.mine_and_slash.database.data.perks.Perk;
import com.robertx22.mine_and_slash.database.data.spell_school.SpellSchool;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.gui.bases.BaseScreen;
import com.robertx22.mine_and_slash.gui.bases.IAlertScreen;
import com.robertx22.mine_and_slash.gui.bases.INamedScreen;
import com.robertx22.mine_and_slash.gui.screens.ILeftRight;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.saveclasses.PointData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Gui;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.stream.Collectors;

public class SpellSchoolScreen extends BaseScreen implements INamedScreen, ILeftRight, IAlertScreen {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(SlashRef.MODID, "textures/gui/asc_classes/background.png");

    static int sizeX = 250;
    static int sizeY = 233;

    Minecraft mc = Minecraft.getInstance();

    public List<SpellSchool> schoolsInOrder = ExileDB.SpellSchools()
            .getList();
    public int currentIndex = 0;
    public int maxIndex = ExileDB.SpellSchools()
            .getSize() - 1;

    public SpellSchool currentSchool() {
        return schoolsInOrder.get(currentIndex);
    }


    public void setCurrent(SpellSchool sc) {
        for (int i = 0; i < schoolsInOrder.size(); i++) {
            if (sc.GUID().equals(schoolsInOrder.get(i).GUID())) {
                this.currentIndex = i;
                init();
                break;
            }
        }

    }

    public SpellSchoolScreen() {
        super(sizeX, sizeY);
    }

    @Override
    public ResourceLocation iconLocation() {
        return new ResourceLocation(SlashRef.MODID, "textures/gui/main_hub/icons/spells.png");
    }

    @Override
    public Words screenName() {
        return Words.Classes;
    }

    static int SLOT_SPACING = 21;

    SchoolButton LEFT_SCHOOL;
    SchoolButton RIGHT_SCHOOL;

    @Override
    public void init() {
        super.init();
        this.clearWidgets();

        try {

            var all = Load.player(mc.player).ascClass.school.stream().map(x -> ExileDB.SpellSchools().get(x)).collect(Collectors.toList());

            LEFT_SCHOOL = new SchoolButton(this, guiLeft + 41, guiTop + 13);
            RIGHT_SCHOOL = new SchoolButton(this, guiLeft + 185, guiTop + 13);

            this.publicAddButton(LEFT_SCHOOL);
            this.publicAddButton(RIGHT_SCHOOL);

            if (all.size() > 0) {
                LEFT_SCHOOL.school = all.get(0);
            }
            if (all.size() > 1) {
                RIGHT_SCHOOL.school = all.get(1);
            }


            addRenderableWidget(new LeftRightButton(this, guiLeft + 100 - LeftRightButton.xSize - 5, guiTop + 25 - LeftRightButton.ySize / 2, true));
            addRenderableWidget(new LeftRightButton(this, guiLeft + 150 + 5, guiTop + 25 - LeftRightButton.ySize / 2, false));


            currentSchool().perks.entrySet()
                    .forEach(e -> {

                        PointData point = e.getValue();
                        Perk perk = ExileDB.Perks().get(e.getKey());


                        if (perk != null && ExileDB.Perks().isRegistered(e.getKey())) {
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

    // so 1 guy can mixin to replace it
    public void mnsRenderBG(GuiGraphics gui) {
        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        gui.blit(BACKGROUND, mc.getWindow()
                        .getGuiScaledWidth() / 2 - sizeX / 2,
                mc.getWindow()
                        .getGuiScaledHeight() / 2 - sizeY / 2, 0, 0, sizeX, sizeY
        );
    }

    @Override
    public void render(GuiGraphics gui, int x, int y, float ticks) {

        try {
            mnsRenderBG(gui);

            gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            gui.blit(currentSchool().getIconLoc(), guiLeft + 107, guiTop + 8, 36, 36, 36, 36, 36, 36);

            // background
            gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);

            gui.blit(currentSchool().getBackgroundLoc(), guiLeft + 7, guiTop + 8, 93, 36, 93, 36, 93, 36);
            gui.blit(currentSchool().getBackgroundLoc(), guiLeft + 150, guiTop + 8, 93, 36, 93, 36, 93, 36);

            super.render(gui, x, y, ticks);

            String txt = Gui.SPELL_POINTS.locName().append(String.valueOf(PlayerPointsType.SPELLS.getFreePoints(mc.player))).getString();
            GuiUtils.renderScaledText(gui, guiLeft + 50, guiTop + 215, 1, txt, ChatFormatting.WHITE);

            String tx2 = Gui.PASSIVE_POINTS.locName().append(String.valueOf(PlayerPointsType.PASSIVES.getFreePoints(mc.player))).getString();
            GuiUtils.renderScaledText(gui, guiLeft + 195, guiTop + 215, 1, tx2, ChatFormatting.WHITE);

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

    @Override
    public boolean shouldAlert() {
        var p = ClientOnly.getPlayer();
        return PlayerPointsType.SPELLS.hasFreePoints(p) || PlayerPointsType.PASSIVES.hasFreePoints(p);
    }
}