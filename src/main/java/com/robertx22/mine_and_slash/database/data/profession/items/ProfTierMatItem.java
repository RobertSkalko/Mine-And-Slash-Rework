package com.robertx22.mine_and_slash.database.data.profession.items;

import com.google.common.collect.ImmutableList;
import com.robertx22.mine_and_slash.capability.player.data.Backpacks;
import com.robertx22.mine_and_slash.capability.player.data.IGoesToBackpack;
import com.robertx22.mine_and_slash.database.data.profession.Profession;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.gui.texts.textblocks.dropblocks.ProfessionDropSourceBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.usableitemblocks.UsageBlock;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.AutoItem;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;


public class ProfTierMatItem extends AutoItem implements IGoesToBackpack {

    public SkillItemTier tier;
    String name;
    String prof;

    public ProfTierMatItem(String prof, SkillItemTier tier, String name) {
        super(new Properties());
        this.tier = tier;
        this.name = name;
        this.prof = prof;

    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> l, TooltipFlag pIsAdvanced) {
        var pro = ExileDB.Professions().get(prof);
        var tip = makeTooltip(pro, tier);
        tip.accept(new UsageBlock(Arrays.asList(Chats.PROF_MAT_DROPGUIDE_COMMON.locName().withStyle(ChatFormatting.AQUA))));
        l.addAll(tip.release());
    }

    public static ExileTooltips makeTooltip(Profession pro, @Nullable SkillItemTier tier) {

        var tip = new ExileTooltips();

        tip.accept(new UsageBlock(ImmutableList.of(
                Chats.PROF_MAT_DROPGUIDE.locName().withStyle(ChatFormatting.AQUA)
        )));
        if (tier != null) {
            tip.accept(new UsageBlock(ImmutableList.of(
                    Itemtips.PROF_MAT_LEVEL_RANGE_INFO.locName().withStyle(ChatFormatting.RED),
                    Itemtips.LEVEL_TIP.locName(tier.levelRange.getMinLevel() + "-" + tier.levelRange.getMaxLevel()).withStyle(ChatFormatting.RED))));
        }
        tip.accept(new ProfessionDropSourceBlock(pro.GUID()));
        tip.accept(new UsageBlock(ImmutableList.of(pro.locDesc().withStyle(ChatFormatting.YELLOW))));


        return tip;
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
