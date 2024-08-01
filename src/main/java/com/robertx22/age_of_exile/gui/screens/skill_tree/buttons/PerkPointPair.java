package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons;

import com.robertx22.age_of_exile.saveclasses.PointData;

public record PerkPointPair(PointData perk1, PointData perk2) {

    @Override
    public boolean equals(Object o) {
        if (o instanceof PerkPointPair p) {
            return (perk1.equals(p.perk1) || perk1.equals(p.perk2)) && (perk2.equals(p.perk1) || perk2.equals(p.perk2));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hashA = perk1.hashCode();
        int hashB = perk2.hashCode();

        long result = 17;
        result = 31 * result + (hashA ^ hashB);
        result = 31 * result + (hashA & hashB);
        result = 31 * result + (hashA | hashB);

        return (int) result;
    }

}
