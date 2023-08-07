package com.robertx22.age_of_exile.database.data.currency.base;

import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.CurrencyItems;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.library_of_exile.registry.IWeighted;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public abstract class Currency implements IWeighted, IAutoLocName, IAutoLocDesc, IGUID, ExileRegistry<Currency> {


    public Item getCurrencyItem() {

        return CurrencyItems.map.get(GUID()).get();
    }

    @Override
    public AutoLocGroup locDescGroup() {
        return AutoLocGroup.Currency_Items;
    }

    @Override
    public String locDescLangFileGUID() {
        return SlashRef.MODID + ".currency.desc." + GUID();
    }


    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.CURRENCY_ITEMS;
    }

    public abstract ItemStack internalModifyMethod(LocReqContext ctx, ItemStack stack, ItemStack currency);

    public abstract List<BaseLocRequirement> requirements();

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Currency_Items;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".currency." + GUID();
    }

    public boolean canItemBeModified(LocReqContext context) {

        for (BaseLocRequirement req : requirements()) {
            if (req.isNotAllowed(context)) {
                return false;
            }

        }
        return true;
    }

    public void addToTooltip(List<Component> tooltip) {

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

    public ResultItem modifyItem(LocReqContext context) {
        if (context.effect.canItemBeModified(context)) {
            ItemStack copy = context.stack.copy();
            copy = context.effect.internalModifyMethod(context, copy, context.Currency);


            return new ResultItem(copy, ModifyResult.SUCCESS);
        } else {
            return new ResultItem(ItemStack.EMPTY, ModifyResult.NONE);
        }

    }

}
