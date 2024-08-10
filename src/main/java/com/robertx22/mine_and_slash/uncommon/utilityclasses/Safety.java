package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import com.robertx22.mine_and_slash.mmorpg.MMORPG;

public class Safety {
    public static void logIfNull(Object obj, String msg) {
        if (obj == null) {
            MMORPG.LOGGER.log(msg);
        }
    }
}
