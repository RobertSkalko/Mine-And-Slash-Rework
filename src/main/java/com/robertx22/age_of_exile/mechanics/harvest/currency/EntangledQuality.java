package com.robertx22.age_of_exile.mechanics.harvest.currency;

import com.robertx22.age_of_exile.database.data.currency.base.GearOutcome;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.data.profession.ExplainedResult;
import com.robertx22.age_of_exile.mechanics.harvest.HarvestItems;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class EntangledQuality extends BaseHarvestCurrency {
    @Override
    public Item getSpecialCraftItem() {
        return Items.DIAMOND;
    }

    @Override
    public Item getHarvestCraftItem() {
        return HarvestItems.GREEN_INGOT.get();
    }

    @Override
    public GearOutcome getGoodOutcome() {
        return
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
                        data.setQuality(data.getQuality() + RandomUtils.RandomRange(1, 5));
                        StackSaving.GEARS.saveTo(stack, data);
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
        if (data.getQuality() < 21) {
            return ExplainedResult.success();
        }
        return ExplainedResult.failure(Chats.CANT_GO_ABOVE.locName(21));
    }


    @Override
    public String locDescForLangFile() {
        return "Either " + "Randomly Upgrades Quality of An Item (1-5%)" + " or Corrupts the Item, making it Unmodifiable.";
    }

    @Override
    public String locNameForLangFile() {
        return "Entangled Orb of Quality";
    }

    @Override
    public String GUID() {
        return "entangled_quality";
    }

    @Override
    public int Weight() {
        return 1000;
    }
}
