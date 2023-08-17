package com.robertx22.age_of_exile.gui.inv_gui;

import com.robertx22.age_of_exile.gui.inv_gui.actions.GuiAction;
import net.minecraft.world.entity.player.Player;

public class GuiItemData {

    public String action = "";

    public GuiItemData(GuiAction action) {
        this.action = action.GUID();
    }

    public GuiItemData() {

    }


    public boolean isEmpty() {
        return action.isEmpty();
    }


    public GuiAction getAction() {
        return GuiAction.get(action);
    }

    public void onServer(Player p) {
        GuiAction ac = getAction();
        ac.doAction(p);
    }

}
