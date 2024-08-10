package com.robertx22.mine_and_slash.uncommon.error_checks;

import com.robertx22.mine_and_slash.aoe_data.database.affixes.Prefixes;
import com.robertx22.mine_and_slash.aoe_data.database.affixes.Suffixes;
import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.database.data.gear_types.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.requirements.bases.GearRequestedFor;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.uncommon.error_checks.base.IErrorCheck;

public class AllGearsHavePossibleAffixCheck implements IErrorCheck {

    public void check() {

        for (BaseGearType slot : ExileDB.GearTypes()
                .getAll()
                .values()) {

            Affix prefix = Prefixes.INSTANCE.random(new GearRequestedFor(slot));
            Affix suffix = Suffixes.INSTANCE.random(new GearRequestedFor(slot));

            if (prefix == null) {
                throw new RuntimeException(slot.GUID() + " has no possible prefix!");
            }
            if (suffix == null) {
                throw new RuntimeException(slot.GUID() + " has no possible suffix!");
            }

        }

    }

}
