package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.capability.player.data.Backpacks;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class OpenBackpackPacket extends MyPacket<OpenBackpackPacket> {

    public Backpacks.BackpackType type;

    public OpenBackpackPacket(Backpacks.BackpackType type) {
        this.type = type;
    }


    @Override
    public ResourceLocation getIdentifier() {
        return SlashRef.id("open_backpack");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {
        this.type = buf.readEnum(Backpacks.BackpackType.class);


    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {

        buf.writeEnum(type);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {

        Load.backpacks(ctx.getPlayer()).getBackpacks().openBackpack(type, ctx.getPlayer());

    }

    @Override
    public MyPacket<OpenBackpackPacket> newInstance() {
        return new OpenBackpackPacket(Backpacks.BackpackType.GEARS);
    }
}
