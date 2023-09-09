package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.config.forge.ServerContainer;

public class RestedExpData {

    public int bonusProfExp = 0;
    public int bonusCombatExp = 0;

    public void onGiveCombatExp(int give) {
        bonusProfExp += give * ServerContainer.get().COMBAT_PROFESSION_RESTED_XP_GENERATION.get();
    }

    public void onGiveProfExp(int give) {
        bonusCombatExp += give * ServerContainer.get().COMBAT_PROFESSION_RESTED_XP_GENERATION.get();
    }
}
