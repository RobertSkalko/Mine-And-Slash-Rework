package com.robertx22.age_of_exile.database.data.currency.base;

import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface ICurrencyItemEffect {

    
    public abstract ItemStack internalModifyMethod(LocReqContext ctx, ItemStack stack, ItemStack currency);

    public abstract List<BaseLocRequirement> requirements();


    default boolean canItemBeModified(LocReqContext context) {

        for (BaseLocRequirement req : requirements()) {
            if (req.isNotAllowed(context)) {
                return false;
            }

        }
        return true;
    }

    public default void addToTooltip(List<Component> tooltip) {

        if (Screen.hasShiftDown()) {
            tooltip.add(TooltipUtils.color(ChatFormatting.RED, Words.Requirements.locName()
                    .append(": ")));

            for (BaseLocRequirement req : requirements()) {
                tooltip.add(TooltipUtils.color(ChatFormatting.RED,
                        Component.literal(" * ").append(req.getText())
                ));
            }
        } else {
            tooltip.add(TooltipUtils.color(ChatFormatting.GREEN, Words.PressShiftForRequirements.locName()));

        }
    }

    default ResultItem modifyItem(LocReqContext context) {
        if (context.effect.canItemBeModified(context)) {
            ItemStack copy = context.stack.copy();
            copy = context.effect.internalModifyMethod(context, copy, context.Currency);


            return new ResultItem(copy, ModifyResult.SUCCESS);
        } else {
            return new ResultItem(ItemStack.EMPTY, ModifyResult.NONE);
        }

    }

}
