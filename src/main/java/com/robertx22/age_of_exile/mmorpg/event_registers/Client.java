package com.robertx22.age_of_exile.mmorpg.event_registers;

import com.robertx22.age_of_exile.a_libraries.neat.AnotherTry;
import com.robertx22.age_of_exile.event_hooks.ontick.OnClientTick;
import com.robertx22.age_of_exile.event_hooks.player.OnKeyPress;
import com.robertx22.age_of_exile.gui.overlays.bar_overlays.types.RPGGuiOverlay;
import com.robertx22.age_of_exile.gui.overlays.spell_cast_bar.SpellCastBarOverlay;
import com.robertx22.age_of_exile.gui.overlays.spell_hotbar.SpellHotbarOverlay;
import com.robertx22.age_of_exile.mmorpg.ForgeEvents;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;

public class Client {

    public static void register() {

        SpellHotbarOverlay spellHotbarOverlay = new SpellHotbarOverlay();
        SpellCastBarOverlay castbar = new SpellCastBarOverlay();
        RPGGuiOverlay rpggui = new RPGGuiOverlay();

        AnotherTry.register();

        ForgeEvents.registerForgeEvent(TickEvent.ClientTickEvent.class, event -> {
            if (event.phase == TickEvent.Phase.END) {
                OnClientTick.onEndTick(Minecraft.getInstance());
                OnKeyPress.onEndTick(Minecraft.getInstance());
            }
        });

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
    }
}
