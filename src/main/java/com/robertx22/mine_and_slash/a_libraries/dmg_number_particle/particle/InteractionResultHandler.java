package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle;

import com.google.common.collect.ImmutableMap;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.Original;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.Wynn;
import com.robertx22.mine_and_slash.config.forge.ClientConfigs;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.NumberUtils;
import com.robertx22.mine_and_slash.vanilla_mc.packets.interaction.IParticleSpawnNotifier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;

import java.util.Comparator;
import java.util.Map;
import java.util.function.BiConsumer;

public class InteractionResultHandler {

    public enum ParticleSpawnType {
        DAMAGE(ClientConfigs.getConfig().DAMAGE_PARTICLE_STYLE.get().damageStrategy, new ElementDamageParticle.DamageInformation(null, null, false)),
        NULLIFIED_DAMAGE(ClientConfigs.getConfig().DAMAGE_PARTICLE_STYLE.get().nullifiedDamageStrategy, DamageNullifiedParticle.Type.DODGE),
        HEAL(ClientConfigs.getConfig().DAMAGE_PARTICLE_STYLE.get().healStrategy, new HealParticle.HealNumber(0.0f));
        public final BiConsumer<IParticleSpawnNotifier, Entity> strategy;
        public final IParticleSpawnNotifier target;

        ParticleSpawnType(BiConsumer<IParticleSpawnNotifier, Entity> strategy, IParticleSpawnNotifier target) {
            this.strategy = strategy;
            this.target = target;
        }
    }

    public enum ClientReactionStrategy {
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
                    ClientOnly.getPlayer().level().playLocalSound(entity.blockPosition(), mat.sound, SoundSource.PLAYERS, 1, 1.5F, true);
                },
                (type, entity) -> {
                    var mat = (HealParticle.HealNumber) type;
                    double x = entity.getRandomX(0.5D);
                    double y = entity.getEyeY();
                    double z = entity.getRandomZ(0.5D);
                    Minecraft.getInstance().particleEngine.add(new HealParticle(Minecraft.getInstance().level, x, y, z, new Original(), mat.number()));
                }),

        WYNN((info, entity) -> {
            var mat = (ElementDamageParticle.DamageInformation) info;
            ImmutableMap<Elements, Float> dmgMap = mat.getDmgMap();

            boolean crit = mat.isCrit();
            StringBuilder stringBuilder = new StringBuilder();
            dmgMap.entrySet().stream().sorted(Comparator.comparingInt(entry -> entry.getKey().ordinal())).forEachOrdered(x -> {
                Elements key = x.getKey();
                stringBuilder.append(key.format).append("- ").append(NumberUtils.format(x.getValue()));
            });
            String string = stringBuilder.toString();
            double x = entity.getX();
            double y = entity.getEyeY();
            double z = entity.getZ();
            Minecraft.getInstance().particleEngine.add(new ElementDamageParticle(Minecraft.getInstance().level, x, y, z, new Wynn(), ChatFormatting.WHITE.getColor(), crit ? string + "!" : string));

        },
                (type, entity) -> {
            var mat = (DamageNullifiedParticle.Type) type;
                    double x = entity.getX();
                    double y = entity.getEyeY();
                    double z = entity.getZ();
            Minecraft.getInstance().particleEngine.add(new DamageNullifiedParticle(Minecraft.getInstance().level, x, y, z, new Wynn(), mat));
        },
                (type, entity) -> {
                    var mat = (HealParticle.HealNumber) type;
                    double x = entity.getX();
                    double y = entity.getEyeY();
                    double z = entity.getZ();
                    Minecraft.getInstance().particleEngine.add(new HealParticle(Minecraft.getInstance().level, x, y, z, new Wynn(), mat.number()));
                });

        public final BiConsumer<IParticleSpawnNotifier, Entity> damageStrategy;
        public final BiConsumer<IParticleSpawnNotifier, Entity> nullifiedDamageStrategy;
        public final BiConsumer<IParticleSpawnNotifier, Entity> healStrategy;

        ClientReactionStrategy(BiConsumer<IParticleSpawnNotifier, Entity> damageStrategy, BiConsumer<IParticleSpawnNotifier, Entity> nullifiedDamageStrategy, BiConsumer<IParticleSpawnNotifier, Entity> healStrategy) {
            this.damageStrategy = damageStrategy;
            this.nullifiedDamageStrategy = nullifiedDamageStrategy;
            this.healStrategy = healStrategy;
        }
    }

}
