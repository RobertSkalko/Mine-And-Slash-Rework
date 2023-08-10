package com.robertx22.age_of_exile.aoe_data.database.boss_spell;

import com.robertx22.age_of_exile.aoe_data.database.mob_affixes.MobAffixes;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.event_hooks.entity.OnMobSpawn;
import com.robertx22.age_of_exile.maps.processors.helpers.MobBuilder;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.world.entity.LivingEntity;

public class SummonThornMobs extends BossSummonMobs {

    @Override
    public void onStartOverride(LivingEntity en) {
        MobBuilder.of(SlashEntities.THORNY_MINION.get(), x -> {
            x.rarity = ExileDB.MobRarities().get(IRarity.ELITE_ID);
            x.amount = 10;
        }).summonMobs(en.level(), en.blockPosition()).forEach(e -> {
            OnMobSpawn.setupNewMob(e, Load.Unit(e), null);
            Load.Unit(e).getAffixData().affixes.add(MobAffixes.THORNY);
            Load.Unit(e).forceRecalculateStats();
        });
    }


    @Override
    public String getCastSpeech() {
        return "You might want to refrain from touching my minions.";
    }

    @Override
    public String GUID() {
        return "summon_thorn_mobs";
    }
}
