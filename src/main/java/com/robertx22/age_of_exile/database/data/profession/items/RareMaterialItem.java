package com.robertx22.age_of_exile.database.data.profession.items;

import com.robertx22.age_of_exile.capability.player.data.Backpacks;
import com.robertx22.age_of_exile.capability.player.data.IGoesToBackpack;
import com.robertx22.age_of_exile.database.data.profession.CraftedItemPower;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class RareMaterialItem extends AutoItem implements IGoesToBackpack {

    public CraftedItemPower tier;
    String name;
    public int weight;

    public RareMaterialItem(CraftedItemPower tier, String name) {
        super(new Properties());
        this.tier = tier;
        this.name = name;
        this.weight = tier.weight;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> l, TooltipFlag pIsAdvanced) {
        l.add(Chats.PROF_MAT_DROPGUIDE.locName().withStyle(ChatFormatting.AQUA));
        l.add(Chats.PROF_MAT_DROPGUIDE_RARE.locName().withStyle(ChatFormatting.AQUA));
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
