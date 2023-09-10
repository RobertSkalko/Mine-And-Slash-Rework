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

public class QualityUpgrade extends GearCurrency {
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
                    public ItemStack modify(LocReqContext ctx, GearItemData data, ItemStack stack) {

                        if (data.getQualityType() != GearItemData.GearQualityType.BASE) {
                            data.setQuality(0, GearItemData.GearQualityType.BASE);
                        }

                        data.setQuality(data.getQuality() + 1, GearItemData.GearQualityType.BASE);
                        StackSaving.GEARS.saveTo(stack, data);
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
        return 0;
    }

    @Override
    public ExplainedResult canBeModified(GearItemData data) {
        if (data.getQuality() < 20) {
            return ExplainedResult.success();
        }
        return ExplainedResult.failure(Chats.CANT_GO_ABOVE.locName(20));
    }

    @Override
    public String locDescForLangFile() {
        return "Upgrades Quality of An Item, quality boosts defenses.";
    }

    @Override
    public String locNameForLangFile() {
        return "Orb of Quality";
    }

    @Override
    public String GUID() {
        return "orb_of_quality";
    }

    @Override
    public int Weight() {
        return 1000;
    }
}
