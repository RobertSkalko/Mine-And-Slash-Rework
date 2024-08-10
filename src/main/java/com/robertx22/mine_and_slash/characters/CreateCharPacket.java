package com.robertx22.mine_and_slash.characters;

import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class CreateCharPacket extends MyPacket<CreateCharPacket> {

    String name;

    public CreateCharPacket(String name) {
        this.name = name;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return SlashRef.id("create_char");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {
        name = buf.readUtf();
    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {
        buf.writeUtf(name);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        var chars = Load.player(ctx.getPlayer()).characters;

        chars.tryAddNewCharacter(ctx.getPlayer(), name);

        var opt = chars.getByName(name);

        if (opt.isPresent()) {
            var slot = chars.getSlotOf(opt.get());
            if (slot > -1) {
                chars.load(slot, ctx.getPlayer());
            }
        }
    }

    @Override
    public MyPacket<CreateCharPacket> newInstance() {
        return new CreateCharPacket("");
    }
}
