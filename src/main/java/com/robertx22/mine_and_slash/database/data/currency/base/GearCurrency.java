package com.robertx22.mine_and_slash.database.data.currency.base;

import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.library_of_exile.utils.SoundUtils;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.mine_and_slash.database.data.profession.ExplainedResult;
import com.robertx22.mine_and_slash.gui.texts.textblocks.WorksOnBlock;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;


public abstract class GearCurrency extends Currency {

    @Override
    public WorksOnBlock.ItemType usedOn() {
        return WorksOnBlock.ItemType.GEAR;
    }

    public abstract List<GearOutcome> getOutcomes();

    public abstract int getPotentialLoss();

    public boolean spendsGearPotential() {
        return getPotentialLoss() > 0;
    }

    @Override
    public ItemStack internalModifyMethod(LocReqContext ctx, ItemStack stack, ItemStack currency) {
        GearItemData data = StackSaving.GEARS.loadFrom(stack);

        GearOutcome outcome = getOutcome();
        data.setPotential(data.getPotentialNumber() - getPotentialLoss());

        Player player = ctx.player;
        if (outcome.getOutcomeType() == GearOutcome.OutcomeType.GOOD) {
            SoundUtils.ding(player.level(), player.blockPosition());
            SoundUtils.playSound(player.level(), player.blockPosition(), SoundEvents.ANVIL_USE, 1, 1);
        } else {
            SoundUtils.playSound(player.level(), player.blockPosition(), SoundEvents.GLASS_BREAK, 1, 1);
            SoundUtils.playSound(player.level(), player.blockPosition(), SoundEvents.VILLAGER_NO, 1, 1);
        }
        return outcome.modify(ctx, data, stack);
    }

    private GearOutcome getOutcome() {
        return RandomUtils.weightedRandom(getOutcomes());
    }

    @Override
    public ExplainedResult canItemBeModified(LocReqContext context) {
        GearItemData data = StackSaving.GEARS.loadFrom(context.stack);


        if (data == null) {
            return ExplainedResult.failure(Chats.NOT_GEAR.locName());
        }

        if (data.isCorrupted() && this.spendsGearPotential()) {
            return ExplainedResult.failure(Chats.CORRUPT_CANT_BE_MODIFIED.locName());
        }

        if (data.getPotentialNumber() < 1) {
            if (this.spendsGearPotential()) {
                return ExplainedResult.failure(Chats.GEAR_NO_POTENTIAL.locName());
            }
        }

        var can = canBeModified(data);
        if (!can.can) {
            return can;
        }
        return super.canItemBeModified(context);
    }

    public abstract ExplainedResult canBeModified(GearItemData data);


    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList();
    }
}
