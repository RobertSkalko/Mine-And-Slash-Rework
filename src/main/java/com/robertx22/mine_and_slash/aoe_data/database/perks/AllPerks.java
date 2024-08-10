package com.robertx22.mine_and_slash.aoe_data.database.perks;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class AllPerks implements ExileRegistryInit {

    @Override
    public void registerAll() {

        new Perks().registerAll();
        new PerksAddtl().registerAll();
        new GameChangerPerks().registerAll();
        new GameChangerPerksAddtl().registerAll();
        new StartPerks().registerAll();

        new StartPerksAddtl().registerAll();

        new SpellPassives().registerAll();

        AscendancyPerks.init();
    }
}
