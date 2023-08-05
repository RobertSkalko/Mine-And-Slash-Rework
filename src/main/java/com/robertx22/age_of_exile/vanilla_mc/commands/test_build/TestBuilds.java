package com.robertx22.age_of_exile.vanilla_mc.commands.test_build;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.HashMap;

public class TestBuilds {

    public static HashMap<EquipmentSlot, BaseGearType> getGearsFor(BaseGearType.SlotTag tag, Player player) {

        int lvl = Load.Unit(player)
            .getLevel();

        HashMap<EquipmentSlot, BaseGearType> map = new HashMap<>();

        ExileDB.GearTypes()
            .getList()
            .forEach(x -> {
                if (x.getLevelRange()
                    .isLevelInRange(lvl)) {
                    if (x.getTags()
                        .contains(tag)) {
                        map.put(x.getVanillaSlotType(), x);
                    }
                }
            });

        return map;

    }

}
