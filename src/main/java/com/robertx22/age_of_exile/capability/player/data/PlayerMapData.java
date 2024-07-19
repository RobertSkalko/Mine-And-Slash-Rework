package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.maps.MapData;
import com.robertx22.library_of_exile.utils.TeleportUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class PlayerMapData {

    // there is always  1 map per player, so this isnt needed, player id is the current map id
    //public String currentMapId = "";

    public boolean sendMapTpMsg = false;
    public String tpbackdim = "";
    public long tp_back_pos = 0;
    public long tp_back_from_league_pos = 0;


    private BlockPos getTeleportBackPos() {
        return BlockPos.of(tp_back_pos);
    }

    public void teleportBackFromLeagueToDungeon(Player p) {
        if (p.level().isClientSide) {
            return;
        }

        BlockPos pos = BlockPos.of(tp_back_from_league_pos);
        if (tp_back_from_league_pos == 0) {
            pos = MapData.getDungeonStartTeleportPos(p.blockPosition());
        }

        TeleportUtils.teleport((ServerPlayer) p, pos);
    }

    public void teleportBack(Player p) {
        if (p.level().isClientSide) {
            return;
        }
        BlockPos pos = getTeleportBackPos();
        TeleportUtils.teleport((ServerPlayer) p, pos, new ResourceLocation(tpbackdim.isEmpty() ? "minecraft:overworld" : tpbackdim));

    }
}
