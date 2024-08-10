package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import com.robertx22.mine_and_slash.capability.player.data.PlayerConfigData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class EntityFinder {

    public static boolean isTamedByAlly(Player other, LivingEntity x) {
        if (x instanceof OwnableEntity) {
            OwnableEntity tame = (OwnableEntity) x;

            return tame.getOwner() instanceof Player p && TeamUtils.areOnSameTeam(other, p); // we dont want players killing pets of others either
        }
        return false;
    }

    public enum SelectionType {
        TARGET() {
            @Override
            public <T extends Entity> List<T> getEntities(Setup setup) {
                return null; // dont use
            }
        },
        RADIUS() {
            @Override
            public <T extends Entity> List<T> getEntities(Setup setup) {


                double x = setup.pos.x();
                double y = setup.pos.y();
                double z = setup.pos.z();

                double hori = setup.horizontal;
                double verti = setup.vertical;

                AABB aabb = new AABB(x - hori, y - verti, z - hori, x + hori, y + verti, z + hori);

                if (setup.addTestParticles) {
                    Utilities.spawnParticlesForTesting(aabb, setup.world);
                }

                List<T> entityList = setup.world.getEntitiesOfClass(setup.entityType, aabb);


                return entityList;
            }
        },

        IN_FRONT {
            @Override
            public <T extends Entity> List<T> getEntities(Setup setup) {

                Entity entity = setup.caster;

                double distance = setup.distanceToSearch;

                double horizontal = setup.horizontal;
                double vertical = setup.vertical;

                double x = entity.getX();
                double y = entity.getY();
                double z = entity.getZ();

                Vec3 l = Utilities.getEndOfLook(entity, distance);

                double minX = Math.min(x, l.x);
                double minY = Math.min(y, l.y);
                double minZ = Math.min(z, l.z);

                double maxX = Math.max(x, l.x);
                double maxY = Math.max(y, l.y);
                double maxZ = Math.max(z, l.z);

                AABB aabb = new AABB(minX - horizontal, minY - vertical, minZ - horizontal,
                        maxX + horizontal, maxY + vertical, maxZ + horizontal
                );

                if (setup.addTestParticles) {
                    Utilities.spawnParticlesForTesting(aabb, setup.world);
                }

                List<T> entityList = entity.level().getEntitiesOfClass(setup.entityType, aabb);
                entityList.removeIf(e -> e == entity);

                return entityList;
            }
        };

        public abstract <T extends Entity> List<T> getEntities(Setup setup);


    }

    public static <T extends LivingEntity> Setup<T> start(Entity caster, Class<T> entityType, Vec3 pos) {
        Setup<T> setup = new Setup<T>(caster, entityType, pos);
        return setup;
    }

    public static <T extends LivingEntity> Setup<T> start(Entity caster, Class<T> entityType, BlockPos p) {
        return start(caster, entityType, new Vec3(p.getX(), p.getY(), p.getZ()));
    }

    public static class Setup<T extends LivingEntity> {

        Predicate<T> pred = x -> true;
        List<T> excluded = new ArrayList<>();
        Class<T> entityType;
        SelectionType selectionType = SelectionType.RADIUS;
        AllyOrEnemy entityPredicate = AllyOrEnemy.enemies;
        Entity caster;
        boolean forceExcludeCaster = false;
        Level world;
        Vec3 pos;
        double radius = 1;
        double horizontal = 1;
        double vertical = 1;
        boolean addTestParticles = false;

        double distanceToSearch = 10;

        public Setup(Entity caster, Class<T> entityType, Vec3 pos) {
            Objects.requireNonNull(caster);
            this.entityType = entityType;
            this.caster = caster;
            this.world = caster.level();
            this.pos = pos;
        }

        public List<T> build() {

            Objects.requireNonNull(caster, "Caster can't be null");
            Objects.requireNonNull(caster, "Blockpos can't be null");
            Objects.requireNonNull(caster, "World can't be null");

            List<T> list = this.selectionType.getEntities(this);

            list.removeIf(x -> x == null);

            list = this.entityPredicate.getMatchingEntities(list, this.caster);

            if (forceExcludeCaster || !entityPredicate.includesCaster()) {
                list.removeIf(x -> x == caster);
            }

            list.removeIf(x -> !x.isAlive());
            list.removeIf(x -> excluded.contains(x));
            list.removeIf(x -> !pred.test(x));

            return list;

        }

        public LivingEntity getClosest() {
            var list = build();
            if (list.isEmpty()) {
                return null;
            }
            LivingEntity en = list.stream().min(Comparator.comparingInt(x -> (int) x.distanceTo(caster))).get();

            return en;

        }

        public Setup<T> finder(SelectionType f) {
            this.selectionType = f;
            return this;
        }

        public Setup<T> predicate(Predicate<T> p) {
            this.pred = p;
            return this;
        }

        public Setup<T> searchFor(AllyOrEnemy f) {
            this.entityPredicate = f;

            if (f == AllyOrEnemy.allies) {
                if (this.caster instanceof Player p) {
                    if (Load.player(p).config.isConfigEnabled(PlayerConfigData.Config.EVERYONE_IS_ALLY)) {
                        this.entityPredicate = AllyOrEnemy.all;
                    }
                }
            }
            return this;
        }

        public Setup<T> distance(double distance) {
            this.distanceToSearch = distance;
            return this;
        }

        public Setup<T> height(double rad) {
            this.vertical = rad;

            return this;
        }

        public Setup<T> radius(double rad) {
            this.radius = rad;
            this.horizontal = rad;
            this.vertical = rad;
            return this;
        }

        public Setup<T> excludeEntity(T en) {
            this.excluded.add(en);
            return this;
        }

        public Setup<T> excludeCaster(boolean bool) {
            this.forceExcludeCaster = bool;
            return this;
        }

    }
}
