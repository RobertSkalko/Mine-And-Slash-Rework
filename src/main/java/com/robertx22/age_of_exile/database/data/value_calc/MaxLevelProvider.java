package com.robertx22.age_of_exile.database.data.value_calc;

import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.library_of_exile.registry.IGUID;
import net.minecraft.world.entity.LivingEntity;

public interface MaxLevelProvider extends IGUID {

    static MaxLevelProvider get(String id) {

        if (ExileDB.Spells()
                .isRegistered(id)) {
            return ExileDB.Spells()
                    .get(id);
        }

        return null;
    }

    int getCurrentLevel(LivingEntity en);
    
    int getMaxLevel();

    int getMaxLevelWithBonuses();
}
