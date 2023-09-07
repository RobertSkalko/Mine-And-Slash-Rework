package com.robertx22.age_of_exile.database.data.profession.all;

import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
import com.robertx22.age_of_exile.database.data.profession.CraftedItemHolder;
import com.robertx22.age_of_exile.database.data.profession.buffs.StatBuffs;
import com.robertx22.age_of_exile.database.data.profession.items.CraftedBuffFoodItem;
import com.robertx22.age_of_exile.database.data.profession.items.CraftedBuffPotionItem;
import com.robertx22.age_of_exile.database.data.profession.items.CraftedEnchantItem;
import com.robertx22.age_of_exile.database.data.profession.items.CraftedSoulItem;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.world.item.Item;

import java.util.HashMap;

public class ProfessionProductItems {

    public static HashMap<SlotFamily, HashMap<String, RegObj<Item>>> CRAFTED_SOULS = new HashMap<>();
    public static HashMap<SlotFamily, HashMap<String, RegObj<Item>>> CRAFTED_ENCHANTS = new HashMap<>();

    public static HashMap<StatBuffs.AlchemyBuff, CraftedItemHolder> POTIONS = new HashMap<>();
    public static HashMap<StatBuffs.FoodBuff, CraftedItemHolder> FOODS = new HashMap<>();

    public static void init() {

        for (StatBuffs.AlchemyBuff buff : StatBuffs.ALCHEMY) {
            CraftedItemHolder ho = new CraftedItemHolder(buff.id, new CraftedItemHolder.Maker("buff_potion", x -> new CraftedBuffPotionItem(buff.id, x)));
            POTIONS.put(buff, ho);
        }
        for (StatBuffs.FoodBuff buff : StatBuffs.COOKING) {
            CraftedItemHolder ho = new CraftedItemHolder(buff.id, new CraftedItemHolder.Maker("food", x -> new CraftedBuffFoodItem(buff.id, x)));
            FOODS.put(buff, ho);
        }

        for (String rar : IRarity.NORMAL_GEAR_RARITIES) {
            for (SlotFamily fam : SlotFamily.values()) {
                if (fam != SlotFamily.NONE) {

                    String id = "stat_soul/family/" + fam.id + "/" + rar;
                    if (!CRAFTED_SOULS.containsKey(fam)) {
                        CRAFTED_SOULS.put(fam, new HashMap<>());
                    }
                    RegObj<Item> obj = Def.item(id, () -> new CraftedSoulItem(fam, rar));
                    CRAFTED_SOULS.get(fam).put(rar, obj);

                }
            }
        }
        for (String rar : IRarity.NORMAL_GEAR_RARITIES) {
            for (SlotFamily fam : SlotFamily.values()) {
                if (fam != SlotFamily.NONE) {
                    String id = "enchantment/family/" + fam.id + "/" + rar;
                    if (!CRAFTED_ENCHANTS.containsKey(fam)) {
                        CRAFTED_ENCHANTS.put(fam, new HashMap<>());
                    }
                    RegObj<Item> obj = Def.item(id, () -> new CraftedEnchantItem(fam, rar));
                    CRAFTED_ENCHANTS.get(fam).put(rar, obj);

                }
            }
        }

    }
}
