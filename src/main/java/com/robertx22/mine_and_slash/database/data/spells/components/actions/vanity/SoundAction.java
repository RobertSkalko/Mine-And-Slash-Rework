package com.robertx22.mine_and_slash.database.data.spells.components.actions.vanity;

import com.robertx22.library_of_exile.utils.SoundUtils;
import com.robertx22.mine_and_slash.database.data.spells.components.MapHolder;
import com.robertx22.mine_and_slash.database.data.spells.components.actions.SpellAction;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.SpellCtx;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;

import java.util.Arrays;
import java.util.Collection;

import static com.robertx22.mine_and_slash.database.data.spells.map_fields.MapField.*;

public class SoundAction extends SpellAction {

    public SoundAction() {
        super(Arrays.asList(SOUND, PITCH, VOLUME));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {
        if (!ctx.world.isClientSide) {

            float pitch = data.get(PITCH).floatValue();
            float volume = data.get(VOLUME).floatValue();
            SoundEvent sound = data.getSound();

            SoundUtils.playSound(ctx.world, ctx.getBlockPos(), sound, volume, pitch);
        }
    }

    public MapHolder create(SoundEvent sound, Double volume, Double pitch) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(VOLUME, volume);
        d.put(PITCH, pitch);
        d.put(SOUND, BuiltInRegistries.SOUND_EVENT.getKey(sound)
                .toString());
        return d;
    }

    @Override
    public String GUID() {
        return "sound";
    }
}
