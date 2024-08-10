package com.robertx22.mine_and_slash.mechanics.harvest.currency;

import com.robertx22.mine_and_slash.database.data.currency.base.GearCurrency;
import com.robertx22.mine_and_slash.database.data.currency.base.GearOutcome;
import com.robertx22.mine_and_slash.database.data.currency.base.IShapedRecipe;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.mine_and_slash.database.data.league.LeagueMechanics;
import com.robertx22.mine_and_slash.loot.req.DropRequirement;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public abstract class BaseHarvestCurrency extends GearCurrency implements IShapedRecipe {
    @Override
    public final DropRequirement getDropReq() {
        return DropRequirement.Builder.of().setOnlyDropsInLeague(LeagueMechanics.HARVEST.GUID()).build();
    }

    public abstract Item getSpecialCraftItem();

    public abstract Item getHarvestCraftItem();

    @Override
    public ShapedRecipeBuilder getRecipe() {
        return shaped(this.getCurrencyItem())
                .define('X', getSpecialCraftItem())
                .define('Y', getHarvestCraftItem())
                .pattern("YYY")
                .pattern("YXY")
                .pattern("YYY")
                .unlockedBy("player_level", trigger());
    }

    @Override
    public List<GearOutcome> getOutcomes() {
        return Arrays.asList(
                new GearOutcome() {
                    @Override
                    public Words getName() {
                        return Words.CorruptsItemHarvest;
                    }

                    @Override
                    public OutcomeType getOutcomeType() {
                        return OutcomeType.BAD;
                    }

                    @Override
                    public ItemStack modify(LocReqContext ctx, GearItemData gear, ItemStack stack) {
                        gear.data.set(GearItemData.KEYS.CORRUPT, true);
                        StackSaving.GEARS.saveTo(stack, gear);
                        return stack;
                    }

                    @Override
                    public int Weight() {
                        return 1000;
                    }
                },
                getGoodOutcome()
        );
    }

    public abstract GearOutcome getGoodOutcome();

    @Override
    public int getPotentialLoss() {
        return 0;
    }


}
