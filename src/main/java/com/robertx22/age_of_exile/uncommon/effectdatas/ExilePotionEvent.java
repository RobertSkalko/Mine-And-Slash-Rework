package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffectInstanceData;
import com.robertx22.age_of_exile.database.data.spells.entities.CalculatedSpellData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.types.ExileStatusEffect;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class ExilePotionEvent extends EffectEvent {

    public static String ID = "on_exile_effect";

    public String spellid = "";

    @Override
    public String GUID() {
        return ID;
    }

    int lvl;

    CalculatedSpellData calc;

    public ExilePotionEvent(CalculatedSpellData calc, int lvl, ExileEffect effect, GiveOrTake giveOrTake, LivingEntity caster, LivingEntity target, int tickDuration) {
        super(1, caster, target);
        this.lvl = lvl;
        this.calc = calc;

        this.data.setupNumber(EventData.STACKS, 1);
        this.data.setString(EventData.GIVE_OR_TAKE, giveOrTake.name());
        this.data.setString(EventData.EXILE_EFFECT, effect.GUID());
        this.data.setupNumber(EventData.EFFECT_DURATION_TICKS, tickDuration);

    }

    @Override
    protected void activate() {

        if (source.level().isClientSide) {
            return;
        }
        if (this.data.isCanceled()) {
            return;
        }

        int stacks = (int) data.getNumber(EventData.STACKS).number;

        GiveOrTake action = data.getGiveOrTake();
        ExileEffect effect = data.getExileEffect();
        int duration = (int) data.getNumber(EventData.EFFECT_DURATION_TICKS).number;

        if (effect.id.contains("35")) {
            byte effectId = (byte) (MobEffect.getId(effect.getStatusEffect()) & 255);

            System.out.print(effectId);
        }

        ExileStatusEffect status = effect.getStatusEffect();

        if (action == GiveOrTake.take) {
            ExileEffectInstanceData extraData = Load.Unit(target)
                    .getStatusEffectsData()
                    .get(status);

            extraData.stacks -= stacks;
            extraData.stacks = Mth.clamp(extraData.stacks, 0, 1000);
            extraData.str_multi = data.getNumber();

            if (extraData.stacks < 1) {
                target.removeEffect(status);
                Load.Unit(target)
                        .getStatusEffectsData()
                        .set(status, null);
            }
            Load.Unit(target)
                    .setEquipsChanged(true);
            Load.Unit(target)
                    .trySync();
        } else {

            MobEffectInstance instance = target.getEffect(status);
            ExileEffectInstanceData extraData;

            if (instance != null) {
                extraData = Load.Unit(target)
                        .getStatusEffectsData()
                        .get(status);
                if (extraData == null) {
                    extraData = new ExileEffectInstanceData();
                } else {
                    extraData.stacks++;
                    extraData.stacks = Mth.clamp(extraData.stacks, 1, effect.max_stacks);
                }
            } else {
                extraData = new ExileEffectInstanceData();
            }


            extraData.caster_uuid = source.getStringUUID();
            extraData.spell_id = this.spellid;
            extraData.str_multi = data.getNumber();
            extraData.calcSpell = this.calc;
            extraData.ticks_left = (int) data.getNumber(EventData.EFFECT_DURATION_TICKS).number;

            MobEffectInstance newInstance = new MobEffectInstance(status, duration, extraData.stacks, false, false, true);

            Load.Unit(target)
                    .getStatusEffectsData()
                    .set(status, extraData);

            if (target.hasEffect(newInstance.getEffect())) {
                target.getActiveEffectsMap()
                        .put(newInstance.getEffect(), newInstance);
            } else {
                target.addEffect(newInstance);
            }


            // sync packets to client
            ClientboundUpdateMobEffectPacket packet = new ClientboundUpdateMobEffectPacket(target.getId(), newInstance);

            PlayerUtils.getNearbyPlayers(target, 50D)
                    .forEach((x) -> {
                        ServerPlayer server = (ServerPlayer) x;
                        server.connection.send(packet);
                    });

            Load.Unit(target).setEquipsChanged(true);
            Load.Unit(target).tryRecalculateStats();
            Load.Unit(target).trySync();
        }
    }

}
