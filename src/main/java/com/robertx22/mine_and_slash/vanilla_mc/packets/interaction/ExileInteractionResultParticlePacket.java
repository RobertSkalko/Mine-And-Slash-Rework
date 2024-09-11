package com.robertx22.mine_and_slash.vanilla_mc.packets.interaction;

import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.InteractionType;
import com.robertx22.mine_and_slash.config.forge.ClientConfigs;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class ExileInteractionResultParticlePacket extends MyPacket<ExileInteractionResultParticlePacket> {


    public int id;
    private IParticleSpawnNotifier notifier;


    public <T> ExileInteractionResultParticlePacket(InteractionType type, T mat, IParticleSpawnNotifier notifier) {

        this.notifier = notifier;
    }

    public ExileInteractionResultParticlePacket() {
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "eirpp");
    }

    @Override
    public void loadFromData(FriendlyByteBuf friendlyByteBuf) {
        this.notifier = notifier.loadFromData(friendlyByteBuf);
    }

    @Override
    public void saveToData(FriendlyByteBuf friendlyByteBuf) {
        notifier.saveToBuf(friendlyByteBuf);
    }

    @Override
    public void onReceived(ExilePacketContext exilePacketContext) {
        Entity entity = exilePacketContext.getPlayer().level().getEntity(id);

        notifier.getSpawnType().strategy.accept(notifier, entity);
    }

    @Override
    public MyPacket<ExileInteractionResultParticlePacket> newInstance() {
        return null;
    }

}
