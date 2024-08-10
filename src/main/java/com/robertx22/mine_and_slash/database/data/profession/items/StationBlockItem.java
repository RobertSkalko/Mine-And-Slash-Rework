package com.robertx22.mine_and_slash.database.data.profession.items;

import com.robertx22.mine_and_slash.database.data.currency.base.IShapedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class StationBlockItem extends BlockItem implements IShapedRecipe {
    Supplier<Item> sup;

    public StationBlockItem(Block pBlock, Properties pProperties, Supplier<Item> sup) {
        super(pBlock, pProperties);
        this.sup = sup;
    }

    @Override
    public ShapedRecipeBuilder getRecipe() {
        return shaped(this)
                .define('X', sup.get())
                .define('C', Items.CRAFTING_TABLE)
                .pattern("XXX")
                .pattern("XCX")
                .pattern("XXX")
                .unlockedBy("player_level", trigger());
    }

}
