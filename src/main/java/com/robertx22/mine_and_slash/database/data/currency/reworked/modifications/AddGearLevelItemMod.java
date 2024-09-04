package com.robertx22.mine_and_slash.database.data.currency.reworked.modifications;

import com.robertx22.mine_and_slash.database.data.currency.reworked.GearModification;
import com.robertx22.mine_and_slash.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;

public class AddGearLevelItemMod extends GearModification {

    @Override
    public void modifyGear(GearItemData gear) {
        gear.lvl++;

        if (gear.lvl > GameBalanceConfig.get().MAX_LEVEL) {
            gear.lvl = GameBalanceConfig.get().MAX_LEVEL;
        }
    }

    @Override
    public String GUID() {
        return "add_gear_level";
    }

    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public String getDesc() {
        return "Adds Gear Level";
    }
}
