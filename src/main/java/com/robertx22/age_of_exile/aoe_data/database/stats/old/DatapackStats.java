package com.robertx22.age_of_exile.aoe_data.database.stats.old;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.CoreStatData;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.AddPerPercentOfOther;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.AttributeStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.CoreStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.MoreXPerYOf;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.BonusAttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SkillDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.Blood;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.Arrays;
import java.util.UUID;

public class DatapackStats implements ExileRegistryInit {
       
    public static Stat INT = new CoreStat("intelligence", "Intelligence", CoreStatData.of(Arrays.asList(
            new OptScaleExactStat(5, Mana.getInstance(), ModType.FLAT),
            new OptScaleExactStat(0.1F, ManaRegen.getInstance(), ModType.FLAT),
            new OptScaleExactStat(0.5F, MagicShield.getInstance(), ModType.PERCENT)
    )));

    public static Stat STR = new CoreStat("strength", "Strength", CoreStatData.of(Arrays.asList(
            new OptScaleExactStat(0.1F, WeaponDamage.getInstance(), ModType.FLAT),
            new OptScaleExactStat(0.1F, HealthRegen.getInstance(), ModType.FLAT),
            new OptScaleExactStat(5, Health.getInstance(), ModType.FLAT)
    )));

    public static Stat DEX = new CoreStat("dexterity", "Dexterity", CoreStatData.of(Arrays.asList(
            new OptScaleExactStat(1, DodgeRating.getInstance(), ModType.PERCENT),
            new OptScaleExactStat(0.2F, EnergyRegen.getInstance(), ModType.FLAT),
            new OptScaleExactStat(5, Energy.getInstance(), ModType.FLAT)
    )));


    public static Stat HEAL_TO_SPELL_DMG = new AddPerPercentOfOther(Stats.HEAL_STRENGTH.get(), SkillDamage.getInstance());
    public static Stat PHYS_DMG_PER_MANA = new AddPerPercentOfOther(Mana.getInstance(), new BonusAttackDamage(Elements.Physical));

    public static Stat BLOOD_PER_10STR = new MoreXPerYOf(DatapackStats.STR, Blood.getInstance(), 10);
    public static Stat HEALTH_PER_10_INT = new MoreXPerYOf(DatapackStats.INT, Health.getInstance(), 10);
    public static Stat MANA_PER_10_INT = new MoreXPerYOf(DatapackStats.INT, Mana.getInstance(), 10);


    public static Stat MOVE_SPEED = new AttributeStat("move_speed", "Move Speed", UUID.fromString("7e286d81-3fcf-471c-85b8-980072b30907"), Attributes.MOVEMENT_SPEED, true);

    public static Stat HP_REGEN_PER_INT = new MoreXPerYOf(DatapackStats.INT, HealthRegen.getInstance(), 10);
    public static Stat HP_PER_DEX = new MoreXPerYOf(DatapackStats.DEX, Health.getInstance(), 10);
    public static Stat ARMOR_PER_MANA = new MoreXPerYOf(Mana.getInstance(), Armor.getInstance(), 10);

    @Override
    public void registerAll() {

        DEX.addToSerializables();
        INT.addToSerializables();
        STR.addToSerializables();

        HEAL_TO_SPELL_DMG.addToSerializables();
        ARMOR_PER_MANA.addToSerializables();
        HP_PER_DEX.addToSerializables();
        HP_REGEN_PER_INT.addToSerializables();
        BLOOD_PER_10STR.addToSerializables();
        HEALTH_PER_10_INT.addToSerializables();
        MANA_PER_10_INT.addToSerializables();
        MOVE_SPEED.addToSerializables();
        PHYS_DMG_PER_MANA.addToSerializables();
    }
}
