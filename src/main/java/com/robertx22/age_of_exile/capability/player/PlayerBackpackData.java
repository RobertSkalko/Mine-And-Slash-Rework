package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.capability.player.data.Backpacks;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.components.ICap;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerBackpackData implements ICap {


    public static final ResourceLocation RESOURCE = new ResourceLocation(SlashRef.MODID, "backpacks");
    public static Capability<PlayerBackpackData> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {
    });

    public static PlayerBackpackData get(LivingEntity entity) {
        return entity.getCapability(INSTANCE).orElse(null);
    }

    transient final LazyOptional<PlayerBackpackData> supp = LazyOptional.of(() -> this);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == INSTANCE) {
            return supp.cast();
        }
        return LazyOptional.empty();

    }


    transient Player player;
    private Backpacks data;

    public PlayerBackpackData(Player player) {
        this.player = player;
        this.data = new Backpacks(player);
    }

    public Backpacks getBackpacks() {
        return data;
    }


    @Override
    public CompoundTag serializeNBT() {

        CompoundTag nbt = new CompoundTag();

        for (Backpacks.BackpackType type : Backpacks.BackpackType.values()) {
            nbt.put(type.id, data.getInv(type).createTag());
        }

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

        for (Backpacks.BackpackType type : Backpacks.BackpackType.values()) {
            data.getInv(type).fromTag(nbt.getList(type.id, 10)); // todo
        }
    }

    @Override
    public void syncToClient(Player player) {
        // dont sync backpacks to client
        //  Packets.sendToClient(player, new SyncPlayerCapToClient(player, this.getCapIdForSyncing()));
    }


    @Override
    public String getCapIdForSyncing() {
        return "backpack_data";
    }

}
