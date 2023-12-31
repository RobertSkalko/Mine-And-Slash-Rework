package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;

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
        var callback = new FXConfigCheckerPacket();
        if(ModList.get().getMods().stream().map(IModInfo::getModId).anyMatch(s -> s.contentEquals("photon"))){
            Packets.sendToServer(callback);
        }
    }

    @Override
    public MyPacket<askForFXConfigPacket> newInstance() {
        return new askForFXConfigPacket();
    }
}
