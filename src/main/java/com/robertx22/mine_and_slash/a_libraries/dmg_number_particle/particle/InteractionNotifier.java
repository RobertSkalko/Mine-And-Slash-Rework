package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle;

import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.mine_and_slash.config.forge.ClientConfigs;
import com.robertx22.mine_and_slash.vanilla_mc.packets.interaction.ExileInteractionResultPacket;
import com.robertx22.mine_and_slash.vanilla_mc.packets.interaction.IParticleSpawnMaterial;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

public class InteractionNotifier {

    public static void notifyClient(IParticleSpawnMaterial notifier, ServerPlayer source, LivingEntity target){
        Packets.sendToClient(source, new ExileInteractionResultPacket(target.getId(), notifier));
    }

}
