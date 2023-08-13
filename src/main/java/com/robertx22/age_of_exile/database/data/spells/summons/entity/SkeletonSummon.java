package com.robertx22.age_of_exile.database.data.spells.summons.entity;

import com.robertx22.age_of_exile.aoe_data.database.spells.SummonType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;

public class SkeletonSummon extends SummonEntity {
    public SkeletonSummon(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

    }

    @Override
    public SummonType summonType() {
        return SummonType.UNDEAD;
    }

    @Override
    public boolean usesRanged() {
        return true;
    }

    // todo maybe better way
    @Override
    public boolean countsTowardsMaxSummons() {
        return false;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {

        var d = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);

        this.equipItemIfPossible(Items.BOW.getDefaultInstance());

        return d;
    }

    @Override
    public boolean usesMelee() {
        return false;
    }
}
