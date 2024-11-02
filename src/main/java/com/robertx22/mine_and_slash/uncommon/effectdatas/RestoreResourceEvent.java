package com.robertx22.mine_and_slash.uncommon.effectdatas;

import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.InteractionNotifier;
import com.robertx22.mine_and_slash.capability.entity.CooldownsData;
import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.saveclasses.unit.ResourceType;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.mine_and_slash.vanilla_mc.packets.interaction.IParticleSpawnMaterial;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class RestoreResourceEvent extends EffectEvent {

    public static String ID = "on_restore_resource";

    protected RestoreResourceEvent(float num, LivingEntity source, LivingEntity target) {
        super(num, source, target);
    }

    @Override
    public String GUID() {
        return ID;
    }

    @Override
    public String getName() {
        return "Restore Resource Event";
    }

    @Override
    protected void activate() {
        if (data.isCanceled()) {
            return;
        }
        float num = data.getNumber();

        // todo will see if this is good or not
        if (data.getRestoreType() == RestoreType.regen) {
            if (Load.Unit(target).getCooldowns().isOnCooldown(CooldownsData.IN_COMBAT)) {
                var type = data.getResourceType();
                if (type != ResourceType.energy) {
                    num *= ServerContainer.get().IN_COMBAT_REGEN_MULTI.get();
                }
            }
        }

        this.targetData.getResources().restore(target, data.getResourceType(), num);

        if (this.data.getResourceType() == ResourceType.health) {
            if (data.getRestoreType() == RestoreType.heal) {
                if (source instanceof ServerPlayer p) {

                    if (source != target) {
                        InteractionNotifier.notifyClient(new IParticleSpawnMaterial.HealNumber(num), p, target);
                        /*String text = NumberUtils.format(num);
                        DmgNumPacket packet = new DmgNumPacket(target, text, data.isCrit(), ChatFormatting.GREEN);
                        Packets.sendToClient((Player) source, packet);*/
                    }
                }
            }
        }

        if (target instanceof Player p) {
            this.targetData.sync.setDirty();
        }

    }
}