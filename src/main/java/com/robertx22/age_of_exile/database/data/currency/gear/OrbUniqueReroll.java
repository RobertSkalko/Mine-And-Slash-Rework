package com.robertx22.age_of_exile.database.data.currency.gear;

import com.robertx22.age_of_exile.database.data.currency.base.GearCurrency;
import com.robertx22.age_of_exile.database.data.currency.base.GearOutcome;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.data.profession.ExplainedResult;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class OrbUniqueReroll extends GearCurrency {
    @Override
    public List<GearOutcome> getOutcomes() {

        return Arrays.asList(
                new GearOutcome() {
                    @Override
                    public Words getName() {
                        return Words.Increased;
                    }

                    @Override
                    public OutcomeType getOutcomeType() {
                        return OutcomeType.GOOD;
                    }

                    @Override
                    public ItemStack modify(LocReqContext ctx, GearItemData gear, ItemStack stack) {
                        gear.uniqueStats.increaseAllBy(gear, 5);
                        StackSaving.GEARS.saveTo(stack, gear);
                        return stack;
                    }

                    @Override
                    public int Weight() {
                        return 1000;
                    }
                },
                new GearOutcome() {
                    @Override
                    public Words getName() {
                        return Words.Decreased;
                    }

                    @Override
                    public OutcomeType getOutcomeType() {
                        return OutcomeType.BAD;
                    }

                    @Override
                    public ItemStack modify(LocReqContext ctx, GearItemData gear, ItemStack stack) {
                        gear.uniqueStats.increaseAllBy(gear, -5);
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
        return 5;
    }

    @Override
    public ExplainedResult canBeModified(GearItemData data) {
        if (data.isUnique() && data.uniqueStats != null) {
            return ExplainedResult.success();
        }
        return ExplainedResult.failure(Chats.BE_UNIQUE.locName());
    }

    @Override
    public String locDescForLangFile() {
        return "Increases or reduces unique stats by 5%";
    }

    @Override
    public String locNameForLangFile() {
        return "Orb of Imperfection";
    }

    @Override
    public String GUID() {
        return "unique_reroll";
    }

    @Override
    public int Weight() {
        return Weights.UBER;
    }
}
