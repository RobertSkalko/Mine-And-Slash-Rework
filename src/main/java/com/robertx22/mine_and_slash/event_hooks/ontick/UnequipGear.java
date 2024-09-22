package com.robertx22.mine_and_slash.event_hooks.ontick;

import com.robertx22.mine_and_slash.a_libraries.curios.MyCurioUtils;
import com.robertx22.mine_and_slash.database.data.omen.OmenData;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.PlayerUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Arrays;
import java.util.List;

public class UnequipGear {


    // dont drop weapons becasuse then newbies can't use stuff like axes at low level!

    public static List<EquipmentSlot> SLOTS = Arrays.asList(EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND);

    static void drop(Player player, EquipmentSlot slot, ItemStack stack, MutableComponent txt) {
        //ItemStack old = player.getItemBySlot(slot);
        ItemStack copy = stack.copy();

        player.setItemSlot(slot, ItemStack.EMPTY); // todo is this good?

        if (player.getItemBySlot(slot).isEmpty()) {
            if (slot == EquipmentSlot.MAINHAND) {
                var en = player.spawnAtLocation(stack, 1F);
                en.setPickUpDelay(40);

            } else {
                PlayerUtils.giveItem(copy, player);
            }
            player.displayClientMessage(txt
                    , false);
        } else {
            player.setItemSlot(slot, copy);
            System.out.print("Error in unequipping gear, weird!!!");
        }
        // player.onEquipItem(slot, old, copy); // todo will this fix modded items leaving effects?
    }

    static void drop(Player player, ICurioStacksHandler handler, int number, ItemStack stack, MutableComponent txt) {
        ItemStack copy = stack.copy();
        handler.getStacks().setStackInSlot(number, ItemStack.EMPTY);
        PlayerUtils.giveItem(copy, player);
        player.displayClientMessage(txt, false);
    }

    public static void check(Player player) {

        for (EquipmentSlot slot : SLOTS) {

            ItemStack stack = player.getItemBySlot(slot);

            GearItemData gear = StackSaving.GEARS.loadFrom(stack);

            if (gear != null) {
                if (!gear.canPlayerWear(Load.Unit(player))) {
                    if (!gear.isWeapon() && (slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND)) {

                    } else {
                        drop(player, slot, stack, Chats.GEAR_DROP.locName().withStyle(ChatFormatting.RED));
                    }
                }
            }
        }

        for (ICurioStacksHandler handler : MyCurioUtils.getHandlers(player)) {

            for (int i = 0; i < handler
                    .getSlots(); i++) {

                ItemStack stack = handler
                        .getStacks()
                        .getStackInSlot(i);

                if (!stack.isEmpty()) {
                    GearItemData gear = StackSaving.GEARS.loadFrom(stack);

                    if (gear != null) {
                        if (!gear.canPlayerWear(Load.Unit(player))) {
                            drop(player, handler, i, stack, Chats.GEAR_DROP.locName().withStyle(ChatFormatting.RED));
                        }
                    }
                    if (StackSaving.OMEN.has(stack)) {
                        OmenData omen = StackSaving.OMEN.loadFrom(stack);

                        if (omen != null) {
                            if (omen.lvl > Load.Unit(player).getLevel()) {
                                drop(player, handler, i, stack, Chats.GEAR_DROP.locName().withStyle(ChatFormatting.RED));
                            }
                        }

                    }

                }

            }

        }
    }
}
