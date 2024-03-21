package com.robertx22.age_of_exile.prophecy;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class AcceptProphecyPacket extends MyPacket<AcceptProphecyPacket> {

    String id;

    public AcceptProphecyPacket(String id) {
        this.id = id;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return SlashRef.id("accept_prophecy");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {

        id = buf.readUtf();
    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {

        buf.writeUtf(id);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {

        Load.player(ctx.getPlayer()).prophecy.tryAcceptOffer(ctx.getPlayer(), id);

    }

    @Override
    public MyPacket<AcceptProphecyPacket> newInstance() {
        return new AcceptProphecyPacket("");
    }
}
