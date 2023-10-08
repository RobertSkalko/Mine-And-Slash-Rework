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
                .add(BasicAttackSpells.FIREBALL_ID, new PointData(0, 0))
                .add(SpellPassives.BURN_CHANCE, new PointData(0, 1))
                .add(BasicAttackSpells.FROSTBALL_ID, new PointData(6, 0))

                .add(FireSpells.FIRE_NOVA_ID, new PointData(1, 1))
                .add(WaterSpells.FROST_NOVA_AOE, new PointData(7, 1))

                .add(FireSpells.METEOR, new PointData(2, 2))
                .add(WaterSpells.CHILLING_FIELD, new PointData(8, 2))

                .add(WaterSpells.ICE_COMET, new PointData(9, 3))
                .add(WaterSpells.HEART_OF_ICE, new PointData(10, 3))

                .add(IntSpells.TELEPORT, new PointData(5, 4))
                .add(WaterSpells.FROZEN_ORB, new PointData(6, 4))

                .add(SummonSpells.SUMMON_FIRE_GOLEM, new PointData(3, 5))
                .add(SummonSpells.SUMMON_FIRE_GOLEM, new PointData(7, 5))

                .add(WaterSpells.MAGE_CIRCLE, new PointData(4, 6))
                .add(NatureSpells.REFRESH, new PointData(8, 6))

                .build();


        // summons + chaos + curses
        // dps, summoner, debuffer
        SchoolBuilder.of("warlock", "Warlock")
                .add(BasicAttackSpells.POISONBALL_ID, new PointData(0, 0))
                .add(SummonSpells.SUMMON_ZOMBIE, new PointData(6, 0))

                .add(SummonSpells.CHILLING_TOUCH, new PointData(7, 1))

                .add(NatureSpells.THORN_BUSH, new PointData(1, 2))
                .add(CurseSpells.CURSE_OF_AGONY, new PointData(5, 2))
                .add(SummonSpells.RETURN_SUMMONS, new PointData(8, 2))

                .add(CurseSpells.CURSE_OF_WEAK, new PointData(5, 3))
                .add(SummonSpells.SUMMON_SKELETAL_ARMY, new PointData(9, 3))

                .add(NatureSpells.POISON_CLOUD, new PointData(2, 4))
                .add(FireSpells.DRACONIC_BLOOD, new PointData(5, 4))

                .add(CurseSpells.CURSE_OF_DESPAIR, new PointData(5, 5))

                .add(IntSpells.BLACK_HOLE, new PointData(3, 6))

                .build();

        // songs + heals
        // dps, buffer, healer
        SchoolBuilder.of("minstrel", "Minstrel")
                .add(SongSpells.POWER_CHORD, new PointData(0, 0))
                .add(HolySpells.HYMN_OF_VIGOR, new PointData(10, 0))

                .add(HolySpells.HEALING_AURA_ID, new PointData(5, 1))

                .add(HolySpells.HYMN_OF_PERSERVANCE, new PointData(10, 2))

                .add(HolySpells.HYMN_OF_VALOR, new PointData(10, 3))

                .add(HolySpells.SHOOTING_STAR, new PointData(6, 4))

                .add(SongSpells.RESONANCE, new PointData(2, 5))
                .add(HolySpells.INSPIRATION, new PointData(7, 5))

                .add(HolySpells.WISH, new PointData(8, 6))

                .build();

        // archer
        // dps, pet
        SchoolBuilder.of("hunter", "Hunter")
                .add(RangerSpells.BARRAGE, new PointData(0, 0))
                .add(RangerSpells.CHARGED_BOLT, new PointData(4, 0))
                .add(RangerSpells.FIRE_TRAP, new PointData(7, 0))

                .add(RangerSpells.DASH_ID, new PointData(1, 1))
                .add(SummonSpells.SUMMON_SPIRIT_WOLF, new PointData(10, 1))

                .add(RangerSpells.QUICKDRAW, new PointData(2, 2))
                .add(RangerSpells.EXPLOSIVE_ARROW_ID, new PointData(5, 2))
                .add(RangerSpells.FROST_TRAP, new PointData(8, 2))

                .add(RangerSpells.ARROW_TOTEM, new PointData(3, 3))
                .add(RangerSpells.RECOIL_SHOT, new PointData(4, 3))
                .add(RangerSpells.HUNTER_POTION, new PointData(10, 3))

                .add(RangerSpells.GALE_WIND, new PointData(6, 4))
                .add(RangerSpells.SMOKE_BOMB, new PointData(9, 4))

                .add(RangerSpells.METEOR_ARROW, new PointData(4, 5))
                .add(RangerSpells.POISON_TRAP, new PointData(10, 5))

                .add(RangerSpells.ARROW_STORM, new PointData(1, 6))

                .build();

        // lightning + totems
        // dps, healer
        SchoolBuilder.of("shaman", "Shaman")
                .add(LightningSpells.LIGHTNING_SPEAR, new PointData(8, 0))
                .add(TotemSpells.HEAL_TOTEM_ID, new PointData(0, 0))

                .add(NatureSpells.NATURE_BALM, new PointData(4, 1))

                .add(LightningSpells.LIGHTNING_NOVA, new PointData(9, 2))
                .add(NatureSpells.ENTANGLE_SEED, new PointData(5, 2))

                .add(LightningSpells.CHAIN_LIGHTNING, new PointData(10, 3))
                .add(TotemSpells.MANA_TOTEM_ID, new PointData(1, 3))

                .add(FireSpells.MAGMA_FLOWER, new PointData(3, 4))
                .add(WaterSpells.FROST_FLOWER, new PointData(4, 4))

                .add(NatureSpells.CHAOS_TOTEM, new PointData(2, 5))

                .build();

        // uh warrior stuff
        // tank dps
        SchoolBuilder.of("warrior", "Warrior")
                .add(HolySpells.GONG_STRIKE_ID, new PointData(0, 0))

                .add(HolySpells.TAUNT, new PointData(1, 1))
                .add(RangerSpells.BOOMERANG, new PointData(10, 1))

                .add(StrSpells.SHRED, new PointData(5, 2))
                .add(HolySpells.PULL, new PointData(3, 2))

                .add(HolySpells.CHARGE_ID, new PointData(4, 3))
                .add(FireSpells.FLAME_STRIKE_ID, new PointData(5, 3))
                .add(WaterSpells.TIDAL_STRIKE, new PointData(6, 3))

                .add(HolySpells.WHIRLWIND, new PointData(1, 4))
                .add(FireSpells.VAMP_BLOOD, new PointData(7, 4))

                .add(DexSpells.EXECUTE, new PointData(2, 5))

                .add(HolySpells.UNDYING_WILL, new PointData(8, 6))

                .build();


    }
}
