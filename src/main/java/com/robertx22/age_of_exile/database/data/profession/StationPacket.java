package com.robertx22.age_of_exile.database.data.profession;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import com.robertx22.library_of_exile.registry.serialization.MyGSON;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class StationPacket extends MyPacket<StationPacket> {

    public StationSyncData data;

    public StationPacket(StationSyncData data) {
        this.data = data;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return SlashRef.id("besyncpacket");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {

        data = MyGSON.GSON.fromJson(buf.readUtf(), StationSyncData.class);
    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {

        buf.writeUtf(MyGSON.GSON.toJson(data));
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        StationSyncData.SYNCED_DATA = data;
    }

    @Override
    public MyPacket<StationPacket> newInstance() {
        return StationPacket.this;
    }
}
