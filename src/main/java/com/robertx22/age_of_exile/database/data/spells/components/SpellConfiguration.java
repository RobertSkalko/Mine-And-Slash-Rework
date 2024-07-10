package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.aoe_data.database.spells.SummonType;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.PetSpells;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.data.value_calc.LeveledValue;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.tags.TagList;
import com.robertx22.age_of_exile.tags.all.SpellTags;
import com.robertx22.age_of_exile.tags.imp.SpellTag;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;

public class SpellConfiguration {

    public boolean swing_arm = true;
    public CastingWeapon castingWeapon = CastingWeapon.ANY_WEAPON;
    public LeveledValue mana_cost = new LeveledValue(0, 0);
    public LeveledValue ene_cost = new LeveledValue(0, 0);
    public int times_to_cast = 1;
    public int charges = 0;
    public int charge_regen = 0;
    public int aggro_radius = 10;
    public int imbues = 0;
    public SummonType summonType = SummonType.NONE;
    public String charge_name = "";
    public String summon_basic_atk = PetSpells.PET_BASIC;
    private int cast_time_ticks = 0;
    public int cooldown_ticks = 20;
    private String style = PlayStyle.STR.id;
    public TagList<SpellTag> tags = new TagList<>();
    public int tracking_radius = 5;
    public AllyOrEnemy tracks = AllyOrEnemy.enemies;

  
    public int getCastTimeTicks() {
        return cast_time_ticks;
    }

    public SpellConfiguration applyCastSpeedToCooldown() {
        this.tags.add(SpellTags.CAST_TO_CD);
        return this;
    }

    public PlayStyle getStyle() {
        return PlayStyle.fromID(style);
    }


    public SpellConfiguration setStyle(PlayStyle s) {
        this.style = s.id;
        return this;
    }

    public boolean hasSummonBasicAttack() {
        return !summon_basic_atk.isEmpty();
    }

    public Spell getSummonBasicSpell() {
        return ExileDB.Spells().get(summon_basic_atk);
    }

    public boolean usesCharges() {
        return charges > 0;
    }

    public SpellConfiguration setChargesAndRegen(String name, int charges, int ticksToRegen) {
        this.charge_regen = ticksToRegen;
        this.charges = charges;
        this.charge_name = name;


        if (ticksToRegen > (20 * 30)) {
            this.cooldown_ticks = 20; // we force the cooldown to be the same for all spells with charges so it feels consistent and good
        } else {
            this.cooldown_ticks = 10; // we force the cooldown to be the same for all spells with charges so it feels consistent and good
        }


        return this;
    }


    public boolean isProjectile() {
        return tags.contains(SpellTags.projectile);
    }

    public SpellConfiguration setSwingArm() {
        this.swing_arm = true;
        return this;
    }

    public SpellConfiguration setImbue(int times) {
        this.imbues = times;
        return this;
    }

    public SpellConfiguration setTrackingRadius(int rad) {
        this.tracking_radius = rad;
        return this;
    }

    public SpellConfiguration setSummonBasicAttack(String s) {
        this.summon_basic_atk = s;
        return this;
    }

    public SpellConfiguration setSummonType(SummonType type) {
        this.summonType = type;
        return this;
    }


    public static class Builder {
        public static SpellConfiguration energy(int ene, int cd) {
            SpellConfiguration c = new SpellConfiguration();
            c.cast_time_ticks = 0;
            c.ene_cost = new LeveledValue(1F * ene, 0.75F * ene);
            c.cooldown_ticks = cd;
            return c;
        }

        public static SpellConfiguration instant(int mana, int cd) {
            SpellConfiguration c = new SpellConfiguration();
            c.cast_time_ticks = 0;
            c.mana_cost = new LeveledValue(1F * mana, 0.75F * mana);
            c.cooldown_ticks = cd;
            return c;
        }

        public static SpellConfiguration arrowSpell(int mana, int cd) {
            SpellConfiguration c = new SpellConfiguration();
            c.cast_time_ticks = 0;
            c.mana_cost = new LeveledValue(1F * mana, 0.75F * mana);
            c.cooldown_ticks = cd;
            c.swing_arm = false;
            return c;
        }

        public static SpellConfiguration nonInstant(int mana, int cd, int casttime) {
            SpellConfiguration c = new SpellConfiguration();
            c.cast_time_ticks = casttime;
            c.mana_cost = new LeveledValue(1F * mana, 0.75F * mana);
            c.cooldown_ticks = cd;
            return c;
        }

        public static SpellConfiguration multiCast(int mana, int cd, int casttime, int times) {
            SpellConfiguration c = new SpellConfiguration();
            c.times_to_cast = times;
            c.cast_time_ticks = casttime;
            c.mana_cost = new LeveledValue(1F * mana, 0.75F * mana);
            c.cooldown_ticks = cd;
            return c;
        }

    }
}
