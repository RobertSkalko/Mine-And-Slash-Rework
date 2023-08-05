package com.robertx22.age_of_exile.uncommon.testing;

import com.robertx22.library_of_exile.registry.IGUID;
import net.minecraft.server.level.ServerPlayer;

public abstract class CommandTest implements IGUID {

    public abstract void run(ServerPlayer player);
}
