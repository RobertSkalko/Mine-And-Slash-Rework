package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.lowdragmc.photon.client.fx.FXEffect;
import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

import static com.robertx22.age_of_exile.event_hooks.player.OnLogin.writeFXConfigValueFromPacket;

public class FXConfigCheckerPacket extends MyPacket<FXConfigCheckerPacket> {

    public Boolean ifFXEnable;

    public FXConfigCheckerPacket(){
    }


    @Override
    public ResourceLocation getIdentifier()  {
        return new ResourceLocation(SlashRef.MODID, "fx_config_checker");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {
        this.ifFXEnable = buf.readBoolean();
    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {
        buf.writeBoolean(ClientConfigs.getConfig().ENABLE_PHOTON_FX.get());
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        ctx.getPlayer().displayClientMessage(Component.literal("get callback!"), false);
        ctx.getPlayer().displayClientMessage(Component.literal("callback is:" + ifFXEnable), false);

        writeFXConfigValueFromPacket(ctx.getPlayer(), this.ifFXEnable);
    }

    @Override
    public MyPacket<FXConfigCheckerPacket> newInstance() {
        return new FXConfigCheckerPacket();
    }
}
