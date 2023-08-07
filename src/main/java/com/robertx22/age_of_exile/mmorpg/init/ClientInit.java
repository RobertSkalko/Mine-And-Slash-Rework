package com.robertx22.age_of_exile.mmorpg.init;

import com.robertx22.age_of_exile.mmorpg.event_registers.Client;
import com.robertx22.age_of_exile.mmorpg.registers.client.ClientSetup;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientInit {

    public static void onInitializeClient(final FMLClientSetupEvent event) {

        ClientSetup.setup();
        Client.register();
      

    }
}
