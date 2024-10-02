package com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.gear;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.GearModification;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModificationSers;
import com.robertx22.mine_and_slash.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import net.minecraft.network.chat.MutableComponent;

public class AddGearLevelItemMod extends GearModification {


    public Data data;

    public static record Data(int add_levels) {
    }

    public AddGearLevelItemMod(String id, Data data) {
        super(ItemModificationSers.ADD_LEVEL, id);
        this.data = data;
    }

    @Override
    public void modifyGear(ExileStack stack) {
        stack.GEAR.edit(gear -> {
            gear.lvl += data.add_levels;

            if (gear.lvl > GameBalanceConfig.get().MAX_LEVEL) {
                gear.lvl = GameBalanceConfig.get().MAX_LEVEL;
            }
        });
    }

    @Override
    public Class<?> getClassForSerialization() {
        return AddGearLevelItemMod.class;
    }


    @Override
    public OutcomeType getOutcomeType() {
        return OutcomeType.GOOD;
    }

    @Override
    public MutableComponent getDescWithParams() {
        return this.getDescParams(data.add_levels);
    }

    @Override
    public String locDescForLangFile() {
        return "Adds %1$s Gear Levels";
    }
}
