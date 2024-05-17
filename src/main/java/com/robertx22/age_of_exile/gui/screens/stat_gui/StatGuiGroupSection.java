package com.robertx22.age_of_exile.gui.screens.stat_gui;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.aoe_data.database.stats.OffenseStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.defense.MaxElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentChance;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentDamage;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentDuration;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentProcStat;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalPenetration;
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
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public enum StatGuiGroupSection implements IAutoLocName {

    // todo add leech section

    CORE("core", "Core Stats", () -> Arrays.asList(WeaponDamage.getInstance(), Health.getInstance(), MagicShield.getInstance(), Mana.getInstance(), Energy.getInstance(), Armor.getInstance(), DodgeRating.getInstance(), DatapackStats.STR, DatapackStats.DEX, DatapackStats.INT, OffenseStats.CRIT_CHANCE.get(), OffenseStats.CRIT_DAMAGE.get())),
    REGEN("regen", "Regen Stats", () -> Arrays.asList(HealthRegen.getInstance(), MagicShieldRegen.getInstance(), ManaRegen.getInstance(), EnergyRegen.getInstance(), RegeneratePercentStat.HEALTH, RegeneratePercentStat.MAGIC_SHIELD, RegeneratePercentStat.MANA, RegeneratePercentStat.ENERGY)),
    RESISTS("elemental_resists", "Elemental Resists", () -> Arrays.asList(new ElementalResist(Elements.Cold), new MaxElementalResist(Elements.Physical))),
    ELE_DAMAGE("elemental_damage", "Elemental Damage", () -> Arrays.asList(OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Fire), OffenseStats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Fire), new ElementalPenetration(Elements.Fire))),
    AILMENTS("ailments", "Ailments", () -> Arrays.asList(new AilmentChance(Ailments.BLEED), new AilmentDamage(Ailments.BLEED), new AilmentProcStat(Ailments.BLEED), new AilmentDuration(Ailments.BLEED))),
    OTHER("other", "All other Stats", () -> {
        return Arrays.asList();

    }),

    ;

    public String id;
    public String name;
    private Supplier<List<Stat>> sup;

    StatGuiGroupSection(String id, String name, Supplier<List<Stat>> sup) {
        this.id = id;
        this.name = name;
        this.sup = sup;
    }

    public List<Stat> getStats() {
        if (this == OTHER) {
            List<Stat> list = new ArrayList<>();
            for (StatData stat : Load.Unit(ClientOnly.getPlayer()).getUnit().getStats().stats.values()) {
                if (stat.GetStat().show_in_gui) {
                    list.add(stat.GetStat());
                }
            }
            for (StatGuiGroupSection type : StatGuiGroupSection.values()) {
                if (type != StatGuiGroupSection.OTHER) {
                    list.removeAll(type.sup.get());
                    for (Stat stat : type.sup.get()) {
                        if (stat.gui_group.isValid()) {
                            for (Stat s : stat.gui_group.getSameGroupStats()) {
                                list.remove(s);
                            }
                        }
                    }

                }
            }
            return list;
        }
        return sup.get();

    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".grouped_stat_section." + GUID();
    }

    public ResourceLocation getIcon() {
        return SlashRef.guiId("stat_gui/stat_groups/" + GUID());
    }

    @Override
    public String locNameForLangFile() {
        return name;
    }

    @Override
    public String GUID() {
        return id;
    }
}
