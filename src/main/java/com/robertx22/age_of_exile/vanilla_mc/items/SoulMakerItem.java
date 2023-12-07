package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RarityItems;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import com.robertx22.age_of_exile.uncommon.utilityclasses.StringUTIL;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.RarityStoneItem;
import net.minecraft.advancements.critereon.EnchantedItemTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class SoulMakerItem extends AutoItem implements IShapelessRecipe {

    String rar;

    public SoulMakerItem(String rar) {
        super(new Properties().stacksTo(64));
        this.rar = rar;
    }

    public boolean canExtract(GearItemData gear) {
        if (!IRarity.NORMAL_GEAR_RARITIES.contains(gear.rar)) {
            return rar.equals(IRarity.MYTHIC_ID);
        }
        return gear.rar.equals(rar);
    }

    @Override
    public String locNameForLangFile() {
        return StringUTIL.capitalise(rar) + " Gear Soul Extractor";
    }

    @Override
    public String GUID() {
        return rar + "_soul_extractor";
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Itemtips.SOUL_EXTRACTOR_TIP.locName());

    }

    @Override
    public ShapelessRecipeBuilder getRecipe() {

        RarityStoneItem item = RarityItems.RARITY_STONE.get(rar).get();
        Item middle = Items.GLASS_BOTTLE;

        return ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, this)
                .unlockedBy("player_level", EnchantedItemTrigger.TriggerInstance.enchantedItem())
                .requires(item, 2)
                .requires(middle, 1);
    }

}
