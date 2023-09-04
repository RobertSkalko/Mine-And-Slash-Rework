package com.robertx22.age_of_exile.database.data.profession.items;

import com.robertx22.age_of_exile.capability.player.data.PlayerBuffData;
import com.robertx22.age_of_exile.database.data.profession.CraftedItemPower;
import com.robertx22.age_of_exile.database.data.profession.ICreativeTabTiered;
import com.robertx22.age_of_exile.database.data.profession.LeveledItem;
import com.robertx22.age_of_exile.database.data.profession.buffs.StatBuff;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;


public class CraftedBuffPotionItem extends AutoItem implements ICreativeTabTiered {

    public String buff_id;
    CraftedItemPower power;

    public CraftedBuffPotionItem(String buff_id, CraftedItemPower power) {
        super(new Properties());
        this.buff_id = buff_id;
        this.power = power;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level pLevel, LivingEntity pLivingEntity) {
        if (!pLevel.isClientSide) {
            if (pLivingEntity instanceof Player p) {
                boolean did = Load.player(p).buff.tryAdd(p, getBuff(), LeveledItem.getLevel(stack), power.perc, PlayerBuffData.Type.POTION, getTicksDuration());
                if (did) {
                    stack.shrink(1);
                    return stack;
                } else {
                    p.sendSystemMessage(Component.literal("You already have a buff with the same stat."));
                }
            }
        }

        return stack;

    }

    public int getTicksDuration() {
        return 20 * 60 * 15;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 32;

    }

    public StatBuff getBuff() {
        return ExileDB.StatBuffs().get(buff_id);
    }

    // Greater intelligence potion = power + name
    @Override
    public Component getName(ItemStack stack) {
        return power.word.locName().append(" ")
                .append(getBuff().mods.get(0).GetStat().locName()).append(" ")
                .append(Words.POTION.locName())
                .withStyle(LeveledItem.getTier(stack).format);
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {

        try {
            StatBuff buff = getBuff();

            list.add(TooltipUtils.level(LeveledItem.getLevel(stack)));

            int lvl = LeveledItem.getLevel(stack);
            list.add(Component.empty());

            for (ExactStatData stat : buff.getStats(lvl, power.perc)) {
                list.addAll(stat.GetTooltipString(new TooltipInfo()));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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


    @Override
    public Item getThis() {
        return this;
    }
}
