package com.robertx22.mine_and_slash.maps;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MapsData {


    private List<MapData> maps = new ArrayList<>();

    public void deleteOldMap(Player p) {
        maps.removeIf(x -> x.playerUuid.equals(p.getStringUUID()));
    }


    public Optional<MapData> getCurrentMap(Player player) {
        return maps.stream().filter(x -> x.playerUuid.equals(player.getStringUUID())).findFirst();
    }

    public Optional<MapData> getMap(BlockPos pos) {
        return maps.stream().filter(x -> x.isMapHere(pos)).findFirst();
    }

    public Optional<MapData> getMap(ChunkPos pos) {
        return getMap(pos.getMiddleBlockPosition(50));
    }

    public Optional<MapData> getMapFromPlayerID(String id) {
        return maps.stream().filter(x -> x.playerUuid.equals(id)).findFirst();

    }

    public MapData startNewMap(Player player, MapItemData data) {
        MapData m = MapData.newMap(player, data, this);
        this.maps.add(m);
        return m;
    }

}
