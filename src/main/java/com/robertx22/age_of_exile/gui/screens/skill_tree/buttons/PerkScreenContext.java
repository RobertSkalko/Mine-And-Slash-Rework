package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons;

import com.robertx22.age_of_exile.gui.screens.skill_tree.SkillTreeScreen;
import net.minecraft.client.Minecraft;

public class PerkScreenContext {

    public float zoom, scrollX, scrollY;

    public int offsetX;
    public int offsetY;

    public PerkScreenContext(float zoom, float scrolLX, float scrollY) {
        this.zoom = zoom;
        this.scrollX = scrolLX;
        this.scrollY = scrollY;
        setupOffsets();
    }

    public PerkScreenContext(SkillTreeScreen screen) {
        this.zoom = screen.zoom;
        this.scrollX = screen.scrollX;
        this.scrollY = screen.scrollY;
        setupOffsets();
    }

    private void setupOffsets() {
        offsetX = (int) (Minecraft.getInstance().getWindow().getGuiScaledWidth() * getZoomMulti() / 2F - SkillTreeScreen.sizeX() * getZoomMulti() / 2F);

        offsetY = (int) (Minecraft.getInstance().getWindow().getGuiScaledHeight() * getZoomMulti() / 2F - SkillTreeScreen.sizeY() * getZoomMulti() / 2F);
    }

    public float getZoomMulti() {
        return 1 / zoom;
    }

}
