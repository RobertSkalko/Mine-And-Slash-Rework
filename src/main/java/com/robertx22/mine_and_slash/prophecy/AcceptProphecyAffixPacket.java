package com.robertx22.mine_and_slash.prophecy;

import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

public class AcceptProphecyAffixPacket extends MyPacket<AcceptProphecyPacket> {

    String id;

    public AcceptProphecyAffixPacket(String id) {
        this.id = id;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return SlashRef.id("accept_prophecy_affix");
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

        var data = Load.player(ctx.getPlayer()).prophecy;

        if (data.affixOffers.contains(this.id)) {
            if (data.numMobAffixesCanAdd > 0 && data.affixesTaken.size() < 9) {
                data.affixesTaken.removeIf(x -> x == null || x.isEmpty() || !ExileDB.MapAffixes().isRegistered(x));

                data.affixesTaken.add(id);
                
                data.affixOffers = new ArrayList<>();

                data.numMobAffixesCanAdd--;
            }
        }
        Load.player(ctx.getPlayer()).playerDataSync.setDirty();
    }

    @Override
    public MyPacket<AcceptProphecyPacket> newInstance() {
        return new AcceptProphecyAffixPacket("");
    }
}
