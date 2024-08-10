package com.robertx22.mine_and_slash.uncommon.datasaving;

import com.robertx22.mine_and_slash.saveclasses.unit.Unit;
import net.minecraft.nbt.CompoundTag;

public class UnitNbt {
    private static final String LOC = "unit_object";

    public static Unit Load(CompoundTag nbt) {

        if (nbt == null) {
            return null;
        }

        Unit unit = new Unit();

        try {
            unit.fromNbt(nbt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return unit;

        // return LoadSave.Load(Unit.class, new Unit(), nbt, LOC);

    }

    public static void Save(CompoundTag nbt, Unit gear) {
        if (nbt == null) {
            return;
        }

        try {
            gear.toNbt(nbt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // if (gear != null) {
        //    LoadSave.Save(gear, nbt, LOC);
        //}

    }
}
