package com.robertx22.mine_and_slash.gui.screens.map;

import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import com.robertx22.mine_and_slash.database.data.league.LeagueMechanics;
import com.robertx22.mine_and_slash.database.data.league.LeagueStructure;
import com.robertx22.mine_and_slash.mechanics.base.LeagueTeleportBlock;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.EntityFinder;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.WorldUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;

public class MapTeleportPacket extends MyPacket<MapTeleportPacket> {
    public enum Type {
        TO_BOSS() {
            @Override
            public void teleport(ServerPlayer p) {

                var world = p.level();

                if (WorldUtils.isMapWorldClass(world)) {
                    if (!EntityFinder.start(p, Mob.class, p.blockPosition()).radius(4).searchFor(AllyOrEnemy.enemies).build().isEmpty()) {
                        p.sendSystemMessage(Chats.ENEMY_TOO_CLOSE.locName());
                        return;
                    }
                    var map = Load.mapAt(p.level(), p.blockPosition());

                    if (map == null) {
                        return;
                    }
                    var cantp = map.canTeleportToArena(p);
                    if (!cantp.can) {
                        p.sendSystemMessage(cantp.answer);
                        return;
                    }
                    var can = LeagueStructure.canTeleportToLeagueStart(p, LeagueMechanics.MAP_BOSS);
                    if (!can.can) {
                        p.sendSystemMessage(can.answer);
                        return;
                    }

                    var league = LeagueStructure.getMechanicFromPosition((ServerLevel) world, p.blockPosition());

                    if (league != null && league == LeagueMechanics.MAP_BOSS) {
                        p.sendSystemMessage(Chats.ALREADY_IN_ARENA.locName());
                        return;
                    }

                    LeagueTeleportBlock.teleportToLeague(p, p.blockPosition(), LeagueMechanics.MAP_BOSS_ID);
                    return;
                } else {
                    p.sendSystemMessage(Chats.THIS_IS_ONLY_USABLE_INSIDE_A_MAP.locName());
                    return;
                }
            }
        }, BACK_HOME() {
            @Override
            public void teleport(ServerPlayer p) {

                // ttodo
            }
        };

        public abstract void teleport(ServerPlayer p);
    }


    public Type type;

    public MapTeleportPacket(Type type) {
        this.type = type;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "tpmap");
    }

    @Override
    public void loadFromData(FriendlyByteBuf tag) {
        type = tag.readEnum(Type.class);

    }

    @Override
    public void saveToData(FriendlyByteBuf tag) {
        tag.writeEnum(type);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        type.teleport((ServerPlayer) ctx.getPlayer());
    }

    @Override
    public MyPacket<MapTeleportPacket> newInstance() {
        return new MapTeleportPacket(Type.BACK_HOME);
    }


}
