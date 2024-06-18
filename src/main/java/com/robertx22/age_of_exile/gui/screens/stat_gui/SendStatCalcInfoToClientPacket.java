package com.robertx22.age_of_exile.gui.screens.stat_gui;

import com.robertx22.age_of_exile.capability.player.PlayerData;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class SendStatCalcInfoToClientPacket extends MyPacket<SendStatCalcInfoToClientPacket> {

    StatCalcInfoData data;

    public SendStatCalcInfoToClientPacket(StatCalcInfoData p) {
        this.data = p;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return SlashRef.id("sendsi");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {
        this.data = PlayerData.loadOrBlank(StatCalcInfoData.class, new StatCalcInfoData(), buf.readNbt(), "ctx", new StatCalcInfoData());
    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {
        CompoundTag nbt = new CompoundTag();
        LoadSave.Save(data, nbt, "ctx");
        buf.writeNbt(nbt);

    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        StatCalcInfoData.CLIENT_SYNCED = data;
    }

    @Override
    public MyPacket<SendStatCalcInfoToClientPacket> newInstance() {
        return new SendStatCalcInfoToClientPacket(new StatCalcInfoData());
    }
}
