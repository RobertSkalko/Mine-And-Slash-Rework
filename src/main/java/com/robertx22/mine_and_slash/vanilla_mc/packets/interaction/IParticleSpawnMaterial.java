package com.robertx22.mine_and_slash.vanilla_mc.packets.interaction;

import com.google.common.collect.ImmutableMap;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.InteractionResultHandler;
import com.robertx22.mine_and_slash.config.forge.ClientConfigs;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEvent;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public interface IParticleSpawnMaterial {

    void saveToBuf(FriendlyByteBuf friendlyByteBuf);

    IParticleSpawnMaterial loadFromData(FriendlyByteBuf friendlyByteBuf);

    InteractionResultHandler.ExileParticleType getSpawnType();
    void spawnOnClient(Entity entity);

    enum Type implements IParticleSpawnMaterial {
        DODGE(Words.DODGE.locName(), SoundEvents.SHIELD_BLOCK),
        RESIST(Words.RESIST.locName(), SoundEvents.SHIELD_BLOCK);

        public final MutableComponent text;
        public final SoundEvent sound;

        Type(MutableComponent text, SoundEvent sound) {
            this.text = text;
            this.sound = sound;
        }

        @Override
        public void saveToBuf(FriendlyByteBuf friendlyByteBuf) {
            friendlyByteBuf.writeEnum(this);
        }

        @Override
        public IParticleSpawnMaterial loadFromData(FriendlyByteBuf friendlyByteBuf) {
            return friendlyByteBuf.readEnum(IParticleSpawnMaterial.Type.class);
        }

        @Override
        public InteractionResultHandler.ExileParticleType getSpawnType() {
            return InteractionResultHandler.ExileParticleType.NULLIFIED_DAMAGE;
        }

        @Override
        public void spawnOnClient(Entity entity) {
            ClientConfigs.getConfig().DAMAGE_PARTICLE_STYLE.get().nullifiedDamageStrategy.accept(this, entity);
        }
    }

    record DamageInformation(byte[] elements, FloatList damage,
                                    boolean isCrit) implements IParticleSpawnMaterial {
        public static DamageInformation fromDmgByElement(DamageEvent.DmgByElement mat, boolean isCrit){
            HashMap<Elements, Float> dmgmap = mat.getDmgmap();
            int size = dmgmap.size();
            byte[] bytes = new byte[size];
            AtomicInteger i = new AtomicInteger(0);
            FloatArrayList floats = new FloatArrayList();
            dmgmap.forEach((key, value1) -> {
                float value = value1;
                bytes[i.getAndIncrement()] = ((byte) key.ordinal());
                floats.add(value);
            });
            return new DamageInformation(bytes, floats, isCrit);
        }
        public ImmutableMap<Elements, Float> getDmgMap() {
            ImmutableMap.Builder<Elements, Float> builder = ImmutableMap.builder();
            for (int i = 0; i < elements.length; i++) {
                builder.put(Elements.values()[elements[i]], damage.getFloat(i));
            }
            return builder.build();
        }

        @Override
        public void saveToBuf(FriendlyByteBuf friendlyByteBuf) {
            friendlyByteBuf.writeByteArray(elements);
            friendlyByteBuf.writeCollection(damage, (FriendlyByteBuf::writeFloat));
            friendlyByteBuf.writeBoolean(isCrit);

        }

        @Override
        public DamageInformation loadFromData(FriendlyByteBuf friendlyByteBuf) {
            byte[] bytes = friendlyByteBuf.readByteArray();
            return new DamageInformation(bytes, friendlyByteBuf.readCollection(FloatArrayList::new, FriendlyByteBuf::readFloat), friendlyByteBuf.readBoolean());

        }

        @Override
        public InteractionResultHandler.ExileParticleType getSpawnType() {
            return InteractionResultHandler.ExileParticleType.DAMAGE;
        }

        @Override
        public void spawnOnClient(Entity entity) {
            ClientConfigs.getConfig().DAMAGE_PARTICLE_STYLE.get().damageStrategy.accept(this, entity);
        }


    }

    record HealNumber(float number) implements IParticleSpawnMaterial {
        @Override
        public void saveToBuf(FriendlyByteBuf friendlyByteBuf) {
            friendlyByteBuf.writeFloat(number);
        }

        @Override
        public IParticleSpawnMaterial loadFromData(FriendlyByteBuf friendlyByteBuf) {
            return new HealNumber(friendlyByteBuf.readFloat());
        }

        @Override
        public InteractionResultHandler.ExileParticleType getSpawnType() {
            return InteractionResultHandler.ExileParticleType.HEAL;
        }

        @Override
        public void spawnOnClient(Entity entity) {
            ClientConfigs.getConfig().DAMAGE_PARTICLE_STYLE.get().healStrategy.accept(this, entity);
        }
    }
}
