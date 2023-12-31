package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

import static com.robertx22.age_of_exile.event_hooks.player.OnLogin.clientPlayerEntityFXHolder;

public class sendSpellEntityPositionPacket extends MyPacket<sendSpellEntityPositionPacket> {

    UUID entityUUID = null;


    Vec3 pos = new Vec3(0,0,0);

    public sendSpellEntityPositionPacket(UUID entityUUID, Vec3 pos) {
        this.entityUUID = entityUUID;
        this.pos = pos;
    }

    public sendSpellEntityPositionPacket() {
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "spellfxupdate");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {
        this.entityUUID = buf.readUUID();
        var x = buf.readDouble();
        var y = buf.readDouble();
        var z = buf.readDouble();
        this.pos = new Vec3(x, y, z);

    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {
        buf.writeUUID(this.entityUUID);
        buf.writeDouble(this.pos.x);
        buf.writeDouble(this.pos.y);
        buf.writeDouble(this.pos.z);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        if(ctx.getPlayer().level().isClientSide() && clientPlayerEntityFXHolder.containsKey(this.entityUUID)){
            clientPlayerEntityFXHolder.get(this.entityUUID).setNewPos(this.pos);
            clientPlayerEntityFXHolder.get(this.entityUUID).setLifespan(0);
        }

    }

    @Override
    public MyPacket<sendSpellEntityPositionPacket> newInstance() {
        return new sendSpellEntityPositionPacket();
    }
}
