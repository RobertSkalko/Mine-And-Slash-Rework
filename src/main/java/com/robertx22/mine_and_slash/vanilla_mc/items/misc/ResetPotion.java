package com.robertx22.mine_and_slash.vanilla_mc.items.misc;

import com.robertx22.mine_and_slash.database.data.currency.base.IShapedRecipe;
import com.robertx22.mine_and_slash.database.data.game_balance_config.PlayerPointsType;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class ResetPotion extends AutoItem implements IShapedRecipe {

    PlayerPointsType pointsType;
    ResetType reset;

    public ResetPotion(PlayerPointsType pointsType, ResetType reset) {
        super(new Item.Properties().stacksTo(64));
        this.pointsType = pointsType;
        this.reset = reset;
    }

    public enum ResetType {
        ADD_POINTS(() -> RarityStoneItem.of(IRarity.COMMON_ID), "Minor Reset", "reset_points"),
        FULL_RESET(() -> RarityStoneItem.of(IRarity.MYTHIC_ID), "Major Reset", "full_reset");

        public Supplier<Item> mat;
        public String name;
        public String id;

        ResetType(Supplier<Item> mat, String name, String id) {
            this.mat = mat;
            this.name = name;
            this.id = id;
        }

    }

    @Override
    public String GUID() {
        return "";
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity player) {

        if (!world.isClientSide) {
            stack.shrink(1);

            if (player instanceof Player) {
                Player p = (Player) player;
                if (reset == ResetType.FULL_RESET) {
                    this.pointsType.fullReset(p);
                } else {
                    this.pointsType.addResetPoints(p, 10);
                }

                Load.player(p).playerDataSync.setDirty();
                Load.Unit(p).sync.setDirty();

                p.addItem(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        return stack;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player player, InteractionHand handIn) {
        ItemStack itemStack = player.getItemInHand(handIn);
        player.startUsingItem(handIn);
        return InteractionResultHolder.success(itemStack);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 30;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> l, TooltipFlag pIsAdvanced) {
        if (reset == ResetType.ADD_POINTS) {
            l.addAll(TooltipUtils.splitLongText(Chats.RESET_POINTS_POTION_DESC.locName().withStyle(ChatFormatting.AQUA)));
        } else {
            l.add(Chats.RESET_FULL_POTION_DESC.locName().withStyle(ChatFormatting.AQUA));
        }
    }

    @Override
    public ShapedRecipeBuilder getRecipe() {
        return shaped(this)
                .define('v', reset.mat.get())
                .define('t', pointsType.matItem())
                .define('b', Items.GLASS_BOTTLE)
                .pattern("vvv")
                .pattern("vtv")
                .pattern("vbv")
                .unlockedBy("player_level", trigger());
    }

    @Override
    public String locNameForLangFile() {
        return reset.name + " " + pointsType.word().locNameForLangFile() + " Potion";
    }

}
