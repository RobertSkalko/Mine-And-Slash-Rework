package com.robertx22.mine_and_slash.characters;

import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import com.robertx22.mine_and_slash.characters.reworked_gui.ToonActionButton;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class ToonActionPacket extends MyPacket<CreateCharPacket> {

    int num;
    ToonActionButton.Action act;
    String name;

    public ToonActionPacket(ToonActionButton.Action act, Integer num, String name) {
        this.num = num;
        this.act = act;
        this.name = name;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return SlashRef.id("toon_action");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {
        num = buf.readInt();
        act = buf.readEnum(ToonActionButton.Action.class);
        name = buf.readUtf();
    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {
        buf.writeInt(num);
        buf.writeEnum(act);
        buf.writeUtf(name);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        var chars = Load.player(ctx.getPlayer()).characters;

        var current = chars.current;
        var p = ctx.getPlayer();

        if (chars.canChangeCharactersRightNow(ctx.getPlayer())) {

            var opt = chars.map.get(num);

            if (opt != null) {
                if (act == ToonActionButton.Action.LOAD) {
                    if (this.num == current) {
                        p.sendSystemMessage(Chats.CANT_LOAD_CURRENT_CHAT.locName().withStyle(ChatFormatting.RED));
                    } else {
                        chars.load(num, ctx.getPlayer());
                    }
                }
                if (act == ToonActionButton.Action.DELETE) {
                    if (this.num == current) {
                        p.sendSystemMessage(Chats.CANT_DEL_CURRENT_CHAT.locName().withStyle(ChatFormatting.RED));
                    } else {
                        chars.map.remove(num);
                    }
                }
                if (act == ToonActionButton.Action.RENAME) {
                    if (chars.nameIsValid(p, name)) {
                        chars.map.get(num).name = name;
                    }
                }
            }
            Load.player(ctx.getPlayer()).playerDataSync.setDirty();
        }
    }

    @Override
    public MyPacket<CreateCharPacket> newInstance() {
        return new ToonActionPacket(ToonActionButton.Action.LOAD, -1, "");
    }
}
