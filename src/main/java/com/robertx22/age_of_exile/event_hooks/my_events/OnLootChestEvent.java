package com.robertx22.age_of_exile.event_hooks.my_events;

import com.google.common.collect.Lists;
import com.robertx22.age_of_exile.capability.player.data.PlayerConfigData;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.MasterLootGen;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.OnScreenMessageUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

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

        if(Load.player(player).config.isConfigEnabled(PlayerConfigData.Config.DROP_MAP_CHEST_CONTENTS_ON_GROUND) && WorldUtils.isMapWorldClass(player.level())) {
            for(ItemStack item : items) {
                player.spawnAtLocation(item, 1F);
            }

            for(int i=0; i<event.inventory.getContainerSize(); i++) {
                event.inventory.setItem(i, ItemStack.EMPTY);
            }

            return;
        }

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
