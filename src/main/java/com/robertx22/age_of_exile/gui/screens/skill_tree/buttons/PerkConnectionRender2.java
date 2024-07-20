package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons;

import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.saveclasses.PointData;

public class PerkConnectionRender2 {

    public PerkPointPair pair;
    public Perk.Connection connection;

    public PerkConnectionRender2(PerkPointPair pair, Perk.Connection connection) {
        this.pair = pair;
        this.connection = connection;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PerkConnectionRender2 p) {
            if (this.pair.equals(p.pair)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return pair.hashCode();
    }
}
