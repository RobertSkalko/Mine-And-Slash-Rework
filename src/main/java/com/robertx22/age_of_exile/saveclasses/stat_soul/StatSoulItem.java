package com.robertx22.age_of_exile.saveclasses.stat_soul;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.NonNullList;
import net.minecraft.util.text.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item.Properties;

public class StatSoulItem extends Item implements IGUID {

    public static String TAG = "stat_soul";

    public StatSoulItem() {
        super(new Properties().tab(CreativeTabs.GearSouls));
    }

    public static ItemStack ofAnySlotOfRarity(String rar) {
        ItemStack stack = new ItemStack(SlashItems.STAT_SOUL.get());
        StatSoulData data = StatSoulData.anySlotOfRarity(rar);
        StackSaving.STAT_SOULS.saveTo(stack, data);
        return stack;
    }

    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> stacks) {
        if (this.allowdedIn(group)) {

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
                        stacks.add(stack);
                    }

                }
            }

        }
    }

    @Override
    public Component getName(ItemStack stack) {

        MutableComponent txt = new TranslatableComponent(this.getDescriptionId());

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

        if (data != null) {
            if (data.gear != null) {
                data.gear.BuildTooltip(new TooltipContext(stack, tooltip, Load.Unit(ClientOnly.getPlayer())));
            } else {
                tooltip.add(TooltipUtils.gearTier(data.tier));
                if (data.canBeOnAnySlot()) {

                } else {
                    tooltip.add(new TextComponent("Item Type: ").withStyle(ChatFormatting.WHITE)
                        .append(ExileDB.GearSlots()
                            .get(data.slot)
                            .locName()
                            .withStyle(ChatFormatting.BLUE)));
                }
                tooltip.add(TooltipUtils.gearRarity(ExileDB.GearRarities()
                    .get(data.rar)));

            }
        }

        tooltip.add(new TextComponent(""));

        tooltip.add(new TextComponent("Infuses stats into empty gear").withStyle(ChatFormatting.AQUA));
        tooltip.add(TooltipUtils.dragOntoGearToUse());

    }

    @Override
    public String GUID() {
        return "stat_soul/stat_soul";
    }
}
