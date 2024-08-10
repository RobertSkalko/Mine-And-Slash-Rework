package com.robertx22.mine_and_slash.aoe_data.database.unique_gears;

import com.robertx22.mine_and_slash.aoe_data.database.base_gear_types.BaseGearTypes;
import com.robertx22.mine_and_slash.aoe_data.database.unique_gears.uniques.ProphecyUniques;
import com.robertx22.mine_and_slash.aoe_data.database.unique_gears.uniques.armor.BootsUniques;
import com.robertx22.mine_and_slash.aoe_data.database.unique_gears.uniques.armor.ChestUniques;
import com.robertx22.mine_and_slash.aoe_data.database.unique_gears.uniques.armor.HelmetUniques;
import com.robertx22.mine_and_slash.aoe_data.database.unique_gears.uniques.armor.PantsUniques;
import com.robertx22.mine_and_slash.aoe_data.database.unique_gears.uniques.jewelry.UniqueNecklaces;
import com.robertx22.mine_and_slash.aoe_data.database.unique_gears.uniques.jewelry.UniqueRings;
import com.robertx22.mine_and_slash.aoe_data.database.unique_gears.uniques.offhand.ShieldUniques;
import com.robertx22.mine_and_slash.aoe_data.database.unique_gears.uniques.weapon.BowUniques;
import com.robertx22.mine_and_slash.aoe_data.database.unique_gears.uniques.weapon.StaffUniques;
import com.robertx22.mine_and_slash.aoe_data.database.unique_gears.uniques.weapon.SwordUniques;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.stats.types.gear_base.GearDamage;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class UniqueGearReg implements ExileRegistryInit {

    public static String EMPTY = "empty";

    @Override
    public void registerAll() {
        UniqueGearBuilder.of(EMPTY, "Empty/Invalid Unique", BaseGearTypes.SWORD)
                .setReplacesName()
                .weight(0)
                .stats(Arrays.asList(
                        new StatMod(1, 1, GearDamage.getInstance(), ModType.PERCENT)
                ))
                .build();

        new HelmetUniques().registerAll();
        new ChestUniques().registerAll();
        new PantsUniques().registerAll();
        new BootsUniques().registerAll();

        new ShieldUniques().registerAll();

        new UniqueRings().registerAll();
        new UniqueNecklaces().registerAll();

        new StaffUniques().registerAll();
        new BowUniques().registerAll();
        new SwordUniques().registerAll();


        new ProphecyUniques().registerAll();

    }
}
