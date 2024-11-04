package com.robertx22.mine_and_slash.vanilla_mc.packets;

import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.mine_and_slash.maps.MapRoomsData;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class MapCompletePacket extends MyPacket<MapCompletePacket> {

    public static MapRoomsData SYNCED_DATA = new MapRoomsData();

    public MapRoomsData data;

    public MapCompletePacket() {

    }

    public MapCompletePacket(MapRoomsData data) {
        this.data = data;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "completemapsyncdata");
    }

    @Override
    public void loadFromData(FriendlyByteBuf tag) {
        data = IAutoGson.GSON.fromJson(tag.readUtf(), MapRoomsData.class);
    }

    @Override
    public void saveToData(FriendlyByteBuf tag) {
        tag.writeUtf(IAutoGson.GSON.toJson(data));
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        SYNCED_DATA = data;
    }

    @Override
    public MyPacket<MapCompletePacket> newInstance() {
        return new MapCompletePacket();
    }
}
