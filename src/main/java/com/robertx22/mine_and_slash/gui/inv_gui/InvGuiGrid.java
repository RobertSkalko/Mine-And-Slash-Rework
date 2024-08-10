package com.robertx22.mine_and_slash.gui.inv_gui;

import java.util.ArrayList;
import java.util.List;

public class InvGuiGrid {

    public static int X_MAX = 9;
    public static int Y_MAX = 6;
    public static int TOTAL_SLOTS = X_MAX * Y_MAX;


    public List<GuiItemData> list = new ArrayList<>();


    // this can be used as placeholder for now
    public static InvGuiGrid ofList(List<GuiItemData> list) {
        InvGuiGrid g = new InvGuiGrid();

        for (int i = 0; i < TOTAL_SLOTS; i++) {
            if (list.size() > i) {
                g.list.add(list.get(i));
            } else {
                g.list.add(new GuiItemData());
            }
        }
        return g;
    }

    public static InvGuiGrid ofYRowLists(List<List<GuiItemData>> list) {
        InvGuiGrid g = new InvGuiGrid();


        for (int y = 0; y < Y_MAX; y++) {

            int amount = 0;
            if (list.size() > y) {
                List<GuiItemData> l = list.get(y);
                g.list.addAll(l);
                amount = l.size();
            }
            int toadd = X_MAX - amount;

            for (int x = 0; x < toadd; x++) {
                g.list.add(new GuiItemData());
            }

        }
        return g;
    }

}
