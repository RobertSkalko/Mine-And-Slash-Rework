package com.robertx22.age_of_exile.vanilla_mc.items.gemrunes;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.BaseRuneGem;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.item.Item.Properties;

public abstract class BaseGemRuneItem extends Item {
    public BaseGemRuneItem(Properties settings) {
        super(settings);
    }

    public int weight;

    public abstract BaseRuneGem getBaseRuneGem();

    public abstract float getStatValueMulti();

    public abstract List<StatModifier> getStatModsForSerialization(SlotFamily family);

    public List<OptScaleExactStat> getStatsForSerialization(SlotFamily family) {

        List<OptScaleExactStat> list = new ArrayList<>();

        float multi = getStatValueMulti();

        this.getStatModsForSerialization(family)
            .forEach(x -> {
                OptScaleExactStat stat = new OptScaleExactStat(x.max * multi, x.GetStat(), x.getModType());
                stat.scale_to_lvl = true;
                list.add(stat);

            });

        return list;
    }

    public List<Component> getBaseTooltip() {
        List<Component> tooltip = new ArrayList<>();

        if (ExileDB.Runes()
            .isEmpty() || ExileDB.Gems()
            .isEmpty() || getBaseRuneGem() == null) {
            return tooltip; // datapacks didnt register yet
        }

        BaseRuneGem gem = getBaseRuneGem();

        int efflvl = Load.Unit(ClientOnly.getPlayer())
            .getLevel();

        TooltipInfo info = new TooltipInfo();

        tooltip.add(new TextComponent(""));
        List<OptScaleExactStat> wep = gem.getFor(SlotFamily.Weapon);
        tooltip.add(new TextComponent("On Weapon:").withStyle(ChatFormatting.RED));
        for (OptScaleExactStat x : wep) {
            tooltip.addAll(x.GetTooltipString(info));
        }

        tooltip.add(new TextComponent(""));
        List<OptScaleExactStat> armor = gem.getFor(SlotFamily.Armor);
        tooltip.add(new TextComponent("On Armor:").withStyle(ChatFormatting.BLUE));
        for (OptScaleExactStat x : armor) {
            tooltip.addAll(x.GetTooltipString(info));
        }

        tooltip.add(new TextComponent(""));
        List<OptScaleExactStat> jewelry = gem.getFor(SlotFamily.Jewelry);
        tooltip.add(new TextComponent("On Jewelry:").withStyle(ChatFormatting.LIGHT_PURPLE));
        for (OptScaleExactStat x : jewelry) {
            tooltip.addAll(x.GetTooltipString(info));
        }

        tooltip.add(new TextComponent(""));
        tooltip.add(TooltipUtils.tier(gem.tier));

        return tooltip;
    }

}
