package com.robertx22.mine_and_slash.vanilla_mc.new_commands;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.database.data.rarities.MobRarity;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.vanilla_mc.new_commands.wrapper.*;

public class EntityCommands {

    public static void init(CommandDispatcher dis) {


        CommandBuilder.of(dis, x -> {
            EntityWrapper enarg = new EntityWrapper();
            IntWrapper intarg = new IntWrapper("level");

            x.addLiteral("set", PermWrapper.OP);
            x.addLiteral("level", PermWrapper.OP);

            x.addArg(enarg);
            x.addArg(intarg);

            x.action(e -> {
                var en = enarg.get(e);
                var num = intarg.get(e);

                EntityData data = Load.Unit(en);
                data.setLevel(num);
            });
        }, "Sets Mine and Slash level of entity.");

        CommandBuilder.of(dis, x -> {
            EntityWrapper enarg = new EntityWrapper();
            var strarg = new RegistryWrapper<MobRarity>(ExileRegistryTypes.MOB_RARITY);

            x.addLiteral("set", PermWrapper.OP);
            x.addLiteral("rarity", PermWrapper.OP);

            x.addArg(enarg);
            x.addArg(strarg);

            x.action(e -> {
                var en = enarg.get(e);
                var rar = strarg.get(e);
                EntityData data = Load.Unit(en);
                data.setRarity(rar);
            });
        }, "Sets the Mine and Slash rarity of an entity.");

    }
}
