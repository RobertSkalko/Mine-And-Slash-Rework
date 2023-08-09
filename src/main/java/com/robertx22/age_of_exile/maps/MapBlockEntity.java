package com.robertx22.age_of_exile.maps;

import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MapBlockEntity extends BlockEntity {


    public String mapId = "";

    public MapBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(SlashBlockEntities.MAP.get(), pPos, pBlockState);

    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putString("mapid", mapId);
    }

    @Override
    public void load(CompoundTag pTag) {
        this.mapId = pTag.getString("mapid");
        super.load(pTag);
    }

}
