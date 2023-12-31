package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

import static com.robertx22.age_of_exile.database.data.spells.spell_fx.FXInfoHolder.clientPlayerEntityFXHolder;

public class sendSpellEntityDeath extends MyPacket<sendSpellEntityDeath> {

    UUID entityUUID = null;


    public sendSpellEntityDeath(UUID entityUUID) {
        this.entityUUID = entityUUID;
    }

    public sendSpellEntityDeath() {
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "spellentitydeath");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {
        this.entityUUID = buf.readUUID();

    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {
        buf.writeUUID(this.entityUUID);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        if(ctx.getPlayer().level().isClientSide() && clientPlayerEntityFXHolder.containsKey(this.entityUUID)){
            clientPlayerEntityFXHolder.get(this.entityUUID).setStop(true);
            clientPlayerEntityFXHolder.remove(this.entityUUID);
        }

    }

    @Override
    public MyPacket<sendSpellEntityDeath> newInstance() {
        return new sendSpellEntityDeath();
    }
}
