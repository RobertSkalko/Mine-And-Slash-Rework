package com.robertx22.age_of_exile.gui.screens.stat_gui;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class RequestStatCalcInfoPacket extends MyPacket<RequestStatCalcInfoPacket> {

    @Override
    public ResourceLocation getIdentifier() {
        return SlashRef.id("reqsi");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {

    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {

    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        Packets.sendToClient(ctx.getPlayer(), new SendStatCalcInfoToClientPacket(Load.player(ctx.getPlayer()).ctxs));
    }

    @Override
    public MyPacket<RequestStatCalcInfoPacket> newInstance() {
        return new RequestStatCalcInfoPacket();
    }
}
