package com.robertx22.mine_and_slash.aoe_data.database.boss_spell;

import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.maps.processors.helpers.MobBuilder;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashEntities;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import net.minecraft.world.entity.LivingEntity;

public class SummonExplodyMobs extends BossSummonMobs {

    @Override
    public void onStartOverride(LivingEntity en) {
        MobBuilder.of(SlashEntities.EXPLODE_MINION.get(), x -> {
            x.rarity = ExileDB.GearRarities().get(IRarity.UNCOMMON);
            x.amount = 5;
        }).summonMobs(en.level(), en.blockPosition()).forEach(e -> {

        });
    }


    @Override
    public String getCastSpeech() {
        return "HAHA, don't think you can kill them all!";
    }

    @Override
    public String GUID() {
        return "summon_explody_mobs";
    }
}
