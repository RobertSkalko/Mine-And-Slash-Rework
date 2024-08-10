package com.robertx22.mine_and_slash.mmorpg;

import com.robertx22.mine_and_slash.database.data.spells.components.Spell;
import com.robertx22.mine_and_slash.database.data.stats.types.LearnSpellStat;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import com.robertx22.library_of_exile.registry.Database;
import com.robertx22.library_of_exile.registry.ExileRegistry;

public class DerivedRegistries {

    public static void init() {

        // todo check if this works
        // would be a way to more easily add stuff that is derived on other things that could be datapacks
        ExileEvents.ON_REGISTER_TO_DATABASE.register(new EventConsumer<ExileEvents.OnRegisterToDatabase>() {
            @Override
            public void accept(ExileEvents.OnRegisterToDatabase event) {

                if (event.item instanceof Spell spell) {
                    reRegister(new LearnSpellStat(spell));
                }

            }
        });
    }

    private static void reRegister(ExileRegistry o) {
        var db = Database.getRegistry(o.getExileRegistryType());
        if (db.isRegistered(o)) {
            db.unRegister(o);
        }
        db.register(o);

    }
}
