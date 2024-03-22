package com.robertx22.age_of_exile.prophecy;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class ClaimProphecyRewardsPacket extends MyPacket<ClaimProphecyRewardsPacket> {


    public ClaimProphecyRewardsPacket() {

    }

    @Override
    public ResourceLocation getIdentifier() {
        return SlashRef.id("claim_prophecy_rewards");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {


    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {

    }

    @Override
    public void onReceived(ExilePacketContext ctx) {

        Load.player(ctx.getPlayer()).prophecy.onBarFinishGiveRewards(ctx.getPlayer());
        Load.player(ctx.getPlayer()).playerDataSync.setDirty();
    }

    @Override
    public MyPacket<ClaimProphecyRewardsPacket> newInstance() {
        return new ClaimProphecyRewardsPacket();
    }
}
