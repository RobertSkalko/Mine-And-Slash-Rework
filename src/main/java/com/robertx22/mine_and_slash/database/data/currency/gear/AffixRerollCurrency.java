package com.robertx22.mine_and_slash.database.data.currency.gear;

import com.robertx22.mine_and_slash.database.data.currency.base.GearCurrency;
import com.robertx22.mine_and_slash.database.data.currency.base.GearOutcome;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.mine_and_slash.database.data.profession.ExplainedResult;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.AffixData;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class AffixRerollCurrency extends GearCurrency {
    @Override
    public List<GearOutcome> getOutcomes() {

        return Arrays.asList(
                new GearOutcome() {
                    @Override
                    public Words getName() {
                        return Words.RerollsAffix;
                    }

                    @Override
                    public OutcomeType getOutcomeType() {
                        return OutcomeType.GOOD;
                    }

                    @Override
                    public ItemStack modify(LocReqContext ctx, GearItemData gear, ItemStack stack) {
                        AffixData data = RandomUtils.randomFromList(gear.affixes.getAllAffixesAndSockets());
                        data.RerollFully(gear);
                        data.rar = IRarity.COMMON_ID;
                        data.p = 0;
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
        return 1;
    }

    @Override
    public ExplainedResult canBeModified(GearItemData data) {
        if (data.affixes.getNumberOfAffixes() < 1) {
            return ExplainedResult.failure(Chats.NEEDS_AN_AFFIX.locName());
        }
        return ExplainedResult.success();
    }

    @Override
    public String locDescForLangFile() {
        return "Re-rolls a random affix into a Common Rarity Random Affix.";
    }

    @Override
    public String locNameForLangFile() {
        return "Orb of New Beginnings";
    }

    @Override
    public String GUID() {
        return "affix_common_reroll";
    }

    @Override
    public int Weight() {
        return 1000;
    }
}
