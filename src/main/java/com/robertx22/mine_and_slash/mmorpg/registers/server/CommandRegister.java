package com.robertx22.mine_and_slash.mmorpg.registers.server;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.mine_and_slash.loot.blueprints.JewelBlueprint;
import com.robertx22.mine_and_slash.loot.blueprints.LootChestBlueprint;
import com.robertx22.mine_and_slash.loot.blueprints.SkillGemBlueprint;
import com.robertx22.mine_and_slash.saveclasses.skill_gem.SkillGemData;
import com.robertx22.mine_and_slash.vanilla_mc.commands.ListStatSourcesCommand;
import com.robertx22.mine_and_slash.vanilla_mc.commands.RollCommand;
import com.robertx22.mine_and_slash.vanilla_mc.commands.RunTestCommand;
import com.robertx22.mine_and_slash.vanilla_mc.commands.TeamCommand;
import com.robertx22.mine_and_slash.vanilla_mc.commands.auto_salvage.AutoSalvageGenericConfigure;
import com.robertx22.mine_and_slash.vanilla_mc.commands.auto_salvage.AutoSalvageGenericList;
import com.robertx22.mine_and_slash.vanilla_mc.commands.auto_salvage.AutoSalvageGenericShow;
import com.robertx22.mine_and_slash.vanilla_mc.commands.auto_salvage.AutoSalvageHelp;
import com.robertx22.mine_and_slash.vanilla_mc.commands.entity.GiveExp;
import com.robertx22.mine_and_slash.vanilla_mc.commands.entity.GivePerLvlExp;
import com.robertx22.mine_and_slash.vanilla_mc.commands.entity.SetEntityRarity;
import com.robertx22.mine_and_slash.vanilla_mc.commands.entity.SpawnBoss;
import com.robertx22.mine_and_slash.vanilla_mc.commands.giveitems.GenericGive;
import com.robertx22.mine_and_slash.vanilla_mc.commands.giveitems.GiveExactUnique;
import com.robertx22.mine_and_slash.vanilla_mc.commands.giveitems.GiveMap;
import com.robertx22.mine_and_slash.vanilla_mc.commands.report.ReportMapIssue;
import com.robertx22.mine_and_slash.vanilla_mc.commands.stats.ClearStats;
import com.robertx22.mine_and_slash.vanilla_mc.commands.stats.GiveStat;
import com.robertx22.mine_and_slash.vanilla_mc.commands.stats.ListStats;
import com.robertx22.mine_and_slash.vanilla_mc.commands.stats.RemoveStat;
import com.robertx22.mine_and_slash.vanilla_mc.new_commands.DevCommands;
import com.robertx22.mine_and_slash.vanilla_mc.new_commands.EntityCommands;
import com.robertx22.mine_and_slash.vanilla_mc.new_commands.PlayerCommands;
import net.minecraft.commands.CommandSourceStack;

public class CommandRegister {

    public static void Register(CommandDispatcher<CommandSourceStack> dispatcher) {
        //ExileLog.get().log("Registering Mine and slash Commands.");


        EntityCommands.init(dispatcher);
        PlayerCommands.init(dispatcher);
        DevCommands.init(dispatcher);

        GiveExactUnique.register(dispatcher);
        GiveMap.register(dispatcher);

        //new GenericGive("spell", ExileRegistryTypes.SPELL, x -> new SkillGemBlueprint(x, SkillGemData.SkillGemType.SKILL)).register(dispatcher);
        new GenericGive("aura", ExileRegistryTypes.AURA, x -> new SkillGemBlueprint(x, SkillGemData.SkillGemType.AURA)).register(dispatcher);
        new GenericGive("support_gem", ExileRegistryTypes.SUPPORT_GEM, x -> new SkillGemBlueprint(x, SkillGemData.SkillGemType.SUPPORT)).register(dispatcher);
        new GenericGive("jewel", null, x -> new JewelBlueprint(x)).register(dispatcher);

        new GenericGive("gear", ExileRegistryTypes.GEAR_TYPE, x -> new GearBlueprint(x)).register(dispatcher);
        new GenericGive("loot_chest", ExileRegistryTypes.LOOT_CHEST, x -> new LootChestBlueprint(x)).register(dispatcher);


        new AutoSalvageGenericConfigure(ExileRegistryTypes.GEAR_SLOT).register(dispatcher);
        new AutoSalvageGenericConfigure(ExileRegistryTypes.SUPPORT_GEM).register(dispatcher);

        new AutoSalvageGenericList(ExileRegistryTypes.GEAR_SLOT).register(dispatcher);
        new AutoSalvageGenericList(ExileRegistryTypes.SUPPORT_GEM).register(dispatcher);

        new AutoSalvageGenericShow(ExileRegistryTypes.GEAR_SLOT).register(dispatcher);
        new AutoSalvageGenericShow(ExileRegistryTypes.SUPPORT_GEM).register(dispatcher);

        AutoSalvageHelp.register(dispatcher);

        SetEntityRarity.register(dispatcher);
        SpawnBoss.register(dispatcher);

        GiveExp.register(dispatcher);
        GivePerLvlExp.register(dispatcher);


        GiveStat.register(dispatcher);
        RemoveStat.register(dispatcher);
        ClearStats.register(dispatcher);
        ListStats.register(dispatcher);

        TeamCommand.register(dispatcher);
        RollCommand.register(dispatcher);

        ListStatSourcesCommand.register(dispatcher);


        RunTestCommand.register(dispatcher);


        ReportMapIssue.register(dispatcher);


    }
}