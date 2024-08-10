package com.robertx22.mine_and_slash.capability.entity;

import com.robertx22.mine_and_slash.aoe_data.database.boss_spell.BossSpell;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashPotions;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BossData {

    public boolean isBoss = false;
    private int castTicks = 0;
    private String spellBeingCast = "";
    private List<String> spells = new ArrayList<>();
    private List<Float> hpTreshs = new ArrayList<>();
    private Long origPos = 0L;


    public void tick(LivingEntity en) {
        if (en.level().isClientSide) {
            return;
        }

        if (en.tickCount % 20 == 0) {
            en.addEffect(new MobEffectInstance(SlashPotions.KNOCKBACK_RESISTANCE.get(), 200, 10));
        }

        castTicks--;

        if (!spellBeingCast.isEmpty()) {

            BossSpell spell = ExileDB.BossSpells().get(spellBeingCast);

            if (spell != null) {
                if (castTicks < 0) {
                    spellBeingCast = "";
                    spell.onFinish(en);
                } else {
                    en.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10));
                    spell.onTick(en, castTicks);
                }
            }

        } else {

            float hp = en.getHealth() / en.getMaxHealth();

            if (hpTreshs.stream().anyMatch(x -> hp < x)) {
                hpTreshs.removeIf(x -> hp < x);// todo make sure you can only remove one.. or allow 1 shotting..?
                starCasting(en);
                // todo make him come back to original place
            }

        }

    }

    private void starCasting(LivingEntity en) {
        this.origPos = en.blockPosition().asLong();

        var id = RandomUtils.randomFromList(this.spells);
        var spell = ExileDB.BossSpells().get(id);
        this.spellBeingCast = id;
        this.castTicks = spell.castTicks();
        spell.onStart(en);

        BlockPos pos = BlockPos.of(origPos);
        en.setPos(pos.getX(), pos.getY(), pos.getZ());
    }

    // todo probably better
    public void setupRandomBoss() {
        this.isBoss = true;

        hpTreshs = new ArrayList<>();
        hpTreshs.addAll(Arrays.asList(0.25F, 0.5F, 0.75F));

        int amount = hpTreshs.size();

        while (spells.size() < amount) {
            String id = ExileDB.BossSpells().random().GUID();
            this.spells.add(id);
        }

    }
}
