package com.robertx22.age_of_exile.uncommon.datasaving;

import com.robertx22.age_of_exile.saveclasses.CustomExactStatsData;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.nbt.CompoundNBT;

public class CustomExactStats {

    public static final String LOC = "mmorpg:custom_exact_stats_data";

    public static CustomExactStatsData Load(CompoundNBT nbt) {

        if (nbt == null) {
            return null;
        }

        return LoadSave.Load(CustomExactStatsData.class, new CustomExactStatsData(), nbt, LOC);

    }

    public static CompoundNBT Save(CompoundNBT nbt, CustomExactStatsData gear) {

        if (nbt == null) {
            return new CompoundNBT();
        }

        if (gear != null) {
            return LoadSave.Save(gear, nbt, LOC);
        }

        return new CompoundNBT();
    }
}
