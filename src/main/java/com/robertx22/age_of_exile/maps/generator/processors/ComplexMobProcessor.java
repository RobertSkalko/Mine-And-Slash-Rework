package com.robertx22.age_of_exile.maps.generator.processors;

import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.maps.MobSpawnUtils;
import com.robertx22.age_of_exile.maps.generator.ChunkProcessData;
import com.robertx22.age_of_exile.maps.mobs.SpawnedMob;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.codehaus.plexus.util.StringUtils;

import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ComplexMobProcessor extends DataProcessor {

    public ComplexMobProcessor() {
        super("spawn", Type.CONTAINS);
    }

    @Override
    public void processImplementation(String key, BlockPos pos, Level world, ChunkProcessData data) {

        try {
            String[] parts = StringUtils.split(key, ";");

            MobRarity rarity = null;
            boolean isBoss = false;
            EntityType<? extends Mob> type = null;
            boolean addPotion = false;

            Stream<SpawnedMob> filter = null;

            int amount = 1;

            for (String x : parts) {

                int am = 0;
                try {
                    am = Integer.parseInt(x);
                } catch (NumberFormatException e) {
                }

                if (am > 0) {
                    amount = am;
                }

            }


            for (String x : parts) {

                if (ExileDB.MobRarities().isRegistered(x)) {
                    rarity = ExileDB.MobRarities().get(x);

                    if (rarity.boss) {
                        isBoss = true;
                    }
                }

            }

            if (rarity == null) {
                rarity = ExileDB.MobRarities().random();
            }

            for (String x : parts) {

                ResourceLocation loc = new ResourceLocation(x);

                if (ForgeRegistries.ENTITY_TYPES.containsKey(loc)) {
                    type = (EntityType<? extends Mob>) ForgeRegistries.ENTITY_TYPES.getValue(loc);
                }

            }

            if (type == null) {
                for (String x : parts) {
                    if (x.equals("ranged")) {
                        filter = SpawnedMob.getAll()
                                .stream()
                                .filter(m -> m.isRanged);

                    } else if (x.equals("spider")) {
                        filter = SpawnedMob.getAll()
                                .stream()
                                .filter(m -> m.isSpider);
                    } else if (x.equals("nether")) {
                        filter = SpawnedMob.getAll()
                                .stream()
                                .filter(m -> m.isNether);
                    } else if (x.equals("undead")) {
                        filter = SpawnedMob.getAll()
                                .stream()
                                .filter(m -> m.isUndead);
                    }

                }
            }

            if (filter == null) {
                filter = SpawnedMob.getAll()
                        .stream()
                        .filter(x -> data.getRoom()
                                .canSpawnMob(x));
            }

            if (type == null) {

                if (isBoss) {
                    filter = filter.filter(m -> m.canBeBoss);
                }

                type = RandomUtils.weightedRandom(filter.collect(Collectors.toList())).type;

            }



            /*
            Boss boss = null;

            if (isBoss) {

                if (data.getRoom().group.canSpawnFireMobs) {
                    boss = SlashRegistry.Bosses()
                            .random();
                } else {
                    boss = SlashRegistry.Bosses()
                            .getFilterWrapped(x -> !x.isFire)
                            .random();
                }
            } else if (RandomUtils.RandomRange(1, 4) == 1) { // hacky solution to increase mob density
                amount++;
            }
             */

            if (RandomUtils.roll(50)) { // chance for mobs to be fast strong or regenerative
                addPotion = true;
            }

            for (int i = 0; i < amount; i++) {
                MobSpawnUtils.summon(type, world, pos); // todo
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}