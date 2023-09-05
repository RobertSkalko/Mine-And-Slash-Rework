package com.robertx22.age_of_exile.database.data.profession;

import com.robertx22.age_of_exile.database.data.profession.all.Professions;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.world.entity.player.Player;

import java.util.Arrays;

public class ProfessionEvents {

    public static void init() {

        ExileEvents.PLAYER_MINE_ORE.register(new EventConsumer<ExileEvents.PlayerMineOreEvent>() {
            @Override
            public void accept(ExileEvents.PlayerMineOreEvent e) {
                Player p = e.player;
                if (!p.level().isClientSide) {
                    var drops = ExileDB.Professions().get(Professions.MINING).onMineGetBonusDrops(p, Arrays.asList(), e.state);
                    e.itemsToAddToDrop.addAll(drops);
                }
            }
        });

        ExileEvents.PLAYER_MINE_FARMABLE.register(new EventConsumer<ExileEvents.PlayerMineFarmableBlockEvent>() {
            @Override
            public void accept(ExileEvents.PlayerMineFarmableBlockEvent e) {
                Player p = e.player;
                if (!p.level().isClientSide) {
                    var drops = ExileDB.Professions().get(Professions.FARMING).onMineGetBonusDrops(p, e.droppedItems, e.state);
                    e.itemsToAddToDrop.addAll(drops);
                }
            }
        });
    }
}
