package com.robertx22.mine_and_slash.gui.screens.skill_tree.buttons;

import com.robertx22.mine_and_slash.database.data.perks.Perk;
import com.robertx22.mine_and_slash.saveclasses.PointData;

public class PerkConnectionRender {

    public PointData perk1, perk2;
    public Perk.Connection connection;

    public PerkConnectionRender(PointData perk1, PointData perk2,
                                Perk.Connection connection) {
        this.perk1 = perk1;
        this.perk2 = perk2;
        this.connection = connection;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PerkConnectionRender p) {
            if ((perk1.equals(p.perk1) || perk1.equals(p.perk2)) && (perk2.equals(p.perk1) || perk2.equals(p.perk2))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = perk1.hashCode();
        result = 31 * result + perk2.hashCode();
        return result;
    }
}
