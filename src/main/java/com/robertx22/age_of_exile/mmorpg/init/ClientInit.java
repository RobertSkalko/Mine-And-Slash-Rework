package com.robertx22.age_of_exile.mmorpg.init;

import com.robertx22.age_of_exile.a_libraries.dmg_number_particle.DamageParticle;
import com.robertx22.age_of_exile.a_libraries.dmg_number_particle.DamageParticleRenderer;
import com.robertx22.age_of_exile.mmorpg.ForgeEvents;
import com.robertx22.age_of_exile.mmorpg.event_registers.Client;
import com.robertx22.age_of_exile.mmorpg.registers.client.ClientSetup;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientInit {

    public static void onInitializeClient(final FMLClientSetupEvent event) {

        ForgeEvents.registerForgeEvent(RenderLivingEvent.class, x -> {
            for (DamageParticle p : DamageParticleRenderer.PARTICLES) {
                Minecraft mc = Minecraft.getInstance();
                DamageParticleRenderer.renderNameTag(mc.getEntityRenderDispatcher().camera, p.renderString, p, x.getPoseStack(), x.getPartialTick(), x.getMultiBufferSource());
                p.tick();
            }

            DamageParticleRenderer.PARTICLES.removeIf(e -> e.age > 50);
        });

        ClientSetup.setup();
        Client.register();


    }
}
