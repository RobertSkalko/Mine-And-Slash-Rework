package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.stream.Collectors;

public enum AllyOrEnemy {
    allies() {
        @Override
        public <T extends LivingEntity> List<T> getMatchingEntities(List<T> list, Entity caster) {

            return list.stream()
                    .filter(x -> is(caster, x))
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
            if (caster instanceof Player) {
                if (EntityFinder.isTamed(target) && target instanceof OwnableEntity pet && pet.getOwner() == caster) {
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

            if (caster instanceof Player) {
                if (EntityFinder.isTamed(target)) {
                    return false;
                }
                if (target instanceof Player) {
                    if (target == caster) {
                        return false;
                    }
                    if (TeamUtils.areOnSameTeam((Player) caster, (Player) target)) {
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
