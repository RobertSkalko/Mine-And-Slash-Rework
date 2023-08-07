package com.robertx22.age_of_exile.database.data.currency.gear;

import com.robertx22.age_of_exile.database.data.currency.base.GearCurrency;
import com.robertx22.age_of_exile.database.data.currency.base.GearOutcome;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.AffixData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class OrbAffixUpgrade extends GearCurrency {
    @Override
    public List<GearOutcome> getOutcomes() {
        return Arrays.asList(
                new GearOutcome() {
                    @Override
                    public Words getName() {
                        return Words.UpgradeAffix;
                    }

                    @Override
                    public OutcomeType getOutcomeType() {
                        return OutcomeType.GOOD;
                    }

                    @Override
                    public ItemStack modify(LocReqContext ctx, GearItemData gear, ItemStack stack) {
                        AffixData data = RandomUtils.randomFromList(gear.affixes.getAllAffixesAndSockets());
                        data.setTier(data.getAffixTier() + 1);
                        data.RerollNumbers(gear);
                        StackSaving.GEARS.saveTo(stack, gear);
                        return stack;
                    }

                    @Override
                    public int Weight() {
                        return 500;
                    }
                },
                new GearOutcome() {
                    @Override
                    public Words getName() {
                        return Words.DowngradeAffix;
                    }

                    @Override
                    public OutcomeType getOutcomeType() {
                        return OutcomeType.BAD;
                    }

                    @Override
                    public ItemStack modify(LocReqContext ctx, GearItemData gear, ItemStack stack) {
                        AffixData data = RandomUtils.randomFromList(gear.affixes.getAllAffixesAndSockets());
                        data.setTier(data.getAffixTier() - 1);
                        data.RerollNumbers(gear);
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
    public boolean canBeModified(GearItemData data) {
        return data.affixes.getNumberOfAffixes() > 0;
    }

    @Override
    public String locDescForLangFile() {
        return "Unpredictably Adds or removes a tier from a random affix.";
    }

    @Override
    public String locNameForLangFile() {
        return "Orb of Imbalance";
    }

    @Override
    public String GUID() {
        return "affix_tier_up_down";
    }

    @Override
    public int Weight() {
        return 1000;
    }
}
