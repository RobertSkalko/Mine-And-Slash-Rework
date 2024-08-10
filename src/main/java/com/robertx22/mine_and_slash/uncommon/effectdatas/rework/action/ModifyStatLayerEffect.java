package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.action;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.layers.StatLayer;
import com.robertx22.mine_and_slash.database.data.stats.layers.StatLayerData;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.number_provider.NumberModifier;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.number_provider.NumberProvider;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ModifyStatLayerEffect extends StatEffect {

    String layer = "";
    String number_to_modify = "";
    public ModificationType modification = ModificationType.ADD;

    public NumberProvider number_provider = NumberProvider.ofStatData();
    public List<NumberModifier> number_modifiers = new ArrayList<>();

    // todo finish renaming later.. not now as to not break datapacks
    public ModifyStatLayerEffect(String id, StatLayer layer, String number_to_modify, ModificationType modification, NumberProvider calculation, NumberModifier... numberMod) {
        super(id + "_" + layer.id + "_" + number_to_modify + "_" + modification.id + "_" + calculation.getId(), "modify_stat_layer");
        this.layer = layer.GUID();
        this.number_to_modify = number_to_modify;
        this.modification = modification;
        this.number_provider = calculation;

        for (NumberModifier mod : numberMod) {
            this.number_modifiers.add(mod);
        }
    }

    static String modifiersToId(NumberModifier... mods) {
        String id = "";
        for (NumberModifier mod : mods) {
            id += mod.type.name().toLowerCase(Locale.ROOT);
        }
        return id;
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


    ModifyStatLayerEffect() {
        super("", "modify_stat_layer");
    }

    @Override
    public void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {

        var layerData = event.getLayer(ExileDB.StatLayers().get(layer), number_to_modify, statSource);


        float num = this.number_provider.getValue(event, event.getSide(statSource), data);
        for (NumberModifier mod : this.number_modifiers) {
            num = mod.type.modify(event, num);
        }

        this.modification.apply(layerData, num);

        if (stat.getMultiUseType() == Stat.MultiUseType.MULTIPLICATIVE_DAMAGE) {
            event.addMoreMulti(stat, number_to_modify, data.getMoreStatTypeMulti());
        }

    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return ModifyStatLayerEffect.class;
    }
}
