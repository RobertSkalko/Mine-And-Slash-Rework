package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.capability.player.PlayerData;
import com.robertx22.age_of_exile.database.data.game_balance_config.PlayerPointsType;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.CoreStat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class AllocateStatPacket extends MyPacket<AllocateStatPacket> {

    public String stat;
    AllocateStatPacket.ACTION action;

    public enum ACTION {
        ALLOCATE, REMOVE
    }

    public AllocateStatPacket() {

    }

    public AllocateStatPacket(Stat stat) {
        this.stat = stat.GUID();
        this.action = ACTION.ALLOCATE;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "stat_alloc");
    }

    @Override
    public void loadFromData(FriendlyByteBuf tag) {
        stat = tag.readUtf(30);
        action = tag.readEnum(AllocateStatPacket.ACTION.class);

    }

    @Override
    public void saveToData(FriendlyByteBuf tag) {
        tag.writeUtf(stat, 30);
        tag.writeEnum(action);

    }

    @Override
    public void onReceived(ExilePacketContext ctx) {

        Load.Unit(ctx.getPlayer()).setEquipsChanged();

        PlayerData cap = Load.player(ctx.getPlayer());

        if (PlayerPointsType.STATS.getFreePoints(ctx.getPlayer()) > 0) {
            if (ExileDB.Stats().get(stat) instanceof CoreStat) {
                cap.statPoints.map.put(stat, 1 + cap.statPoints.map.getOrDefault(stat, 0));

                //TODO abandoncaptian, add Stat Rewards
                Load.Unit(ctx.getPlayer()).setEquipsChanged();
                cap.playerDataSync.setDirty();
            }
        }
    }

    @Override
    public MyPacket<AllocateStatPacket> newInstance() {
        return new AllocateStatPacket();
    }
}
