package com.robertx22.age_of_exile.mechanics.harvest.currency;

import com.robertx22.age_of_exile.database.data.currency.base.GearOutcome;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.data.profession.ExplainedResult;
import com.robertx22.age_of_exile.mechanics.harvest.HarvestItems;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class EntangledUniqueReroll extends BaseHarvestCurrency {

    @Override
    public Item getSpecialCraftItem() {
        return Items.DIAMOND_BLOCK;
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
                return Words.UpgradesUniqueStats;
            }

            @Override
            public OutcomeType getOutcomeType() {
                return OutcomeType.GOOD;
            }

            @Override
            public ItemStack modify(LocReqContext ctx, GearItemData gear, ItemStack stack) {
                gear.uniqueStats.increaseAllBy(gear, 10);
                StackSaving.GEARS.saveTo(stack, gear);
                return stack;
            }

            @Override
            public int Weight() {
                return 1000;
            }
        };
    }


    @Override
    public int getPotentialLoss() {
        return 0;
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
        return "Either Upgrades unique stats by +10%" + " or Corrupts the Item, making it Unmodifiable.";
    }

    @Override
    public String locNameForLangFile() {
        return "Entangled Orb of Imperfection";
    }

    @Override
    public String GUID() {
        return "entangled_unique_reroll";
    }

    @Override
    public int Weight() {
        return Weights.UBER;
    }
}
