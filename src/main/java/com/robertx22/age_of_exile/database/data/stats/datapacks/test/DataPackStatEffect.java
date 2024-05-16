package com.robertx22.age_of_exile.database.data.stats.datapacks.test;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.priority.StatPriority;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.action.StatEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition.StatCondition;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;

public class DataPackStatEffect implements IStatEffect {

    public String order = "";

    public EffectSides side = EffectSides.Source;

    public List<String> ifs = new ArrayList<>();

    public List<String> effects = new ArrayList<>();

    public List<String> events = new ArrayList<>();

    public boolean worksOnEvent(EffectEvent ev) {
        return events.contains(ev.GUID());
    }

    @Override
    public EffectSides Side() {
        return side;
    }


    public List<MutableComponent> getTooltip() {
        List<MutableComponent> t = new ArrayList<>();


        var prio = GetPriority();

        t.add(prio.locName().append(" (" + +prio.priority + ")"));

        t.add(side.word.locName());

        t.add(Words.WorksOnEvent.locName());
        for (String event : events) {
            t.add(Component.literal(event));
        }

        t.add(Words.Conditions.locName());
        for (String i : ifs) {
            t.add(Component.literal("- " + i)); // todo localize all
        }
        t.add(Words.Effects.locName());
        for (String i : effects) {
            t.add(Component.literal("- " + i));// todo localize all
        }

        return t;
    }

    @Override
    public StatPriority GetPriority() {
        var p = StatPriority.MAP.get(order);
        if (p == null) {
            // todo find better error way
            System.out.println("No such stat priority: " + order);
            return StatPriority.Spell.FIRST;
        }
        return p;
    }

    @Override
    public void TryModifyEffect(EffectEvent effect, EffectSides statSource, StatData data, Stat stat) {

        if (ifs.stream()
                .allMatch(x -> {
                    StatCondition cond = ExileDB.StatConditions().get(x);
                    if (cond == null) {
                        return false;
                    }
                    Boolean istrue = cond.can(effect, statSource, data, stat) == cond.getConditionBoolean();
                    return istrue;
                })) {

            effects.forEach(x -> {
                StatEffect e = ExileDB.StatEffects().get(x);
                if (e == null) {
                    return;
                }
                e.activate(effect, statSource, data, stat);
            });

        }


    }
}
