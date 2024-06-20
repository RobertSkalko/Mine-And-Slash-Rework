package com.robertx22.age_of_exile.mechanics.harvest.currency;

import com.robertx22.age_of_exile.database.data.currency.base.GearOutcome;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.data.profession.ExplainedResult;
import com.robertx22.age_of_exile.mechanics.harvest.HarvestItems;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class EntangledPotentialUpgrade extends BaseHarvestCurrency {

    static int potentialAdd = 25;

    @Override
    public Item getSpecialCraftItem() {
        return Items.GOLD_INGOT;
    }

    @Override
    public Item getHarvestCraftItem() {
        return HarvestItems.PURPLE_INGOT.get();
    }


    @Override
    public GearOutcome getGoodOutcome() {
        return new GearOutcome() {
            @Override
            public Words getName() {
                return Words.AddPotential;
            }

            @Override
            public OutcomeType getOutcomeType() {
                return OutcomeType.GOOD;
            }

            @Override
            public ItemStack modify(LocReqContext ctx, GearItemData gear, ItemStack stack) {
                gear.setPotential(gear.getPotentialNumber() + potentialAdd);
                StackSaving.GEARS.saveTo(stack, gear);
                return stack;
            }

            @Override
            public int Weight() {
                return 2000;
            }
        };
    }

    @Override
    public int getPotentialLoss() {
        return 1;
    }

    @Override
    public ExplainedResult canBeModified(GearItemData data) {
        return ExplainedResult.success();
    }

    @Override
    public String locDescForLangFile() {
        return "Either Adds " + potentialAdd + " Potential to the item or Corrupts the Item, making it Unmodifiable.";
    }

    @Override
    public String locNameForLangFile() {
        return "Entangled Orb of Potential";
    }

    @Override
    public String GUID() {
        return "entangled_potential";
    }

    @Override
    public int Weight() {
        return 250;
    }
}
