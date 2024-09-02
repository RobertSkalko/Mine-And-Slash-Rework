package com.robertx22.mine_and_slash.database.data.profession.items;

import com.robertx22.mine_and_slash.database.data.currency.base.IShapedRecipe;
import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.gui.texts.textblocks.usableitemblocks.UsageBlock;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.RarityItems;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.StringUTIL;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.AutoItem;
import net.minecraft.ChatFormatting;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

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
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> tip, @NotNull TooltipFlag pIsAdvanced) {
        tip.addAll(
                new ExileTooltips()
                        .accept(new UsageBlock(Collections.singletonList(Itemtips.LOOT_CHEST_KEY_DESC.locName().withStyle(ChatFormatting.AQUA))))
                        .release());
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
