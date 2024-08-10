package com.robertx22.mine_and_slash.aoe_data.database.stats.base;

import com.robertx22.mine_and_slash.saveclasses.unit.ResourceType;
import com.robertx22.mine_and_slash.uncommon.enumclasses.AttackType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResourceAndAttack extends AutoHashClass {


    public ResourceType resource;
    public AttackType attackType;

    public ResourceAndAttack(ResourceType resource, AttackType attackType) {
        this.resource = resource;
        this.attackType = attackType;

    }

    @Override
    public int hashCode() {
        return Objects.hash(resource, attackType);
    }

    public static List<ResourceAndAttack> allCombos() {
        List<ResourceAndAttack> list = new ArrayList<>();

        for (AttackType type : AttackType.getAllUsed()) {
            list.add(new ResourceAndAttack(ResourceType.magic_shield, type));
            list.add(new ResourceAndAttack(ResourceType.health, type));
            list.add(new ResourceAndAttack(ResourceType.mana, type));
            list.add(new ResourceAndAttack(ResourceType.energy, type));
        }
        return list;

    }

    // this produces "on_hit_hit"
    @Override
    public String GUID() {
        return resource.id + "_on_" + attackType.id + "_hit";
    }
}