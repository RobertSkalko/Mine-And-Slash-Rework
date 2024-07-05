package com.robertx22.age_of_exile.saveclasses.item_classes;

import com.google.common.collect.ImmutableList;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.gui.texts.ExileTooltips;
import com.robertx22.age_of_exile.gui.texts.textblocks.*;
import com.robertx22.age_of_exile.gui.texts.textblocks.gearblocks.DurabilityBlock;
import com.robertx22.age_of_exile.gui.texts.textblocks.gearblocks.GearStatBlock;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class GearTooltipUtils {

    public static void BuildTooltip(GearItemData gear, ItemStack stack, List<Component> tooltip, EntityData data) {

        if (gear.GetBaseGearType() == null) {
            return;
        }

        TooltipInfo info = new TooltipInfo(data, new MinMax(0, 100));
        tooltip.clear();

        List<Component> release = new ExileTooltips()
                .accept(new NameBlock(gear.GetDisplayName(stack)))
                .accept(new RarityBlock(gear.getRarity()))
                .accept(new RequirementBlock()
                        .setStatRequirement(gear.getRequirement())
                        .setLevelRequirement(gear.getLevel()))
                .accept(new GearStatBlock(gear, info))
                .accept(new AdditionalBlock(
                        ImmutableList.of(
                                gear.isCorrupted() ? Component.literal("").append(Itemtips.POTENTIAL.locName(gear.getPotentialNumber()).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.STRIKETHROUGH)).append(Component.literal(" ")).append(Words.Corrupted.locName().withStyle(ChatFormatting.RED)) : Itemtips.POTENTIAL.locName(gear.getPotentialNumber()).withStyle(ChatFormatting.GOLD),
                                Itemtips.QUALITY.locName(gear.getQuality()).withStyle(ChatFormatting.GOLD)
                        )
                ))
                .accept(new AdditionalBlock(() -> {
                            int cost = (int) Energy.getInstance().scale(ModType.FLAT, gear.GetBaseGearType().getGearSlot().weapon_data.energy_cost_per_swing, data.getLevel());
                            int permob = (int) Energy.getInstance().scale(ModType.FLAT, gear.GetBaseGearType().getGearSlot().weapon_data.energy_cost_per_mob_attacked, data.getLevel());
                            float damageFactor = (gear.GetBaseGearType().getGearSlot().getBasicDamageMulti() * 100) / 100F;

                            return Collections.singletonList(Words.Energy_Cost_Per_Mob.locName(cost, permob, damageFactor).withStyle(ChatFormatting.GREEN));
                        }).showWhen(() -> info.hasShiftDown && gear.GetBaseGearType().getGearSlot().weapon_data.damage_multiplier > 0)
                )
                .accept(new OperationTipBlock().setAll())
                .accept(new DurabilityBlock(stack))
                .release();

        tooltip.addAll(release);


    }

}