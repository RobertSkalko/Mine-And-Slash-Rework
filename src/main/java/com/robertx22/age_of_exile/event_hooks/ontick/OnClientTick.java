package com.robertx22.age_of_exile.event_hooks.ontick;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.drawer.AllPerkButtonPainter;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.drawer.PerkButtonPainter;
import com.robertx22.age_of_exile.gui.screens.skill_tree.connections.PerkConnectionPainter;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ChatUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class OnClientTick {

    public static HashMap<String, Integer> COOLDOWN_READY_MAP = new HashMap<>();
    public static ConcurrentHashMap<Integer, AllPerkButtonPainter> container = new ConcurrentHashMap<>();
    public static boolean isRegistering = false;
    static int TICKS_TO_SHOW = 50;
    private static int NO_MANA_SOUND_COOLDOWN = 0;
    private static int handleDrawInterval = 0;

    public static boolean canSoundNoMana() {
        return NO_MANA_SOUND_COOLDOWN <= 0;
    }

    public static void setNoManaSoundCooldown() {
        NO_MANA_SOUND_COOLDOWN = 30;
    }

    public static void onEndTick(Minecraft mc) {

        try {
            Player player = Minecraft.getInstance().player;

            container.values().forEach(x -> {
                x.checkIfNeedRepaint();
                x.scheduleRepaint();
            });


            RenderSystem.recordRenderCall(() -> {
                PerkButtonPainter.handleRegisterQueue();
                container.values().forEach(AllPerkButtonPainter::tryRegister);
                PerkConnectionPainter.handleRegisterQueue();
            });


            if (player == null) {
                return;
            }
            if (player.tickCount < 10) {
                return;
            }
            if (player.isDeadOrDying()) {
                return;
            }
            if (Load.Unit(player) == null) {
                return;
            }

            if (ChatUtils.isChatOpen()) {
                ClientOnly.ticksSinceChatWasOpened = 0;
            } else {
                ClientOnly.ticksSinceChatWasOpened--;
            }

            if (player.is(player)) {

                Load.Unit(player)
                        .getResources()
                        .onTickBlock(player, 1);

                NO_MANA_SOUND_COOLDOWN--;


                List<String> onCooldown = Load.player(player)
                        .spellCastingData
                        .getSpellsOnCooldown(player);

                Load.Unit(player)
                        .getCooldowns()
                        .onTicksPass(1);

                Load.player(player).spellCastingData
                        .onTimePass(player); // ticks spells on client

                List<String> onCooldownAfter = Load.player(player)
                        .spellCastingData
                        .getSpellsOnCooldown(player);

                onCooldown.removeAll(onCooldownAfter);

                COOLDOWN_READY_MAP.entrySet()
                        .forEach(x -> x.setValue(x.getValue() - 1));

                onCooldown.forEach(x -> {
                    COOLDOWN_READY_MAP.put(x, TICKS_TO_SHOW);
                    x.isEmpty();
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}