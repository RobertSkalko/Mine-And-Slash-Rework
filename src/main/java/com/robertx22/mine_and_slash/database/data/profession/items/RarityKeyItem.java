package com.robertx22.mine_and_slash.database.data.profession.items;

import com.robertx22.mine_and_slash.database.data.currency.base.IShapedRecipe;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.RarityItems;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.StringUTIL;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.AutoItem;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

public class RarityKeyItem extends AutoItem implements IShapedRecipe {

    String name;
    String rar;

    public RarityKeyItem(String rar, String name) {
        super(new Properties().stacksTo(64));
        this.name = name;
        this.rar = rar;
    }

    @Override
    public String locNameForLangFile() {
        return StringUTIL.capitalise(rar) + " " + name;
    }

    @Override
    public String GUID() {
        return "";
    }

    @Override
    public ShapedRecipeBuilder getRecipe() {
        return shaped(this)
                .define('X', RarityItems.RARITY_STONE.get(rar).get())
                .define('C', Items.STICK)
                .pattern("XXX")
                .pattern("XCX")
                .pattern("XXX")
                .unlockedBy("player_level", trigger());
    }

}
