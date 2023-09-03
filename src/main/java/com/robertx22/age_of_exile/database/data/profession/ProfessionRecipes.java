package com.robertx22.age_of_exile.database.data.profession;

import net.minecraft.world.item.Items;

public class ProfessionRecipes {

    public static void init() {

        
        ProfessionRecipe.Builder.of(Items.DIAMOND, 1, Professions.COOKING)
                .materialItems(Items.IRON_INGOT, Items.COAL)
                .build();


    }
}
