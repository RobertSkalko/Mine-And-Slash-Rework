package com.robertx22.age_of_exile.prophecy;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class RerollPropheciesPacket extends MyPacket<RerollPropheciesPacket> {
    @Override
    public ResourceLocation getIdentifier() {
        return SlashRef.id("rerollprophecies");
    }

    @Override
    public void loadFromData(FriendlyByteBuf friendlyByteBuf) {

    }

    @Override
    public void saveToData(FriendlyByteBuf friendlyByteBuf) {

    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        Player p = ctx.getPlayer();
        var data = Load.player(p).prophecy;

        int cost = data.getRerollCost();

        // todo need to save favor currency.. something
    }

    @Override
    public MyPacket<RerollPropheciesPacket> newInstance() {
        return new RerollPropheciesPacket();
    }
}
