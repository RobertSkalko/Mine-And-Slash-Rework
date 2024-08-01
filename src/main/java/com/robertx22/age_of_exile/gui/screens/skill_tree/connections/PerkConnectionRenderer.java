package com.robertx22.age_of_exile.gui.screens.skill_tree.connections;

import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.PerkPointPair;

public record PerkConnectionRenderer(PerkPointPair pair, Perk.Connection connection) {


    @Override
    public int hashCode() {
        return pair.hashCode();
    }

    public int hashCodeWithConnection(){
        long result = 17;
        result = 29 * result + pair.hashCode() | connection.hashCode();

        return (int) result;
    }

}
