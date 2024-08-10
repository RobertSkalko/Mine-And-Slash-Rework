package com.robertx22.mine_and_slash.database.data.aura;

import com.robertx22.mine_and_slash.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.stats.ResourceStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.base.LeechInfo;
import com.robertx22.mine_and_slash.aoe_data.database.stats.base.ResourceAndAttack;
import com.robertx22.mine_and_slash.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.stats.effects.defense.MaxElementalResist;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.BlockChance;
import com.robertx22.mine_and_slash.saveclasses.unit.ResourceType;
import com.robertx22.mine_and_slash.uncommon.enumclasses.AttackType;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;

public class WatcherEyeAffixes {

    public static void init() {

        of(AuraGems.COLD_RESIST, new MaxElementalResist(Elements.Cold).mod(1, 3));
        of(AuraGems.FIRE_RESIST, new MaxElementalResist(Elements.Fire).mod(1, 3));
        of(AuraGems.LIGHTNING_RESIST, new MaxElementalResist(Elements.Nature).mod(1, 3));
        of(AuraGems.CHAOS_RESIST, new MaxElementalResist(Elements.Shadow).mod(1, 3));

        of(AuraGems.cold_damage, ResourceStats.ELEMENT_LEECH_RESOURCE.get(new LeechInfo(Elements.Cold, ResourceType.health)).mod(1, 3));
        of(AuraGems.fire_damage, ResourceStats.ELEMENT_LEECH_RESOURCE.get(new LeechInfo(Elements.Fire, ResourceType.health)).mod(1, 3));
        of(AuraGems.lightning_damage, ResourceStats.ELEMENT_LEECH_RESOURCE.get(new LeechInfo(Elements.Nature, ResourceType.health)).mod(1, 3));
        of(AuraGems.chaos_damage, ResourceStats.ELEMENT_LEECH_RESOURCE.get(new LeechInfo(Elements.Shadow, ResourceType.health)).mod(1, 3));
        of(AuraGems.physical_damage, ResourceStats.ELEMENT_LEECH_RESOURCE.get(new LeechInfo(Elements.Physical, ResourceType.health)).mod(1, 3));

        of(AuraGems.ARMOR, BlockChance.getInstance().mod(3, 5));
        of(AuraGems.DODGE, DatapackStats.MOVE_SPEED.mod(3, 5));
        of(AuraGems.MAGIC_SHIELD, DatapackStats.MANA_REG_PER_500_MS.mod(1, 3));

        of(AuraGems.mana_reg, DatapackStats.MS_PER_10_MANA.mod(1, 3));
        of(AuraGems.energy_reg, ResourceStats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.energy, AttackType.hit)).mod(1, 3));
        of(AuraGems.health_reg, ResourceStats.LEECH_CAP.get(ResourceType.health).mod(1, 3));
        of(AuraGems.magic_shield_reg, ResourceStats.RESOURCE_ON_KILL.get(ResourceType.magic_shield).mod(10, 30));

        of(AuraGems.summon_dmg, DatapackStats.ENERGY_PER_10_MANA.mod(3, 5));

    }

    static void of(AuraGems.AuraInfo aura, StatMod... stats) {

        AffixBuilder.Normal(aura.id + "_eye")
                .Named("Unused")
                .stats(stats)
                .WatchersEye()
                .AuraReq(aura.id)
                .Build();

    }
}
