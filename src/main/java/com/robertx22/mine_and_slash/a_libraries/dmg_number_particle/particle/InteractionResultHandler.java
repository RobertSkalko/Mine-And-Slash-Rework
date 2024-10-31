package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.impl.DamageNullifiedParticle;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.impl.ElementDamageParticle;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.impl.HealParticle;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.Default;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.IParticleRenderMaterial;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.Row;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.NumberUtils;
import com.robertx22.mine_and_slash.vanilla_mc.packets.interaction.IParticleSpawnMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;

public class InteractionResultHandler {



    public enum ExileParticleType {
        DAMAGE(new IParticleSpawnMaterial.DamageInformation(null, null, false)) ,
        NULLIFIED_DAMAGE(IParticleSpawnMaterial.Type.DODGE) ,
        HEAL(new IParticleSpawnMaterial.HealNumber(0.0f)) ;
        public final IParticleSpawnMaterial target;

        ExileParticleType(IParticleSpawnMaterial target) {
            this.target = target;
        }
    }

    public enum ClientSpawnStrategy {
        DEFAULT((info, entity) -> {
            var mat = (IParticleSpawnMaterial.DamageInformation) info;
            ImmutableMap<Elements, Float> dmgMap = mat.getDmgMap();

            boolean crit = mat.isCrit();
            for (Map.Entry<Elements, Float> entry : dmgMap.entrySet()) {
                Float damage = entry.getValue();
                if (damage.intValue() > 0) {

                    double x = entity.getRandomX(0.5D);
                    double y = entity.getEyeY();
                    double z = entity.getRandomZ(0.5D);
                    String damageString = NumberUtils.format(damage);
                    Minecraft.getInstance().particleEngine.add(new ElementDamageParticle(Minecraft.getInstance().level, x, y, z, new Default(), new IParticleRenderMaterial.singleElement(Pair.of(entry.getKey(), damageString), crit)));
                }
            }
        },
                (type, entity) -> {
                    var mat = (IParticleSpawnMaterial.Type) type;
                    double x = entity.getRandomX(0.5D);
                    double y = entity.getEyeY();
                    double z = entity.getRandomZ(0.5D);
                    Minecraft.getInstance().particleEngine.add(new DamageNullifiedParticle(Minecraft.getInstance().level, x, y, z, new Default(), mat));
                    ClientOnly.getPlayer().level().playLocalSound(entity.blockPosition(), mat.sound, SoundSource.PLAYERS, 1, 1.5F, true);
                },
                (type, entity) -> {
                    var mat = (IParticleSpawnMaterial.HealNumber) type;
                    double x = entity.getRandomX(0.5D);
                    double y = entity.getEyeY();
                    double z = entity.getRandomZ(0.5D);
                    Minecraft.getInstance().particleEngine.add(new HealParticle(Minecraft.getInstance().level, x, y, z, new Default(), mat.number()));
                }),


        IN_A_ROW((info, entity) -> {
            var mat = (IParticleSpawnMaterial.DamageInformation) info;
            ImmutableMap<Elements, Float> dmgMap = mat.getDmgMap();

            boolean crit = mat.isCrit();
            ImmutableList.Builder<Pair<Elements, String>> builder = ImmutableList.builder();
            dmgMap.entrySet().stream().sorted(Comparator.comparingInt(entry -> entry.getKey().ordinal())).forEachOrdered(x -> {
                Elements key = x.getKey();
                String value = NumberUtils.format(x.getValue());
                builder.add(Pair.of(key, value));
            });

            Random random = new Random();
            double x = entity.getX() + random.nextDouble(-1d, 1d);
            double y = entity.getEyeY() + random.nextDouble(-0.5d, 0.8d);
            double z = entity.getZ();
            Minecraft.getInstance().particleEngine.add(new ElementDamageParticle(Minecraft.getInstance().level, x, y, z, new Row(), new IParticleRenderMaterial.multipleElements(builder.build(), crit)));

        },
                (type, entity) -> {
            var mat = (IParticleSpawnMaterial.Type) type;
                    double x = entity.getX();
                    double y = entity.getEyeY();
                    double z = entity.getZ();
            Minecraft.getInstance().particleEngine.add(new DamageNullifiedParticle(Minecraft.getInstance().level, x, y, z, new Row(), mat));
        },
                (type, entity) -> {
                    var mat = (IParticleSpawnMaterial.HealNumber) type;
                    double x = entity.getX();
                    double y = entity.getEyeY();
                    double z = entity.getZ();
                    Minecraft.getInstance().particleEngine.add(new HealParticle(Minecraft.getInstance().level, x, y, z, new Row(), mat.number()));
                });

        public final BiConsumer<IParticleSpawnMaterial, Entity> damageStrategy;
        public final BiConsumer<IParticleSpawnMaterial, Entity> nullifiedDamageStrategy;
        public final BiConsumer<IParticleSpawnMaterial, Entity> healStrategy;

        ClientSpawnStrategy(BiConsumer<IParticleSpawnMaterial, Entity> damageStrategy, BiConsumer<IParticleSpawnMaterial, Entity> nullifiedDamageStrategy, BiConsumer<IParticleSpawnMaterial, Entity> healStrategy) {
            this.damageStrategy = damageStrategy;
            this.nullifiedDamageStrategy = nullifiedDamageStrategy;
            this.healStrategy = healStrategy;
        }
    }

}
