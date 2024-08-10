package com.robertx22.mine_and_slash.uncommon.testing.tests;

import com.robertx22.mine_and_slash.database.data.perks.Perk;
import com.robertx22.mine_and_slash.database.data.talent_tree.TalentTree;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.uncommon.testing.CommandTest;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

public class FindUnusedPerksTest extends CommandTest {

    @Override
    public void run(ServerPlayer player) {

        TalentTree tree = ExileDB.TalentTrees()
            .get("talents");

        List<Perk> perks = ExileDB.Perks()
            .getList();

        perks.forEach(x -> {
            if (x.type == Perk.PerkType.SPECIAL) {
                if (tree.calcData.perks.values()
                    .stream()
                    .noneMatch(e -> e.equals(x.GUID()))) {
                    System.out.print(x.GUID() + " perk isn't used anywhere in the tree." + "\n");
                }
            }
        });
    }

    @Override
    public String GUID() {
        return "find_unused_talents";
    }
}
