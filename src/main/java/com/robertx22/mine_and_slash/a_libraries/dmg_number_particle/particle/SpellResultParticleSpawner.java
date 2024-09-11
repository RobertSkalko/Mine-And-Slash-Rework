package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle;

import com.google.common.collect.ImmutableMap;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.Original;
import com.robertx22.mine_and_slash.config.forge.ClientConfigs;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.vanilla_mc.packets.interaction.IParticleSpawnNotifier;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SpellResultParticleSpawner {

    public enum SpawnType {
        DAMAGE(ClientConfigs.getConfig().DAMAGE_PARTICLE_STYLE.get().damageStrategy),
        NULLIFIED_DAMAGE(ClientConfigs.getConfig().DAMAGE_PARTICLE_STYLE.get().nullifiedDamageStrategy);
        public final BiConsumer<IParticleSpawnNotifier, Entity> strategy;

        SpawnType(BiConsumer<IParticleSpawnNotifier, Entity> Strategy) {
            this.strategy = Strategy;
        }
    }

    public enum ClientParticleSpawnStrategy {
        ORIGINAL((info, entity) -> {
            var mat = (ElementDamageParticle.DamageInformation) info;
            ImmutableMap<Elements, Float> dmgMap = mat.getDmgMap();
            double x = entity.getRandomX(1.0D);
            double y = entity.getEyeY();
            double z = entity.getRandomZ(1.0D);

            boolean crit = ((ElementDamageParticle.DamageInformation) info).isCrit();
            for (Map.Entry<Elements, Float> entry : dmgMap.entrySet()) {
                if (entry.getValue()
                        .intValue() > 0) {
                    Minecraft.getInstance().particleEngine.add(new ElementDamageParticle(Minecraft.getInstance().level, x, y, z, new Original(), entry.getKey().format.getColor(), crit ? entry.getValue() + "!" : entry.getValue() + ""));
                }
            }
        },
                (type, entity) -> {
                    var mat = (DamageNullifiedParticle.Type) type;
                    double x = entity.getRandomX(1.0D);
                    double y = entity.getEyeY();
                    double z = entity.getRandomZ(1.0D);
                    Minecraft.getInstance().particleEngine.add(new DamageNullifiedParticle(Minecraft.getInstance().level, x, y, z, new Original(), mat));
                });

        public final BiConsumer<IParticleSpawnNotifier, Entity> damageStrategy;
        public final BiConsumer<IParticleSpawnNotifier, Entity> nullifiedDamageStrategy;

        ClientParticleSpawnStrategy(BiConsumer<IParticleSpawnNotifier, Entity> damageStrategy, BiConsumer<IParticleSpawnNotifier, Entity> nullifiedDamageStrategy) {
            this.damageStrategy = damageStrategy;
            this.nullifiedDamageStrategy = nullifiedDamageStrategy;
        }
    }

}
