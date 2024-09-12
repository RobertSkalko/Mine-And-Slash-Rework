package com.robertx22.mine_and_slash.mmorpg.event_registers;

import com.robertx22.mine_and_slash.config.forge.ClientConfigs;
import com.robertx22.mine_and_slash.config.forge.overlay.OverlayType;
import com.robertx22.mine_and_slash.gui.overlays.EffectsOverlay;
import com.robertx22.mine_and_slash.gui.overlays.bar_overlays.types.RPGGuiOverlay;
import com.robertx22.mine_and_slash.gui.overlays.spell_cast_bar.SpellCastBarOverlay;
import com.robertx22.mine_and_slash.gui.overlays.spell_hotbar.SpellHotbarOverlay;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

// todo why doesnt my wrapper register this?
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = SlashRef.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GuiOverlays {
    @SubscribeEvent
    public static void registerOverlay(RegisterGuiOverlaysEvent event) {


        event.registerAbove(VanillaGuiOverlay.CHAT_PANEL.id(), SlashRef.MODID + ".spell_hotbar", new IGuiOverlay() {
            @Override
            public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
                if (ClientConfigs.CLIENT.SPELL_HOTBAR_OVERLAY_TYPE.get() == ClientConfigs.HorizontalOrVertical.HORIZONTAL) {
                    if (ClientConfigs.getConfig().shouldRenderOverlay(OverlayType.SPELL_HOTBAR_HORIZONTAL)) {
                        new SpellHotbarOverlay().onHudRender(guiGraphics, ClientConfigs.getConfig().getOverlayConfig(OverlayType.SPELL_HOTBAR_HORIZONTAL), OverlayType.SPELL_HOTBAR_HORIZONTAL);
                    }
                } else {
                    if (ClientConfigs.getConfig().shouldRenderOverlay(OverlayType.SPELL_HOTBAR_VERTICAL)) {
                        new SpellHotbarOverlay().onHudRender(guiGraphics, ClientConfigs.getConfig().getOverlayConfig(OverlayType.SPELL_HOTBAR_VERTICAL), OverlayType.SPELL_HOTBAR_VERTICAL);
                    }
                }
            }
        });

        event.registerAbove(VanillaGuiOverlay.CHAT_PANEL.id(), SlashRef.MODID + ".cast_bar", new IGuiOverlay() {
            @Override
            public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
                if (ClientConfigs.getConfig().shouldRenderOverlay(OverlayType.SPELL_CAST_BAR)) {
                    new SpellCastBarOverlay().onHudRender(guiGraphics, partialTick);
                }
            }
        });

        event.registerAbove(VanillaGuiOverlay.CHAT_PANEL.id(), SlashRef.MODID + ".rpg_gui", new IGuiOverlay() {
            @Override
            public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
                new RPGGuiOverlay().onHudRender(guiGraphics);
            }
        });
        event.registerAbove(VanillaGuiOverlay.CHAT_PANEL.id(), SlashRef.MODID + ".status_effects", new IGuiOverlay() {
            @Override
            public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {

                if (ClientConfigs.CLIENT.SPELL_HOTBAR_OVERLAY_TYPE.get() == ClientConfigs.HorizontalOrVertical.HORIZONTAL) {
                    if (ClientConfigs.getConfig().shouldRenderOverlay(OverlayType.EFFECTS_HORIZONATL)) {
                        EffectsOverlay.render(guiGraphics, true);
                    }
                } else {
                    if (ClientConfigs.getConfig().shouldRenderOverlay(OverlayType.EFFECTS_VERTICAL)) {
                        EffectsOverlay.render(guiGraphics, false);
                    }
                }

            }
        });


    }


}
