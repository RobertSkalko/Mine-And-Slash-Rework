package com.robertx22.mine_and_slash.database.data.boss_arena;

import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BossArena implements JsonExileRegistry<BossArena>, IAutoGson<BossArena> {

    public static BossArena SERIALIZER = new BossArena("", 1, new ArrayList<>(), 0);

    public String id = "";

    public List<String> possible_bosses = new ArrayList<>();

    public EntityType getRandomBoss() {
        return RandomUtils.randomFromList(possible_bosses.stream().map(x -> BuiltInRegistries.ENTITY_TYPE.get(new ResourceLocation(x))).collect(Collectors.toList()));
    }

    public int weight = 1000;

    public int size = 0;

    public BossArena(String id, int size, List<String> possible_bosses, int weight) {
        this.id = id;
        this.possible_bosses = possible_bosses;
        this.size = size;
        this.weight = weight;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.BOSS_ARENA;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public Class<BossArena> getClassForSerialization() {
        return BossArena.class;
    }

    @Override
    public int Weight() {
        return weight;
    }
}
