package com.robertx22.age_of_exile.mmorpg.registers.server;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.loot.blueprints.JewelBlueprint;
import com.robertx22.age_of_exile.loot.blueprints.LootChestBlueprint;
import com.robertx22.age_of_exile.loot.blueprints.SkillGemBlueprint;
import com.robertx22.age_of_exile.saveclasses.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.vanilla_mc.commands.GivePoints;
import com.robertx22.age_of_exile.vanilla_mc.commands.RollCommand;
import com.robertx22.age_of_exile.vanilla_mc.commands.RunTestCommand;
import com.robertx22.age_of_exile.vanilla_mc.commands.TeamCommand;
import com.robertx22.age_of_exile.vanilla_mc.commands.entity.*;
import com.robertx22.age_of_exile.vanilla_mc.commands.giveitems.GenericGive;
import com.robertx22.age_of_exile.vanilla_mc.commands.giveitems.GiveExactUnique;
import com.robertx22.age_of_exile.vanilla_mc.commands.giveitems.GiveMap;
import com.robertx22.age_of_exile.vanilla_mc.commands.open_gui.OpenHub;
import com.robertx22.age_of_exile.vanilla_mc.commands.report.ReportMapIssue;
import com.robertx22.age_of_exile.vanilla_mc.commands.reset.ResetCommand;
import com.robertx22.age_of_exile.vanilla_mc.commands.reset.ResetSpellCooldowns;
import com.robertx22.age_of_exile.vanilla_mc.commands.stats.ClearStats;
import com.robertx22.age_of_exile.vanilla_mc.commands.stats.GiveStat;
import com.robertx22.age_of_exile.vanilla_mc.commands.stats.ListStats;
import com.robertx22.age_of_exile.vanilla_mc.commands.stats.RemoveStat;
import net.minecraft.commands.CommandSourceStack;

public class CommandRegister {

    public static void Register(CommandDispatcher<CommandSourceStack> dispatcher) {
        System.out.println("Registering Mine and slash Commands.");


        GiveExactUnique.register(dispatcher);
        GiveMap.register(dispatcher);

        //new GenericGive("spell", ExileRegistryTypes.SPELL, x -> new SkillGemBlueprint(x, SkillGemData.SkillGemType.SKILL)).register(dispatcher);
        new GenericGive("aura", ExileRegistryTypes.AURA, x -> new SkillGemBlueprint(x, SkillGemData.SkillGemType.AURA)).register(dispatcher);
        new GenericGive("support_gem", ExileRegistryTypes.SUPPORT_GEM, x -> new SkillGemBlueprint(x, SkillGemData.SkillGemType.SUPPORT)).register(dispatcher);
        new GenericGive("jewel", null, x -> new JewelBlueprint(x)).register(dispatcher);

        new GenericGive("gear", ExileRegistryTypes.GEAR_TYPE, x -> new GearBlueprint(x)).register(dispatcher);
        new GenericGive("loot_chest", ExileRegistryTypes.LOOT_CHEST, x -> new LootChestBlueprint(x)).register(dispatcher);


        SetEntityRarity.register(dispatcher);
        SpawnBoss.register(dispatcher);
        SetLevel.register(dispatcher);
        SetProphecyFavor.register(dispatcher);
        GiveExp.register(dispatcher);
        SetFavor.register(dispatcher);
        GivePerLvlExp.register(dispatcher);

        ResetSpellCooldowns.register(dispatcher);

        ResetCommand.register(dispatcher);

        GiveStat.register(dispatcher);
        RemoveStat.register(dispatcher);
        ClearStats.register(dispatcher);
        ListStats.register(dispatcher);

        TeamCommand.register(dispatcher);
        RollCommand.register(dispatcher);

        RunTestCommand.register(dispatcher);


        GivePoints.giveCommand(dispatcher);
        GivePoints.resetCommand(dispatcher);

        OpenHub.register(dispatcher);

        ReportMapIssue.register(dispatcher);
    }
}