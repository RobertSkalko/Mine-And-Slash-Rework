package com.robertx22.age_of_exile.prophecy;

import com.robertx22.age_of_exile.database.data.prophecy.ProphecyModifierType;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.world.entity.player.Player;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ProphecyGeneration {


    public static ProphecyData generate(Player p) {


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
                var mod = ExileDB.ProphecyModifiers().getFilterWrapped(x -> x.modifier_type == type).random();
                ProphecyModifierData md = new ProphecyModifierData(mod.GUID());
                data.mods.add(md);
                cost *= mod.cost_multi;
            }
        }

        data.cost = cost;

        return data;


    }
}
