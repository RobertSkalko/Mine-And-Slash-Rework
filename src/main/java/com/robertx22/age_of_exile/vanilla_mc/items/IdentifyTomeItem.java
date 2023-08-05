package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class IdentifyTomeItem extends AutoItem {

    public IdentifyTomeItem() {
        super(new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand handIn) {
        ItemStack tomeStack = player.getItemInHand(handIn);
        List<ItemStack> list = new ArrayList<>();

        for (ItemStack stack : player.getInventory().items) {

            if (tomeStack.getCount() > 0) {
                StatSoulData data = StackSaving.STAT_SOULS.loadFrom(stack);

                if (data != null && data.gear == null) {

                    GearItemData gear = data.createGearData(null);
                    data.gear = gear;

                    if (stack.getCount() == 1) {
                        StackSaving.STAT_SOULS.saveTo(stack, data);
                        tomeStack.shrink(1);

                    } else {

                        int count = 0;
                        for (int i = 0; i < stack.getCount(); i++) {

                            if (tomeStack.getCount() > 0) {
                                count++;

                                GearItemData newgear = data.createGearData(null);
                                data.gear = newgear;

                                ItemStack newsoul = new ItemStack(stack.getItem());
                                StackSaving.STAT_SOULS.saveTo(newsoul, data);
                                tomeStack.shrink(1);

                                list.add(newsoul);
                            }

                        }
                        stack.shrink(count);

                    }

                }
            }

        }
        list.forEach(x -> {
            PlayerUtils.giveItem(x, player);
        });

        return InteractionResultHolder.success(tomeStack);
    }

    @Override
    public String locNameForLangFile() {
        return "Identify Tome";
    }

    @Override
    public String GUID() {
        return "";
    }
}
