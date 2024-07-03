package com.robertx22.age_of_exile.mmorpg.init;

import com.mojang.datafixers.util.Either;
import com.robertx22.age_of_exile.a_libraries.dmg_number_particle.DamageParticle;
import com.robertx22.age_of_exile.a_libraries.dmg_number_particle.DamageParticleRenderer;
import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.gui.SocketTooltip;
import com.robertx22.age_of_exile.gui.overlays.GuiPosition;
import com.robertx22.age_of_exile.mmorpg.ForgeEvents;
import com.robertx22.age_of_exile.mmorpg.event_registers.Client;
import com.robertx22.age_of_exile.mmorpg.registers.client.ClientSetup;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.SocketData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.*;

public class ClientInit {

    public static void onInitializeClient(final FMLClientSetupEvent event) {

        /*
        ForgeEvents.registerForgeEvent(ScreenEvent.Init.class, x -> {
            if (x.getScreen() instanceof BackpackScreen) {
                if (MOUSEPOS.SET_MOUSE) {
                    MOUSEPOS.SET_MOUSE = false;

                    MouseMixin acc = (MouseMixin) Minecraft.getInstance().mouseHandler;
                    acc.setXpos(MOUSEPOS.X);
                    acc.setYpos(MOUSEPOS.Y);
                }
            }
        });

         */

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


        ForgeEvents.registerForgeEvent(RenderTooltipEvent.GatherComponents.class, x -> {

            try {
                GearItemData data = StackSaving.GEARS.loadFrom(x.getItemStack());
                if (data != null) {

                    List<SocketData> gems = new ArrayList<>();

                    for (SocketData socket : data.sockets.getSocketed()) {
                        gems.add(socket);
                    }


                    int e = 0;
                    if (!gems.isEmpty()) {

                        List<Either<FormattedText, TooltipComponent>> list = x.getTooltipElements();
                        for (int i = 0; i < list.size() && e < gems.size(); i++) {
                            Optional<FormattedText> o = list.get(i).left();
                            if (o.isPresent() && o.get() instanceof Component comp && comp.getContents() instanceof LiteralContents tc) {
                                if (tc.text().contains("[SOCKET_PLACEHOLDER]")) {
                                    list.set(i, Either.right(new SocketTooltip.SocketComponent(x.getItemStack(), Collections.singletonList(gems.get(e)))));
                                    e++;

                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
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
