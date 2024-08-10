package com.robertx22.mine_and_slash.content.ubers;

import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ICreativeIgnoreOption;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.AutoItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

import static com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils.splitLongText;

public class UberFragmentItem extends AutoItem implements ICreativeIgnoreOption {

    public int uberTier;
    public UberEnum uber;

    public UberFragmentItem(int uberTier, UberEnum uber) {
        super(new Properties().stacksTo(64));
        this.uberTier = uberTier;
        this.uber = uber;
    }

    @Override
    public String locNameForLangFile() {
        return uber.fragmentName;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.addAll(splitLongText(Itemtips.UBER_BOSS_FRAG_TIP.locName().withStyle(ChatFormatting.RED)));

        pTooltipComponents.add(Words.Level.locName().append(" " + UberBossTier.map.get(uberTier).boss_lvl).withStyle(ChatFormatting.YELLOW));
    }

    @Override
    public String GUID() {
        return null;
    }


    @Override
    public boolean shouldShowInCreative() {
        return ExileDB.UberBoss().isRegistered(uber.id);
    }
}
