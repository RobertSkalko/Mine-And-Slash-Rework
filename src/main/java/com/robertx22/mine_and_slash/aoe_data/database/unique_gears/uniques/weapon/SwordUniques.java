package com.robertx22.mine_and_slash.aoe_data.database.unique_gears.uniques.weapon;

import com.robertx22.mine_and_slash.aoe_data.database.base_gear_types.BaseGearTypes;
import com.robertx22.mine_and_slash.aoe_data.database.stats.ResourceStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.base.ResourceAndAttack;
import com.robertx22.mine_and_slash.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.stats.types.gear_base.GearDamage;
import com.robertx22.mine_and_slash.saveclasses.unit.ResourceType;
import com.robertx22.mine_and_slash.uncommon.enumclasses.AttackType;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class SwordUniques implements ExileRegistryInit {
    
    @Override
    public void registerAll() {

        UniqueGearBuilder.of("omni_vamp", "Omnidirectional Vampire", BaseGearTypes.SWORD)
                .setReplacesName()
                .stats(Arrays.asList(
                        new StatMod(25, 150, GearDamage.getInstance(), ModType.PERCENT),
                        new StatMod(3, 3, ResourceStats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.energy, AttackType.hit)), ModType.FLAT),
                        new StatMod(3, 3, ResourceStats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.mana, AttackType.hit)), ModType.FLAT),
                        new StatMod(3, 3, ResourceStats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.health, AttackType.hit)), ModType.FLAT)
                ))
                .build();
    }
}
