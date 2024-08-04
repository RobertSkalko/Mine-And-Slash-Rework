package com.robertx22.age_of_exile.database.data.profession.items;

import com.google.common.collect.ImmutableList;
import com.robertx22.age_of_exile.capability.player.data.Backpacks;
import com.robertx22.age_of_exile.capability.player.data.IGoesToBackpack;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.texts.ExileTooltips;
import com.robertx22.age_of_exile.gui.texts.textblocks.usableitemblocks.UsageBlock;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;


public class MaterialItem extends AutoItem implements IGoesToBackpack {

    public SkillItemTier tier;
    String name;
    String prof;

    public MaterialItem(String prof, SkillItemTier tier, String name) {
        super(new Properties());
        this.tier = tier;
        this.name = name;
        this.prof = prof;

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
                Itemtips.PROF_MAT_LEVEL_RANGE_INFO.locName().withStyle(ChatFormatting.RED),
                Itemtips.LEVEL_TIP.locName(tier.levelRange.getMinLevel() + "-" + tier.levelRange.getMaxLevel()).withStyle(ChatFormatting.RED))));

        tip.accept(new UsageBlock(ImmutableList.of(pro.locDesc().withStyle(ChatFormatting.YELLOW))));

        l.addAll(tip.release());

    }

    @Override
    public String locNameForLangFile() {
        return tier.word + " " + name;
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
