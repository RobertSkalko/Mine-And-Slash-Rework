package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.library_of_exile.utils.TeleportUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class PlayerMapData {

    // there is always  1 map per player, so this isnt needed, player id is the current map id
    //public String currentMapId = "";

    public String tpbackdim = "";

    public long tp_back_pos = 0;

    private BlockPos getTeleportBackPos() {
        return BlockPos.of(tp_back_pos);
    }

    public void teleportBack(Player p) {
        if (p.level().isClientSide) {
            return;
        }
        BlockPos pos = getTeleportBackPos();
        TeleportUtils.teleport((ServerPlayer) p, pos, new ResourceLocation(tpbackdim), false);

    }
}
