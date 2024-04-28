package com.robertx22.age_of_exile.uncommon.effectdatas.rework.action;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.layers.EffectiveStats;
import com.robertx22.age_of_exile.database.data.stats.layers.StatLayer;
import com.robertx22.age_of_exile.database.data.stats.layers.StatLayerData;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class ModifyStatLayerEffect extends StatEffect {

    String layer = "";
    String number_to_modify = "";
    public ModificationType modification = ModificationType.ADD;
    public CalculationType calculation = CalculationType.JUST_STAT_NUMBER;

    public ModifyStatLayerEffect(StatLayer layer, String number_to_modify, ModificationType modification, CalculationType calculation) {
        super(layer.id + "_" + number_to_modify + "_" + modification.id + "_" + calculation.id, "modify_stat_layer");
        this.layer = layer.GUID();
        this.number_to_modify = number_to_modify;
        this.modification = modification;
        this.calculation = calculation;
    }

    public enum ModificationType {
      
        ADD("add") {
            @Override
            public void apply(StatLayerData data, float num) {
                data.add(num);
            }
        },

        REDUCE("reduce") {
            @Override
            public void apply(StatLayerData data, float num) {
                data.reduce(num);
            }
        };

        public String id;

        public abstract void apply(StatLayerData data, float num);

        ModificationType(String id) {
            this.id = id;
        }
    }

    public enum CalculationType {
        JUST_STAT_NUMBER("just_stat_number") {
            @Override
            public float getCalcValue(EffectEvent event, StatData data, EffectSides side) {
                return data.getValue();
            }
        },

        EFFECTIVE_ARMOR("effective_armor") {
            @Override
            public float getCalcValue(EffectEvent event, StatData data, EffectSides side) {
                return EffectiveStats.ARMOR.getUsableValue((int) data.getValue(), event.targetData.getLevel());
            }
        },
        EFFECTIVE_DODGE("effective_dodge") {
            @Override
            public float getCalcValue(EffectEvent event, StatData data, EffectSides side) {
                return EffectiveStats.DODGE.getUsableValue((int) data.getValue(), event.targetData.getLevel());
            }
        };

        public String id;

        CalculationType(String id) {
            this.id = id;
        }

        public abstract float getCalcValue(EffectEvent event, StatData data, EffectSides side);

    }


    ModifyStatLayerEffect() {
        super("", "modify_stat_layer");
    }

    @Override
    public void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {

        var layerData = event.getLayer(ExileDB.StatLayers().get(layer), number_to_modify);
        float num = this.calculation.getCalcValue(event, data, statSource);
        this.modification.apply(layerData, num);

        if (stat.getMultiUseType() == Stat.MultiUseType.MULTIPLICATIVE_DAMAGE) {
            event.addMoreMulti(number_to_modify, data.getMoreStatTypeMulti());
        }

    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return ModifyStatLayerEffect.class;
    }
}
