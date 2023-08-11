package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PerkBuilder {
    private final Perk perk = new Perk();

    public PerkBuilder addStat(OptScaleExactStat stat) {
        perk.stats.add(stat);
        return this;
    }

    public Perk build() {
        this.perk.addToSerializables();
        return perk;
    }

    public static Perk ascPoint(String id, OptScaleExactStat... stat) {
        List<OptScaleExactStat> list = Arrays.asList(stat);

        Perk perk = new Perk();
        perk.stats = list;
        perk.type = Perk.PerkType.STAT;
        perk.id = id;
        perk.icon = new ResourceLocation(SlashRef.MODID, "textures/gui/asc_classes/perk/" + id + ".png").toString();

        perk.addToSerializables();
        return perk;
    }

    public static Perk stat(String id, OptScaleExactStat... stat) {
        List<OptScaleExactStat> list = Arrays.asList(stat);

        Perk perk = new Perk();
        perk.stats = list;
        perk.type = Perk.PerkType.STAT;
        perk.id = id;
        perk.icon = list.get(0)
                .getStat()
                .getIconLocation()
                .toString();
        perk.addToSerializables();
        return perk;
    }

    public static Perk gameChanger(String id, String locname, OptScaleExactStat... stats) {
        Perk perk = new Perk();

        perk.stats = new ArrayList<>();

        for (OptScaleExactStat stat : stats) {
            perk.stats.add(stat);
        }
        perk.locname = locname;
        perk.type = Perk.PerkType.MAJOR;
        perk.id = id;
        perk.icon = new ResourceLocation(SlashRef.MODID, "textures/gui/stat_icons/game_changers/" + id + ".png")
                .toString();
        perk.addToSerializables();
        return perk;
    }


    public static Perk bigStat(String id, String locname, OptScaleExactStat... stat) {
        List<OptScaleExactStat> list = Arrays.asList(stat);


        Perk perk = stat(id, stat);
        perk.type = Perk.PerkType.SPECIAL;
        perk.locname = locname;

        if (true) {
            perk.icon = list.get(0)
                    .getStat()
                    .getIconLocation()
                    .toString();
        } else {
            perk.icon = new ResourceLocation(SlashRef.MODID, "textures/gui/talent_icons/" + perk.id + ".png")
                    .toString();
        }
        return perk;
    }


    public static Perk bigStat(OptScaleExactStat... stat) {
        List<OptScaleExactStat> list = Arrays.asList(stat);
        var opt = list.get(0);
        return bigStat(opt.stat + opt.getPerkIdSuffix() + "_big", "Major " + opt.getStat().locNameForLangFile(), stat);
    }

    public static Perk bigStat(String id, OptScaleExactStat... stat) {
        List<OptScaleExactStat> list = Arrays.asList(stat);
        return bigStat(id, "Major " + list.get(0).getStat().locNameForLangFile(), stat);

    }

    public static Perk stat(OptScaleExactStat stat) {
        return stat(stat.stat + stat.getPerkIdSuffix(), stat);
    }


}
