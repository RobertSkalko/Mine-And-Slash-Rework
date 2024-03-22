package com.robertx22.age_of_exile.prophecy;

import com.robertx22.age_of_exile.database.data.prophecy.ProphecyModifierType;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.world.entity.player.Player;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ProphecyGeneration {


    public static ProphecyData generate(Player p) {


        var pdata = Load.player(p).prophecy;

        int lvl = pdata.getAverageLevel();
        int tier = pdata.getAverageTier();

        GearRarity rar = ExileDB.GearRarities().getFilterWrapped(x -> x.map_tiers.isInRange(tier)).list.get(0);


        int cost = 1000;

        List<ProphecyModifierType> modtypes = Arrays.stream(ProphecyModifierType.values()).toList();

        ProphecyData data = new ProphecyData();
        data.uuid = UUID.randomUUID().toString();

        data.amount = RandomUtils.RandomRange(1, 3);

        cost *= data.amount;

        var start = ExileDB.ProphecyStarts().random();

        data.start = start.GUID();


        for (ProphecyModifierType type : modtypes) {
            if (RandomUtils.roll(type.chanceToSpawn())) {
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
