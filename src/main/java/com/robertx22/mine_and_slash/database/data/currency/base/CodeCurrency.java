package com.robertx22.mine_and_slash.database.data.currency.base;

import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.library_of_exile.registry.IWeighted;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.mine_and_slash.database.data.profession.ExplainedResult;
import com.robertx22.mine_and_slash.gui.texts.textblocks.WorksOnBlock;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.loot.req.DropRequirement;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public abstract class CodeCurrency implements IWeighted, IAutoLocName, IAutoLocDesc, IGUID {

    public static class Weights {

        public static int COMMON = 1000;
        public static int RARE = 250;
        public static int UBER = 50;
        public static int MEGA_UBER = 10;

    }


    public abstract WorksOnBlock.ItemType usedOn();


    @Override
    public AutoLocGroup locDescGroup() {
        return AutoLocGroup.Currency_Items;
    }

    @Override
    public String locDescLangFileGUID() {
        return SlashRef.MODID + ".currency.desc." + GUID();
    }

    public DropRequirement getDropReq() {
        return DropRequirement.Builder.of().build();
    }


    public abstract void internalModifyMethod(LocReqContext ctx);

    public abstract List<BaseLocRequirement> requirements();

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Currency_Items;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".currency." + GUID();
    }

    public ExplainedResult canItemBeModified(LocReqContext context) {

        for (BaseLocRequirement req : requirements()) {
            if (req.isNotAllowed(context)) {
                return ExplainedResult.failure(Component.literal(""));
            }

        }
        return ExplainedResult.success();
    }

    public void addToTooltip(List<Component> tooltip) {
        if (!this.requirements().isEmpty()) {

            if (Screen.hasShiftDown()) {
                tooltip.add(TooltipUtils.color(ChatFormatting.RED, Words.Requirements.locName()));

                for (BaseLocRequirement req : requirements()) {
                    tooltip.add(TooltipUtils.color(ChatFormatting.RED,
                            Component.literal(" * ").append(req.getText())
                    ));
                }
            } else {
                tooltip.add(TooltipUtils.color(ChatFormatting.GREEN, Words.PressShiftForRequirements.locName()));
            }
        }
    }

    public ResultItem modifyItem(LocReqContext context) {
        var can = context.effect.canItemBeModified(context);
        if (can.can) {
            //ExileStack copy = ExileStack.of(context.stack.getStack());
            context.effect.internalModifyMethod(context);
            return new ResultItem(context.stack, ModifyResult.SUCCESS, can);
        } else {
            return new ResultItem(ExileStack.of(ItemStack.EMPTY), ModifyResult.NONE, can);
        }

    }

}
