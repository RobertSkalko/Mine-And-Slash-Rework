package com.robertx22.mine_and_slash.vanilla_mc.items.gemrunes;

import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.gear_types.bases.SlotFamily;

import java.util.List;

public abstract class GemStatPerTypes {

    public abstract List<StatMod> onArmor();

    public abstract List<StatMod> onJewelry();

    public abstract List<StatMod> onWeapons();

    public final List<StatMod> getFor(SlotFamily sfor) {
        if (sfor == SlotFamily.Armor) {
            return onArmor();
        }
        if (sfor == SlotFamily.Jewelry) {
            return onJewelry();
        }
        if (sfor == SlotFamily.Weapon) {
            return onWeapons();
        }

        return null;

    }

    
}