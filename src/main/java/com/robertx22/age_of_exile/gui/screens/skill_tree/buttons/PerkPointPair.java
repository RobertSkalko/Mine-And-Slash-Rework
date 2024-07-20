package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons;

import com.robertx22.age_of_exile.saveclasses.PointData;

public class PerkPointPair {
    public PointData perk1, perk2;

    public PerkPointPair(PointData perk1, PointData perk2) {
        this.perk1 = perk1;
        this.perk2 = perk2;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PerkPointPair p) {
            return (perk1.equals(p.perk1) || perk1.equals(p.perk2)) && (perk2.equals(p.perk1) || perk2.equals(p.perk2));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return perk1.hashCode() + perk2.hashCode();
    }

}
