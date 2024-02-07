package com.robertx22.age_of_exile.capability.chunk;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.components.ICap;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChunkData implements ICap {


    public static final ResourceLocation RESOURCE = new ResourceLocation(SlashRef.MODID, "chunk");
    public static Capability<ChunkData> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {
    });

    transient final LazyOptional<ChunkData> supp = LazyOptional.of(() -> this);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == INSTANCE) {
            return supp.cast();
        }
        return LazyOptional.empty();

    }


    transient LevelChunk chunk;

    public boolean generatedMobs = false;
    public boolean generatedTerrain = false;

    public ChunkData(LevelChunk chunk) {
        this.chunk = chunk;
    }


    @Override
    public CompoundTag serializeNBT() {

        CompoundTag nbt = new CompoundTag();

        nbt.putBoolean("gen", generatedTerrain);
        nbt.putBoolean("genmobs", generatedMobs);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

        this.generatedTerrain = nbt.getBoolean("gen");
        this.generatedMobs = nbt.getBoolean("genmobs");

    }


    @Override
    public String getCapIdForSyncing() {
        return "chunk_data";
    }

}
