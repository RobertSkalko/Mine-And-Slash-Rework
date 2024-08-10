package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.player.Player;

public class EntityTypeUtils {

    public enum EntityClassification {
        MOB("mob"),
        PLAYER("player"),
        ANIMAL("animal"),
        NPC("npc"),
        AMBIENT("ambient"),
        OTHER("other");

        EntityClassification(String id) {
            this.id = id;
        }

        public String id;

    }

    public static EntityClassification getType(LivingEntity entity) {


        if (isMob(entity)) {
            return EntityClassification.MOB;
        } else if (isAnimal(entity)) {
            return EntityClassification.ANIMAL;
        } else if (isNPC(entity)) {
            return EntityClassification.NPC;
        } else if (entity instanceof Player) {
            return EntityClassification.PLAYER;
        } else if (entity instanceof AmbientCreature || entity.getType().getCategory() == MobCategory.AMBIENT || entity.getType().getCategory() == MobCategory.WATER_AMBIENT) {
            return EntityClassification.AMBIENT;
        } else {
            return EntityClassification.OTHER;
        }

    }

    public static boolean isMob(Entity en) {
        if (en instanceof Enemy) {
            return true;
        }
        if (en instanceof NeutralMob) {
            return true;
        }
        if (!en.getType()
                .getCategory()
                .isFriendly()) {
            return true;
        }

        return false;
    }

    public static boolean isAnimal(Entity en) {
        return en instanceof Animal;
    }

    /**
     * has to be checked first because inpc extends ianimals
     *
     * @param en
     * @return
     */
    public static boolean isNPC(Entity en) {

        return en instanceof Npc;

    }
}
