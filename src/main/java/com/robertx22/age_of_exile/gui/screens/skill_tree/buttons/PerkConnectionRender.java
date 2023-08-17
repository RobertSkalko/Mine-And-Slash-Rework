package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons;

import com.robertx22.age_of_exile.database.data.perks.Perk;

public class PerkConnectionRender {

    public PerkButton perk1, perk2;
    public Perk.Connection connection;

    public PerkConnectionRender(PerkButton perk1, PerkButton perk2,
                                Perk.Connection connection) {
        this.perk1 = perk1;
        this.perk2 = perk2;
        this.connection = connection;

    }

    @Override
    public int hashCode() {
        return perk1.point.hashCode() + perk2.point.hashCode();
    }

}
