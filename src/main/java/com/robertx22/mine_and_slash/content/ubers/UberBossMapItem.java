package com.robertx22.mine_and_slash.content.ubers;

import com.robertx22.mine_and_slash.database.data.currency.base.IShapedRecipe;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.MapBlueprint;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ICreativeIgnoreOption;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.SlashItems;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.AutoItem;
import net.minecraft.ChatFormatting;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

import static com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils.splitLongText;

public class UberBossMapItem extends AutoItem implements IShapedRecipe, ICreativeIgnoreOption {

    public int uberTier;
    public UberEnum uber;

    public UberBossMapItem(int uberTier, UberEnum uber) {
        super(new Properties().stacksTo(1));
        this.uberTier = uberTier;
        this.uber = uber;
    }

    @Override
    public boolean shouldShowInCreative() {
        return ExileDB.UberBoss().isRegistered(uber.id);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player p, InteractionHand pUsedHand) {
        ItemStack itemstack = p.getItemInHand(pUsedHand);

        if (!pLevel.isClientSide) {

            p.getItemInHand(pUsedHand).shrink(1);

            var tier = UberBossTier.map.get(uberTier);

            MapBlueprint b = new MapBlueprint(LootInfo.ofLevel(tier.boss_lvl));
            b.setUberBoss(ExileDB.UberBoss().get(uber.id), tier);
            b.level.set(tier.boss_lvl);

            ItemStack stack = b.createStack();

            PlayerUtils.giveItem(stack, p);

        }
        return InteractionResultHolder.pass(p.getItemInHand(pUsedHand));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.addAll(splitLongText(Itemtips.UBER_BOSS_MAP_TIP.locName().withStyle(ChatFormatting.RED)));
        pTooltipComponents.add(Words.Level.locName().append(" " + UberBossTier.map.get(uberTier).boss_lvl).withStyle(ChatFormatting.YELLOW));

    }

    @Override
    public ShapedRecipeBuilder getRecipe() {
        return shaped(this)
                .define('X', SlashItems.UBER_FRAGS.get(uber).get(uberTier).get())
                .pattern("XX")
                .pattern("XX")
                .unlockedBy("player_level", trigger());
    }

    @Override
    public String locNameForLangFile() {
        return uber.arenaName;
    }

    @Override
    public String GUID() {
        return null;
    }
}
