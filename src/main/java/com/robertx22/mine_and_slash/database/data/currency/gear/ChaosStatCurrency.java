package com.robertx22.mine_and_slash.database.data.currency.gear;

import com.robertx22.mine_and_slash.database.data.currency.base.GearCurrency;
import com.robertx22.mine_and_slash.database.data.currency.base.GearOutcome;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.mine_and_slash.database.data.profession.ExplainedResult;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Arrays;
import java.util.List;

public class ChaosStatCurrency extends GearCurrency {
    @Override
    public List<GearOutcome> getOutcomes() {

        return Arrays.asList(

                new GearOutcome() {
                    @Override
                    public Words getName() {
                        return Words.DestroysItem;
                    }

                    @Override
                    public OutcomeType getOutcomeType() {
                        return OutcomeType.BAD;
                    }

                    @Override
                    public ItemStack modify(LocReqContext ctx, GearItemData gear, ItemStack stack) {
                        return Items.COAL.getDefaultInstance();
                    }

                    @Override
                    public int Weight() {
                        return 250;
                    }
                },
                new GearOutcome() {
                    @Override
                    public Words getName() {
                        return Words.CHAOS_STAT_SUCCESS;
                    }

                    @Override
                    public OutcomeType getOutcomeType() {
                        return OutcomeType.GOOD;
                    }

                    @Override
                    public ItemStack modify(LocReqContext ctx, GearItemData gear, ItemStack stack) {
                        var chaos = ExileDB.ChaosStats().getFilterWrapped(x -> x.for_item_rarities.contains(gear.rar)).random();
                        chaos.applyToGear(gear);
                        StackSaving.GEARS.saveTo(stack, gear);
                        return stack;
                    }

                    @Override
                    public int Weight() {
                        return 750;
                    }
                }
        );
    }


    @Override
    public int getPotentialLoss() {
        return 1;
    }

    @Override
    public ExplainedResult canBeModified(GearItemData data) {
        if (data.isCorrupted()) {
            return ExplainedResult.failure(Chats.ITEM_CANT_CORRUPT_TWICE.locName());
        }
        return ExplainedResult.success();
    }

    @Override
    public String locDescForLangFile() {
        return "DANGER! Corrupts the item, either destroying it, or upgrading it. \nAlso sets Potential to 0, meaning the item can never be crafted on again.";
    }

    @Override
    public String locNameForLangFile() {
        return "Orb of Chaos";
    }

    @Override
    public String GUID() {
        return "chaos_orb";
    }

    @Override
    public int Weight() {
        return 500;
    }
}
