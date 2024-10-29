package com.robertx22.mine_and_slash.uncommon.testing.tests;

import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.mine_and_slash.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.mine_and_slash.uncommon.testing.CommandTest;
import com.robertx22.mine_and_slash.uncommon.testing.TestResult;
import net.minecraft.server.level.ServerPlayer;

public class GenerateGearTest extends CommandTest {
    @Override
    public TestResult runINTERNAL(ServerPlayer player) {

        TestResult res = TestResult.SUCCESS;

        try {
            for (int i = 0; i < 200; i++) {
                int lvl = RandomUtils.RandomRange(1, GameBalanceConfig.get().MAX_LEVEL);
                GearBlueprint b = new GearBlueprint(LootInfo.ofLevel(lvl));
                b.createStack();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return TestResult.FAIL;
        }

        return res;
    }

    @Override
    public boolean shouldRunEveryLogin() {
        return true;
    }

    @Override
    public String GUID() {
        return "gen_random_gear";
    }
}
