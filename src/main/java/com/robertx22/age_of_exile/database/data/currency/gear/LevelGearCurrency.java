package com.robertx22.age_of_exile.database.data.currency.gear;

import com.robertx22.age_of_exile.database.data.currency.base.GearCurrency;
import com.robertx22.age_of_exile.database.data.currency.base.GearOutcome;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.profession.ExplainedResult;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class LevelGearCurrency extends GearCurrency {
    @Override
    public List<GearOutcome> getOutcomes() {

        return Arrays.asList(
                new GearOutcome() {
                    @Override
                    public Words getName() {
                        return Words.UpgradeQuality;
                    }

                    @Override
                    public OutcomeType getOutcomeType() {
                        return OutcomeType.GOOD;
                    }

                    @Override
                    public ItemStack modify(LocReqContext ctx, GearItemData gear, ItemStack stack) {
                        gear.lvl++;
                        gear.data.set(GearItemData.KEYS.LEVEL_TIMES, gear.data.get(GearItemData.KEYS.LEVEL_TIMES) + 1);
                        StackSaving.GEARS.saveTo(stack, gear);
                        return stack;
                    }

                    @Override
                    public int Weight() {
                        return 1000;
                    }
                }
        );
    }


    @Override
    public int getPotentialLoss() {
        return 25;
    }

    @Override
    public ExplainedResult canBeModified(GearItemData data) {

        
        if (data.data.get(GearItemData.KEYS.LEVEL_TIMES) >= 5) {
            return ExplainedResult.failure(Chats.THIS_ITEM_CANT_BE_USED_MORE_THAN_X_TIMES.locName(5));
        }
        if (data.lvl >= GameBalanceConfig.get().MAX_LEVEL) {
            return ExplainedResult.failure(Chats.CANT_GO_ABOVE_MAX_LEVEL.locName(GameBalanceConfig.get().MAX_LEVEL));
        }
        return ExplainedResult.success();
    }

    @Override
    public String locDescForLangFile() {
        return "Increases gear level, maximum 5 uses on an item.";
    }

    @Override
    public String locNameForLangFile() {
        return "Orb of Infinity";
    }

    @Override
    public String GUID() {
        return "level_up_orb";
    }

    @Override
    public int Weight() {
        return 500;
    }
}
