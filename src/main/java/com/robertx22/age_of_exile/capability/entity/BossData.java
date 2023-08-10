package com.robertx22.age_of_exile.capability.entity;

import com.robertx22.age_of_exile.aoe_data.database.boss_spell.BossSpell;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class BossData {

    public int castTicks = 0;
    public String spellBeingCast = "";
    public List<String> spells = new ArrayList<>();


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
                    spell.onTick(en, castTicks);

                }
            }

        }

    }

    // todo probably better
    public void setupRandomBoss() {
        int amount = 1; // todo later i can add more

        while (spells.size() < amount) {
            String id = ExileDB.BossSpells().random().GUID();
            if (!spells.contains(id)) {
                this.spells.add(id);
            }
        }

    }
}
