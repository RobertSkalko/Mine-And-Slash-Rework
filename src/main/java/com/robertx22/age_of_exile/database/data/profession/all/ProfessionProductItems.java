package com.robertx22.age_of_exile.database.data.profession.all;

import com.robertx22.age_of_exile.database.data.profession.CraftedItemHolder;
import com.robertx22.age_of_exile.database.data.profession.buffs.StatBuffs;
import com.robertx22.age_of_exile.database.data.profession.items.CraftedBuffFoodItem;
import com.robertx22.age_of_exile.database.data.profession.items.CraftedBuffPotionItem;

import java.util.HashMap;

public class ProfessionProductItems {

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

    }
}
