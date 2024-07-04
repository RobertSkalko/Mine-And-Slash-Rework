package com.robertx22.age_of_exile.gui.texts;

import com.google.common.collect.Sets;
import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailment;
import com.robertx22.age_of_exile.aoe_data.database.stats.DefenseStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.OffenseStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.test.DataPackStatAccessor;
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
import com.robertx22.library_of_exile.registry.IGUID;
import lombok.AllArgsConstructor;

import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//use to sort the stat when showing MergeStats.
@AllArgsConstructor
public enum StatCategory {
    SDI(x -> AllAttributes.getInstance().equals(x) || new HashSet<>(AllAttributes.getInstance().coreStatsThatBenefit()).contains(x)),
    CORE(x -> Sets.newHashSet(WeaponDamage.getInstance(), Health.getInstance(), MagicShield.getInstance(), Mana.getInstance(), Energy.getInstance(), Armor.getInstance(), DodgeRating.getInstance(), OffenseStats.CRIT_CHANCE.get(), OffenseStats.CRIT_DAMAGE.get()).contains(x)),
    REGEN(x -> Sets.newHashSet(HealthRegen.getInstance(), MagicShieldRegen.getInstance(), ManaRegen.getInstance(), EnergyRegen.getInstance(), RegeneratePercentStat.HEALTH, RegeneratePercentStat.MAGIC_SHIELD, RegeneratePercentStat.MANA, RegeneratePercentStat.ENERGY).contains(x)),
    DEFENSE(x -> {
        boolean b = x instanceof ElementalResist;
        DefenseStats defenseStats = new DefenseStats();
        Set<Stat> dataPackStatAccessors = Arrays.stream(DefenseStats.class.getFields())
                .filter(field -> {
                    if (field.getGenericType() instanceof ParameterizedType parameterizedType) {
                        return parameterizedType.getRawType().equals(DataPackStatAccessor.class);
                    }
                    return false;
                })
                .map(field -> {
                    try {
                        return (DataPackStatAccessor<?>) field.get(defenseStats);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .flatMap(dataPackStatAccessor -> dataPackStatAccessor.getAll().stream())
                .collect(Collectors.toSet());

        return b || dataPackStatAccessors.contains(x);

    }),

    AILMENTS(x -> ExileDB.Ailments().getAll().values().stream().map(Ailment::GUID).collect(Collectors.toCollection(HashSet::new)).contains(x.GUID())),
    ELE_DAMAGE(x -> x.GUID().contains("_damage") || x.GUID().contains("_penetration") || x.GUID().contains("dmg"));


    public final Predicate<Stat> predicate;

    public static void distributeStat(TooltipStatInfo tooltipStatInfo, Map<String, ArrayList<TooltipStatInfo>> container) {
        boolean isRegular = false;
        for (StatCategory value : values()) {
            if (value.predicate.test(tooltipStatInfo.stat)) {
                container.get(value.name()).add(tooltipStatInfo);
                isRegular = true;
                break;
            }
        }
        if (!isRegular) {
            container.get("other").add(tooltipStatInfo);
        }
    }
}


