package com.robertx22.age_of_exile.database.data.profession;

import com.robertx22.age_of_exile.database.data.profession.buffs.StatBuff;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class CraftedBuffPotionItem extends AutoItem {

    public String buff_id;
    CraftedItemPower power;

    public CraftedBuffPotionItem(String buff_id, CraftedItemPower power) {
        super(new Properties());
        this.buff_id = buff_id;
        this.power = power;
    }

    public StatBuff getBuff() {
        return ExileDB.StatBuffs().get(buff_id);
    }

    // Greater intelligence potion = power + name
    @Override
    public Component getName(ItemStack stack) {
        return power.word.locName().append(" ").append(getBuff().mods.get(0).GetStat().locName()).append(" ").append(Words.POTION.locName()).withStyle(LeveledItem.getTier(stack).format);
    }


    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".potion_name_unused";
    }

    @Override
    public String locNameForLangFile() {
        return "";
    }

    @Override
    public String GUID() {
        return null;
    }
}
