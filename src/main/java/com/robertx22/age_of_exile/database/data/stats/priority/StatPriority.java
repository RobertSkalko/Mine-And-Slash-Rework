package com.robertx22.age_of_exile.database.data.stats.priority;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;

import java.util.HashMap;
import java.util.Locale;

// todo use this for every stat, and make sure each is used for its own event.s
// should it be a registry or just like the tags

public class StatPriority implements IAutoLocName {

    // todo does this need to be a datapack
    public static HashMap<String, StatPriority> MAP = new HashMap<>();


    private static StatPriority damage(String id, int priority) {
        return new StatPriority(id, DamageEvent.ID, priority);
    }

    public int priority;
    String id;
    public String event;

    public StatPriority(String id, String event, int priority) {
        this.id = id.toLowerCase(Locale.ROOT);
        this.event = event;
        this.priority = priority;

        MAP.put(this.id, this);
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.StatPriority;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".stat_priority." + id;
    }

    @Override
    public String locNameForLangFile() {
        return id.toUpperCase(Locale.ROOT);
    }

    @Override
    public String GUID() {
        return id;
    }

    public interface Damage {

        // todo dmg increases are based on original number, which mess with conversion..
        // they should be based on saved value at point of dmg bonus event start

        //StatPriority DAMAGE_TRANSFER = damage("DAMAGE_TRANSFER", 0);
        StatPriority FIRST = damage("FIRST", 0);
        StatPriority BEFORE_HIT_PREVENTION = damage("BEFORE_HIT_PREVENTION", 9); // for stuff like accuracy
        StatPriority HIT_PREVENTION = damage("HIT_PREVENTION", 10); // dodge, block
        StatPriority BEFORE_DAMAGE_LAYERS = damage("BEFORE_DAMAGE_LAYERS", 19); // currently only used for old dmg stats that arent converted to datapacks yet
        StatPriority DAMAGE_LAYERS = damage("DAMAGE_LAYERS", 20); // this one contains a bunch of its own ordered multipliers
        StatPriority AFTER_DAMAGE_LAYERS = damage("DAMAGE_LAYERS", 21); // this one contains a bunch of its own ordered multipliers
        StatPriority CALC_DAMAGE_LAYERS = damage("CALC_DAMAGE_LAYERS", 30); // this should only be used by the custom stat that exists to fit right here at this place in the order of effect calc
        StatPriority AFTER_DAMAGE_BONUSES = damage("AFTER_DAMAGE_BONUSES", 32); // for stuff like leech, because we don't want absorbs, mana shields etc to stop leech
        // todo not using this yet  StatPriority DAMAGE_TAKEN_AS = damage("DAMAGE_TAKEN_AS", 40);
        // todo should this be the same as dmg layer StatPriority DAMAGE_REDUCTION = damage("DAMAGE_REDUCTION", 50);
        //this row must have a custom priority for each type of damage absorb so it's never random!!

        // todo i dont think this works
        // StatPriority DAMAGE_ABSORBED_BY_SHIELD = damage("DAMAGE_ABSORBED_BY_SHIELD", 60);
        // StatPriority DAMAGE_ABSORBED_BY_MANA = damage("DAMAGE_ABSORBED_BY_MANA", 61);
        // todo this is currently not a stat effect, why?  StatPriority DAMAGE_ABSORBED_BY_MAGIC_SHIELD = damage("DAMAGE_ABSORBED_BY_MAGIC_SHIELD", 62);
        StatPriority FINAL_DAMAGE = damage("FINAL_DAMAGE", 100);

        public static void init() {

        }
    }

    public interface Spell {
        StatPriority FIRST = damage("DATA_MODIFICATION", 0);

        public static void init() {

        }
    }


    public static void init() {
        Damage.init();
        Spell.init();
    }
}
