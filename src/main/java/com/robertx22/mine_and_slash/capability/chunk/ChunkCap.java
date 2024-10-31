package com.robertx22.mine_and_slash.capability.chunk;

import com.robertx22.library_of_exile.components.ICap;
import com.robertx22.library_of_exile.main.ExileLog;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChunkCap implements ICap {


    public static final ResourceLocation RESOURCE = new ResourceLocation(SlashRef.MODID, "chunk");
    public static Capability<ChunkCap> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {
    });

    transient final LazyOptional<ChunkCap> supp = LazyOptional.of(() -> this);

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

    public ChunkCap(LevelChunk chunk) {
        this.chunk = chunk;
    }


    List<CompoundTag> savedMobs = new ArrayList<>();

    public void tryLoadMobs(Level world) {
        if (!savedMobs.isEmpty()) {
            try {

                for (CompoundTag nbt : savedMobs) {
                    var en = EntityType.loadEntityRecursive(nbt, world, x -> {
                        return x;
                    });
                    world.addFreshEntity(en);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            savedMobs.clear();
        }
    }

    public List<UUID> mobIds = new ArrayList<>();

    public void trySaveMob(LivingEntity en) {
        if (en instanceof Player) {
            return;
        }

        if (savedMobs.size() > 40) {
            ExileLog.get().warn("Saved too many mobs in 1 chunk, stopping just in case");
            return;
        }
        if (mobIds.contains(en.getUUID())) {
            return;
        }
        mobIds.add(en.getUUID());

        var nbt = en.serializeNBT();

        savedMobs.add(nbt);
    }

    @Override
    public CompoundTag serializeNBT() {

        CompoundTag nbt = new CompoundTag();

        try {
            nbt.putBoolean("gen", generatedTerrain);
            nbt.putBoolean("genmobs", generatedMobs);

            nbt.putInt("mobs", savedMobs.size());

            for (int i = 0; i < savedMobs.size(); i++) {
                nbt.put(i + "", savedMobs.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

        try {
            this.generatedTerrain = nbt.getBoolean("gen");
            this.generatedMobs = nbt.getBoolean("genmobs");

            int mobs = nbt.getInt("mobs");

            this.savedMobs = new ArrayList<>();

            for (int i = 0; i < mobs; i++) {
                savedMobs.add(nbt.getCompound(i + ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getCapIdForSyncing() {
        return "chunk_data";
    }

}
