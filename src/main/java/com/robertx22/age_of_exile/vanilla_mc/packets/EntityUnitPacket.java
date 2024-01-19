package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class EntityUnitPacket extends MyPacket<EntityUnitPacket> {

    public int id;
    public CompoundTag nbt;

    public EntityUnitPacket() {

    }

    public EntityUnitPacket(Entity entity) {
        this.id = entity.getId();
        this.nbt = Load.Unit(entity)
                .serializeNBT();
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "enpack");
    }

    @Override
    public void loadFromData(FriendlyByteBuf tag) {
        id = tag.readInt();
        nbt = tag.readNbt();

    }

    @Override
    public void saveToData(FriendlyByteBuf tag) {
        tag.writeInt(id);
        tag.writeNbt(nbt);

    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        Entity entity = ctx.getPlayer().level().getEntity(id);

        if (entity instanceof LivingEntity) {

            LivingEntity en = (LivingEntity) entity;

            Load.Unit(en).deserializeNBT(nbt);
        }
    }

    @Override
    public MyPacket<EntityUnitPacket> newInstance() {
        return new EntityUnitPacket();
    }
}
