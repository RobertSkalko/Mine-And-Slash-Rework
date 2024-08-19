package com.robertx22.mine_and_slash.vanilla_mc.items;

import com.robertx22.mine_and_slash.database.data.currency.base.IShapelessRecipe;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.gui.texts.textblocks.AdditionalBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.OperationTipBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.WorksOnBlock;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.RarityItems;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.StringUTIL;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.AutoItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.RarityStoneItem;
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

import java.util.List;

import static com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils.splitLongText;

public class SoulExtractorItem extends AutoItem implements IShapelessRecipe {

    String rar;

    public SoulExtractorItem(String rar) {
        super(new Properties().stacksTo(64));
        this.rar = rar;
    }

    public boolean canExtract(GearRarity rarity) {
        if (!IRarity.NORMAL_GEAR_RARITIES.contains(rarity.GUID())) {
            return this.rar.equals(IRarity.MYTHIC_ID);
        }
        return this.rar.equals(rarity.GUID());
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
        ExileTooltips tip = new ExileTooltips();
        tip.accept(new AdditionalBlock(splitLongText(Itemtips.SOUL_EXTRACTOR_TIP.locName().withStyle(ChatFormatting.RED))));
        tip.accept(new WorksOnBlock(WorksOnBlock.Type.USABLE_ON).itemTypes(WorksOnBlock.ItemType.GEAR).rarities(ExileDB.GearRarities().getFilterWrapped(x -> canExtract(x)).list));
        tip.accept(new OperationTipBlock().setShift());
        tooltip.addAll(tip.release());
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
