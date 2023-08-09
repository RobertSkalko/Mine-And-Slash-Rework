package com.robertx22.age_of_exile.maps.mobs;


import com.robertx22.age_of_exile.maps.DungeonRoom;
import com.robertx22.library_of_exile.registry.IWeighted;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpawnedMob implements IWeighted {

    private static List<SpawnedMob> all = new ArrayList<>();

    public SpawnedMob(EntityType<? extends Mob> type, int weight) {
        this.type = type;
        this.weight = weight;
    }

    public static SpawnedMob random(DungeonRoom room) {
        return RandomUtils.weightedRandom(getAll().stream()
                .filter(x -> room.canSpawnMob(x))
                .collect(Collectors.toList()));
    }

    public static List<SpawnedMob> getAll() {

        if (all.isEmpty()) {
            all.add(new SpawnedMob(EntityType.ZOMBIE, 1200).setUndead());
            all.add(new SpawnedMob(EntityType.WITHER_SKELETON, 100).setNether().setUndead().setCanBeBoss(true));
            all.add(new SpawnedMob(EntityType.SKELETON, 400).setRanged()
                    .setNether().setUndead());
            all.add(new SpawnedMob(EntityType.BLAZE, 100).setRanged()
                    .setFire()
                    .setNether().setCanBeBoss(true));
            all.add(new SpawnedMob(EntityType.MAGMA_CUBE, 100).setFire()
                    .setNether());
            all.add(new SpawnedMob(EntityType.ENDERMITE, 10));
            all.add(new SpawnedMob(EntityType.CAVE_SPIDER, 200).setSpider());
            all.add(new SpawnedMob(EntityType.SPIDER, 300).setSpider());
            all.add(new SpawnedMob(EntityType.PILLAGER, 500).setRanged());
            all.add(new SpawnedMob(EntityType.WITCH, 200).setRanged().setCanBeBoss(true));
            all.add(new SpawnedMob(EntityType.VINDICATOR, 100).setCanBeBoss(true));
            all.add(new SpawnedMob(EntityType.SLIME, 5));
            all.add(new SpawnedMob(EntityType.EVOKER, 50).setCanBeBoss(true));
            all.add(new SpawnedMob(EntityType.ILLUSIONER, 50).setCanBeBoss(true));
            all.add(new SpawnedMob(EntityType.HUSK, 500).setUndead());
            all.add(new SpawnedMob(EntityType.RAVAGER, 50));
            all.add(new SpawnedMob(EntityType.STRAY, 300).setRanged().setUndead());
        }

        return all;

    }

    public EntityType<? extends Mob> type;
    public boolean isRanged = false;
    public boolean isSpider = false;
    public boolean isNether = false;
    public boolean isUndead = false;
    public boolean isFire = false;

    int weight = 1000;
    public boolean canBeBoss = false;

    public SpawnedMob setRanged() {
        this.isRanged = true;
        return this;
    }

    public SpawnedMob setNether() {
        this.isNether = true;
        return this;
    }

    public SpawnedMob setUndead() {
        this.isUndead = true;
        return this;
    }

    public SpawnedMob setSpider() {
        this.isSpider = true;
        return this;
    }

    public SpawnedMob setFire() {
        this.isFire = true;
        return this;
    }

    public SpawnedMob setCanBeBoss(Boolean bool) {
        this.canBeBoss = bool;
        return this;
    }

    @Override
    public int Weight() {
        return weight;
    }
}