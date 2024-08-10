package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.number_provider;

import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import net.minecraft.world.entity.LivingEntity;

import java.util.Locale;

public class NumberProvider {

    public NumberProvider() {
    }

    public static NumberProvider specificNumber(int num) {
        NumberProvider p = new NumberProvider();
        p.calc = num + "";
        p.type = Type.SPECIFIC_NUMBER;
        return p;
    }

    public static NumberProvider ofPercentOfStat(String stat) {
        NumberProvider p = new NumberProvider();
        p.calc = stat;
        p.type = Type.STAT_PERCENT;
        return p;
    }

    public static NumberProvider ofPercentOfDataNumber(String numId) {
        NumberProvider p = new NumberProvider();
        p.calc = numId;
        p.type = Type.NUMBER_PERCENT;
        return p;
    }

    public static NumberProvider ofStatData() {
        NumberProvider p = new NumberProvider();
        p.type = Type.STAT_DATA;
        return p;
    }

    private Type type = Type.STAT_DATA;
    private String calc = "";

    public String getId() {
        return type.name().toLowerCase(Locale.ROOT);
    }

    public float getValue(EffectEvent event, LivingEntity source, StatData data) {
        float num = type.getValue(event, source, data, calc);
        return num;
    }

    public enum Type {
        SPECIFIC_NUMBER() {
            @Override
            public float getValue(EffectEvent event, LivingEntity source, StatData data, String calc) {
                int num = Integer.valueOf(calc);
                return num;
            }
        },
        STAT_DATA() {
            @Override
            public float getValue(EffectEvent event, LivingEntity source, StatData data, String calc) {
                return data.getValue();
            }
        }, STAT_PERCENT() {
            @Override
            public float getValue(EffectEvent event, LivingEntity source, StatData data, String calc) {
                float val = Load.Unit(source)
                        .getUnit()
                        .getCalculatedStat(calc)
                        .getValue() * data.getValue() / 100F;
                return val;
            }
        }, NUMBER_PERCENT() {
            @Override
            public float getValue(EffectEvent event, LivingEntity source, StatData data, String calc) {
                float val = event.data.getNumber(calc).number * data.getValue() / 100F;
                return val;
            }
        };

        public abstract float getValue(EffectEvent event, LivingEntity source, StatData data, String calc);
    }

}
