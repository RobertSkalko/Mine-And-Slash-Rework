package com.robertx22.mine_and_slash.maps;

import com.robertx22.mine_and_slash.maps.dungeon_reg.Dungeon;
import com.robertx22.mine_and_slash.maps.generator.RoomType;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.library_of_exile.registry.IWeighted;
import net.minecraft.resources.ResourceLocation;

public class DungeonRoom implements IWeighted {

    public ResourceLocation loc;
    public RoomType type;
    int weight = 1000;
    public boolean isBoss = false;
    public boolean isBarrier = false;

    public DungeonRoom(Dungeon dun, String id, RoomType type) {
        this.loc = new ResourceLocation(SlashRef.MODID, "dun/" + dun.id + "/" + type.id + "/" + id);
        this.type = type;
    }


    public DungeonRoom setBoss() {
        this.isBoss = true;
        return this;
    }


    public DungeonRoom weight(int weight) {
        this.weight = weight;
        return this;
    }

    @Override
    public int Weight() {
        return weight;
    }
}