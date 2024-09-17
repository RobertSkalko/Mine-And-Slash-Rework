package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle;

import com.google.common.collect.ImmutableMap;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.Original;
import com.robertx22.mine_and_slash.config.forge.ClientConfigs;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.NumberUtils;
import com.robertx22.mine_and_slash.vanilla_mc.packets.interaction.IParticleSpawnNotifier;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SpellResultParticleSpawner {

    public enum SpawnType {
        DAMAGE(ClientConfigs.getConfig().DAMAGE_PARTICLE_STYLE.get().damageStrategy, new ElementDamageParticle.DamageInformation(null, null, false)),
        NULLIFIED_DAMAGE(ClientConfigs.getConfig().DAMAGE_PARTICLE_STYLE.get().nullifiedDamageStrategy, DamageNullifiedParticle.Type.DODGE);
        public final BiConsumer<IParticleSpawnNotifier, Entity> strategy;
        public final IParticleSpawnNotifier target;

        SpawnType(BiConsumer<IParticleSpawnNotifier, Entity> strategy, IParticleSpawnNotifier target) {
            this.strategy = strategy;
            this.target = target;
        }
    }

    public enum ClientParticleSpawnStrategy {
        ORIGINAL((info, entity) -> {
            var mat = (ElementDamageParticle.DamageInformation) info;
            ImmutableMap<Elements, Float> dmgMap = mat.getDmgMap();

            boolean crit = mat.isCrit();
            for (Map.Entry<Elements, Float> entry : dmgMap.entrySet()) {
                Float damage = entry.getValue();
                if (damage
                        .intValue() > 0) {

                    double x = entity.getRandomX(0.5D);
                    double y = entity.getEyeY();
                    double z = entity.getRandomZ(0.5D);
                    String damageString = NumberUtils.format(damage);
                    Minecraft.getInstance().particleEngine.add(new ElementDamageParticle(Minecraft.getInstance().level, x, y, z, new Original(), entry.getKey().format.getColor(), crit ? damageString + "!" : damageString));
                }
            }
        },
                (type, entity) -> {
                    var mat = (DamageNullifiedParticle.Type) type;
                    double x = entity.getRandomX(0.5D);
                    double y = entity.getEyeY();
                    double z = entity.getRandomZ(0.5D);
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
