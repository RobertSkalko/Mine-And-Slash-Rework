package com.robertx22.age_of_exile.mmorpg.event_registers;

import com.robertx22.age_of_exile.a_libraries.neat.AnotherTry;
import com.robertx22.age_of_exile.event_hooks.ontick.OnClientTick;
import com.robertx22.age_of_exile.event_hooks.player.OnKeyPress;
import com.robertx22.age_of_exile.mmorpg.ForgeEvents;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;

public class Client {

    public static void register() {

        AnotherTry.register();

        ForgeEvents.registerForgeEvent(TickEvent.ClientTickEvent.class, event -> {
            if (event.phase == TickEvent.Phase.END) {
                OnClientTick.onEndTick(Minecraft.getInstance());
                OnKeyPress.onEndTick(Minecraft.getInstance());
            }
        });

        /*

        SpellHotbarOverlay spellHotbarOverlay = new SpellHotbarOverlay();
        SpellCastBarOverlay castbar = new SpellCastBarOverlay();
        RPGGuiOverlay rpggui = new RPGGuiOverlay();

        FMLJavaModLoadingContext.get()
                .getModEventBus()
                .addListener(EventPriority.LOW, (Consumer<RegisterGuiOverlaysEvent>) x -> {
                    x.registerAbove(VanillaGuiOverlay.HOTBAR.id(), SlashRef.MODID, new IGuiOverlay() {
                        @Override
                        public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
                            guiGraphics.drawManaged(() -> {
                                spellHotbarOverlay.onHudRender(guiGraphics);
                                castbar.onHudRender(guiGraphics);
                                rpggui.onHudRender(guiGraphics);
                            });
                        }
                    });
                });

        ForgeEvents.registerForgeEvent(RegisterGuiOverlaysEvent.class, x -> {
            x.registerAbove(VanillaGuiOverlay.HOTBAR.id(), SlashRef.MODID, new IGuiOverlay() {
                @Override
                public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
                    guiGraphics.drawManaged(() -> {
                        spellHotbarOverlay.onHudRender(guiGraphics);
                        castbar.onHudRender(guiGraphics);
                        rpggui.onHudRender(guiGraphics);
                    });
                }
            });
        });


         */
        /*
        ForgeEvents.registerForgeEvent(RenderGuiOverlayEvent.Post.class, event -> {
            if (event.getOverlay().id() == VanillaGuiOverlay.HOTBAR.id()) {
                event.getGuiGraphics().drawManaged(() -> {
                    spellHotbarOverlay.onHudRender(event.getGuiGraphics());
                    castbar.onHudRender(event.getGuiGraphics());
                    rpggui.onHudRender(event.getGuiGraphics());
                });

            }
            //RenderSystem.enableDepthTest();
        });

         */
    }
}
