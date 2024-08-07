package com.robertx22.age_of_exile.aoe_data.database.stats.old;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.ModEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.DefenseStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.OffenseStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.ResourceStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.SpellChangeStats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.BaseDatapackStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.CoreStatData;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.*;
import com.robertx22.age_of_exile.database.data.stats.effects.defense.MaxElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.BlockChance;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.BonusAttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SkillDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.Blood;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.data.stats.types.summon.SummonHealth;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class DatapackStats implements ExileRegistryInit {

    private static List<BaseDatapackStat> all = new ArrayList<>();

    public static void tryAdd(BaseDatapackStat stat) {
        if (all.stream().noneMatch(x -> x.GUID().equals(stat.GUID()))) {
            all.add(stat);
        }
    }

    // vanilla attributes
    public static Stat MOVE_SPEED = new AttributeStat("move_speed", "Move Speed", UUID.fromString("7e286d81-3fcf-471c-85b8-980072b30907"), Attributes.MOVEMENT_SPEED, true, AttributeModifier.Operation.MULTIPLY_TOTAL, true);
    public static Stat KNOCKBACK_RESIST = new AttributeStat("knockback_resist", "Knockback Resist", UUID.fromString("7e286d81-3fcf-471c-85b8-980072b30905"), Attributes.KNOCKBACK_RESISTANCE, true, AttributeModifier.Operation.ADDITION, true);

    // core stats
    public static Stat INT = new CoreStat("intelligence", "Intelligence", CoreStatData.of(Arrays.asList(
            new OptScaleExactStat(10, Mana.getInstance(), ModType.FLAT),
            new OptScaleExactStat(0.5F, ManaRegen.getInstance(), ModType.FLAT),
            new OptScaleExactStat(0.5F, MagicShield.getInstance(), ModType.PERCENT)
    )));

    public static Stat STR = new CoreStat("strength", "Strength", CoreStatData.of(Arrays.asList(
            new OptScaleExactStat(5, Health.getInstance(), ModType.FLAT),
            new OptScaleExactStat(0.25F, HealthRegen.getInstance(), ModType.FLAT),
            new OptScaleExactStat(1, Armor.getInstance(), ModType.PERCENT)
    )));

    public static Stat DEX = new CoreStat("dexterity", "Dexterity", CoreStatData.of(Arrays.asList(
            new OptScaleExactStat(10, Energy.getInstance(), ModType.FLAT),
            new OptScaleExactStat(1, DodgeRating.getInstance(), ModType.PERCENT),
            new OptScaleExactStat(0.5F, EnergyRegen.getInstance(), ModType.FLAT)
    )));

    // bonus per effect charges

    public static Stat MORE_DMG_PER_POWER = BonusStatPerEffectStacks.of(ModEffects.POWER_CHARGE, "more_dmg_per_power", "Damage per Power Charge",
            new OptScaleExactStat(1, OffenseStats.TOTAL_DAMAGE.get(), ModType.MORE
            ));
    public static Stat MORE_DMG_PER_FRENZY = BonusStatPerEffectStacks.of(ModEffects.POWER_CHARGE, "more_dmg_per_frenzy", "Damage per Frenzy Charge",
            new OptScaleExactStat(1, OffenseStats.TOTAL_DAMAGE.get(), ModType.MORE
            ));

    public static Stat MOVE_SPEED_PER_ENDURANCE_CHARGE = BonusStatPerEffectStacks.of(ModEffects.ENDURANCE_CHARGE, "move_speed_per_endurance_charge", "Move Speed per Endurance Charge",
            new OptScaleExactStat(1, MOVE_SPEED, ModType.FLAT
            ));
    public static Stat BLOCK_PER_ENDURANCE_CHARGE = BonusStatPerEffectStacks.of(ModEffects.ENDURANCE_CHARGE, "block_per_endurance_charge", "Block Chance per Endurance Charge",
            new OptScaleExactStat(1, BlockChance.getInstance(), ModType.FLAT
            ));
    public static Stat DODGE_PER_ENDURANCE_CHARGE = BonusStatPerEffectStacks.of(ModEffects.ENDURANCE_CHARGE, "dodge_er_endurance_charge", "Dodge per Endurance Charge",
            new OptScaleExactStat(1, DodgeRating.getInstance(), ModType.FLAT).scale());


    public static Stat DMG_PER_ENDURANCE_CHARGE = BonusStatPerEffectStacks.of(ModEffects.ENDURANCE_CHARGE, "dmg_per_endurance_charge", "Damage per Endurance Charge",
            new OptScaleExactStat(1, OffenseStats.TOTAL_DAMAGE.get(), ModType.FLAT
            ));
    public static Stat AOE_PER_POWER_CHARGE = BonusStatPerEffectStacks.of(ModEffects.POWER_CHARGE, "aoe_per_power_charge", "Aoe Increase per Power Charge",
            new OptScaleExactStat(1, SpellChangeStats.INCREASED_AREA.get(), ModType.FLAT));

    public static Stat DMG_PER_POWER_CHARGE = BonusStatPerEffectStacks.of(ModEffects.POWER_CHARGE, "dmg_per_power_charge", "Damage per Power Charge",
            new OptScaleExactStat(1, OffenseStats.TOTAL_DAMAGE.get(), ModType.FLAT));

    public static Stat CRIT_DMG_PER_POWER_CHARGE = BonusStatPerEffectStacks.of(ModEffects.POWER_CHARGE, "crit_dmg_per_power_charge", "Crit Damage per Power Charge",
            new OptScaleExactStat(1, OffenseStats.CRIT_DAMAGE.get(), ModType.FLAT));

    public static Stat DMG_REDUCTION_PER_GALE_FORCE = BonusStatPerEffectStacks.of(ModEffects.GALE_FORCE, "dmg_reduction_per_gale_force", "Damage Reduction per Gale Force",
            new OptScaleExactStat(1, DefenseStats.DAMAGE_REDUCTION.get(), ModType.FLAT));

    public static Stat DODGE_PER_FRENZY_CHARGE = BonusStatPerEffectStacks.of(ModEffects.FRENZY_CHARGE, "dodge_er_power_charge", "Dodge per Frenzy Charge",
            new OptScaleExactStat(1, DodgeRating.getInstance(), ModType.FLAT).scale());


    // bonus per percent of other
    public static Stat HEAL_TO_SKILL_DMG = new AddPerPercentOfOther(ResourceStats.HEAL_STRENGTH.get(), SkillDamage.getInstance());
    public static Stat PHYS_DMG_PER_MANA = new AddPerPercentOfOther(Mana.getInstance(), new BonusAttackDamage(Elements.Physical));
    public static Stat SUMMON_HP_PER_HP = new AddPerPercentOfOther(Health.getInstance(), SummonHealth.getInstance());

    // more x per y
    public static Stat BLOOD_PER_10STR = new MoreXPerYOf(DatapackStats.STR, Blood.getInstance(), 10);
    public static Stat HEALTH_PER_10_INT = new MoreXPerYOf(DatapackStats.INT, Health.getInstance(), 10);
    public static Stat HEALTH_PER_10_STR = new MoreXPerYOf(DatapackStats.STR, Health.getInstance(), 10);
    public static Stat MANA_PER_10_INT = new MoreXPerYOf(DatapackStats.INT, Mana.getInstance(), 10);
    public static Stat MANA_PER_10_STR = new MoreXPerYOf(DatapackStats.STR, Mana.getInstance(), 10);
    public static Stat MANA_PER_10_DEX = new MoreXPerYOf(DatapackStats.DEX, Mana.getInstance(), 10);
    public static Stat ACCURACY_PER_10_DEX = new MoreXPerYOf(DatapackStats.DEX, OffenseStats.ACCURACY.get(), 10);
    public static Stat ENERGY_PER_10_DEX = new MoreXPerYOf(DatapackStats.DEX, Energy.getInstance(), 10);
    public static Stat ENERGY_PER_10_MANA = new MoreXPerYOf(Mana.getInstance(), Energy.getInstance(), 10);
    public static Stat MS_PER_10_MANA = new MoreXPerYOf(Mana.getInstance(), MagicShield.getInstance(), 10);
    public static Stat MS_PER_10_DODGE = new MoreXPerYOf(DodgeRating.getInstance(), MagicShield.getInstance(), 10);
    public static Stat MANA_REG_PER_500_MS = new MoreXPerYOf(MagicShield.getInstance(), ManaRegen.getInstance(), 500);
    public static Stat DODGE_PER_MS = new MoreXPerYOf(MagicShield.getInstance(), DodgeRating.getInstance(), 10);
    public static Stat HP_REGEN_PER_INT = new MoreXPerYOf(DatapackStats.INT, HealthRegen.getInstance(), 10);
    public static Stat HP_PER_DEX = new MoreXPerYOf(DatapackStats.DEX, Health.getInstance(), 10);
    public static Stat ARMOR_PER_MANA = new MoreXPerYOf(Mana.getInstance(), Armor.getInstance(), 10);
    public static Stat ARMOR_PER_DODGE = new MoreXPerYOf(DodgeRating.getInstance(), Armor.getInstance(), 10);
    public static Stat MANA_PER_DODGE = new MoreXPerYOf(DodgeRating.getInstance(), Mana.getInstance(), 10);
    public static Stat PROJ_DMG_PER_STR = new MoreXPerYOf(DatapackStats.STR, OffenseStats.PROJECTILE_DAMAGE.get(), 5);
    public static Stat SPELL_DMG_PER_STR = new MoreXPerYOf(DatapackStats.STR, OffenseStats.STYLE_DAMAGE.get(PlayStyle.INT), 5);
    public static Stat HP_REGEN_PER_MS_REGEN = new MoreXPerYOf(MagicShieldRegen.getInstance(), HealthRegen.getInstance(), 1);
    public static Stat MS_REGEN_PER_HP_REGEN = new MoreXPerYOf(HealthRegen.getInstance(), MagicShieldRegen.getInstance(), 1);
    public static Stat CRIT_PER_10_STR = new MoreXPerYOf(DatapackStats.STR, OffenseStats.CRIT_CHANCE.get(), 10);
    public static Stat MANA_PER_10_ARMOR = new MoreXPerYOf(Armor.getInstance(), Mana.getInstance(), 10);
    public static Stat HEALTH_PER_10_ARMOR = new MoreXPerYOf(Armor.getInstance(), Health.getInstance(), 10);

    public static Stat MANA_PER_10_HEALTH = new MoreXPerYOf(Health.getInstance(), Mana.getInstance(), 10);


    public static Stat MAX_COLD_PER_MAX_FIRE = new MoreXPerYOf(new MaxElementalResist(Elements.Fire), new MaxElementalResist(Elements.Cold), 1);
    public static Stat MAX_LIGHTNING_PER_MAX_FIRE = new MoreXPerYOf(new MaxElementalResist(Elements.Fire), new MaxElementalResist(Elements.Nature), 1);

    public static Stat COLDRES_PER_FIRE = new MoreXPerYOf(new ElementalResist(Elements.Fire), new ElementalResist(Elements.Cold), 1);
    public static Stat LIGHTNINGRES_PER_FIRE = new MoreXPerYOf(new ElementalResist(Elements.Fire), new ElementalResist(Elements.Nature), 1);

    @Override
    public void registerAll() {
        for (BaseDatapackStat stat : all) {
            stat.addToSerializables();
        }

    }
}
