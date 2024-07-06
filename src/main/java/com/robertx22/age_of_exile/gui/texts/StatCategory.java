package com.robertx22.age_of_exile.gui.texts;

import com.google.common.collect.Sets;
import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailment;
import com.robertx22.age_of_exile.aoe_data.database.stats.OffenseStats;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.defense.MaxElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//use to sort the stat when showing MergeStats.

public enum StatCategory {
    SDI(x -> AllAttributes.getInstance().equals(x) || new HashSet<>(AllAttributes.getInstance().coreStatsThatBenefit()).contains(x)),
    CORE(x -> Sets.newHashSet(WeaponDamage.getInstance(), Health.getInstance(), MagicShield.getInstance(), Mana.getInstance(), Energy.getInstance(), Armor.getInstance(), DodgeRating.getInstance(), OffenseStats.CRIT_CHANCE.get(), OffenseStats.CRIT_DAMAGE.get()).contains(x)),
    REGEN(x -> Sets.newHashSet(HealthRegen.getInstance(), MagicShieldRegen.getInstance(), ManaRegen.getInstance(), EnergyRegen.getInstance(), RegeneratePercentStat.HEALTH, RegeneratePercentStat.MAGIC_SHIELD, RegeneratePercentStat.MANA, RegeneratePercentStat.ENERGY).contains(x)),
    DEFENSE(x -> {
        boolean b = x instanceof ElementalResist;
        boolean b1 = x instanceof MaxElementalResist;
        HashSet<String> strings = new HashSet<>(List.of("_defense"));
        return b || b1 || strings.stream().anyMatch(clip -> x.GUID().contains(clip));
    }),

    AILMENTS(x -> {
        HashSet<String> ailmentNames = ExileDB.Ailments().getAll().values().stream().map(Ailment::GUID).collect(Collectors.toCollection(HashSet::new));
        return ailmentNames.stream().anyMatch(ailmentName -> x.GUID().contains(ailmentName));
    }),
    DAMAGE(x -> {
        HashSet<String> strings = new HashSet<>(List.of("_damage", "_penetration", "dmg"));
        return strings.stream().anyMatch(clip -> x.GUID().contains(clip));
    });


    public final Predicate<Stat> predicate;

    StatCategory(Predicate<Stat> predicate) {
        this.predicate = predicate;
    }

    public static void distributeStat(TooltipStatInfo tooltipStatInfo, Map<String, ArrayList<TooltipStatInfo>> container) {
        boolean isRegular = false;
        for (StatCategory value : values()) {
            if (value.predicate.test(tooltipStatInfo.stat)) {
                //System.out.println("distribute " + tooltipStatInfo.stat.GUID() + " to " + value.name());
                container.get(value.name()).add(tooltipStatInfo);
                isRegular = true;
                break;
            }
        }
        if (!isRegular) {
            //System.out.println("distribute " + tooltipStatInfo.stat.GUID() + " to other");
            container.get("other").add(tooltipStatInfo);
        }
    }
}


