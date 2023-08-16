package com.robertx22.age_of_exile.mmorpg;

import com.robertx22.age_of_exile.mmorpg.registers.server.CommandRegister;
import com.robertx22.age_of_exile.uncommon.testing.TestManager;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;

public class LifeCycleEvents {

    static boolean regenDefault = true;

    public static void register() {


        ForgeEvents.registerForgeEvent(PlayerEvent.PlayerLoggedInEvent.class, event -> {
            if (MMORPG.RUN_DEV_TOOLS) {
                DataGeneration.generateAll();
            }
        });

        ForgeEvents.registerForgeEvent(ServerStartedEvent.class, event -> {

            LevelUtils.runTests();

            CommandRegister.Register(event.getServer());

            regenDefault = event.getServer()
                    .getGameRules()
                    .getRule(GameRules.RULE_NATURAL_REGENERATION)
                    .get();

            event.getServer()
                    .getGameRules()
                    .getRule(GameRules.RULE_NATURAL_REGENERATION)
                    .set(false, event.getServer());

            if (MMORPG.RUN_DEV_TOOLS) { // CHANGE ON PUBLIC BUILDS TO FALSE
                TestManager.RunAllTests(event.getServer()
                        .overworld());
            }
        });

        ForgeEvents.registerForgeEvent(ServerStoppedEvent.class, event -> {
            event.getServer()
                    .getGameRules()
                    .getRule(GameRules.RULE_NATURAL_REGENERATION)
                    .set(regenDefault, event.getServer());
        });

    }
}
