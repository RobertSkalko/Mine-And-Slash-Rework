package com.robertx22.mine_and_slash.uncommon.testing.tests;

import com.robertx22.mine_and_slash.database.data.perks.Perk;
import com.robertx22.mine_and_slash.database.data.talent_tree.TalentTree;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.uncommon.testing.CommandTest;
import com.robertx22.mine_and_slash.uncommon.testing.TestResult;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class FindUnusedPerksTest extends CommandTest {

    @Override
    public TestResult runINTERNAL(ServerPlayer player) {

        TalentTree tree = ExileDB.TalentTrees()
                .get("talents");

        List<Perk> perks = ExileDB.Perks()
                .getList();

        AtomicBoolean has = new AtomicBoolean(false);
        perks.forEach(x -> {
            if (x.type == Perk.PerkType.SPECIAL) {
                if (tree.calcData.perks.values()
                        .stream()
                        .noneMatch(e -> e.equals(x.GUID()))) {
                    System.out.print(x.GUID() + " perk isn't used anywhere in the tree." + "\n");
                    has.set(true);
                }
            }
        });

        if (has.get()) {
            return TestResult.FAIL;
        }
        return TestResult.SUCCESS;


    }

    @Override
    public boolean shouldRunEveryLogin() {
        return false;
    }

    @Override
    public String GUID() {
        return "find_unused_talents";
    }
}
