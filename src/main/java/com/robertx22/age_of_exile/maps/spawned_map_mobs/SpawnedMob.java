package com.robertx22.age_of_exile.maps.spawned_map_mobs;


import com.robertx22.age_of_exile.maps.MapData;
import com.robertx22.age_of_exile.tags.TagList;
import com.robertx22.age_of_exile.tags.all.MobTags;
import com.robertx22.age_of_exile.tags.imp.MobTag;
import com.robertx22.library_of_exile.registry.IWeighted;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.registries.ForgeRegistries;

public class SpawnedMob implements IWeighted {


    public SpawnedMob(EntityType<? extends Mob> type, int weight) {
        this.type = ForgeRegistries.ENTITY_TYPES.getKey(type).toString();
        this.weight = weight;
    }

    public static SpawnedMob random(MapData map) {
        return RandomUtils.weightedRandom(map.getMobSpawns().mobs);
    }


    public String type;

    public EntityType getType() {
        return ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(type));
    }

    int weight = 1000;

    public TagList<MobTag> tags = new TagList<>();

    public boolean canBeBoss = false; // todo replace this with ACTUAL bosses!!!!

    public SpawnedMob setRanged() {
        this.tags.add(MobTags.RANGED);
        return this;
    }

    public SpawnedMob setNether() {
        this.tags.add(MobTags.NETHER);
        return this;
    }

    public SpawnedMob setUndead() {
        this.tags.add(MobTags.UNDEAD);
        return this;
    }

    public SpawnedMob setSpider() {
        this.tags.add(MobTags.SPIDER);
        return this;
    }

    public SpawnedMob setFire() {
        this.tags.add(MobTags.FIRE);
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