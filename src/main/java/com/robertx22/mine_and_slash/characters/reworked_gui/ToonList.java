package com.robertx22.mine_and_slash.characters.reworked_gui;

import com.robertx22.mine_and_slash.characters.CharacterData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ToonList extends ObjectSelectionList<ToonEntry> {

    public static int HEIGHT = 50;
    ToonScreen screen;

    public ToonList(ToonScreen screen, Minecraft mc, int pWidth, int pHeight, int pY0, int pY1, int pItemHeight) {
        super(mc, pWidth, pHeight, 48, screen.height - 64, 36);
        this.screen = screen;


        reloadAllEntries();

        this.setRenderBackground(false);
    }

    private List<ToonData> all = new ArrayList<>();


    private void reloadAllEntries() {

        this.all = new ArrayList<>();

        for (Map.Entry<Integer, CharacterData> en : Load.player(ClientOnly.getPlayer()).characters.map.entrySet()) {
            var data = new ToonData(en.getValue(), en.getKey());
            all.add(data);
            addEntry(new ToonEntry(this, data));
        }
    }


    @Override
    protected void renderBackground(GuiGraphics pGuiGraphics) {
        pGuiGraphics.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);
    }


}
