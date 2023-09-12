package com.robertx22.age_of_exile.saveclasses.stat_soul;

import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.ICreativeTabNbt;
import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

public class StatSoulItem extends Item implements IGUID, ICreativeTabNbt {

    public static String TAG = "stat_soul";

    public StatSoulItem() {
        super(new Properties());
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player p, InteractionHand pUsedHand) {
        ItemStack itemstack = p.getItemInHand(pUsedHand);

        if (!pLevel.isClientSide) {

            if (StackSaving.STAT_SOULS.has(itemstack)) {

                StatSoulData data = StackSaving.STAT_SOULS.loadFrom(itemstack);

                Item item = data.getRarity().getLootableItem(ExileDB.GearSlots().get(data.slot));

                ItemStack stack = item.getDefaultInstance();

                StackSaving.GEARS.saveTo(stack, data.createGearData(stack, p));

                PlayerUtils.giveItem(stack, p);
                itemstack.shrink(1);
            }

        }

        return InteractionResultHolder.pass(p.getItemInHand(pUsedHand));

    }


    @Override
    public List<ItemStack> createAllVariationsForCreativeTabs() {
        var list = new ArrayList<ItemStack>();
        for (GearRarity rarity : ExileDB.GearRarities()
                .getList()) {
            for (GearSlot slot : ExileDB.GearSlots()
                    .getList()) {
                for (int i = 0; i <= LevelUtils.getMaxTier(); i++) {
                    StatSoulData data = new StatSoulData();
                    data.tier = i;
                    data.rar = rarity.GUID();
                    data.slot = slot.GUID();

                    ItemStack stack = data.toStack();
                    list.add(stack);
                }

            }
        }
        return list;
    }


    @Override
    public Component getName(ItemStack stack) {

        MutableComponent txt = Component.translatable(this.getDescriptionId());

        try {
            StatSoulData data = getSoul(stack);

            if (data == null) {
                return txt;
            } else {

                GearRarity rar = ExileDB.GearRarities()
                        .get(data.rar);
                GearSlot slot = ExileDB.GearSlots()
                        .get(data.slot);

                MutableComponent t = rar.locName();
                if (!data.canBeOnAnySlot()) {
                    t.append(" ")
                            .append(slot.locName());
                }

                t.append(" ")
                        .append(Words.Soul.locName())
                        .withStyle(rar.textFormatting());

                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return txt;
    }

    public static boolean hasSoul(ItemStack stack) {
        return stack.hasTag() && stack.getTag()
                .contains(TAG);
    }

    public static StatSoulData getSoul(ItemStack stack) {
        StatSoulData data = LoadSave.Load(StatSoulData.class, new StatSoulData(), stack.getOrCreateTag(), TAG);
        return data;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {

        StatSoulData data = StackSaving.STAT_SOULS.loadFrom(stack);

        for (Component c : data.getTooltip(stack)) {
            tooltip.add(c);
        }


    }

    @Override
    public String GUID() {
        return "stat_soul/stat_soul";
    }


}
