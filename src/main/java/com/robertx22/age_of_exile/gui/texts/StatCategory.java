package com.robertx22.age_of_exile.gui.texts;

import com.google.common.collect.Sets;
import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailment;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
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
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//use to sort the stat when showing MergeStats.
@AllArgsConstructor
public enum StatCategory {
    SDI(x -> Sets.newHashSet("all_attributes", "strength", "dexterity", "intelligence").contains(x)),
    CORE(x -> Sets.newHashSet(WeaponDamage.GUID, Health.GUID, MagicShield.GUID, Mana.GUID, Energy.GUID, Armor.GUID, DodgeRating.GUID, "critical_hit", "critical_damage").contains(x)),
    REGEN(x -> Sets.newHashSet(HealthRegen.getInstance().GUID(), MagicShieldRegen.getInstance().GUID(), ManaRegen.getInstance().GUID(), EnergyRegen.getInstance().GUID(), RegeneratePercentStat.HEALTH.GUID(), RegeneratePercentStat.MAGIC_SHIELD.GUID(), RegeneratePercentStat.MANA.GUID(), RegeneratePercentStat.ENERGY.GUID()).contains(x)),
    RESISTS(x -> x.contains("_resist")),

    AILMENTS(x -> ExileDB.Ailments().getAll().values().stream().map(Ailment::GUID).collect(Collectors.toCollection(HashSet::new)).contains(x)),
    ELE_DAMAGE(x -> x.contains("_damage") || x.contains("_penetration"));


    public final Predicate<String> predicate;

    public static void distributeStat(TooltipStatInfo tooltipStatInfo, Map<String, ArrayList<TooltipStatInfo>> container) {
        boolean isRegular = false;
        for (StatCategory value : values()) {
            if (value.predicate.test(tooltipStatInfo.stat.GUID())) {
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


