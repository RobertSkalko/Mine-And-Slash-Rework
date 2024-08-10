package com.robertx22.mine_and_slash.characters;

import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class LoadCharPacket extends MyPacket<CreateCharPacket> {

    int num;

    public LoadCharPacket(Integer num) {
        this.num = num;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return SlashRef.id("load_char");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {
        num = buf.readInt();
    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {
        buf.writeInt(num);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        var chars = Load.player(ctx.getPlayer()).characters;

        if (chars.canChangeCharactersRightNow(ctx.getPlayer())) {

            var opt = chars.map.get(num);

            if (opt != null) {

                chars.load(num, ctx.getPlayer());

            }
        }
    }

    @Override
    public MyPacket<CreateCharPacket> newInstance() {
        return new LoadCharPacket(-1);
    }
}
