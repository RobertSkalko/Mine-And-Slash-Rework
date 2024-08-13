package com.robertx22.mine_and_slash.uncommon.coins;

import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.mine_and_slash.database.data.profession.ExplainedResult;
import com.robertx22.mine_and_slash.database.data.profession.LeveledItem;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.SlashItems;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.function.Function;

public class Coin implements IGUID {
    public static HashMap<String, Coin> ALL = new HashMap<>();

    private static String PROPHECY_ID = "prophecy";

    public static Coin PROPHECY = new Coin(PROPHECY_ID, "Prophecy", new Validator() {
        @Override
        public ExplainedResult isCoinValid(Player p, ItemStack stack) {
            var map = Load.mapAt(p.level(), p.blockPosition());
            if (map == null) {
                return ExplainedResult.failure(Component.literal("Not Inside a Map - Can't Check"));
            }
            var range = LeveledItem.getTier(stack);

            if (!range.levelRange.isLevelInRange(map.map.getLevel())) {
                return ExplainedResult.failure(Component.literal("Coin not usable in this map - Wrong Level"));
            }
            return ExplainedResult.success(Component.literal("Usable Coin"));
        }
    }, x -> {
        if (x.getItem() == SlashItems.COINS.get(PROPHECY_ID).get()) {
            return 1;
        }
        return 0;
    });

    public static void init() {


    }

    public String id;
    public String locname;
    Validator validator;
    Function<ItemStack, Integer> value;

    public Coin(String id, String locname, Validator validator, Function<ItemStack, Integer> value) {
        this.id = id;
        this.locname = locname;
        this.validator = validator;
        this.value = value;
        ALL.put(id, this);
    }

    public CoinItem getItem() {
        return SlashItems.COINS.get(id).get();
    }


    public int getTotalFromInventory(Player p) {
        int num = 0;
        for (int i = 0; i < p.getInventory().getContainerSize(); i++) {
            ItemStack stack = p.getInventory().getItem(i);
            num += getValueIgnoringStackCount(p, stack) * stack.getCount();
        }
        return num;

    }

    public ExplainedResult spend(Player p, int amount) {

        int tospend = amount;

        for (int i = 0; i < p.getInventory().getContainerSize(); i++) {
            ItemStack stack = p.getInventory().getItem(i);
            int val = getValueIgnoringStackCount(p, stack);


            if (val > 0) {
                int max = val * stack.getCount();
                if (tospend > (max)) {
                    tospend -= max;
                    stack.shrink(stack.getCount());
                } else {
                    while (tospend > 0 && stack.getCount() > 0) {
                        tospend -= val;
                        stack.shrink(1);
                    }
                }
            }
        }

        if (tospend > 0) {
            return ExplainedResult.failure(Component.literal("Didn't find enough currency to spend, this is a bug!"));
        } else {
            return ExplainedResult.success(Component.literal("Spent " + amount + this.locname + " coins"));
        }
    }

    public int getValueIgnoringStackCount(Player p, ItemStack stack) {
        if (!validator.isCoinValid(p, stack).can) {
            return 0;
        }
        return value.apply(stack);
    }

    @Override
    public String GUID() {
        return id;
    }


    public static abstract class Validator {
        public abstract ExplainedResult isCoinValid(Player p, ItemStack stack);
    }
}
