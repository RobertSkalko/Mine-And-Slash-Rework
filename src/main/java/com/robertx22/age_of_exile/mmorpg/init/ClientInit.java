package com.robertx22.age_of_exile.mmorpg.init;

import com.robertx22.age_of_exile.a_libraries.dmg_number_particle.DamageParticle;
import com.robertx22.age_of_exile.a_libraries.dmg_number_particle.DamageParticleRenderer;
import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.gui.overlays.GuiPosition;
import com.robertx22.age_of_exile.mmorpg.ForgeEvents;
import com.robertx22.age_of_exile.mmorpg.event_registers.Client;
import com.robertx22.age_of_exile.mmorpg.registers.client.ClientSetup;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Arrays;

public class ClientInit {

    public static void onInitializeClient(final FMLClientSetupEvent event) {


        var todisable = Arrays.asList(
                VanillaGuiOverlay.ARMOR_LEVEL,
                VanillaGuiOverlay.MOUNT_HEALTH,
                VanillaGuiOverlay.PLAYER_HEALTH,
                VanillaGuiOverlay.EXPERIENCE_BAR
        );
        ForgeEvents.registerForgeEvent(RenderGuiOverlayEvent.class, x -> {
            if (ClientConfigs.getConfig().GUI_POSITION.get() == GuiPosition.OVER_VANILLA) {
                if (todisable.stream().anyMatch(e -> e.id().equals(x.getOverlay().id()))) {
                    x.setCanceled(true);
                }
            }

        });
        
        ForgeEvents.registerForgeEvent(RenderLivingEvent.class, x -> {
            for (DamageParticle p : DamageParticleRenderer.PARTICLES) {
                Minecraft mc = Minecraft.getInstance();
                DamageParticleRenderer.renderNameTag(mc.getEntityRenderDispatcher().camera, p.renderString, p, x.getPoseStack(), x.getPartialTick(), x.getMultiBufferSource());
                p.tick();
            }

            DamageParticleRenderer.PARTICLES.removeIf(e -> e.age > 50);
        });

        // RenderMobInfo.register();

        ClientSetup.setup();
        Client.register();


    }
}
