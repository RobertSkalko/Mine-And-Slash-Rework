package com.robertx22.age_of_exile.characters.gui;

import com.robertx22.age_of_exile.characters.CharacterData;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class CharacterSelectScreen extends BaseScreen implements INamedScreen {
    private static int SEARCH_WIDTH = 100;
    private static int SEARCH_HEIGHT = 14;

    public static EditBox SEARCH = new EditBox(Minecraft.getInstance().font, 0, 0, SEARCH_WIDTH, SEARCH_HEIGHT, Component.translatable("fml.menu.mods.search"));

    static ResourceLocation TEX = SlashRef.guiId("char_select");


    public CharacterSelectScreen() {
        super(250, 233);
    }


    @Override
    public void render(GuiGraphics gui, int x, int y, float ticks) {

        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        gui.blit(TEX, mc.getWindow().getGuiScaledWidth() / 2 - sizeX / 2, mc.getWindow().getGuiScaledHeight() / 2 - sizeY / 2, 0, 0, sizeX, sizeY);

        super.render(gui, x, y, ticks);


    }

    @Override
    protected void init() {
        super.init();


        this.publicAddButton(SEARCH);

        SEARCH.setX(guiLeft + 70);
        SEARCH.setY(guiTop + 207);

        this.publicAddButton(new NewCharButton(guiLeft + 188, guiTop + 205));

        int xpos = guiLeft + 10;
        int ypos = guiTop + 50;


        for (Map.Entry<Integer, CharacterData> en : Load.player(mc.player).characters.map.entrySet()) {
            if (en.getValue() != null) {
                this.publicAddButton(new CharSelectButton(en.getKey(), xpos, ypos));
                ypos += 21;
            }
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
