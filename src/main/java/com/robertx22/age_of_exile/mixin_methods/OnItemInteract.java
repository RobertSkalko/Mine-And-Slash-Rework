package com.robertx22.age_of_exile.mixin_methods;

import com.robertx22.age_of_exile.database.data.auto_item.AutoItem;
import com.robertx22.age_of_exile.database.data.currency.IItemAsCurrency;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.data.profession.items.CraftedSoulItem;
import com.robertx22.age_of_exile.mmorpg.ForgeEvents;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulItem;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.SoulMakerItem;
import com.robertx22.age_of_exile.vanilla_mc.items.TagForceSoulItem;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.RarityStoneItem;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ItemStackedOnOtherEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

public class OnItemInteract {

    public static void register() {

        ForgeEvents.registerForgeEvent(EntityItemPickupEvent.class, x -> {

            ItemStack stack = x.getItem().getItem();


            if (!StackSaving.GEARS.has(stack)) {
                if (!stack.hasTag() || (stack.hasTag() && !stack.getTag().getBoolean("free_souled"))) {
                    var auto = AutoItem.get(stack.getItem());
                    if (auto != null) {
                        stack.getOrCreateTag().putBoolean("free_souled", true);
                        StackSaving.GEARS.saveTo(stack, auto.create(x.getEntity()));
                    }

                }
            }
        });

        ForgeEvents.registerForgeEvent(ItemStackedOnOtherEvent.class, x -> {
            Player player = x.getPlayer();

            if (player.level().isClientSide) {
                return;
            }
            ItemStack cursor = x.getStackedOnItem();
            ItemStack stack = x.getCarriedItem();

            boolean sound = true;

            boolean success = false;


            if (StackSaving.JEWEL.has(stack)) {
                var data = StackSaving.JEWEL.loadFrom(stack);

                if (data.uniq.isCraftableUnique()) {
                    ItemStack cost = data.uniq.getStackNeededForUpgrade();

                    if (cost.getItem() == cursor.getItem()) {
                        if (cursor.getCount() >= cost.getCount()) {
                            if (data.uniq.getCraftedTier().canUpgradeMore()) {
                                data.uniq.upgradeUnique(data);

                                StackSaving.JEWEL.saveTo(stack, data);
                                cursor.shrink(cost.getCount());

                                SoundUtils.ding(player.level(), player.blockPosition());
                                SoundUtils.playSound(player.level(), player.blockPosition(), SoundEvents.ANVIL_USE, 1, 1);
                                x.setCanceled(true);

                                return;
                            }
                        }
                    }
                }
            }


            if (cursor.getItem() instanceof TagForceSoulItem force) {
                if (stack.getCount() == 1) {
                    StatSoulData data = StackSaving.STAT_SOULS.loadFrom(stack);
                    if (data != null && data.isArmor()) {
                        data.force_tag = force.tag;
                        data.saveToStack(stack);
                        success = true;
                        //cursor.shrink(1);

                    } else {
                        if (stack.getItem() instanceof CraftedSoulItem i) {
                            if (i.getSoul(stack) != null && i.getSoul(stack).isArmor()) {
                                stack.getOrCreateTag().putString("force_tag", force.tag);
                                success = true;
                                //cursor.shrink(1);
                            }
                        }
                    }
                }
            }


            if (stack.isDamaged() && cursor.getItem() instanceof RarityStoneItem) {

                if (!StackSaving.GEARS.has(stack)) {
                    return;
                }
                RarityStoneItem essence = (RarityStoneItem) cursor.getItem();

                SoundUtils.playSound(player, SoundEvents.ANVIL_USE, 1, 1);

                int repair = essence.getTotalRepair();

                stack.setDamageValue(stack.getDamageValue() - repair);
                success = true;

            } else if (cursor.getItem() instanceof StatSoulItem || cursor.getItem() instanceof CraftedSoulItem) {
                StatSoulData data = StackSaving.STAT_SOULS.loadFrom(cursor);
                if (cursor.getItem() instanceof CraftedSoulItem cs) {
                    data = cs.getSoul(cursor);
                }
                if (data != null) {
                    if (data.canInsertIntoStack(stack)) {
                        if (stack.getCount() == 1) {
                            data.insertAsUnidentifiedOn(stack, player);
                            success = true;
                        }
                    }
                }

            } else if (cursor.getItem() instanceof IItemAsCurrency) {
                LocReqContext ctx = new LocReqContext(player, stack, cursor);

                if (!stack.isEmpty()) {
                    var can = ctx.effect.canItemBeModified(ctx);
                    if (can.can) {
                        ItemStack result = ctx.effect.modifyItem(ctx).stack;

                        stack.shrink(1); // seems the currency creates a copy of a new item, so we delete the old one

                        sound = false;
                        PlayerUtils.giveItem(result, player);
                        //slot.set(result);
                        success = true;
                    } else {
                        player.sendSystemMessage(can.answer);
                    }
                }
            } else if (cursor.getItem() == SlashItems.SOCKET_EXTRACTOR.get()) {

                GearItemData gear = StackSaving.GEARS.loadFrom(stack);

                if (gear != null) {
                    if (gear.sockets != null && gear.sockets.getSocketed().size() > 0) {
                        try {
                            ItemStack gem = gear.sockets.getSocketed().get(0).getOriginalItemStack();
                            gear.sockets.getSocketed().remove(0);
                            StackSaving.GEARS.saveTo(stack, gear);
                            PlayerUtils.giveItem(gem, player);
                            cursor.shrink(1);
                            x.setCanceled(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }


                }
            } else if (cursor.getItem() instanceof SoulMakerItem se) {

                GearItemData gear = StackSaving.GEARS.loadFrom(stack);

                if (gear != null) {
                    try {

                        if (se.canExtract(gear)) {
                            StatSoulData soul = new StatSoulData();
                            soul.slot = gear.GetBaseGearType().getGearSlot().GUID();
                            soul.gear = gear;

                            ItemStack soulstack = soul.toStack();

                            SoundUtils.playSound(player, SoundEvents.EXPERIENCE_ORB_PICKUP);

                            stack.shrink(1);
                            cursor.shrink(1);
                            PlayerUtils.giveItem(soulstack, player);
                            x.setCanceled(true);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;


                }
            } else if (cursor.is(SlashItems.SOUL_CLEANER.get())) {

                GearItemData gear = StackSaving.GEARS.loadFrom(stack);

                if (gear != null) {
                    try {
                        stack.getOrCreateTag().remove(StackSaving.GEARS.GUID());
                        cursor.shrink(1);
                        x.setCanceled(true);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;


                }
            }

            if (success) {
                if (sound) {
                    SoundUtils.ding(player.level(), player.blockPosition());
                    SoundUtils.playSound(player.level(), player.blockPosition(), SoundEvents.ANVIL_USE, 1, 1);
                }
                x.setCanceled(true);
                cursor.shrink(1);
                //stack.shrink(1000);
                //ci.setReturnValue(ItemStack.EMPTY);
                //ci.cancel();
            }


        });
    }


}
