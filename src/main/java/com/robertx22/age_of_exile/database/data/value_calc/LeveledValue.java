package com.robertx22.age_of_exile.database.data.value_calc;

import net.minecraft.world.entity.LivingEntity;

public class LeveledValue {
    public final float min;
    public final float max;

    public LeveledValue(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public float getValue(LivingEntity en, MaxLevelProvider provider) {
        if (provider == null) {
            return 0;
        }
        if (min == max) {
            return min;
        }

        int maxlevel = provider.getMaxLevelWithBonuses();
        int level = provider.getCurrentLevel(en);

        float perlevel = (max - min) / maxlevel;
        return min + (perlevel * level);
    }

}
