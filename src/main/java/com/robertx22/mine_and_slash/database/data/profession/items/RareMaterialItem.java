package com.robertx22.mine_and_slash.database.data.profession.items;

import com.google.common.collect.ImmutableList;
import com.robertx22.mine_and_slash.capability.player.data.Backpacks;
import com.robertx22.mine_and_slash.capability.player.data.IGoesToBackpack;
import com.robertx22.mine_and_slash.database.data.profession.CraftedItemPower;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.gui.texts.textblocks.usableitemblocks.UsageBlock;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.AutoItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class RareMaterialItem extends AutoItem implements IGoesToBackpack {

    // todo probably remove this item, just confuses players

    public CraftedItemPower tier;
    String name;
    public int weight;
    String prof;

    public RareMaterialItem(String prof, CraftedItemPower tier, String name) {
        super(new Properties());
        this.tier = tier;
        this.name = name;
        this.prof = prof;
        this.weight = tier.weight;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> l, TooltipFlag pIsAdvanced) {
        var pro = ExileDB.Professions().get(prof);

        var tip = new ExileTooltips();

        tip.accept(new UsageBlock(ImmutableList.of(
                Chats.PROF_MAT_DROPGUIDE.locName().withStyle(ChatFormatting.AQUA),
                Chats.PROF_MAT_DROPGUIDE_COMMON.locName().withStyle(ChatFormatting.AQUA),
                Chats.PROF_MAT_SOURCE.locName(pro.locName().withStyle(ChatFormatting.YELLOW)).withStyle(ChatFormatting.GREEN)
        )));
        tip.accept(new UsageBlock(ImmutableList.of(
                Component.literal("Rare Drop at any level.")
        )));

        tip.accept(new UsageBlock(ImmutableList.of(pro.locDesc().withStyle(ChatFormatting.YELLOW))));

        l.addAll(tip.release());

    }

    @Override
    public String locNameForLangFile() {
        return tier.word.locNameForLangFile() + " " + name;
    }

    @Override
    public String GUID() {
        return null;
    }

    @Override
    public Backpacks.BackpackType getBackpackPickup() {
        return Backpacks.BackpackType.PROFESSION;
    }
}
