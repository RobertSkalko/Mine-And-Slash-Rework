package com.robertx22.mine_and_slash.vanilla_mc.packets.interaction;

import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.InteractionResultHandler;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class ExileInteractionResultParticlePacket extends MyPacket<ExileInteractionResultParticlePacket> {


    public int id;
    private InteractionResultHandler.ParticleSpawnType type;
    private IParticleSpawnNotifier notifier;


    public ExileInteractionResultParticlePacket(int id, IParticleSpawnNotifier notifier) {
        this.id = id;
        this.notifier = notifier;
        this.type = notifier.getSpawnType();
    }

    public ExileInteractionResultParticlePacket() {
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "eirpp");
    }

    @Override
    public void loadFromData(FriendlyByteBuf friendlyByteBuf) {
        this.id = friendlyByteBuf.readInt();
        this.type = friendlyByteBuf.readEnum(InteractionResultHandler.ParticleSpawnType.class);
        this.notifier = this.type.target.loadFromData(friendlyByteBuf);
    }

    @Override
    public void saveToData(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(id);
        friendlyByteBuf.writeEnum(type);
        notifier.saveToBuf(friendlyByteBuf);
    }

    @Override
    public void onReceived(ExilePacketContext exilePacketContext) {
        Entity entity = exilePacketContext.getPlayer().level().getEntity(id);
        System.out.println("received!");
        type.strategy.accept(notifier, entity);
    }

    @Override
    public MyPacket<ExileInteractionResultParticlePacket> newInstance() {
        return new ExileInteractionResultParticlePacket();
    }

}
