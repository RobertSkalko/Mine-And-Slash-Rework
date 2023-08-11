package com.robertx22.age_of_exile.gui.screens;

import com.robertx22.age_of_exile.gui.bases.IContainerNamedScreen;
import com.robertx22.age_of_exile.inv_gui.InvGuiGrid;
import com.robertx22.age_of_exile.inv_gui.InvGuiScreen;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public class OpenInvGuiScreen implements IContainerNamedScreen {
    Words word;
    String icon;
    InvGuiGrid grid;

    public OpenInvGuiScreen(Words word, String icon, InvGuiGrid grid) {
        this.word = word;
        this.icon = icon;
        this.grid = grid;
    }

    @Override
    public ResourceLocation iconLocation() {
        return new ResourceLocation(SlashRef.MODID, "textures/gui/main_hub/icons/" + icon + ".png");
    }

    @Override
    public Words screenName() {
        return word;
    }

    @Override
    public void openContainer() {
        Minecraft.getInstance().setScreen(new InvGuiScreen(grid));
    }
}
