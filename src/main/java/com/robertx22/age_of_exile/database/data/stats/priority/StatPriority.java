package com.robertx22.age_of_exile.database.data.stats.priority;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// todo use this for every stat, and make sure each is used for its own event.s
// should it be a registry or just like the tags

public class StatPriority implements IAutoLocName {

    public static List<StatPriority> ALL = new ArrayList<>();

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
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.StatPriority;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + "stat_priority." + id;
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

        StatPriority DAMAGE_TRANSFER = damage("DAMAGE_TRANSFER", 0);
        StatPriority DAMAGE_ADDITION = damage("DAMAGE_ADDITION", 10);
        StatPriority HIT_PREVENTION = damage("HIT_PREVENTION", 20);
        StatPriority DAMAGE_BONUSES = damage("DAMAGE_BONUSES", 30);
        StatPriority AFTER_DAMAGE_BONUSES = damage("AFTER_DAMAGE_BONUSES", 31); // for stuff like leech
        StatPriority DAMAGE_TAKEN_AS = damage("DAMAGE_TAKEN_AS", 40);
        StatPriority DAMAGE_REDUCTION = damage("DAMAGE_REDUCTION", 50);
        StatPriority DAMAGE_ABSORPTION = damage("DAMAGE_ABSORPTION", 60);

    }
}
