package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.capability.entity.CooldownsData;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.age_of_exile.uncommon.utilityclasses.NumberUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.DmgNumPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.List;

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
                if (source instanceof Player) {

                    if (source != target) {
                        String text = NumberUtils.format(num);
                        DmgNumPacket packet = new DmgNumPacket(target, text, data.isCrit(), ChatFormatting.GREEN);
                        Packets.sendToClient((Player) source, packet);
                    }

                    float threat = (int) (num * 0.1F);
                    List<Mob> mobs = EntityFinder.start(source, Mob.class, source.blockPosition())
                            .radius(10)
                            .build();
                    for (Mob x : mobs) {
                        GenerateThreatEvent threatEvent = new GenerateThreatEvent((Player) source, x, ThreatGenType.heal, threat);
                        threatEvent.Activate();
                    }
                }
            }
        }

    }
}