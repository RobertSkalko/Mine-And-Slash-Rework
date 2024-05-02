package com.robertx22.age_of_exile.saveclasses.item_classes;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.MergedStats;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipStatsAligner;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.wrappers.ExileText;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GearTooltipUtils {

    public static void BuildTooltip(GearItemData gear, ItemStack stack, List<Component> tooltip, EntityData data) {


        List<Component> tip = new ArrayList<>();

        if (gear.GetBaseGearType() == null) {
            return;
        }

        TooltipInfo info = new TooltipInfo(data, new MinMax(0, 100));

        tooltip.clear();

        List<MutableComponent> name = gear.GetDisplayName(stack);


        name.forEach(x -> {
            tip.add(x.withStyle(ChatFormatting.BOLD));
        });

        if (gear.baseStats != null) {
            tip.addAll(gear.baseStats.GetTooltipString(info, gear));
        }

        tip.add(ExileText.ofText("").get());


        List<IGearPartTooltip> list = new ArrayList<IGearPartTooltip>();

        List<ExactStatData> specialStats = new ArrayList<>();

        //tip.add(new LiteralText(""));

        if (ClientConfigs.getConfig().IN_DEPTH_TOOLTIPS_BY_DEFAULT.get() || info.useInDepthStats()) {

            tip.addAll(gear.imp.GetTooltipString(info, gear));

            if (gear.uniqueStats != null) {
                tip.add(Itemtips.UNIQUE_STATS.locName().withStyle(ChatFormatting.YELLOW));
                tip.addAll(gear.uniqueStats.GetTooltipString(info, gear));
            }

            tip.addAll(gear.affixes.GetTooltipString(info, gear));
            //tip.addAll(gear.imp.GetTooltipString(info, gear));


        } else {
            List<ExactStatData> stats = new ArrayList<>();
            gear.affixes.getAllAffixesAndSockets().forEach(x -> stats.addAll(x.GetAllStats(gear)));

            stats.addAll(gear.imp.GetAllStats(gear));

            if (gear.uniqueStats != null) {
                stats.addAll(gear.uniqueStats.GetAllStats(gear));
            }
            List<ExactStatData> longstats = stats.stream()
                    .filter(x -> x.getStat().is_long)
                    .collect(Collectors.toList());
            specialStats.addAll(longstats);

            MergedStats merged = new MergedStats(stats, info);

            list.add(merged);

        }


        for (IGearPartTooltip part : list) {
            if (part != null) {
                tip.addAll(part.GetTooltipString(info, gear));

            }
        }


        specialStats.forEach(x -> {
            x.GetTooltipString(info)
                    .forEach(e -> {
                        tip.add(e);

                    });
        });
        tip.add(Component.literal(""));

        List<Component> finalList = new TooltipStatsAligner(tip).buildNewTooltipsStats();
        tip.clear();
        tip.addAll(finalList);


        if (Screen.hasShiftDown()) {
            if (gear.data.get(GearItemData.KEYS.SALVAGING_DISABLED)) {
                tip.add(Words.Unsalvagable.locName().withStyle(ChatFormatting.RED));
            }
        }

        tip.add(Component.literal(""));
        tip.addAll(gear.sockets.GetTooltipString(info, gear));
        tip.add(Component.literal(""));

        if (gear.ench != null) {
            tip.addAll(gear.ench.GetTooltipString(info, gear));
        }

        tip.add(Component.literal(""));

        MutableComponent lvl = TooltipUtils.levelAndRarity(gear.lvl, Load.Unit(info.player).getLevel(), gear.getRarity());
        tip.add(lvl);

        if (gear.isUnique()) {
            if (!gear.uniqueStats.getUnique(gear).league.isEmpty()) {
                var league = ExileDB.LeagueMechanics().get(gear.uniqueStats.getUnique(gear).league);
                tip.add(league.locName().withStyle(league.getTextColor()));
            }
        }


        if (gear.isCorrupted()) {
            if (info.useInDepthStats()) {
                tip.add(Component.literal(ChatFormatting.RED + "").append(Words.Corrupted.locName()).withStyle(ChatFormatting.RED));
            }
        } else {
            int potential = (int) gear.getPotentialNumber();
            tip.add(Itemtips.POTENTIAL.locName(potential).withStyle(ChatFormatting.GRAY));

        }

        TooltipUtils.addRequirements(tip, gear.getLevel(), gear.getRequirement(), Load.Unit(info.player));
        tip.add(Component.literal(""));

        if (gear.getQuality() > 0) {
            tip.add(Itemtips.QUALITY.locName(gear.getQuality()).withStyle(ChatFormatting.GOLD));
        }

        if (Screen.hasShiftDown()) {
            if (gear.GetBaseGearType().getGearSlot().weapon_data.damage_multiplier > 0) {
                int cost = (int) Energy.getInstance().scale(ModType.FLAT, gear.GetBaseGearType().getGearSlot().weapon_data.energy_cost_per_swing, data.getLevel());
                int permob = (int) Energy.getInstance().scale(ModType.FLAT, gear.GetBaseGearType().getGearSlot().weapon_data.energy_cost_per_mob_attacked, data.getLevel());
                float damageFactor = (gear.GetBaseGearType().getGearSlot().getBasicDamageMulti() * 100) / 100F;

                tip.add(Words.Energy_Cost_Per_Mob.locName(cost, permob, damageFactor).withStyle(ChatFormatting.GREEN));
            }
        }

        tip.add(Component.literal(""));


        //  ItemStack.appendEnchantmentNames(tip, stack.getEnchantmentTags());

        if (gear.isUnique() && !gear.uniqueStats.getUnique(gear).locDesc().getString().isEmpty()) {
            var desc = gear.uniqueStats.getUnique(gear).locDesc();
            if (I18n.exists(gear.uniqueStats.getUnique(gear).locDescLangFileGUID())) {
                tip.add(desc);
                tip.add(Component.literal(""));
            }
        }


        if (ClientConfigs.getConfig().SHOW_DURABILITY.get()) {
            if (stack.isDamageableItem()) {
                tip.add(Itemtips.Durability.locName().withStyle(ChatFormatting.WHITE)
                        .append(stack.getMaxDamage() - stack.getDamageValue() + "/" + stack.getMaxDamage()));
            } else {
                tip.add(Itemtips.Unbreakable.locName().withStyle(ChatFormatting.WHITE));
            }
        }


        if (Screen.hasShiftDown() == false) {
            tip.add(Component.literal(ChatFormatting.BLUE + "").append(Component.translatable(SlashRef.MODID + ".tooltip." + "press_shift_more_info")
                    )
                    .withStyle(ChatFormatting.BLUE));
        }


        List<Component> tool = TooltipUtils.removeDoubleBlankLines(tip);

        tip.clear();

        tooltip.addAll(tool);

    }

}