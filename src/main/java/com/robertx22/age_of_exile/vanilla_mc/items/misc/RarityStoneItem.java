package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RarityItems;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.registry.IWeighted;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class RarityStoneItem extends Item implements IWeighted, IShapelessRecipe {

    String name;
    public int rarTier;

    public RarityStoneItem(String name, int rar) {
        super(new Properties().stacksTo(64));
        this.name = name;
        this.rarTier = rar;
    }

    public int getTotalRepair() {
        return 50 + (50 * rarTier);
    }

    public static Item of(String rar) {
        return RarityItems.RARITY_STONE.get(ExileDB.GearRarities().get(rar).item_tier).get(); // todo bad
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {

        tooltip.add(Words.CreatedInSalvageStation.locName());

        tooltip.add(Component.literal(""));

        tooltip.add(Component.literal("Repairs " + getTotalRepair() + " durability."));

        tooltip.add(TooltipUtils.dragOntoGearToUse());

    }


    @Override
    public int Weight() {
        return 100;
    }


    @Override
    public ShapelessRecipeBuilder getRecipe() {
        // de-craft recipe into lower tiers
        if (rarTier < 1) {
            return null;
        }

        Item output = RarityItems.RARITY_STONE.values()
                .stream()
                .filter(x -> x.get().rarTier == rarTier - 1)
                .findAny()
                .get()
                .get();

        return ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, 3)
                .requires(this, 1)
                .unlockedBy("player_level", trigger());

    }
}
