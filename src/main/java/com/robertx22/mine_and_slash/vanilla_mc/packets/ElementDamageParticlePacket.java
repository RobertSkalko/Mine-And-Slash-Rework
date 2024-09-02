package com.robertx22.mine_and_slash.vanilla_mc.packets;

import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class ElementDamageParticlePacket extends ExileNumberParticlePacket {

    @Override
    public String spawnParticle(Vec3 position) {
        return null;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "edp");
    }

    @Override
    public void loadFromData(FriendlyByteBuf friendlyByteBuf) {

    }

    @Override
    public void saveToData(FriendlyByteBuf friendlyByteBuf) {

    }

    @Override
    public void onReceived(ExilePacketContext exilePacketContext) {

    }

    @Override
    public MyPacket<ExileNumberParticlePacket> newInstance() {
        return null;
    }
}
