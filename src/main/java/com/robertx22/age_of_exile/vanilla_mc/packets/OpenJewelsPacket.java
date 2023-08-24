package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;

public class OpenJewelsPacket extends MyPacket<OpenJewelsPacket> {


    public OpenJewelsPacket() {

    }


    @Override
    public ResourceLocation getIdentifier() {
        return SlashRef.id("openjewels");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {


    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {


        var inv = Load.player(ctx.getPlayer()).getJewels().inv;

        Player p = ctx.getPlayer();

        p.openMenu(new SimpleMenuProvider((i, playerInventory, playerEntity) -> {
            return oneRow(i, playerInventory, inv); // todo why doesnt vanilla have this
        }, Component.literal("")));

    }

    public static ChestMenu oneRow(int pContainerId, Inventory pPlayerInventory, Container pContainer) {
        return new ChestMenu(MenuType.GENERIC_9x1, pContainerId, pPlayerInventory, pContainer, 1);
    }

    @Override
    public MyPacket<OpenJewelsPacket> newInstance() {
        return new OpenJewelsPacket();
    }
}
