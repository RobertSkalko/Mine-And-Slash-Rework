package com.robertx22.mine_and_slash.database.data.value_calc;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.WeaponDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.health.Health;

public class ValueCalcBuilder {
    ValueCalculation calc;

    public static ValueCalcBuilder of(String id) {
        ValueCalcBuilder b = new ValueCalcBuilder();
        b.calc = new ValueCalculation();
        b.calc.id = id;
        return b;
    }

    private ValueCalcBuilder baseValue(float min, float max) {
        calc.base = new LeveledValue(min, max);
        return this;
    }

    private ValueCalcBuilder defaultBaseValue(float v1, float v2) {
        float min = 2 * v1;
        float max = 6 * v2;
        return this.baseValue(min, max);
    }

    public ValueCalcBuilder attackScaling(float min, float max) {
        this.calc.dmg_effectiveness = new ScalingCalc(Health.getInstance(), new LeveledValue(min, max));


        defaultBaseValue(min, max);
        return statScaling(WeaponDamage.getInstance(), min, max);

    }

    public ValueCalcBuilder capScaling(float min) {
        this.calc.cap_to_wep_dmg = min;
        this.calc.dmg_effectiveness.multi.max += (min / 2F);
        this.calc.dmg_effectiveness.multi.min += (min / 2F);
        return this;
    }

    public ValueCalcBuilder spellScaling(float min, float max) {
        this.calc.dmg_effectiveness = new ScalingCalc(Health.getInstance(), new LeveledValue(min, max));

        defaultBaseValue(min, max);
        return statScaling(WeaponDamage.getInstance(), min, max);

    }

    public ValueCalcBuilder statScaling(Stat stat, float min, float max) {
        calc.stat_scalings.add(new ScalingCalc(stat, new LeveledValue(min, max)));
        return this;
    }

    public ValueCalculation build() {
        calc.addToSerializables();
        return calc;
    }
}
