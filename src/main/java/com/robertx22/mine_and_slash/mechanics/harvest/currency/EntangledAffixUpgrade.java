package com.robertx22.mine_and_slash.mechanics.harvest.currency;

import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.mine_and_slash.database.data.currency.base.GearOutcome;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.mine_and_slash.database.data.profession.ExplainedResult;
import com.robertx22.mine_and_slash.mechanics.harvest.HarvestItems;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.AffixData;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

// todo rework all harvest items simply into: upgrade OR destroy item
// rework them to be crafted with seeds

public class EntangledAffixUpgrade extends BaseHarvestCurrency {


    @Override
    public Item getSpecialCraftItem() {
        return Items.IRON_INGOT;
    }

    @Override
    public Item getHarvestCraftItem() {
        return HarvestItems.BLUE_INGOT.get();
    }

    @Override
    public GearOutcome getGoodOutcome() {
        return new GearOutcome() {
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
                AffixData data = RandomUtils.randomFromList(gear.affixes.getPrefixesAndSuffixes());
                data.upgradeRarity();
                data.RerollNumbers(gear);
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
        return 1;
    }

    @Override
    public ExplainedResult canBeModified(GearItemData data) {
        if (data.affixes.getNumberOfAffixes() > 0) {
            return ExplainedResult.success();
        }
        return ExplainedResult.failure(Chats.NEEDS_AN_AFFIX.locName());
    }

    @Override
    public String locDescForLangFile() {
        return "Either Upgrades a random affix, or Corrupts the Item, making it Unmodifiable.";
    }

    @Override
    public String locNameForLangFile() {
        return "Entangled Orb of Upgrade";
    }

    @Override
    public String GUID() {
        return "entangled_affix_upgrade";
    }

    @Override
    public int Weight() {
        return 250;
    }

 
}
