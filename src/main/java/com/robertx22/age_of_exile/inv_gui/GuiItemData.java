package com.robertx22.age_of_exile.inv_gui;

import com.robertx22.age_of_exile.inv_gui.actions.GuiAction;
import net.minecraft.world.entity.player.Player;

public class GuiItemData {

    public String action = "";

    private GuiAction getAction() {
        return GuiAction.get(action);
    }

    public void onServer(Player p) {


        GuiAction ac = getAction();

        // todo do this tomorrow


    }

}
