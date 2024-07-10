package com.robertx22.age_of_exile.vanilla_mc.new_commands;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.age_of_exile.capability.player.PlayerData;
import com.robertx22.age_of_exile.database.data.profession.Profession;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.commands.wrapper.*;

public class PlayerCommands {

    public static void init(CommandDispatcher dis) {

        CommandBuilder.of(dis, x -> {
            PlayerWrapper PLAYER = new PlayerWrapper();
            var PROFESSION = new RegistryWrapper<Profession>(ExileRegistryTypes.PROFESSION);
            IntWrapper NUMBER = new IntWrapper();

            x.addLiteral("set", PermWrapper.OP);
            x.addLiteral("profession_level", PermWrapper.OP);

            x.addArg(PLAYER);
            x.addArg(PROFESSION);
            x.addArg(NUMBER);


            x.action(e -> {
                var en = PLAYER.get(e);
                var num = NUMBER.get(e);
                var prof = PROFESSION.get(e);

                PlayerData data = Load.player(en);
                data.professions.setLevel(prof, num);
            });

        });


    }
}
