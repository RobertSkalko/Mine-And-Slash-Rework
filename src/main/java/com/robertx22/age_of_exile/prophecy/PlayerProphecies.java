package com.robertx22.age_of_exile.prophecy;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;

import java.util.ArrayList;
import java.util.List;

public class PlayerProphecies {

    public List<ProphecyData> offers = new ArrayList<>();
    public List<ProphecyData> taken = new ArrayList<>();

    public int getRerollCost() {
        return GameBalanceConfig.get().PROPHECY_REROLL_COST;
    }

    public void regenerateNewOffers() {

        offers = new ArrayList<>();

        for (int i = 0; i < ServerContainer.get().PROPHECY_OFFERS_PER_REROLL.get(); i++) {
            offers.add(ProphecyGeneration.generate());
        }

    }

}
