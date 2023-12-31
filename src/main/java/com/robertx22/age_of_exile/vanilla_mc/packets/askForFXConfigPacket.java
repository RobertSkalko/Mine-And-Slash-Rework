package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.Pack;

import static com.robertx22.age_of_exile.event_hooks.player.OnLogin.writeFXConfigValueFromPacket;

public class askForFXConfigPacket extends MyPacket<askForFXConfigPacket> {

    public Boolean ifFXEnable;

    public askForFXConfigPacket(){
    }


    @Override
    public ResourceLocation getIdentifier()  {
        return new ResourceLocation(SlashRef.MODID, "ask_for_fx_config");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {
    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        ctx.getPlayer().displayClientMessage(Component.literal("get ask!"), false);
        var callback = new FXConfigCheckerPacket();
        Packets.sendToServer(callback);
        ctx.getPlayer().displayClientMessage(Component.literal("send back!"), false);

    }

    @Override
    public MyPacket<askForFXConfigPacket> newInstance() {
        return new askForFXConfigPacket();
    }
}
