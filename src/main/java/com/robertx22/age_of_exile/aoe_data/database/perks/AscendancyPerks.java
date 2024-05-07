package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.ModEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.*;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectAndCondition;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.LeechInfo;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.ResourceOnAction;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.effects.defense.MaxElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.MaximumChargesStat;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentChance;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentDamage;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentDuration;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.BlockChance;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.AuraCapacity;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.AuraEffect;
import com.robertx22.age_of_exile.database.data.stats.types.summon.SummonHealth;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.tags.all.EffectTags;
import com.robertx22.age_of_exile.tags.all.SpellTags;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class AscendancyPerks {


    public static AscendancyKey ARCANIST = new AscendancyKey("arcanist", "Arcanist");
    public static AscendancyKey LICH = new AscendancyKey("lich", "Death Lich");
    public static AscendancyKey CHIEFTAIN = new AscendancyKey("chieftain", "Chieftain");
    public static AscendancyKey CHAMPION = new AscendancyKey("champion", "Champion");
    public static AscendancyKey RAIDER = new AscendancyKey("raider", "Raider");
    public static AscendancyKey HUNTER = new AscendancyKey("hunter", "Hunter");
    public static AscendancyKey TRICKSTER = new AscendancyKey("trickster", "Trickster");
    public static AscendancyKey ASSASSIN = new AscendancyKey("assassin", "Assassin");
    public static AscendancyKey NECROMANCER = new AscendancyKey("necromancer", "Necromancer");
    public static AscendancyKey ELEMENTALIST = new AscendancyKey("elementalist", "Elementalist");
    public static AscendancyKey GUARDIAN = new AscendancyKey("guardian", "Guardian");
    public static AscendancyKey BATTLEMAGE = new AscendancyKey("battlemage", "Battlemage");
    public static AscendancyKey JESTER = new AscendancyKey("jester", "Jester");
    public static AscendancyKey ASCENDANT = new AscendancyKey("ascendant", "Ascendant");

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
                    new OptScaleExactStat(25, ProcStats.PROFANE_EXPLOSION_ON_KILL.get(), ModType.FLAT),
                    new OptScaleExactStat(100, ProcStats.PROFANE_EXPLOSION_ON_CURSED_KILL.get(), ModType.FLAT)
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
                    new OptScaleExactStat(10, OffenseStats.TOTAL_DAMAGE.get(), ModType.MORE),
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


        CHIEFTAIN.of(x -> {
            x.createPerk(0, "Ignited Explosion",
                    new OptScaleExactStat(25, ProcStats.IGNITE_EXPLODE_ON_KILL.get(), ModType.FLAT)
            );

            x.createPerk(1, "Spirit of Fire",
                    new OptScaleExactStat(3, new MaxElementalResist(Elements.Fire), ModType.FLAT),
                    new OptScaleExactStat(1, DatapackStats.MAX_COLD_PER_MAX_FIRE, ModType.FLAT),
                    new OptScaleExactStat(1, DatapackStats.MAX_LIGHTNING_PER_MAX_FIRE, ModType.FLAT)
            );

            x.createPerk(2, "Skin of Fire",
                    new OptScaleExactStat(10, new ElementalResist(Elements.Fire), ModType.FLAT),
                    new OptScaleExactStat(1, DatapackStats.COLDRES_PER_FIRE, ModType.FLAT),
                    new OptScaleExactStat(1, DatapackStats.LIGHTNINGRES_PER_FIRE, ModType.FLAT)
            );

            x.createPerk(3, "The Torch",
                    new OptScaleExactStat(25, new ElementalPenetration(Elements.Fire), ModType.FLAT)
            );

            x.createPerk(4, "Blooming Flame",
                    new OptScaleExactStat(20, OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.totem), ModType.MORE),
                    new OptScaleExactStat(25, new AilmentChance(Ailments.BURN), ModType.FLAT)
            );

            x.createPerk(5, "Swift Burn",
                    new OptScaleExactStat(30, OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.totem), ModType.MORE),
                    new OptScaleExactStat(-25, SpellChangeStats.TOTEM_DURATION.get(), ModType.FLAT)
            );

            x.createPerk(6, "Fire Drinker",
                    new OptScaleExactStat(5, ResourceStats.ELEMENT_LEECH_RESOURCE.get(new LeechInfo(Elements.Fire, ResourceType.health)), ModType.FLAT),
                    new OptScaleExactStat(50, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Fire), ModType.FLAT)
            );
        });
        NECROMANCER.of(x -> {
            x.createPerk(0, "Minion Lord",
                    new OptScaleExactStat(25, OffenseStats.SUMMON_DAMAGE.get(), ModType.MORE)
            );

            x.createPerk(1, "Skeletal Shield",
                    new OptScaleExactStat(4, DatapackStats.BLOCK_PER_ENDURANCE_CHARGE, ModType.FLAT),
                    new OptScaleExactStat(1, new MaximumChargesStat(ModEffects.ENDURANCE_CHARGE), ModType.FLAT)
            );

            x.createPerk(2, "Infinite Army",
                    new OptScaleExactStat(3, SpellChangeStats.MAX_SUMMON_CAPACITY.get(), ModType.FLAT)
            );

            x.createPerk(3, "Swift Death",
                    new OptScaleExactStat(20, OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.summon), ModType.MORE),
                    new OptScaleExactStat(50, SpellChangeStats.CAST_TIME_PER_SPELL_TAG.get(SpellTags.summon), ModType.FLAT)
            );

            x.createPerk(4, "Flesh Army",
                    new OptScaleExactStat(10, OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.summon), ModType.MORE),
                    new OptScaleExactStat(25, SummonHealth.getInstance(), ModType.MORE)
            );

            x.createPerk(5, "Bulwark of Rot",
                    new OptScaleExactStat(20, BlockChance.getInstance(), ModType.FLAT),
                    new OptScaleExactStat(-25, HealthRegen.getInstance(), ModType.MORE)
            );

            x.createPerk(6, "Sacrificial Minions",
                    new OptScaleExactStat(5, ResourceStats.RESOURCE_ON_ACTION.get(ResourceOnAction.onBlock(ResourceType.energy)), ModType.FLAT),
                    new OptScaleExactStat(5, ResourceStats.RESOURCE_ON_ACTION.get(ResourceOnAction.onBlock(ResourceType.health)), ModType.FLAT),
                    new OptScaleExactStat(5, ResourceStats.RESOURCE_ON_ACTION.get(ResourceOnAction.onBlock(ResourceType.mana)), ModType.FLAT),
                    new OptScaleExactStat(5, ResourceStats.RESOURCE_ON_ACTION.get(ResourceOnAction.onBlock(ResourceType.magic_shield)), ModType.FLAT));
        });

        BATTLEMAGE.of(x -> {
            x.createPerk(0, "Illusion of Defense",
                    new OptScaleExactStat(1, OffenseStats.CRIT_IGNORE_ELEMENTAL_RESISTS.get(), ModType.FLAT)
            );

            x.createPerk(1, "Balance",
                    new OptScaleExactStat(5, DatapackStats.MANA_PER_10_HEALTH, ModType.FLAT)
            );

            x.createPerk(2, "Essence Regeneration",
                    new OptScaleExactStat(1, DatapackStats.MS_REGEN_PER_HP_REGEN, ModType.FLAT)
            );

            x.createPerk(3, "Elemental Overlord",
                    new OptScaleExactStat(25, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Elemental), ModType.MORE)
            );

            x.createPerk(4, "Battle of Wit",
                    new OptScaleExactStat(1, DatapackStats.CRIT_PER_10_STR, ModType.FLAT),
                    new OptScaleExactStat(10, OffenseStats.ELEMENTAL_ANY_WEAPON_DAMAGE.get(Elements.Elemental), ModType.MORE)
            );

            x.createPerk(5, "Resolve",
                    new OptScaleExactStat(3, DatapackStats.MANA_PER_10_ARMOR, ModType.FLAT),
                    new OptScaleExactStat(10, OffenseStats.ELEMENTAL_ANY_WEAPON_DAMAGE.get(Elements.Elemental), ModType.MORE)
            );

            x.createPerk(6, "Final Stand",
                    new OptScaleExactStat(5, new MaxElementalResist(Elements.Shadow), ModType.FLAT),
                    new OptScaleExactStat(10, DefenseStats.DAMAGE_REDUCTION.get(), ModType.FLAT)
            );
        });
        GUARDIAN.of(x -> {
            x.createPerk(0, "Unstoppable",
                    new OptScaleExactStat(100, DatapackStats.KNOCKBACK_RESIST, ModType.FLAT),
                    new OptScaleExactStat(10, Health.getInstance(), ModType.MORE)
            );

            x.createPerk(1, "Beyond Reach",
                    new OptScaleExactStat(3, new MaxElementalResist(Elements.Fire), ModType.FLAT),
                    new OptScaleExactStat(3, new MaxElementalResist(Elements.Cold), ModType.FLAT),
                    new OptScaleExactStat(3, new MaxElementalResist(Elements.Nature), ModType.FLAT),
                    new OptScaleExactStat(10, Armor.getInstance(), ModType.MORE)
            );

            x.createPerk(2, "Bulwark of Flesh",
                    new OptScaleExactStat(2, DatapackStats.HEALTH_PER_10_ARMOR, ModType.FLAT)
            );

            x.createPerk(3, "Terror",
                    new OptScaleExactStat(20, OffenseStats.ATTACK_DAMAGE.get(), ModType.MORE),
                    new OptScaleExactStat(100, OffenseStats.ACCURACY.get(), ModType.MORE)
            );

            x.createPerk(4, "Unlimited Life",
                    new OptScaleExactStat(100, HealthRegen.getInstance(), ModType.MORE)
            );

            x.createPerk(5, "Endurance",
                    new OptScaleExactStat(1, new MaximumChargesStat(ModEffects.ENDURANCE_CHARGE), ModType.FLAT),
                    new OptScaleExactStat(3, DatapackStats.BLOCK_PER_ENDURANCE_CHARGE, ModType.MORE)
            );

            x.createPerk(6, "Unending Pain",
                    new OptScaleExactStat(1, new MaximumChargesStat(ModEffects.ENDURANCE_CHARGE), ModType.FLAT),
                    new OptScaleExactStat(5, DatapackStats.DMG_PER_ENDURANCE_CHARGE, ModType.MORE)
            );

        });

        HUNTER.of(x -> {

            x.createPerk(0, "Gale Force",
                    new OptScaleExactStat(10, EffectStats.CHANCE_TO_GIVE_CASTER_EFFECT.get(new EffectAndCondition(ModEffects.GALE_FORCE, EffectAndCondition.Condition.HIT)), ModType.FLAT)
            );

            x.createPerk(1, "Elusive",
                    new OptScaleExactStat(1, DatapackStats.DMG_REDUCTION_PER_GALE_FORCE, ModType.FLAT),
                    new OptScaleExactStat(10, DodgeRating.getInstance(), ModType.MORE)
            );

            x.createPerk(2, "Ruthless Sniper",
                    new OptScaleExactStat(25, new AilmentChance(Ailments.BLEED), ModType.FLAT),
                    new OptScaleExactStat(25, new AilmentDamage(Ailments.BLEED), ModType.MORE)
            );

            x.createPerk(3, "Multiple Projectiles",
                    new OptScaleExactStat(2, SpellChangeStats.PROJECTILE_COUNT.get(), ModType.FLAT),
                    new OptScaleExactStat(-30, OffenseStats.PROJECTILE_DAMAGE.get(), ModType.MORE)
            );

            x.createPerk(4, "Agile Hands",
                    new OptScaleExactStat(100, DatapackStats.ACCURACY_PER_10_DEX, ModType.FLAT),
                    new OptScaleExactStat(25, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Physical), ModType.MORE)
            );

            x.createPerk(5, "Swift Shooter",
                    new OptScaleExactStat(25, SpellChangeStats.PROJECTILE_SPEED.get(), ModType.FLAT),
                    new OptScaleExactStat(25, OffenseStats.PROJECTILE_DAMAGE.get(), ModType.MORE)
            );

            x.createPerk(6, "Hunter's Chains",
                    new OptScaleExactStat(25, OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.chaining), ModType.MORE),
                    new OptScaleExactStat(25, SpellChangeStats.COOLDOWN_REDUCTION_PER_SPELL_TAG.get(SpellTags.chaining), ModType.FLAT)
            );

        });
        ASSASSIN.of(x -> {

            x.createPerk(0, "Swift Killer",
                    new OptScaleExactStat(30, OffenseStats.DAMAGE_WHEN_TARGET_IS_FULL_HP.get(), ModType.MORE)
            );

            x.createPerk(1, "Stacking Power",
                    new OptScaleExactStat(1, new MaximumChargesStat(ModEffects.POWER_CHARGE), ModType.FLAT),
                    new OptScaleExactStat(10, EffectStats.CHANCE_TO_GIVE_CASTER_EFFECT.get(new EffectAndCondition(ModEffects.POWER_CHARGE, EffectAndCondition.Condition.HIT)), ModType.FLAT)
            );

            x.createPerk(2, "Intense Power",
                    new OptScaleExactStat(10, DatapackStats.CRIT_DMG_PER_POWER_CHARGE, ModType.FLAT)
            );

            x.createPerk(3, "Master Of Criticals",
                    new OptScaleExactStat(50, OffenseStats.CRIT_CHANCE.get(), ModType.PERCENT)
            );

            x.createPerk(4, "Deadly Poisons",
                    new OptScaleExactStat(30, new AilmentDamage(Ailments.POISON), ModType.MORE)
            );

            x.createPerk(5, "Cruel Poisoner",
                    new OptScaleExactStat(25, new AilmentDuration(Ailments.POISON), ModType.FLAT),
                    new OptScaleExactStat(25, new AilmentChance(Ailments.POISON), ModType.FLAT)
            );

            x.createPerk(6, "Stacking Frenzy",
                    new OptScaleExactStat(1, new MaximumChargesStat(ModEffects.FRENZY_CHARGE), ModType.FLAT),
                    new OptScaleExactStat(10, EffectStats.CHANCE_TO_GIVE_CASTER_EFFECT.get(new EffectAndCondition(ModEffects.FRENZY_CHARGE, EffectAndCondition.Condition.HIT)), ModType.FLAT)
            );

        });

        CHAMPION.of(x -> {

            x.createPerk(0, "Warrior's Shield",
                    new OptScaleExactStat(20, BlockChance.getInstance(), ModType.FLAT),
                    new OptScaleExactStat(5, DatapackStats.DMG_PER_ENDURANCE_CHARGE, ModType.FLAT)
            );

            x.createPerk(1, "Frenzied Assault",
                    new OptScaleExactStat(1, new MaximumChargesStat(ModEffects.FRENZY_CHARGE), ModType.FLAT),
                    new OptScaleExactStat(10, EffectStats.CHANCE_TO_GIVE_CASTER_EFFECT.get(new EffectAndCondition(ModEffects.ENDURANCE_CHARGE, EffectAndCondition.Condition.HIT)), ModType.FLAT)
            );

            x.createPerk(2, "Blood Splatter",
                    new OptScaleExactStat(40, ProcStats.BLOOD_EXPLODE_ON_KILL.get(), ModType.FLAT)
            );

            x.createPerk(3, "Cruel Fate",
                    new OptScaleExactStat(50, new AilmentChance(Ailments.BLEED), ModType.FLAT),
                    new OptScaleExactStat(25, new AilmentDamage(Ailments.BLEED), ModType.MORE)
            );

            x.createPerk(4, "Energized Defense",
                    new OptScaleExactStat(30, ResourceStats.RESOURCE_ON_ACTION.get(ResourceOnAction.onBlock(ResourceType.energy)), ModType.FLAT),
                    new OptScaleExactStat(30, ResourceStats.RESOURCE_ON_ACTION.get(ResourceOnAction.onBlock(ResourceType.health)), ModType.FLAT)
            );

            x.createPerk(5, "Cunning",
                    new OptScaleExactStat(10, BlockChance.getInstance(), ModType.FLAT),
                    new OptScaleExactStat(25, DodgeRating.getInstance(), ModType.MORE)
            );

            x.createPerk(6, "Quick Rage",
                    new OptScaleExactStat(1, new MaximumChargesStat(ModEffects.ENDURANCE_CHARGE), ModType.FLAT),
                    new OptScaleExactStat(3, DatapackStats.MOVE_SPEED_PER_ENDURANCE_CHARGE, ModType.FLAT)
            );

        });

        RAIDER.of(x -> {

            x.createPerk(0, "Frenzied Evasion",
                    new OptScaleExactStat(1, new MaximumChargesStat(ModEffects.FRENZY_CHARGE), ModType.FLAT),
                    new OptScaleExactStat(30, DatapackStats.DODGE_PER_FRENZY_CHARGE, ModType.FLAT)
            );

            x.createPerk(1, "Frenzied Defense",
                    new OptScaleExactStat(1, new MaximumChargesStat(ModEffects.FRENZY_CHARGE), ModType.FLAT),
                    new OptScaleExactStat(10, EffectStats.CHANCE_TO_GIVE_CASTER_EFFECT.get(new EffectAndCondition(ModEffects.FRENZY_CHARGE, EffectAndCondition.Condition.HIT)), ModType.FLAT)
            );

            x.createPerk(2, "Turning Chance",
                    new OptScaleExactStat(50, OffenseStats.CRIT_CHANCE.get(), ModType.PERCENT),
                    new OptScaleExactStat(25, DefenseStats.DAMAGE_REDUCTION_CHANCE.get(), ModType.FLAT)
            );

            x.createPerk(3, "Dexterous",
                    new OptScaleExactStat(20, OffenseStats.DAMAGE_WHEN_TARGET_IS_FULL_HP.get(), ModType.MORE),
                    new OptScaleExactStat(100, DodgeRating.getInstance(), ModType.FLAT).scale(),
                    new OptScaleExactStat(30, DodgeRating.getInstance(), ModType.MORE)
            );

            x.createPerk(4, "Resistant",
                    new OptScaleExactStat(20, new ElementalResist(Elements.Elemental), ModType.FLAT),
                    new OptScaleExactStat(3, new MaxElementalResist(Elements.Fire), ModType.FLAT),
                    new OptScaleExactStat(3, new MaxElementalResist(Elements.Cold), ModType.FLAT),
                    new OptScaleExactStat(3, new MaxElementalResist(Elements.Nature), ModType.FLAT)
            );

            x.createPerk(5, "Pain Tolerance",
                    new OptScaleExactStat(20, new ElementalResist(Elements.Physical), ModType.FLAT),
                    new OptScaleExactStat(1, new MaximumChargesStat(ModEffects.ENDURANCE_CHARGE), ModType.FLAT)
            );

            x.createPerk(6, "Quickness",
                    new OptScaleExactStat(10, OffenseStats.CRIT_CHANCE.get(), ModType.FLAT),
                    new OptScaleExactStat(10, DatapackStats.MOVE_SPEED, ModType.FLAT)
            );

        });

        JESTER.of(x -> {

            x.createPerk(0, "Charm the Audience",
                    new OptScaleExactStat(25, ResourceStats.HEAL_STRENGTH.get(), ModType.FLAT),
                    new OptScaleExactStat(25, EffectStats.EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG.get(EffectTags.song), ModType.FLAT),
                    new OptScaleExactStat(3, new MaximumChargesStat(ModEffects.CHARM), ModType.FLAT)
            );

            x.createPerk(1, "Never-Ending Dance",
                    new OptScaleExactStat(1, new MaximumChargesStat(ModEffects.ENDURANCE_CHARGE), ModType.FLAT),
                    new OptScaleExactStat(10, EffectStats.CHANCE_TO_GIVE_CASTER_EFFECT.get(new EffectAndCondition(ModEffects.ENDURANCE_CHARGE, EffectAndCondition.Condition.HIT)), ModType.FLAT),
                    new OptScaleExactStat(10, DatapackStats.DMG_PER_ENDURANCE_CHARGE, ModType.FLAT)
            );

            x.createPerk(2, "Soothing Melody",
                    new OptScaleExactStat(10, ResourceStats.HEAL_STRENGTH.get(), ModType.FLAT),
                    new OptScaleExactStat(-20, SpellChangeStats.MANA_COST.get(), ModType.FLAT),
                    new OptScaleExactStat(25, OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.song), ModType.MORE)
            );

            x.createPerk(3, "Illusive Rhythm",
                    new OptScaleExactStat(25, DatapackStats.DODGE_PER_ENDURANCE_CHARGE, ModType.FLAT),
                    new OptScaleExactStat(3, DatapackStats.MANA_PER_DODGE, ModType.MORE)
            );

            x.createPerk(4, "Universal Language",
                    new OptScaleExactStat(50, DatapackStats.HEAL_TO_SKILL_DMG, ModType.FLAT)
            );

            x.createPerk(5, "Jack of All Trades",
                    new OptScaleExactStat(10, EffectStats.EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG.get(EffectTags.song), ModType.FLAT),
                    new OptScaleExactStat(25, ResourceStats.HEAL_STRENGTH.get(), ModType.FLAT),
                    new OptScaleExactStat(1, new MaximumChargesStat(ModEffects.ENDURANCE_CHARGE), ModType.FLAT),
                    new OptScaleExactStat(1, new MaximumChargesStat(ModEffects.FRENZY_CHARGE), ModType.FLAT),
                    new OptScaleExactStat(1, new MaximumChargesStat(ModEffects.POWER_CHARGE), ModType.FLAT)
            );

            x.createPerk(6, "Smell the Roses",
                    new OptScaleExactStat(25, ResourceStats.HEAL_STRENGTH.get(), ModType.FLAT),
                    new OptScaleExactStat(10, HealthRegen.getInstance(), ModType.MORE),
                    new OptScaleExactStat(10, ManaRegen.getInstance(), ModType.MORE),
                    new OptScaleExactStat(10, EnergyRegen.getInstance(), ModType.MORE),
                    new OptScaleExactStat(10, MagicShieldRegen.getInstance(), ModType.MORE)
            );

        });
        ASCENDANT.of(x -> {

            x.createPerk(0, "Preparation",
                    new OptScaleExactStat(25, DatapackStats.INT, ModType.FLAT),
                    new OptScaleExactStat(25, DatapackStats.DEX, ModType.FLAT),
                    new OptScaleExactStat(25, DatapackStats.STR, ModType.FLAT)
            );

            x.createPerk(1, "Key to Ascension",
                    new OptScaleExactStat(25, DatapackStats.INT, ModType.MORE),
                    new OptScaleExactStat(25, DatapackStats.DEX, ModType.MORE),
                    new OptScaleExactStat(25, DatapackStats.STR, ModType.MORE)
            );

            x.createPerk(2, "Boundless",
                    new OptScaleExactStat(20, AuraCapacity.getInstance(), ModType.PERCENT)
            );

            x.createPerk(3, "All Encompassing",
                    new OptScaleExactStat(10, AuraEffect.getInstance(), ModType.FLAT)
            );

            x.createPerk(4, "Frenzied Path",
                    new OptScaleExactStat(1, new MaximumChargesStat(ModEffects.FRENZY_CHARGE), ModType.FLAT),
                    new OptScaleExactStat(5, EffectStats.CHANCE_TO_GIVE_CASTER_EFFECT.get(new EffectAndCondition(ModEffects.FRENZY_CHARGE, EffectAndCondition.Condition.HIT)), ModType.FLAT)
            );
            x.createPerk(5, "Path of Power",
                    new OptScaleExactStat(1, new MaximumChargesStat(ModEffects.POWER_CHARGE), ModType.FLAT),
                    new OptScaleExactStat(5, EffectStats.CHANCE_TO_GIVE_CASTER_EFFECT.get(new EffectAndCondition(ModEffects.POWER_CHARGE, EffectAndCondition.Condition.HIT)), ModType.FLAT)
            );
            x.createPerk(6, "Enduring Path",
                    new OptScaleExactStat(1, new MaximumChargesStat(ModEffects.ENDURANCE_CHARGE), ModType.FLAT),
                    new OptScaleExactStat(5, EffectStats.CHANCE_TO_GIVE_CASTER_EFFECT.get(new EffectAndCondition(ModEffects.ENDURANCE_CHARGE, EffectAndCondition.Condition.HIT)), ModType.FLAT)
            );


        });
    }
}
