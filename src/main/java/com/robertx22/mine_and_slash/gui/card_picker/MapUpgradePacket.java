package com.robertx22.mine_and_slash.gui.card_picker;

import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class MapUpgradePacket extends MyPacket<MapUpgradePacket> {

    MapUpgradeCard.MapOption option;

    public MapUpgradePacket(MapUpgradeCard.MapOption option) {
        this.option = option;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return SlashRef.id("map_upgrade_option_pick");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {

        option = buf.readEnum(MapUpgradeCard.MapOption.class);
    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {

        buf.writeEnum(option);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        var p = ctx.getPlayer();

        var can = option.canPick(p);
        if (can.can) {
            option.onPick(p);
        } else {
            p.sendSystemMessage(can.answer);
        }
        Load.player(ctx.getPlayer()).playerDataSync.setDirty();
    }

    @Override
    public MyPacket<MapUpgradePacket> newInstance() {
        return new MapUpgradePacket(MapUpgradeCard.MapOption.UPGRADE);
    }
}
