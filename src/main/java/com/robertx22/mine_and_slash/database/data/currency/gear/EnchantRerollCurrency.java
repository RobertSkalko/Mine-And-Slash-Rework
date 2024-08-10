package com.robertx22.mine_and_slash.database.data.currency.gear;

import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.database.data.currency.base.GearCurrency;
import com.robertx22.mine_and_slash.database.data.currency.base.GearOutcome;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.mine_and_slash.database.data.profession.ExplainedResult;
import com.robertx22.mine_and_slash.database.data.requirements.bases.GearRequestedFor;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.GearInfusionData;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.tags.all.SlotTags;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class EnchantRerollCurrency extends GearCurrency {
    @Override
    public List<GearOutcome> getOutcomes() {

        return Arrays.asList(
                new GearOutcome() {
                    @Override
                    public Words getName() {
                        return Words.SUCCESS;
                    }

                    @Override
                    public OutcomeType getOutcomeType() {
                        return OutcomeType.GOOD;
                    }

                    @Override
                    public ItemStack modify(LocReqContext ctx, GearItemData gear, ItemStack stack) {

                        GearInfusionData en = new GearInfusionData();

                        Affix affix = ExileDB.Affixes().getFilterWrapped(x -> {
                            return x.type == Affix.Type.enchant && x.requirements.satisfiesAllRequirements(new GearRequestedFor(gear)) && x.getAllTagReq().contains(SlotTags.enchantment.GUID());
                        }).random();

                        en.en = affix.GUID();

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
        if (!data.ench.isEmpty()) {
            return ExplainedResult.success();
        }
        return ExplainedResult.failure(Chats.NO_ENCHANT_ON_ITEM.locName());
    }

    @Override
    public String locDescForLangFile() {
        return "Randomizes the Infusion";
    }

    @Override
    public String locNameForLangFile() {
        return "Orb of Second Guessing";
    }

    @Override
    public String GUID() {
        return "enchant_reroll";
    }

    @Override
    public int Weight() {
        return Weights.COMMON;
    }
}
