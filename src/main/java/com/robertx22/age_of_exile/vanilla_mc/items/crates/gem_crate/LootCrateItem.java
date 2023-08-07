package com.robertx22.age_of_exile.vanilla_mc.items.crates.gem_crate;

import com.robertx22.age_of_exile.database.data.currency.base.Currency;
import com.robertx22.age_of_exile.database.data.gems.Gem;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TierColors;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.GemItem;
import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Arrays;
import java.util.List;

public class LootCrateItem extends Item implements IGUID {
    public LootCrateItem() {
        super(new Properties());
    }

    public static List<LootType> LOOT_TYPES = Arrays.asList(LootType.Gem, LootType.Rune, LootType.Currency);

    public LootCrateData getData(ItemStack stack) {
        return StackSaving.GEM_CRATE.loadFrom(stack);
    }

    public static ItemStack ofGem(GemItem.GemRank rank) {
        LootCrateData data = new LootCrateData();
        data.tier = rank.tier;
        data.type = data.type;
        ItemStack stack = new ItemStack(SlashItems.LOOT_CRATE.get());
        StackSaving.GEM_CRATE.saveTo(stack, data);
        stack.getTag()
                .putInt("CustomModelData", data.type.custommodeldata);

        return stack;

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!world.isClientSide) {

            try {

                ItemStack reward = ItemStack.EMPTY;

                LootCrateData data = StackSaving.GEM_CRATE.loadFrom(stack);

                if (data.type == LootType.Gem) {
                    Gem gem = ExileDB.Gems()
                            .getFilterWrapped(x -> data.tier == x.tier)
                            .random();
                    reward = new ItemStack(gem.getItem());
                } else if (data.type == LootType.Rune) {
                    Rune rune = ExileDB.Runes()
                            .getFilterWrapped(x -> data.tier >= x.tier)
                            .random();
                    reward = new ItemStack(rune.getItem());
                } else if (data.type == LootType.Currency) {
                    Currency currency = ExileDB.CurrencyItems()
                            .getFilterWrapped(x -> true)
                            .random();
                    if (currency == null) {
                        currency = ExileDB.CurrencyItems()
                                .random();
                    }
                    reward = new ItemStack(currency.getCurrencyItem());
                }

                stack.shrink(1);

                SoundUtils.ding(player.level(), player.blockPosition());
                PlayerUtils.giveItem(reward, player);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new InteractionResultHolder<ItemStack>(InteractionResult.PASS, player.getItemInHand(hand));
    }


    // todo
    /*
    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> stacks) {
        if (this.allowdedIn(group)) {

            for (int tier : LevelUtils.getAllTiers()) {
                for (LootType type : LOOT_TYPES) {
                    ItemStack stack = new ItemStack(this);

                    LootCrateData data = new LootCrateData();
                    data.type = type;
                    data.tier = tier;

                    StackSaving.GEM_CRATE.saveTo(stack, data);

                    stack.getTag()
                            .putInt("CustomModelData", type.custommodeldata);

                    stacks.add(stack);
                }
            }
        }
    }

     */

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip,
                                TooltipFlag flagIn) {
        LootCrateData data = getData(stack);

        if (data != null) {
            tooltip.add(TooltipUtils.gearTier(data.tier));
        }

    }

    @Override
    public Component getName(ItemStack stack) {

        LootCrateData data = getData(stack);

        MutableComponent comp = Component.literal("");

        if (data != null) {

            if (data.type == LootType.Gem) {
                String gemrank = "";
                GemItem.GemRank rank = GemItem.GemRank.ofTier(data.tier);
                gemrank = rank.locName; // todo make loc
                comp.append(gemrank)
                        .append(" ");
            }

            comp.append(data.type.word.locName())
                    .append(" ")
                    .append(Words.Loot.locName())
                    .append(" ")
                    .append(Words.Crate.locName())
                    .withStyle(TierColors.get(data.tier))
                    .withStyle(ChatFormatting.BOLD);

            return comp;

        }

        return Component.literal("Box");

    }

    @Override
    public String GUID() {
        return "loot_crate/default";
    }
}
