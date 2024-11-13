package com.robertx22.mine_and_slash.vanilla_mc.items;

import com.robertx22.library_of_exile.utils.SoundUtils;
import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.database.data.currency.base.IShapedRecipe;
import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.gui.texts.textblocks.AdditionalBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.RequirementBlock;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.MapBlueprint;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.AutoItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.RarityStoneItem;
import net.minecraft.ChatFormatting;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

import static com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils.splitLongText;

public class MapCreator extends AutoItem implements IShapedRecipe {

    public MapCreator() {
        super(new Item.Properties());

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player p, InteractionHand pUsedHand) {
        ItemStack itemstack = p.getItemInHand(pUsedHand);

        if (!pLevel.isClientSide) {

            if (Load.Unit(p).getLevel() < ServerContainer.get().MIN_LEVEL_MAP_DROPS.get()) {


                return InteractionResultHolder.pass(p.getItemInHand(pUsedHand));
            }

            itemstack.shrink(1);

            SoundUtils.playSound(p, SoundEvents.ITEM_PICKUP);

            MapBlueprint map = new MapBlueprint(LootInfo.ofPlayer(p));
            map.level.set(Load.Unit(p).getLevel());
            var stack = map.createStack();
            PlayerUtils.giveItem(stack, p);

            return InteractionResultHolder.success(p.getItemInHand(pUsedHand));

        }
        return InteractionResultHolder.pass(p.getItemInHand(pUsedHand));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
        ExileTooltips tip = new ExileTooltips();
        tip.accept(new RequirementBlock(ServerContainer.get().MIN_LEVEL_MAP_DROPS.get()));
        tip.accept(new AdditionalBlock(splitLongText(Itemtips.MAP_CREATOR_ITEM.locName().withStyle(ChatFormatting.YELLOW))));
        list.addAll(tip.release());
    }


    @Override
    public String locNameForLangFile() {
        return "Adventure Map Creator";
    }

    @Override
    public ShapedRecipeBuilder getRecipe() {
        return shaped(this)
                .define('X', RarityStoneItem.of(IRarity.EPIC_ID))
                .define('H', Items.IRON_INGOT)
                .define('Y', Items.GOLD_INGOT)
                .pattern("YYY")
                .pattern("XHX")
                .pattern("XXX")
                .unlockedBy("player_level", trigger());
    }

    @Override
    public String GUID() {
        return "map_creator";
    }
}
