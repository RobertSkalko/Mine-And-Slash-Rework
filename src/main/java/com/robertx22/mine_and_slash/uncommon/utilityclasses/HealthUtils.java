package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.health.Health;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import net.minecraft.world.entity.LivingEntity;

public class HealthUtils {

    public static void heal(LivingEntity en, float heal) {
        en.heal(heal);
    }

    public static float vanillaToReal(LivingEntity en, float dmg) {

        return Health.getInstance()
                .scale(ModType.FLAT, dmg, Load.Unit(en)
                        .getLevel());
    }

    public static float realToVanilla(LivingEntity en, float dmg) {
        float multi = dmg / getCombinedMaxHealth(en);
        float max = en.getMaxHealth();
        float total = multi * max;
        return total;
    }

    public static float getCombinedHealthMulti(LivingEntity en) {
        return getCombinedCurrentHealth(en) / getCombinedMaxHealth(en);
    }

    public static float getMaxHealth(LivingEntity en) {
        EntityData data = Load.Unit(en);

        if (en.level().isClientSide) {
            return data.getSyncedMaxHealth(); // for client, health needs to be synced
        }

        try {
            return data.getUnit().healthData().getValue();
        } catch (Exception e) {
            return 1;
        }

    }

    public static int getCurrentHealth(LivingEntity entity) {

        float multi = entity.getHealth() / entity.getMaxHealth();

        float max = getMaxHealth(entity);

        return (int) (max * multi);

    }

    public static float getCombinedMaxHealth(LivingEntity en) {
        var hp = getMaxHealth(en);

        return hp;
    }

    public static float getCombinedCurrentHealth(LivingEntity en) {
        EntityData data = Load.Unit(en);
        return getCurrentHealth(en);
    }
}
