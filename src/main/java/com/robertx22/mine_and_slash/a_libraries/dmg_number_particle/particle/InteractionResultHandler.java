package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle;

import com.google.common.collect.ImmutableMap;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.impl.DamageNullifiedParticle;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.impl.ElementDamageParticle;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.impl.HealParticle;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.Original;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.Row;
import com.robertx22.mine_and_slash.config.forge.ClientConfigs;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.NumberUtils;
import com.robertx22.mine_and_slash.vanilla_mc.packets.interaction.IParticleSpawnMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;

import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;

public class InteractionResultHandler {



    public enum ParticleSpawnType {
        DAMAGE(ClientConfigs.getConfig().DAMAGE_PARTICLE_STYLE.get().damageStrategy, new ElementDamageParticle.DamageInformation(null, null, false)),
        NULLIFIED_DAMAGE(ClientConfigs.getConfig().DAMAGE_PARTICLE_STYLE.get().nullifiedDamageStrategy, DamageNullifiedParticle.Type.DODGE),
        HEAL(ClientConfigs.getConfig().DAMAGE_PARTICLE_STYLE.get().healStrategy, new HealParticle.HealNumber(0.0f));
        public final BiConsumer<IParticleSpawnMaterial, Entity> strategy;
        public final IParticleSpawnMaterial target;

        ParticleSpawnType(BiConsumer<IParticleSpawnMaterial, Entity> strategy, IParticleSpawnMaterial target) {
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


        ROW((info, entity) -> {
            var mat = (ElementDamageParticle.DamageInformation) info;
            ImmutableMap<Elements, Float> dmgMap = mat.getDmgMap();

            boolean crit = mat.isCrit();
            StringBuilder stringBuilder = new StringBuilder();
            dmgMap.entrySet().stream().sorted(Comparator.comparingInt(entry -> entry.getKey().ordinal())).forEachOrdered(x -> {
                Elements key = x.getKey();
                stringBuilder.append(key.format).append("-").append(NumberUtils.format(x.getValue()));
            });
            String string = stringBuilder.toString();
            Random random = new Random();
            double x = entity.getX() + random.nextDouble(-1d, 1d);
            double y = entity.getEyeY() + random.nextDouble(-0.5d, 0.8d);
            double z = entity.getZ();
            Minecraft.getInstance().particleEngine.add(new ElementDamageParticle(Minecraft.getInstance().level, x, y, z, new Row(), ChatFormatting.WHITE.getColor(), crit ? string + "!" : string));

        },
                (type, entity) -> {
            var mat = (DamageNullifiedParticle.Type) type;
                    double x = entity.getX();
                    double y = entity.getEyeY();
                    double z = entity.getZ();
            Minecraft.getInstance().particleEngine.add(new DamageNullifiedParticle(Minecraft.getInstance().level, x, y, z, new Row(), mat));
        },
                (type, entity) -> {
                    var mat = (HealParticle.HealNumber) type;
                    double x = entity.getX();
                    double y = entity.getEyeY();
                    double z = entity.getZ();
                    Minecraft.getInstance().particleEngine.add(new HealParticle(Minecraft.getInstance().level, x, y, z, new Row(), mat.number()));
                });

        public final BiConsumer<IParticleSpawnMaterial, Entity> damageStrategy;
        public final BiConsumer<IParticleSpawnMaterial, Entity> nullifiedDamageStrategy;
        public final BiConsumer<IParticleSpawnMaterial, Entity> healStrategy;

        ClientReactionStrategy(BiConsumer<IParticleSpawnMaterial, Entity> damageStrategy, BiConsumer<IParticleSpawnMaterial, Entity> nullifiedDamageStrategy, BiConsumer<IParticleSpawnMaterial, Entity> healStrategy) {
            this.damageStrategy = damageStrategy;
            this.nullifiedDamageStrategy = nullifiedDamageStrategy;
            this.healStrategy = healStrategy;
        }
    }

}
