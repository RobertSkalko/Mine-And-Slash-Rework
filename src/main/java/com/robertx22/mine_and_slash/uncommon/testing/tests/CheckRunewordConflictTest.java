package com.robertx22.mine_and_slash.uncommon.testing.tests;

import com.robertx22.library_of_exile.main.ExileLog;
import com.robertx22.mine_and_slash.database.data.runewords.RuneWord;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.uncommon.testing.CommandTest;
import com.robertx22.mine_and_slash.uncommon.testing.TestResult;
import net.minecraft.server.level.ServerPlayer;

import java.util.concurrent.atomic.AtomicReference;

public class CheckRunewordConflictTest extends CommandTest {
    @Override
    public TestResult runINTERNAL(ServerPlayer player) {

        AtomicReference<TestResult> res = new AtomicReference<>(TestResult.SUCCESS);

        for (RuneWord word : ExileDB.RuneWords().getList()) {
            String s = word.getRunesString();

            for (RuneWord other : ExileDB.RuneWords().getList()) {

                if (other.GUID().equals(word.GUID()) == false) {
                    String s2 = other.getRunesString();

                    if (s2.contains(s)) {
                        ExileLog.get().warn(word.id + " RUNEWORD runes are contained in " + other.GUID());
                        res.set(TestResult.FAIL);
                    }
                }
            }


        }

        return res.get();
    }

    @Override
    public boolean shouldRunEveryLogin() {
        return true;
    }

    @Override
    public String GUID() {
        return "check_runewords_conflict";
    }
}
