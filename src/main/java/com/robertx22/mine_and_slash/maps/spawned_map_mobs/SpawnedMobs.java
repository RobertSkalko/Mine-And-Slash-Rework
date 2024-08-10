package com.robertx22.mine_and_slash.maps.spawned_map_mobs;

import com.robertx22.mine_and_slash.database.registry.ExileDBInit;
import com.robertx22.mine_and_slash.tags.all.DungeonTags;
import net.minecraft.world.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class SpawnedMobs {

    public static void init() {

        List<SpawnedMob> defaultMobs = new ArrayList<>();
        defaultMobs.add(new SpawnedMob(EntityType.WITHER_SKELETON, 100).setNether().setUndead().setCanBeBoss(true));
        defaultMobs.add(new SpawnedMob(EntityType.BLAZE, 100).setRanged().setFire().setNether().setCanBeBoss(true));

        defaultMobs.add(new SpawnedMob(EntityType.ZOMBIE, 1200).setUndead());
        defaultMobs.add(new SpawnedMob(EntityType.ZOMBIFIED_PIGLIN, 500).setUndead());
        defaultMobs.add(new SpawnedMob(EntityType.SKELETON, 400).setRanged().setNether().setUndead());
        defaultMobs.add(new SpawnedMob(EntityType.MAGMA_CUBE, 100).setFire().setNether());
        defaultMobs.add(new SpawnedMob(EntityType.ENDERMITE, 10));
        defaultMobs.add(new SpawnedMob(EntityType.CAVE_SPIDER, 200).setSpider());
        defaultMobs.add(new SpawnedMob(EntityType.SPIDER, 300).setSpider());
        defaultMobs.add(new SpawnedMob(EntityType.PILLAGER, 500).setRanged());
        defaultMobs.add(new SpawnedMob(EntityType.WITCH, 200).setRanged().setCanBeBoss(true));
        defaultMobs.add(new SpawnedMob(EntityType.VINDICATOR, 100).setCanBeBoss(true));
        defaultMobs.add(new SpawnedMob(EntityType.SLIME, 5));
        defaultMobs.add(new SpawnedMob(EntityType.EVOKER, 25).setCanBeBoss(true));
        defaultMobs.add(new SpawnedMob(EntityType.ILLUSIONER, 25));
        defaultMobs.add(new SpawnedMob(EntityType.HUSK, 500).setUndead());
        defaultMobs.add(new SpawnedMob(EntityType.RAVAGER, 5));
        defaultMobs.add(new SpawnedMob(EntityType.STRAY, 300).setRanged().setUndead());

        new SpawnedMobList("default", 1000, defaultMobs, DungeonTags.DEFAULT).addToSerializables();

        List<SpawnedMob> forest = new ArrayList<>();
        forest.add(new SpawnedMob(EntityType.CAVE_SPIDER, 1000).setSpider());
        forest.add(new SpawnedMob(EntityType.SPIDER, 500).setSpider());
        forest.add(new SpawnedMob(EntityType.WITCH, 100).setRanged().setCanBeBoss(true));
        forest.add(new SpawnedMob(EntityType.SLIME, 250));
        new SpawnedMobList("forest", 1000, forest, DungeonTags.FOREST).addToSerializables();


        List<SpawnedMob> unknown = new ArrayList<>();
        unknown.add(new SpawnedMob(EntityType.PIG, 200));
        new SpawnedMobList(ExileDBInit.UNKNOWN_ID, 0, unknown).addToSerializables();


    }


}
