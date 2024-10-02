package com.robertx22.mine_and_slash.database.data.currency.base;

import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.library_of_exile.utils.SoundUtils;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.mine_and_slash.database.data.profession.ExplainedResult;
import com.robertx22.mine_and_slash.gui.texts.textblocks.WorksOnBlock;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;

import java.util.Arrays;
import java.util.List;


public abstract class GearCurrency extends CodeCurrency {

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
    public void internalModifyMethod(LocReqContext ctx) {
        //GearItemData data = ctx.stack.GEAR.get();

        GearOutcome outcome = getOutcome();

        ctx.stack.POTENTIAL.edit(x -> x.spend(getPotentialLoss()));
      
        Player player = ctx.player;
        if (outcome.getOutcomeType() == GearOutcome.OutcomeType.GOOD) {
            SoundUtils.ding(player.level(), player.blockPosition());
            SoundUtils.playSound(player.level(), player.blockPosition(), SoundEvents.ANVIL_USE, 1, 1);
        } else {
            SoundUtils.playSound(player.level(), player.blockPosition(), SoundEvents.GLASS_BREAK, 1, 1);
            SoundUtils.playSound(player.level(), player.blockPosition(), SoundEvents.VILLAGER_NO, 1, 1);
        }
        outcome.modify(ctx);
    }

    private GearOutcome getOutcome() {
        return RandomUtils.weightedRandom(getOutcomes());
    }

    @Override
    public ExplainedResult canItemBeModified(LocReqContext context) {
        GearItemData data = context.stack.GEAR.get();


        if (data == null) {
            return ExplainedResult.failure(Chats.NOT_GEAR.locName());
        }

        if (context.stack.isCorrupted() && this.spendsGearPotential()) {
            return ExplainedResult.failure(Chats.CORRUPT_CANT_BE_MODIFIED.locName());
        }

        if (context.stack.POTENTIAL.get().potential < 1) {
            if (this.spendsGearPotential()) {
                return ExplainedResult.failure(Chats.GEAR_NO_POTENTIAL.locName());
            }
        }

        var can = canBeModified(context.stack);
        if (!can.can) {
            return can;
        }
        return super.canItemBeModified(context);
    }

    public abstract ExplainedResult canBeModified(ExileStack data);


    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList();
    }
}
