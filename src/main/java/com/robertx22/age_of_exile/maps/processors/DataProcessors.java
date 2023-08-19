package com.robertx22.age_of_exile.maps.processors;


import com.robertx22.age_of_exile.maps.processors.league.LeagueControlBlockProcessor;
import com.robertx22.age_of_exile.maps.processors.league.LeagueTpBackProcessor;
import com.robertx22.age_of_exile.maps.processors.misc.RemoveAllBesidesOneProcessor;
import com.robertx22.age_of_exile.maps.processors.mob.*;
import com.robertx22.age_of_exile.maps.processors.reward.ChanceChestProcessor;
import com.robertx22.age_of_exile.maps.processors.reward.ChestProcessor;

import java.util.ArrayList;
import java.util.List;

public class DataProcessors {

    static List<DataProcessor> all = new ArrayList<>();

    public static List<DataProcessor> getAll() {
        

        if (all.isEmpty()) {
            all.add(new BossProcessor());
            all.add(new EliteProcessor());
            all.add(new MobProcessor());
            all.add(new ChestProcessor());
            all.add(new MobHordeProcessor());
            all.add(new EliteMobHorde());
            all.add(new ChanceChestProcessor());
            all.add(new RemoveAllBesidesOneProcessor());
            all.add(new ComplexMobProcessor());
            all.add(new LeagueControlBlockProcessor());
            all.add(new LeagueTpBackProcessor());
        }

        return all;

    }

}
