package com.robertx22.mine_and_slash.mmorpg.event_registers;

import com.robertx22.mine_and_slash.event_hooks.ontick.OnClientTick;
import com.robertx22.mine_and_slash.event_hooks.player.OnKeyPress;
import com.robertx22.mine_and_slash.mmorpg.ForgeEvents;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;

public class Client {

    public static void register() {


        ForgeEvents.registerForgeEvent(TickEvent.ClientTickEvent.class, event -> {
            if (event.phase == TickEvent.Phase.END) {
                OnClientTick.onEndTick(Minecraft.getInstance());
                OnKeyPress.onEndTick(Minecraft.getInstance());
            }
        });

        
    }
}
