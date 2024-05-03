package com.robertx22.age_of_exile.loot;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.library_of_exile.utils.EntityUtils;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Slime;

public class LootUtils {


    // prevents lvl 50 players farming lvl 1 mobs
    public static float getLevelDistancePunishmentMulti(int level, int playerLevel) {

        if (playerLevel == level) {
            return 1F;
        }

        int num = Math.abs(playerLevel - level);


        int leeway = ServerContainer.get().LEVEL_DISTANCE_PENALTY_LEEWAY.get();

        if (num <= leeway) {
            return 1;
        }

        float multi = (float) (1F - num * ServerContainer.get().LEVEL_DISTANCE_PENALTY_PER_LVL.get());

        return (float) Mth.clamp(multi, ServerContainer.get().LEVEL_DISTANCE_PENALTY_MIN_MULTI.get(), 1F);
    }

    public static float getMobHealthBasedLootMulti(LivingEntity entity) {


        float multi = 1;

        float hp = EntityUtils.getVanillaMaxHealth(entity);

        multi += (1 + hp / 40F) - 1;

        if (entity instanceof Slime slime) {

            if (ServerContainer.get().MIN_SLIME_SIZE_FOR_LOOT.get() > slime.getSize()) {
                multi = 0;
            } else {
                if (slime.getSize() < 5) {
                    multi *= 0.05F;
                } else {
                    multi *= 0.1F;
                }
            }
        }

        return multi;
    }

    public static int WhileRoll(float chance) {
        int amount = 0;

        while (chance > 0) {

            float maxChance = 75F;

            float currentChance = chance;

            if (currentChance > maxChance) {
                currentChance = maxChance;
            }

            chance -= currentChance;

            if (RandomUtils.roll(currentChance)) {
                amount++;
            }

        }
        return amount;

    }

}
