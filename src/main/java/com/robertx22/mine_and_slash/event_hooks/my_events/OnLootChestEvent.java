package com.robertx22.mine_and_slash.event_hooks.my_events;

import com.google.common.collect.Lists;
import com.robertx22.mine_and_slash.capability.player.data.PlayerConfigData;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.MasterLootGen;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.WorldUtils;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class OnLootChestEvent extends EventConsumer<ExileEvents.OnChestLooted> {

    @Override
    public void accept(ExileEvents.OnChestLooted event) {

        Player player = event.player;

        LootInfo info = LootInfo.ofChestLoot(player, event.pos);

        Load.player(player).favor.onLootChest(player);

        List<ItemStack> items = MasterLootGen.generateLoot(info);

        List<Integer> list1 = mygetEmptySlotsRandomized(event.inventory, new Random());

        if (list1.isEmpty()) {
            return;
        }

        for (int i = 0; i < items.size(); i++) {
            if (i < list1.size()) {
                int emptyslot = list1.get(i);
                event.inventory.setItem(emptyslot, items.get(i));
            }
        }

        if (Load.player(player).config.isConfigEnabled(PlayerConfigData.Config.DROP_MAP_CHEST_CONTENTS_ON_GROUND) && WorldUtils.isMapWorldClass(player.level())) {
            for (int i = 0; i < event.inventory.getContainerSize(); i++) {
                player.spawnAtLocation(event.inventory.getItem(i), 1F);
                event.inventory.setItem(i, ItemStack.EMPTY);
            }
        }
    }

    private static List<Integer> mygetEmptySlotsRandomized(Container inventory, Random rand) {
        List<Integer> list = Lists.newArrayList();

        for (int i = 0; i < inventory.getContainerSize(); ++i) {
            if (inventory.getItem(i)
                    .isEmpty()) {
                list.add(i);
            }
        }

        Collections.shuffle(list, rand);
        return list;
    }

    @Override
    public int callOrder() {
        return -1;
    }
}
