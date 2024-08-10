package com.robertx22.mine_and_slash.database.data.profession;

import net.minecraft.core.BlockPos;

public class StationSyncData {

    public static StationSyncData SYNCED_DATA = new StationSyncData();


    public Long pos = 0L;
    public Boolean recipe_locked = false;
    public Crafting_State craftingState = Crafting_State.STOPPED;
    public String recipe = "";

    public BlockPos getBlockPos() {
        return BlockPos.of(pos);
    }

    public StationSyncData(ProfessionBlockEntity be) {
        this.recipe_locked = be.recipe_locked;
        this.pos = be.getBlockPos().asLong();
        this.craftingState = be.craftingState;
        if (be.getCurrentRecipe() != null) {
            this.recipe = be.getCurrentRecipe().GUID();
        }
    }

    public StationSyncData() {
    }
}
