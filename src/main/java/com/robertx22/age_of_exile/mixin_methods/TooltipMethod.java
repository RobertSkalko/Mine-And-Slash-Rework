package com.robertx22.age_of_exile.mixin_methods;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.currency.IItemAsCurrency;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.registry.Database;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

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


            }

            if (!hasdata) {

                GearSlot slot = GearSlot.getSlotOf(stack.getItem());

                if (slot != null) {
                    tooltip.add(TooltipUtils.gearSlot(slot));
                }
            }

            if (addCurrencyTooltip) {
                IItemAsCurrency currency = (IItemAsCurrency) stack
                        .getItem();
                currency.currencyEffect(stack).addToTooltip(tooltip);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return tooltip;
    }
}
