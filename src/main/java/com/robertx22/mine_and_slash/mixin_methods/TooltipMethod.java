package com.robertx22.mine_and_slash.mixin_methods;

import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.database.data.currency.IItemAsCurrency;
import com.robertx22.mine_and_slash.database.data.gear_slots.GearSlot;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.saveclasses.unit.Unit;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.interfaces.INeedsNBT;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.registry.Database;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils.splitLongText;

public class TooltipMethod {
    public static List<Component> getTooltip(ItemStack stack, Player entity, TooltipFlag tooltipContext, CallbackInfoReturnable<List<Component>> list) {

        List<Component> tooltip = list.getReturnValue();

        boolean addCurrencyTooltip = stack
                .getItem() instanceof IItemAsCurrency;

        Player player = Minecraft.getInstance().player;

        try {


            if (Screen.hasControlDown()) {
                GearItemData gear = StackSaving.GEARS.loadFrom(stack);

                if (gear != null) {
                    tooltip.addAll(gear.getEnchantCompatTooltip(stack));
                    return tooltip;
                }
            }

            if (player == null || player.level() == null) {
                return tooltip;
            }

            EntityData unitdata = Load.Unit(player);

            if (unitdata == null) {
                return tooltip;
            }

            Unit unit = unitdata.getUnit();

            if (unit == null) {
                return tooltip;
            }
            if (!Database.areDatapacksLoaded(player.level())) {
                return tooltip;
            }


            TooltipContext ctx = new TooltipContext(stack, tooltip, unitdata);

            boolean hasdata = false;

            if (stack.hasTag()) {

                ICommonDataItem data = ICommonDataItem.load(stack);

                if (data != null) {
                    data.BuildTooltip(ctx);
                    hasdata = true;
                }

                MutableComponent broken = TooltipUtils.itemBrokenText(stack, data);
                if (broken != null) {
                    tooltip.add(broken);
                }


                if (StackSaving.TOOL.has(stack)) {
                    StackSaving.TOOL.loadFrom(stack).BuildTooltip(ctx);
                }

            }

            if (!hasdata) {
                GearSlot slot = GearSlot.getSlotOf(stack.getItem());
                if (slot != null) {
                    tooltip.add(TooltipUtils.gearSlot(slot));

                    tooltip.addAll(splitLongText(Chats.SOULLESS_GEAR_INFO.locName()));

                    if (Screen.hasShiftDown()) {
                        tooltip.addAll(splitLongText(Chats.SOULLESS_GEAR_MORE_INFO.locName()));
                    }
                }

                if (stack.getItem() instanceof INeedsNBT) {
                    tooltip.addAll(TooltipUtils.cutIfTooLong(Chats.ITEM_NON_NBT.locName().withStyle(ChatFormatting.RED)));
                }
            }

            if (addCurrencyTooltip) {
                IItemAsCurrency currency = (IItemAsCurrency) stack
                        .getItem();
                currency.currencyEffect(stack).addToTooltip(tooltip);
            }

            tooltip = TooltipUtils.removeDoubleBlankLines(tooltip);
            tooltip = splitLongText(tooltip);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tooltip;
    }
}
