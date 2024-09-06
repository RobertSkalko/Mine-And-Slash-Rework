package com.robertx22.mine_and_slash.mmorpg.init;

import com.mojang.datafixers.util.Either;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.DamageParticle;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.DamageParticleRenderer;
import com.robertx22.mine_and_slash.config.forge.ClientConfigs;
import com.robertx22.mine_and_slash.gui.SocketTooltip;
import com.robertx22.mine_and_slash.gui.overlays.GuiPosition;
import com.robertx22.mine_and_slash.mmorpg.ForgeEvents;
import com.robertx22.mine_and_slash.mmorpg.event_registers.Client;
import com.robertx22.mine_and_slash.mmorpg.registers.client.ClientSetup;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.ModRange;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRangeInfo;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.SocketData;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.WorldUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientInit {

    public static void onInitializeClient(final FMLClientSetupEvent event) {

        AtomicInteger sounds = new AtomicInteger();

        // todo
        // experimental fix for massive 1 min lag after joining a map. No clue what causes it
        ForgeEvents.registerForgeEvent(PlaySoundEvent.class, x -> {
            var p = ClientOnly.getPlayer();
            if (p != null && p.tickCount < (20 * 5)) {
                if (WorldUtils.isMapWorldClass(Minecraft.getInstance().level)) {
                    //Minecraft.getInstance().player.sendSystemMessage(Component.literal("Sounds blocked: " + sounds + " - " + x.getName()));
                    sounds.getAndIncrement();
                    x.setSound(null); // forge wtf.. not cancellable but set nullable?
                }
            } else {
                sounds.set(0);
            }
        });

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
                        for (int i = 0; i < list.size(); i++) {
                            Optional<FormattedText> o = list.get(i).left();
                            if (o.isPresent() && o.get() instanceof Component comp && comp.getContents() instanceof LiteralContents tc) {
                                if (tc.text().contains("[SOCKET_PLACEHOLDER]")) {
                                    if (e < gems.size()) {
                                        if (!new StatRangeInfo(ModRange.hide()).shouldShowDescriptions()) {
                                            list.set(i, Either.right(new SocketTooltip.SocketComponent(x.getItemStack(), Collections.singletonList(gems.get(e)))));
                                        } else {
                                            list.set(i, Either.right(new SocketTooltip.SocketComponent(x.getItemStack(), Collections.singletonList(gems.get(e)))));
                                            int finalI = i;
                                            list.get(i + 1).left().ifPresent(formattedText -> {
                                                if (formattedText instanceof Component && formattedText.getString().contains("[SOCKET_PLACEHOLDER]")) {
                                                    String replaced = formattedText.getString().replace("[SOCKET_PLACEHOLDER]", "");
                                                    MutableComponent desc = Component.literal(replaced).withStyle(ChatFormatting.BLUE);
                                                    list.set(finalI + 1, Either.left(desc));
                                                }
                                            });
                                        }
                                    }
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
