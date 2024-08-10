package com.robertx22.mine_and_slash.gui.screens.spell;

import com.robertx22.mine_and_slash.database.data.spell_school.SpellSchool;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;

public class SchoolButton extends ImageButton {

    static int SIZE = 25;

    SpellSchool school;
    SpellSchoolScreen scren;

    public SchoolButton(SpellSchoolScreen scren, int xPos, int yPos) {
        super(xPos, yPos, SIZE, SIZE, 0, 0, 0, SlashRef.guiId(""), (button) -> {
        });
        this.scren = scren;
    }

    @Override
    public void onPress() {
        if (this.school != null) {
            scren.setCurrent(school);
        }
    }

    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (school != null) {
            this.renderTexture(pGuiGraphics, this.school.getIconLoc(), this.getX(), this.getY(), 0, 0, this.yDiffTex, SIZE, SIZE, SIZE, SIZE);
        }
    }


}