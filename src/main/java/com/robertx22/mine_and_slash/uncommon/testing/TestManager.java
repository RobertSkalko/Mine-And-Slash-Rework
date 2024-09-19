package com.robertx22.mine_and_slash.uncommon.testing;

import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import net.minecraft.server.level.ServerPlayer;

public class TestManager {

    public static void RunAllTests(ServerPlayer p) {

        if (MMORPG.RUN_DEV_TOOLS) {
            for (CommandTest test : CommandTests.tests.values()) {
                if (test.shouldRunEveryLogin()) {
                    test.run(p);
                }
            }
        }

    }

}
