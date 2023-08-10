package com.robertx22.age_of_exile.capability.entity;

import com.robertx22.age_of_exile.aoe_data.database.boss_spell.BossSpell;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BossData {

    private int castTicks = 0;
    private String spellBeingCast = "";
    private List<String> spells = new ArrayList<>();
    private List<Float> hpTreshs = new ArrayList<>();
    private Long origPos = 0L;


    public void tick(LivingEntity en) {
        if (en.level().isClientSide) {
            return;
        }

        castTicks--;

        if (!spellBeingCast.isEmpty()) {

            spellBeingCast = "";
            BossSpell spell = ExileDB.BossSpells().get(spellBeingCast);
            if (spell != null) {
                if (castTicks < 0) {
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
        var spell = ExileDB.BossSpells().get(spellBeingCast);
        this.spellBeingCast = RandomUtils.randomFromList(this.spells);
        this.castTicks = spell.castTicks();
        spell.onStart(en);

        BlockPos pos = BlockPos.of(origPos);
        en.setPos(pos.getX(), pos.getY(), pos.getZ());
    }

    // todo probably better
    public void setupRandomBoss() {

        hpTreshs = new ArrayList<>();
        hpTreshs.addAll(Arrays.asList(0.25F, 0.5F, 0.75F));

        int amount = hpTreshs.size();

        while (spells.size() < amount) {
            String id = ExileDB.BossSpells().random().GUID();
            if (!spells.contains(id)) {
                this.spells.add(id);
            }
        }

    }
}
