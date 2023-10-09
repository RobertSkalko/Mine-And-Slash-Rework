package com.robertx22.age_of_exile.aoe_data.database.spell_schools;

import com.robertx22.age_of_exile.aoe_data.database.perks.SpellPassives;
import com.robertx22.age_of_exile.aoe_data.database.spells.impl.*;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.*;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class SpellSchoolsAdder implements ExileRegistryInit {


    @Override
    public void registerAll() {
        // todo


        // fire + ice + golems
        // dps, summoner, self sustain
        SchoolBuilder.of("sorcerer", "Sorcerer")
                .add(BasicAttackSpells.FIREBALL_ID, new PointData(1, 0))
                .add(FireSpells.FIRE_NOVA_ID, new PointData(1, 1))
                .add(FireSpells.METEOR, new PointData(1, 2))
                .add(SummonSpells.SUMMON_FIRE_GOLEM, new PointData(1, 5))

                .add(BasicAttackSpells.FROSTBALL_ID, new PointData(3, 0))
                .add(WaterSpells.FROST_NOVA_AOE, new PointData(3, 1))
                .add(WaterSpells.CHILLING_FIELD, new PointData(3, 2))
                .add(WaterSpells.ICE_COMET, new PointData(3, 3))
                .add(WaterSpells.FROZEN_ORB, new PointData(3, 4))
                .add(SummonSpells.SUMMON_COLD_GOLEM, new PointData(3, 5))

                .add(WaterSpells.HEART_OF_ICE, new PointData(5, 2))
                .add(IntSpells.TELEPORT, new PointData(5, 3))
                .add(WaterSpells.MAGE_CIRCLE, new PointData(5, 4))
                .add(NatureSpells.REFRESH, new PointData(5, 5))

                .add(SpellPassives.MANA_REGEN, new PointData(8, 1))
                .add(SpellPassives.SPELL_DMG, new PointData(9, 1))
                .add(SpellPassives.BURN_CHANCE, new PointData(8, 2))
                .add(SpellPassives.FREEZE_CHANCE, new PointData(9, 2))
                .add(SpellPassives.MAGIC_SHIELD_REGEN, new PointData(8, 3))
                .add(SpellPassives.ELE_RES, new PointData(8, 4))
                .add(SpellPassives.CAST_SPEED_SORC, new PointData(8, 5))
                .add(SpellPassives.GOLEM_CHANCE, new PointData(8, 6))

                .build();


        // summons + chaos + curses
        // dps, summoner, debuffer
        SchoolBuilder.of("warlock", "Warlock")
                .add(BasicAttackSpells.POISONBALL_ID, new PointData(1, 0))
                .add(NatureSpells.THORN_BUSH, new PointData(1, 2))
                .add(NatureSpells.POISON_CLOUD, new PointData(1, 4))
                .add(IntSpells.BLACK_HOLE, new PointData(1, 6))

                .add(SummonSpells.SUMMON_ZOMBIE, new PointData(3, 0))
                .add(SummonSpells.CHILLING_TOUCH, new PointData(3, 1))
                .add(SummonSpells.RETURN_SUMMONS, new PointData(3, 2))
                .add(SummonSpells.SUMMON_SKELETAL_ARMY, new PointData(3, 3))

                .add(CurseSpells.CURSE_OF_AGONY, new PointData(5, 1))
                .add(CurseSpells.CURSE_OF_WEAK, new PointData(5, 3))
                .add(FireSpells.DRACONIC_BLOOD, new PointData(5, 4))
                .add(CurseSpells.CURSE_OF_DESPAIR, new PointData(5, 5))

                .add(SpellPassives.POISON_CHANCE, new PointData(8, 1))
                .add(SpellPassives.SPELL_LIFESTEAL, new PointData(8, 2))
                .add(SpellPassives.SUMMON_DMG, new PointData(9, 2))
                .add(SpellPassives.DOT_DMG, new PointData(8, 3))
                .add(SpellPassives.POISON_DURATION, new PointData(8, 4))
                .add(SpellPassives.CAST_SPEED_WL, new PointData(9, 4))
                .add(SpellPassives.DMG_TO_CURSED, new PointData(8, 5))
                .add(SpellPassives.DOT_LIFESTEAL, new PointData(8, 6))

                .build();

        // songs + heals
        // dps, buffer, healer
        SchoolBuilder.of("minstrel", "Minstrel")
                .add(SongSpells.POWER_CHORD, new PointData(1, 0))
                .add(SongSpells.RESONANCE, new PointData(1, 5))

                .add(HolySpells.HYMN_OF_VIGOR, new PointData(3, 0))
                .add(HolySpells.HYMN_OF_PERSERVANCE, new PointData(3, 2))
                .add(HolySpells.HYMN_OF_VALOR, new PointData(3, 4))

                .add(HolySpells.HEALING_AURA_ID, new PointData(5, 1))
                .add(HolySpells.SHOOTING_STAR, new PointData(5, 2))
                .add(HolySpells.INSPIRATION, new PointData(5, 4))
                .add(HolySpells.WISH, new PointData(5, 6))

                .add(SpellPassives.SONG_DURATION, new PointData(8, 1))
                .add(SpellPassives.HEAL_STR, new PointData(9, 1))
                .add(SpellPassives.HEALTH_MINS, new PointData(8, 2))
                .add(SpellPassives.HEAL_TO_SPELL, new PointData(9, 2))
                .add(SpellPassives.INC_AOE, new PointData(8, 3))
                .add(SpellPassives.LOW_HP_HEAL, new PointData(8, 4))
                .add(SpellPassives.MANA_COST, new PointData(8, 5))
                .add(SpellPassives.CDR, new PointData(8, 6))

                .build();

        // archer
        // dps, pet
        SchoolBuilder.of("hunter", "Hunter")
                .add(RangerSpells.BARRAGE, new PointData(1, 0))
                .add(RangerSpells.CHARGED_BOLT, new PointData(1, 1))
                .add(RangerSpells.QUICKDRAW, new PointData(1, 2))
                .add(RangerSpells.EXPLOSIVE_ARROW_ID, new PointData(1, 3))
                .add(RangerSpells.ARROW_TOTEM, new PointData(2, 3))
                .add(RangerSpells.RECOIL_SHOT, new PointData(1, 4))
                .add(RangerSpells.METEOR_ARROW, new PointData(1, 5))
                .add(RangerSpells.ARROW_STORM, new PointData(1, 6))

                .add(RangerSpells.FIRE_TRAP, new PointData(3, 0))
                .add(RangerSpells.FROST_TRAP, new PointData(3, 2))
                .add(RangerSpells.POISON_TRAP, new PointData(3, 5))

                .add(RangerSpells.DASH_ID, new PointData(5, 1))
                .add(SummonSpells.SUMMON_SPIRIT_WOLF, new PointData(6, 1))
                .add(RangerSpells.HUNTER_POTION, new PointData(5, 3))
                .add(RangerSpells.SMOKE_BOMB, new PointData(5, 4))
                .add(RangerSpells.GALE_WIND, new PointData(6, 4))

                .add(SpellPassives.PROJ_DMG, new PointData(8, 1))
                .add(SpellPassives.ENE_REGEN, new PointData(9, 1))
                .add(SpellPassives.TRAP_DMG, new PointData(8, 2))
                .add(SpellPassives.DODGE, new PointData(9, 2))
                .add(SpellPassives.PROJ_SPD, new PointData(8, 3))
                .add(SpellPassives.TRAP_CDR, new PointData(8, 4))
                .add(SpellPassives.SUMMON_HEALTH, new PointData(9, 4))
                .add(SpellPassives.CRIT_DMG, new PointData(8, 5))

                .build();

        // lightning + totems
        // dps, healer
        SchoolBuilder.of("shaman", "Shaman")

                .add(LightningSpells.LIGHTNING_SPEAR, new PointData(1, 0))
                .add(LightningSpells.LIGHTNING_NOVA, new PointData(1, 2))
                .add(LightningSpells.CHAIN_LIGHTNING, new PointData(1, 3))

                .add(TotemSpells.HEAL_TOTEM_ID, new PointData(3, 0))
                .add(LightningSpells.LIGHTNING_TOTEM, new PointData(3, 1))
                .add(TotemSpells.MANA_TOTEM_ID, new PointData(3, 3))
                .add(FireSpells.MAGMA_FLOWER, new PointData(3, 4))
                .add(WaterSpells.FROST_FLOWER, new PointData(4, 4))
                .add(NatureSpells.CHAOS_TOTEM, new PointData(3, 5))

                .add(NatureSpells.NATURE_BALM, new PointData(5, 1))
                .add(NatureSpells.ENTANGLE_SEED, new PointData(5, 3))

                .add(SpellPassives.ELECTRIFY_CHANCE, new PointData(8, 1))
                .add(SpellPassives.TOTEM_DMG, new PointData(9, 1))
                .add(SpellPassives.HEALTH_SHA, new PointData(8, 2))
                .add(SpellPassives.MANA_SHA, new PointData(9, 2))
                .add(SpellPassives.LIGHTNING_ELE, new PointData(8, 4))
                .add(SpellPassives.TOTEM_CDR, new PointData(9, 4))
                .add(SpellPassives.ARMOR_PER_MANA, new PointData(8, 5))
                .add(SpellPassives.ELE_PEN, new PointData(8, 6))

                .build();

        // uh warrior stuff
        // tank dps
        SchoolBuilder.of("warrior", "Warrior")
                .add(HolySpells.GONG_STRIKE_ID, new PointData(1, 0))
                .add(StrSpells.SHRED, new PointData(1, 2))
                .add(FireSpells.FLAME_STRIKE_ID, new PointData(1, 3))
                .add(WaterSpells.TIDAL_STRIKE, new PointData(2, 3))
                .add(HolySpells.WHIRLWIND, new PointData(1, 4))
                .add(HolySpells.CHARGE_ID, new PointData(2, 4))
                .add(DexSpells.EXECUTE, new PointData(1, 5))

                .add(HolySpells.TAUNT, new PointData(4, 1))
                .add(RangerSpells.BOOMERANG, new PointData(5, 1))
                .add(HolySpells.PULL, new PointData(4, 2))
                .add(FireSpells.VAMP_BLOOD, new PointData(4, 4))
                .add(HolySpells.UNDYING_WILL, new PointData(4, 6))

                .add(SpellPassives.HEALTH_WAR, new PointData(8, 1))
                .add(SpellPassives.ARMOR_PEN, new PointData(8, 2))
                .add(SpellPassives.HEALTH_REGEN, new PointData(8, 3))
                .add(SpellPassives.BLOCK_CHANCE, new PointData(9, 3))
                .add(SpellPassives.CRIT_HIT, new PointData(8, 4))
                .add(SpellPassives.LIFESTEAL, new PointData(9, 4))
                .add(SpellPassives.AURA_EFFECT, new PointData(8, 5))
                .add(SpellPassives.DAMAGE_RECEIVED, new PointData(8, 6))

                .build();


    }
}
