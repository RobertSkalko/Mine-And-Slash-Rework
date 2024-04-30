package com.robertx22.age_of_exile.aoe_data.database.stats.base;

// add to serializables and create accesor maps

// .withAccessor<Elements>(
// then either. .generateGenericWith(Elemenets.values)   or .addSpecific(Elements.fire)
// T can be a wrapper class with multiple enums!

import com.robertx22.age_of_exile.database.data.stats.datapacks.test.DataPackStatAccessor;
import com.robertx22.age_of_exile.database.data.stats.datapacks.test.DataPackStatEffect;
import com.robertx22.age_of_exile.database.data.stats.datapacks.test.DatapackStat;
import com.robertx22.age_of_exile.database.data.stats.priority.StatPriority;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.action.StatEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition.StatCondition;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ErrorUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class DatapackStatBuilder<T> {

    private DataPackStatAccessor<T> accessor;

    private HashMap<T, DatapackStat> stats = new HashMap<>();

    private HashMap<T, Consumer<DatapackStat>> modify = new HashMap<>();

    private Function<T, String> idMaker;
    private Function<T, String> locNameMaker;
    private Function<T, String> locDescMaker;
    private Function<T, Elements> elementMaker;


    public DatapackStatBuilder() {

        for (EffectPlace value : EffectPlace.values()) {
            EFFECTS.put(value, new EffectMaker());
        }
    }


    public static enum EffectPlace {
        FIRST, SECOND;
    }

    public HashMap<EffectPlace, EffectMaker<T>> EFFECTS = new HashMap<>();

    public static class EffectMaker<T> {
        private List<Function<T, StatEffect>> effectMaker = new ArrayList<>();
        private List<Function<T, StatCondition>> conditionMaker = new ArrayList<>();

        private List<StatCondition> conditions = new ArrayList<>();
        private List<StatEffect> effects = new ArrayList<>();

        public boolean isEmpty() {
            return effectMaker.isEmpty() && effects.isEmpty();
        }
    }

    private Consumer<DatapackStat> modifyAfterDone;

    public boolean usesMoreMulti = false;


    private String priority = "";
    private EffectSides side = EffectSides.Source;

    private List<String> events = new ArrayList<>();

    public static DatapackStatBuilder<EmptyAccessor> ofSingle(String id, Elements ele) {
        DatapackStatBuilder<EmptyAccessor> b = new DatapackStatBuilder<EmptyAccessor>();
        b.accessor = new DataPackStatAccessor<EmptyAccessor>();
        b.idMaker = x -> id;
        b.elementMaker = x -> ele;
        b.addSpecificType(EmptyAccessor.INSTANCE);
        return b;
    }

    public static <T> DatapackStatBuilder<T> of(Function<T, String> id, Function<T, Elements> ele) {
        DatapackStatBuilder<T> b = new DatapackStatBuilder<T>();
        b.accessor = new DataPackStatAccessor<T>();
        b.idMaker = id;
        b.elementMaker = ele;
        return b;
    }

    public DatapackStatBuilder<T> addAllOfType(List<T> list) {
        list.forEach(x -> addSpecificType(x));
        return this;
    }

    public DatapackStatBuilder<T> addAllOfType(T[] list) {
        for (T t : list) {
            addSpecificType(t);
        }
        return this;
    }

    public DatapackStatBuilder<T> addSpecificType(T t) {
        add(t);
        return this;
    }

    public DatapackStatBuilder<T> modifyAfterDone(Consumer<DatapackStat> modifyAfterDone) {
        this.modifyAfterDone = modifyAfterDone;
        return this;
    }

    private void add(T t) {
        this.stats.put(t, new DatapackStat());
    }

    public DatapackStatBuilder<T> addCondition(StatCondition condition) {
        return addCondition(EffectPlace.FIRST, condition);
    }

    public DatapackStatBuilder<T> addCondition(EffectPlace place, StatCondition condition) {
        Objects.requireNonNull(condition);
        EFFECTS.get(place).conditions.add(condition);
        return this;
    }

    public DatapackStatBuilder<T> addCondition(Function<T, StatCondition> condition) {
        Objects.requireNonNull(condition);
        EFFECTS.get(EffectPlace.FIRST).conditionMaker.add(condition);
        return this;
    }


    public DatapackStatBuilder<T> addEffect(StatEffect effect) {
        return addEffect(EffectPlace.FIRST, effect);
    }

    public DatapackStatBuilder<T> addEffect(EffectPlace place, StatEffect effect) {
        Objects.requireNonNull(effect);
        EFFECTS.get(place).effects.add(effect);
        return this;
    }

    public DatapackStatBuilder<T> addEffect(Function<T, StatEffect> effect) {
        Objects.requireNonNull(effect);
        EFFECTS.get(EffectPlace.FIRST).effectMaker.add(effect);
        return this;
    }

    public DatapackStatBuilder<T> worksWithEvent(String event) {
        this.events.add(event);
        return this;
    }

    public DatapackStatBuilder<T> setUsesMoreMultiplier() {
        this.usesMoreMulti = true;
        return this;
    }

    public DatapackStatBuilder<T> setPriority(StatPriority priority) {
        this.priority = priority.GUID();
        return this;
    }

    public DatapackStatBuilder<T> setSide(EffectSides side) {
        this.side = side;
        return this;
    }

    public DatapackStatBuilder<T> setLocName(Function<T, String> id) {
        this.locNameMaker = id;
        return this;
    }

    public DatapackStatBuilder<T> setLocDesc(Function<T, String> id) {
        this.locDescMaker = id;
        return this;
    }

    public static Set<DatapackStat> STATS_TO_ADD_TO_SERIALIZATION = new HashSet<>();

    public DataPackStatAccessor<T> build() {

        Objects.requireNonNull(idMaker);
        Objects.requireNonNull(elementMaker);
        Objects.requireNonNull(locNameMaker);
        Objects.requireNonNull(locDescMaker);

        ErrorUtils.ifFalse(!stats.isEmpty());
        //ErrorUtils.ifFalse(!events.isEmpty());

        stats.entrySet()
                .forEach(x -> {

                    DatapackStat stat = x.getValue();
                    stat.id = idMaker.apply(x.getKey());
                    stat.ele = elementMaker.apply(x.getKey());
                    stat.locdesc = locDescMaker.apply(x.getKey());
                    stat.locname = locNameMaker.apply(x.getKey());

                    if (usesMoreMulti) {
                        stat.setUsesMoreMultiplier();
                    }
                    if (modify.containsKey(x.getKey())) {
                        modify.get(x.getKey())
                                .accept(stat);
                    }
                    if (modifyAfterDone != null) {
                        modifyAfterDone.accept(stat);
                    }

                    for (Map.Entry<EffectPlace, EffectMaker<T>> en : EFFECTS.entrySet()) {
                        var effectMaker = en.getValue();

                        if (effectMaker.isEmpty()) {
                            continue;
                        }
                        
                        DataPackStatEffect dataEffect = new DataPackStatEffect();
                        dataEffect.order = priority;
                        dataEffect.events = events;
                        dataEffect.side = side;

                        effectMaker.conditions.forEach(c -> dataEffect.ifs.add(c.GUID()));
                        effectMaker.effects.forEach(c -> {
                            dataEffect.effects.add(c.GUID());
                        });


                        if (effectMaker.effectMaker != null) {
                            T key = x.getKey();

                            for (Function<T, StatEffect> maker : effectMaker.effectMaker) {
                                StatEffect effect = maker.apply(key);
                                if (effect == null) {
                                    System.out.print("Can't make effect for key: " + key.toString());
                                }
                                dataEffect.effects.add(effect.GUID());
                            }
                        }

                        if (effectMaker.conditionMaker != null) {
                            for (Function<T, StatCondition> maker : effectMaker.conditionMaker) {
                                dataEffect.ifs.add(maker.apply(x.getKey()).GUID());
                            }
                        }

                        stat.effect.add(dataEffect);
                    }

                    accessor.add(x.getKey(), stat);

                    STATS_TO_ADD_TO_SERIALIZATION.add(stat);
                });

        return this.accessor;
    }

}
