package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.ModEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.*;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectAndCondition;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.MaximumChargesStat;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentChance;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentDamage;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.tags.all.EffectTags;
import com.robertx22.age_of_exile.tags.all.SpellTags;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class AscendancyPerks {


    public static AscendancyKey ARCANIST = new AscendancyKey("arcanist", "Arcanist");
    public static AscendancyKey LICH = new AscendancyKey("lich", "Death Lich");
    public static AscendancyKey CHIEF = new AscendancyKey("chief", "Chieftain");
    public static AscendancyKey CHAMP = new AscendancyKey("champ", "Champion");
    public static AscendancyKey RAIDER = new AscendancyKey("raider", "Raider");
    public static AscendancyKey ARCHER = new AscendancyKey("hunter", "Hunter");
    public static AscendancyKey TRICKSTER = new AscendancyKey("trickster", "Trickster");
    public static AscendancyKey ASSASSIN = new AscendancyKey("assassin", "Assassin");
    public static AscendancyKey NECRO = new AscendancyKey("necromancer", "Necromancer");
    public static AscendancyKey ELEMENTALIST = new AscendancyKey("elementalist", "Elementalist");
    public static AscendancyKey GUARDIAN = new AscendancyKey("guardian", "Guardian");
    public static AscendancyKey BATTLEMAGE = new AscendancyKey("battlemage", "Battlemage");

    public static void init() {


        LICH.of(x -> {

            x.createPerk(0, "Dark Power",
                    new OptScaleExactStat(5, DatapackStats.AOE_PER_POWER_CHARGE, ModType.FLAT),
                    new OptScaleExactStat(5, DatapackStats.DMG_PER_POWER_CHARGE, ModType.FLAT),
                    new OptScaleExactStat(1, new MaximumChargesStat(ModEffects.POWER_CHARGE), ModType.FLAT)
            );
            x.createPerk(1, "Accumulating Power",
                    new OptScaleExactStat(5, EffectStats.CHANCE_TO_GIVE_CASTER_EFFECT.get(new EffectAndCondition(ModEffects.POWER_CHARGE, EffectAndCondition.Condition.HIT)), ModType.FLAT),
                    new OptScaleExactStat(5, DatapackStats.CRIT_DMG_PER_POWER_CHARGE, ModType.FLAT)
            );
            x.createPerk(2, "Artic Armor",
                    new OptScaleExactStat(25, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Cold), ModType.MORE),
                    new OptScaleExactStat(5, DatapackStats.ARMOR_PER_MANA, ModType.FLAT)
            );
            x.createPerk(3, "Dark Infusion",
                    new OptScaleExactStat(3, RegeneratePercentStat.MAGIC_SHIELD, ModType.FLAT),
                    new OptScaleExactStat(25, MagicShieldRegen.getInstance(), ModType.PERCENT)
            );
            x.createPerk(4, "Profane Explosion",
                    new OptScaleExactStat(50, ProcStats.PROFANE_EXPLOSION_ON_KILL.get(), ModType.FLAT)
            );
            x.createPerk(5, "Curse Specialist",
                    new OptScaleExactStat(25, OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.curse), ModType.MORE),
                    new OptScaleExactStat(50, SpellChangeStats.COOLDOWN_REDUCTION_PER_SPELL_TAG.get(SpellTags.curse), ModType.FLAT)
            );
            x.createPerk(6, "Chaotic Disposition",
                    new OptScaleExactStat(25, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Shadow), ModType.MORE),
                    new OptScaleExactStat(50, new ElementalResist(Elements.Shadow), ModType.FLAT)
            );
        });

        ARCANIST.of(x -> {
            x.createPerk(0, "Unlimited Mana",
                    new OptScaleExactStat(20, Mana.getInstance(), ModType.MORE),
                    new OptScaleExactStat(3, RegeneratePercentStat.MANA, ModType.FLAT)
            );
            x.createPerk(1, "Arcane Cloak",
                    new OptScaleExactStat(3, DatapackStats.MS_PER_10_MANA, ModType.FLAT),
                    new OptScaleExactStat(25, ManaRegen.getInstance(), ModType.PERCENT)
            );

            x.createPerk(2, "Unyielding Will",
                    new OptScaleExactStat(10, Health.getInstance(), ModType.MORE),
                    new OptScaleExactStat(1, EffectStats.EFFECT_IMMUNITY.get(ModEffects.CURSE_WEAKNESS), ModType.FLAT),
                    new OptScaleExactStat(1, EffectStats.EFFECT_IMMUNITY.get(ModEffects.DESPAIR), ModType.FLAT)
            );
            x.createPerk(3, "Stark Defender",
                    new OptScaleExactStat(1, new MaximumChargesStat(ModEffects.ENDURANCE_CHARGE), ModType.FLAT),
                    new OptScaleExactStat(10, new ElementalResist(Elements.Physical), ModType.FLAT)
            );
            x.createPerk(4, "Arcane Surge",
                    new OptScaleExactStat(10, OffenseStats.TOTAL_DAMAGE.get(), ModType.MORE),
                    new OptScaleExactStat(20, SpellChangeStats.CAST_SPEED.get(), ModType.FLAT)
            );


            x.createPerk(5, "Totem Master",
                    new OptScaleExactStat(25, OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.totem), ModType.MORE),
                    new OptScaleExactStat(25, SpellChangeStats.COOLDOWN_REDUCTION_PER_SPELL_TAG.get(SpellTags.totem), ModType.FLAT)
            );
            x.createPerk(6, "Tactical Genius",
                    new OptScaleExactStat(25, OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.totem), ModType.MORE),
                    new OptScaleExactStat(-25, SpellChangeStats.INCREASED_AREA.get(), ModType.FLAT),
                    new OptScaleExactStat(-10, SpellChangeStats.MANA_COST.get(), ModType.FLAT)
            );
        });

        ELEMENTALIST.of(x -> {
            x.createPerk(0, "Creator of Flames",
                    new OptScaleExactStat(25, new AilmentChance(Ailments.BURN), ModType.FLAT),
                    new OptScaleExactStat(25, new AilmentDamage(Ailments.BURN), ModType.MORE)
            );
            x.createPerk(1, "Creator of Frost",
                    new OptScaleExactStat(25, new AilmentChance(Ailments.FREEZE), ModType.FLAT),
                    new OptScaleExactStat(25, new AilmentDamage(Ailments.FREEZE), ModType.MORE)
            );

            x.createPerk(2, "Creator of Lightning",
                    new OptScaleExactStat(25, new AilmentChance(Ailments.ELECTRIFY), ModType.FLAT),
                    new OptScaleExactStat(25, new AilmentDamage(Ailments.ELECTRIFY), ModType.MORE)
            );
            x.createPerk(3, "Army of Stone",
                    new OptScaleExactStat(1, SpellChangeStats.MAX_SUMMON_CAPACITY.get(), ModType.FLAT),
                    new OptScaleExactStat(25, EffectStats.EFFECT_OF_BUFFS_ON_YOU_PER_EFFECT_TAG.get(EffectTags.golem), ModType.FLAT)
            );
            x.createPerk(4, "Guidance of Stone",
                    new OptScaleExactStat(50, EffectStats.EFFECT_OF_BUFFS_ON_YOU_PER_EFFECT_TAG.get(EffectTags.golem), ModType.FLAT),
                    new OptScaleExactStat(10, OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.golem), ModType.MORE)
            );

            x.createPerk(5, "Doomed Path",
                    new OptScaleExactStat(25, OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.area), ModType.MORE),
                    new OptScaleExactStat(25, SpellChangeStats.INCREASED_AREA.get(), ModType.FLAT)
            );

            x.createPerk(6, "Futility of Defense",
                    new OptScaleExactStat(20, new ElementalPenetration(Elements.Fire), ModType.FLAT),
                    new OptScaleExactStat(20, new ElementalPenetration(Elements.Cold), ModType.FLAT),
                    new OptScaleExactStat(20, new ElementalPenetration(Elements.Nature), ModType.FLAT)
            );
        });
        
        TRICKSTER.of(x -> {
            x.createPerk(0, "Total Frenzy",
                    new OptScaleExactStat(2, new MaximumChargesStat(ModEffects.FRENZY_CHARGE), ModType.FLAT)
            );
            x.createPerk(1, "Resourceful Thief",
                    new OptScaleExactStat(10, ResourceStats.RESOURCE_ON_KILL.get(ResourceType.mana), ModType.FLAT),
                    new OptScaleExactStat(10, ResourceStats.RESOURCE_ON_KILL.get(ResourceType.health), ModType.FLAT),
                    new OptScaleExactStat(10, ResourceStats.RESOURCE_ON_KILL.get(ResourceType.magic_shield), ModType.FLAT),
                    new OptScaleExactStat(10, ResourceStats.RESOURCE_ON_KILL.get(ResourceType.energy), ModType.FLAT)
            );
            x.createPerk(2, "Master of Phasing",
                    new OptScaleExactStat(10, DatapackStats.MOVE_SPEED, ModType.FLAT),
                    new OptScaleExactStat(1, EffectStats.EFFECT_IMMUNITY.get(ModEffects.SLOW), ModType.FLAT)
            );
            x.createPerk(3, "Trickery",
                    new OptScaleExactStat(3, DatapackStats.DODGE_PER_MS, ModType.FLAT),
                    new OptScaleExactStat(2, DatapackStats.MS_PER_10_DODGE, ModType.FLAT)
            );

            x.createPerk(4, "Essence Leech",
                    new OptScaleExactStat(2, ResourceStats.LEECH_CAP.get(ResourceType.magic_shield), ModType.FLAT),
                    new OptScaleExactStat(25, ResourceStats.INCREASED_LEECH.get(), ModType.FLAT)
            );

            x.createPerk(5, "Slippery",
                    new OptScaleExactStat(25, DefenseStats.DAMAGE_REDUCTION_CHANCE.get(), ModType.FLAT),
                    new OptScaleExactStat(25, DodgeRating.getInstance(), ModType.PERCENT)
            );

            x.createPerk(6, "Thick Cloak",
                    new OptScaleExactStat(25, MagicShield.getInstance(), ModType.PERCENT),
                    new OptScaleExactStat(10, DefenseStats.DAMAGE_REDUCTION.get(), ModType.FLAT)
            );
        });

    }
}
