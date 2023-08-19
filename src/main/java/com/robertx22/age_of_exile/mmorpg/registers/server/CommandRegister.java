package com.robertx22.age_of_exile.mmorpg.registers.server;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.age_of_exile.vanilla_mc.commands.RollCommand;
import com.robertx22.age_of_exile.vanilla_mc.commands.RunTestCommand;
import com.robertx22.age_of_exile.vanilla_mc.commands.TeamCommand;
import com.robertx22.age_of_exile.vanilla_mc.commands.entity.GiveExp;
import com.robertx22.age_of_exile.vanilla_mc.commands.entity.SetEntityRarity;
import com.robertx22.age_of_exile.vanilla_mc.commands.entity.SetLevel;
import com.robertx22.age_of_exile.vanilla_mc.commands.entity.SpawnBoss;
import com.robertx22.age_of_exile.vanilla_mc.commands.giveitems.*;
import com.robertx22.age_of_exile.vanilla_mc.commands.open_gui.OpenHub;
import com.robertx22.age_of_exile.vanilla_mc.commands.reset.ResetSpellCooldowns;
import com.robertx22.age_of_exile.vanilla_mc.commands.stats.ClearStats;
import com.robertx22.age_of_exile.vanilla_mc.commands.stats.GiveStat;
import com.robertx22.age_of_exile.vanilla_mc.commands.stats.ListStats;
import com.robertx22.age_of_exile.vanilla_mc.commands.stats.RemoveStat;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;

public class CommandRegister {

    public static void Register(MinecraftServer server) {
        System.out.println("Registering Mine and slash Commands.");

        CommandDispatcher<CommandSourceStack> dispatcher = server.getCommands()
                .getDispatcher();

        GiveExactUnique.register(dispatcher);
        GiveMap.register(dispatcher);
        GiveGear.register(dispatcher);
        GiveSpell.register(dispatcher);
        GiveSupport.register(dispatcher);
        SetEntityRarity.register(dispatcher);
        SpawnBoss.register(dispatcher);
        SetLevel.register(dispatcher);
        GiveExp.register(dispatcher);


        ResetSpellCooldowns.register(dispatcher);

        GiveStat.register(dispatcher);
        RemoveStat.register(dispatcher);
        ClearStats.register(dispatcher);
        ListStats.register(dispatcher);

        TeamCommand.register(dispatcher);
        RollCommand.register(dispatcher);

        RunTestCommand.register(dispatcher);

        OpenHub.register(dispatcher);

    }
}