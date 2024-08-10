package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.baubles;

import com.robertx22.mine_and_slash.a_libraries.curios.interfaces.IRing;
import com.robertx22.mine_and_slash.database.data.currency.base.IShapedRecipe;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.VanillaMaterial;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases.BaseBaublesItem;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.ArmorItem;

public class ItemRing extends BaseBaublesItem implements IRing, IShapedRecipe {

    VanillaMaterial mat;

    public ItemRing(VanillaMaterial mat) {
        super(new Properties().durability(500 + mat.armormat.getDurabilityForType(ArmorItem.Type.CHESTPLATE) * 2)
                , "Ring");
        this.mat = mat;
    }
    
    @Override
    public ShapedRecipeBuilder getRecipe() {
        return shaped(this)
                .define('X', mat.mat.item)
                .pattern(" X ")
                .pattern("X X")
                .pattern(" X ")
                .unlockedBy("player_level", trigger());
    }
}
