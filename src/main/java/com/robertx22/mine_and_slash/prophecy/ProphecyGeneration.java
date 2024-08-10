package com.robertx22.mine_and_slash.prophecy;

import com.robertx22.mine_and_slash.database.data.prophecy.ProphecyModifierType;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.world.entity.player.Player;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ProphecyGeneration {


    public static ProphecyData generate(Player p) {

        //  var pdata = Load.player(p).prophecy;

        var map = Load.mapAt(p.level(), p.blockPosition());

        
        int lvl = map.map.lvl;
        int tier = map.map.tier;

        // GearRarity rar = ExileDB.GearRarities().getFilterWrapped(x -> x.map_tiers.isInRange(tier)).list.get(0);

        int cost = 1000;

        List<ProphecyModifierType> modtypes = Arrays.stream(ProphecyModifierType.values()).toList();

        ProphecyData data = new ProphecyData();
        data.uuid = UUID.randomUUID().toString();

        data.amount = RandomUtils.RandomRange(1, 3);

        cost *= data.amount;

        var start = ExileDB.ProphecyStarts().random();

        data.start = start.GUID();

        var b = start.create(lvl, tier);

        for (ProphecyModifierType type : modtypes) {
            if (start.acceptsModifier(type) && type.canApplyTo(start, b) && RandomUtils.roll(type.chanceToSpawn())) {
                var mod = ExileDB.ProphecyModifiers().getFilterWrapped(x -> x.modifier_type == type).of(x -> {
                    if (x.tier_req > tier) {
                        return false;
                    }
                    if (x.lvl_req > lvl) {
                        return false;
                    }
                    return true;
                }).random();
                ProphecyModifierData md = new ProphecyModifierData(mod.GUID());
                data.mods.add(md);
                cost *= mod.cost_multi;
            }
        }

        data.cost = cost;

        return data;


    }
}
