package com.robertx22.mine_and_slash.uncommon.testing;

import com.robertx22.library_of_exile.registry.IGUID;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public abstract class CommandTest implements IGUID {

    public abstract TestResult runINTERNAL(ServerPlayer player);

    public TestResult run(ServerPlayer player) {
        TestResult res = TestResult.FAIL;
        try {
            res = runINTERNAL(player);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (res == TestResult.FAIL) {

            player.sendSystemMessage(Component.literal(GUID() + " TEST FAILED"));
        }

        return res;
    }

    public abstract boolean shouldRunEveryLogin();
}
