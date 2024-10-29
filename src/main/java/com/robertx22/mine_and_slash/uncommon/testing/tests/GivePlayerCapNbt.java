package com.robertx22.mine_and_slash.uncommon.testing.tests;

import com.robertx22.mine_and_slash.uncommon.testing.CommandTest;
import com.robertx22.mine_and_slash.uncommon.testing.TestResult;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;

public class GivePlayerCapNbt extends CommandTest {

    @Override
    public TestResult runINTERNAL(ServerPlayer player) {

        CompoundTag tag = new CompoundTag();
        player.saveWithoutId(tag);

        System.out.print(tag.toString());

        return TestResult.SUCCESS;
    }

    @Override
    public boolean shouldRunEveryLogin() {
        return false;
    }

    @Override
    public String GUID() {
        return "print_player_nbt";
    }
}
