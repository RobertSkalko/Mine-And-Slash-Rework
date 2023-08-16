package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.a_libraries.curios.interfaces.IBackpack;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.capability.player.data.Backpacks;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RarityItems;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.utilityclasses.StringUTIL;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.RarityStoneItem;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.critereon.EnchantedItemTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class BackpackItem extends AutoItem implements IBackpack, IAutoLocName, IAutoModel, IShapelessRecipe {
    String rar;

    public BackpackItem(String rar) {
        super(new Properties());
        this.rar = rar;

    }

    public GearRarity getRarity() {

        return ExileDB.GearRarities().get(rar);
    }

    public int getSlots() {
        return getRarity().backpack_slots;
    }


    @Override
    public String locNameForLangFile() {
        return getRarity().textFormatting() + StringUTIL.capitalise(getRarity().GUID()) + " Backpack";
    }

    @Override
    public String GUID() {
        return "we";
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {

        //list.clear();

        //  list.add(getRarity().locName().append(" Backpack").withStyle(getRarity().textFormatting()));

        for (Backpacks.BackpackType type : Backpacks.BackpackType.values()) {
            int slots = getSlots();
            list.add(type.name.locName().append(" Slots: " + slots).withStyle(ChatFormatting.GREEN));
        }

        list.add(Component.literal(""));
        list.add(Component.literal("Equip to Curio Slot"));

    }


    @Override
    public ShapelessRecipeBuilder getRecipe() {

        RarityStoneItem item = RarityItems.RARITY_STONE.get(rar).get();

        Item middle = Items.CHEST;

        if (getRarity().item_tier != 0) {
            if (getRarity().getLowerRarity().isPresent()) {
                GearRarity lower = getRarity().getLowerRarity().get();
                middle = RarityItems.BACKPACKS.get(lower.GUID()).get();
            }
        }

        return ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, this)
                .unlockedBy("player_level", EnchantedItemTrigger.TriggerInstance.enchantedItem())
                .requires(item, 8)
                .requires(middle, 1);
    }

}
