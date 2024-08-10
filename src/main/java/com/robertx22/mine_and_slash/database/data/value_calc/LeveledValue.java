package com.robertx22.mine_and_slash.database.data.value_calc;

import net.minecraft.world.entity.LivingEntity;

public class LeveledValue {
    public float min;
    public float max;

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
