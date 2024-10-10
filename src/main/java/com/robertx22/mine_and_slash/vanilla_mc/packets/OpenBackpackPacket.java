package com.robertx22.mine_and_slash.vanilla_mc.packets;

import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import com.robertx22.mine_and_slash.capability.player.BackpackItem;
import com.robertx22.mine_and_slash.capability.player.data.Backpacks;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
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
        int rows = 0;
        if (ctx.getPlayer().getMainHandItem().getItem() instanceof BackpackItem i) {
            rows = i.getSlots() / 9;
            Load.backpacks(ctx.getPlayer()).getBackpacks().openBackpack(type, ctx.getPlayer(), rows);
        }
    }

    @Override
    public MyPacket<OpenBackpackPacket> newInstance() {
        return new OpenBackpackPacket(Backpacks.BackpackType.GEARS);
    }
}
