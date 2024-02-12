package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.stream.Collectors;

public enum AllyOrEnemy {


    summonShouldAttack() {
        @Override
        public <T extends LivingEntity> List<T> getMatchingEntities(List<T> list, Entity caster) {
            return list.stream()
                    .filter(x -> is(caster, x))
                    .collect(Collectors.toList());
        }

        @Override
        public boolean is(Entity caster, LivingEntity target) {

            if (!enemies.is(caster, target)) {
                return false;
            }

            var type = Load.Unit(target).getType();

            if (type == EntityTypeUtils.EntityClassification.AMBIENT) {
                return false;
            }
            if (type == EntityTypeUtils.EntityClassification.ANIMAL) {
                return false;
            }
            if (type == EntityTypeUtils.EntityClassification.NPC) {
                return false;
            }

            if (target instanceof NeutralMob) {
                return false;
            }

            return true;
        }

        @Override
        public boolean includesCaster() {
            return false;
        }
    },


    allies() {
        @Override
        public <T extends LivingEntity> List<T> getMatchingEntities(List<T> list, Entity caster) {
            return list.stream().filter(x -> is(caster, x))
                    .collect(Collectors.toList());
        }

        @Override
        public boolean is(Entity caster, LivingEntity target) {
            return enemies.is(caster, target) == false;
        }

        @Override
        public boolean includesCaster() {
            return true;
        }
    },
    pets() {
        @Override
        public <T extends LivingEntity> List<T> getMatchingEntities(List<T> list, Entity caster) {
            return list.stream()
                    .filter(x -> is(caster, x))
                    .collect(Collectors.toList());
        }

        @Override
        public boolean is(Entity caster, LivingEntity target) {
            if (caster instanceof Player p) {
                if (EntityFinder.isTamedByAlly(p, target) && target instanceof OwnableEntity pet && pet.getOwner() == caster) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean includesCaster() {
            return false;
        }
    },
    enemies {
        @Override
        public boolean is(Entity caster, LivingEntity target) {

            if (caster instanceof Player p) {
                if (EntityFinder.isTamedByAlly(p, target)) {
                    return false;
                }
                if (target instanceof Player) {
                    if (target == caster || TeamUtils.areOnSameTeam((Player) caster, (Player) target)) {
                        return false;
                    } else {
                        return true;
                    }
                }

            } else {
                if (target instanceof Player) {
                    return true;
                } else {
                    return false;
                }
            }

            return true;
        }

        @Override
        public <T extends LivingEntity> List<T> getMatchingEntities(List<T> list, Entity caster) {
            return list.stream()
                    .filter(x -> is(caster, x))
                    .collect(Collectors.toList());
        }

        @Override
        public boolean includesCaster() {
            return false;
        }
    },
    all {
        @Override
        public <T extends LivingEntity> List<T> getMatchingEntities(List<T> list, Entity caster) {
            return list;
        }

        @Override
        public boolean is(Entity caster, LivingEntity target) {
            return true;
        }

        @Override
        public boolean includesCaster() {
            return true;
        }
    };

    public abstract <T extends LivingEntity> List<T> getMatchingEntities(List<T> list, Entity caster);

    public abstract boolean is(Entity caster, LivingEntity target);

    public abstract boolean includesCaster();
}
