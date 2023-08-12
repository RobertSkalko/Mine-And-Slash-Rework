package com.robertx22.age_of_exile.saveclasses.item_classes;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.MergedStats;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.wrappers.ExileText;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
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

        if (gear.imp != null) {
            tip.addAll(gear.imp.GetTooltipString(info, gear));
        }

        List<IGearPartTooltip> list = new ArrayList<IGearPartTooltip>();

        List<ExactStatData> specialStats = new ArrayList<>();

        //tip.add(new LiteralText(""));

        if (info.useInDepthStats()) {
            if (gear.uniqueStats != null) {
                tip.addAll(gear.uniqueStats.GetTooltipString(info, gear));
            }
            tip.addAll(gear.affixes.GetTooltipString(info, gear));
            tip.addAll(gear.imp.GetTooltipString(info, gear));


        } else {
            List<ExactStatData> stats = new ArrayList<>();
            gear.affixes.getAllAffixesAndSockets()
                    .forEach(x -> stats.addAll(x.GetAllStats(gear)));

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

        int n = 0;
        for (IGearPartTooltip part : list) {
            if (part != null) {
                tip.addAll(part.GetTooltipString(info, gear));
                tip.add(Component.literal(""));
            }
            n++;
        }

        specialStats.forEach(x -> {
            x.GetTooltipString(info)
                    .forEach(e -> {
                        tip.add(e);

                    });
        });
        tip.add(Component.literal(""));


        if (Screen.hasShiftDown()) {
            if (!gear.sal) {
                tip.add(
                        Words.Unsalvagable.locName()
                                .withStyle(ChatFormatting.RED));
            }
        }

        tip.add(Component.literal(""));
        tip.addAll(gear.sockets.GetTooltipString(info, gear));
        tip.add(Component.literal(""));

        tip.add(Component.literal(""));

        MutableComponent lvl = TooltipUtils.gearLevel(gear.lvl);

        tip.add(lvl);
        TooltipUtils.addRequirements(tip, gear.getLevel(), gear.getRequirement(), Load.Unit(info.player));

        tip.add(Component.literal(""));

        tip.add(TooltipUtils.gearRarity(gear.getRarity()));

        tip.add(ExileText.ofText("Potential: " + (int) (gear.getPotential().multi * 100F) + "%").format(gear.getPotentialColor()).get());

        tip.add(Component.literal(""));

        if (gear.isCorrupted()) {
            tip.add(Component.literal(ChatFormatting.RED + "").append(
                            Words.Corrupted.locName())
                    .withStyle(ChatFormatting.RED));
        }

        int socketed = gear.sockets.getSocketedGems().size();
        if (socketed > 0) {
            TooltipUtils.addSocketNamesLine(tip, gear);
        }
        tip.add(Component.literal(""));


        //  ItemStack.appendEnchantmentNames(tip, stack.getEnchantmentTags());

        if (ClientConfigs.getConfig().SHOW_DURABILITY.get()) {
            if (stack.isDamageableItem()) {
                tip.add(ExileText.ofText(ChatFormatting.WHITE + "Durability: " + (stack.getMaxDamage() - stack.getDamageValue()) + "/" + stack.getMaxDamage()).get());
            } else {
                tip.add(ExileText.ofText(ChatFormatting.WHITE + "Unbreakable").get());
            }
        }


        if (Screen.hasShiftDown() == false) {
            tip.add(Component.literal(ChatFormatting.BLUE + "").append(Component.translatable(SlashRef.MODID + ".tooltip." + "press_shift_more_info")
                    )
                    .withStyle(ChatFormatting.BLUE));
        }

        List<Component> tool = TooltipUtils.removeDoubleBlankLines(tip,
                ClientConfigs.getConfig().REMOVE_EMPTY_TOOLTIP_LINES_IF_MORE_THAN_X_LINES);

        tip.clear();

        tooltip.addAll(tool);

    }

}
