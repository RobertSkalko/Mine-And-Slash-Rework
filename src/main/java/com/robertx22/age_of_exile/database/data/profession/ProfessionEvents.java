package com.robertx22.age_of_exile.database.data.profession;

import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.world.entity.player.Player;

public class ProfessionEvents {

    public static void init() {

        ExileEvents.PLAYER_MINE_ORE.register(new EventConsumer<ExileEvents.PlayerMineOreEvent>() {
            @Override
            public void accept(ExileEvents.PlayerMineOreEvent e) {
                Player p = e.player;
                if (!p.level().isClientSide) {
                    for (Profession prof : ExileDB.Professions().getList()) {
                        prof.onMine(p, e);
                    }
                }
            }
        });

    }
}
