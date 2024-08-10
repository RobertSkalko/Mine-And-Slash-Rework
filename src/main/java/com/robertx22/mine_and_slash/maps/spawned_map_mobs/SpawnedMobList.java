package com.robertx22.mine_and_slash.maps.spawned_map_mobs;

import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.tags.TagList;
import com.robertx22.mine_and_slash.tags.imp.DungeonTag;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpawnedMobList implements JsonExileRegistry<SpawnedMobList>, IAutoGson<SpawnedMobList> {

    public static SpawnedMobList SERIALIZER = new SpawnedMobList();


    public TagList<DungeonTag> possible_dungeon_tags = new TagList<DungeonTag>();
    public String id = "";
    public int weight = 1000;
    public List<SpawnedMob> mobs = new ArrayList<>();

    public SpawnedMobList(String id, int weight, List<SpawnedMob> mobs, DungeonTag... tags) {
        this.possible_dungeon_tags.addAll(Arrays.asList(tags));
        this.id = id;
        this.weight = weight;
        this.mobs = mobs;
    }

    public SpawnedMobList() {
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.SPAWNED_MOBS;
    }

    @Override
    public Class<SpawnedMobList> getClassForSerialization() {
        return SpawnedMobList.class;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return weight;
    }
}
