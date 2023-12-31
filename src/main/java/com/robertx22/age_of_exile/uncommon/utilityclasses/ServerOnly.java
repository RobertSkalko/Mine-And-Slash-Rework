package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServerOnly {

    public static Entity getEntityByUUID(Level world, UUID id) {

        if (world instanceof ServerLevel) {
            return ((ServerLevel) world).getEntity(id);
        }

        return null;

    }

    public static List<ServerPlayer> getPlayerWithinRange(Vec3 origin, Level world, Double range){
        if (world instanceof ServerLevel) {
            var wholePlayerList = ((ServerLevel)world).players();
            return wholePlayerList.stream()
                    .map(player -> Pair.of(player, player.blockPosition()))
                    .filter(blockPosPair -> blockPosPair.getRight().closerToCenterThan(origin, range))
                    .map(Pair::getLeft)
                    .toList();
        }
        return new ArrayList<>();
    }

}
