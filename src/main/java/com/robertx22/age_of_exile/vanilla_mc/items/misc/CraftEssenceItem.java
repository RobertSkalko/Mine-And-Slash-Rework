package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.function.Supplier;

import com.robertx22.age_of_exile.uncommon.interfaces.IBaseAutoLoc.AutoLocGroup;
import net.minecraft.world.item.Item.Properties;

public class CraftEssenceItem extends Item implements IAutoLocName, IAutoModel, IShapedRecipe {

    String id;

    Supplier<Item> craftItem;
    String locname;

    public CraftEssenceItem(String id, Supplier<Item> craftItem, String locname) {
        super(new Properties().tab(CreativeTabs.MyModTab));
        this.id = id;
        this.craftItem = craftItem;
        this.locname = locname;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
        tooltip.add(new TextComponent("Used for crafting Special Gears."));
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getKey(this)
            .toString();
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

    @Override
    public String GUID() {
        return "gear_mat/essences/" + id;
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    @Override
    public ShapedRecipeBuilder getRecipe() {

        Item resultItem = Registry.ITEM.get(new ResourceLocation(SlashRef.MODID, GUID()));

        ShapedRecipeBuilder fac = ShapedRecipeBuilder.shaped(resultItem, 8);

        return fac.define('e', Items.COAL)
            .define('s', craftItem.get())
            .pattern("ses")
            .unlockedBy("player_level", trigger());

    }

}
