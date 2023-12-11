package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.gui.inv_gui.GuiInventoryGrids;
import com.robertx22.age_of_exile.gui.inv_gui.InvGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientOnly {

    public static int ticksSinceChatWasOpened = 0;

    public static void totemAnimWithItem(ItemStack stack) {
        Minecraft.getInstance().player.playSound(SoundEvents.TOTEM_USE, 1, 1);
        Minecraft.getInstance().gameRenderer.displayItemActivation(stack);
    }

    public static void runewordsScreen(Player p) {
        Minecraft.getInstance().setScreen(new InvGuiScreen(GuiInventoryGrids.ofCratableRuneWords(p)));
    }

    public static void ALLrunewordsScreen(Player p) {
        Minecraft.getInstance().setScreen(new InvGuiScreen(GuiInventoryGrids.ofAllRuneWords(p)));
    }

    public static Entity getEntityByUUID(Level world, UUID id) {

        if (world instanceof ClientLevel) {
            for (Entity entity : ((ClientLevel) world).entitiesForRendering()) {
                if (entity.getUUID()
                        .equals(id)) {

                    return entity;
                }
            }
        }
        return null;

    }

    public static Player getPlayerById(UUID id) {

        try {
            return Minecraft.getInstance().level.getPlayerByUUID(id);
        } catch (Exception e) {

        }
        return null;
    }

    public static Player getPlayer() {
        return Minecraft.getInstance().player;
    }

    public static void pressUseKey() {
        Minecraft.getInstance().options.keyUse.setDown(true);
    }


    public static void stopUseKey() {
        Minecraft.getInstance().options.keyUse.setDown(false);
    }

    public static void printInChat(MutableComponent text) {
        Minecraft.getInstance().player.sendSystemMessage(text);
    }



}
