package com.robertx22.mine_and_slash.database.data;

import com.robertx22.mine_and_slash.database.OptScaleExactStat;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.EntityTypeUtils;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class EntityConfig implements JsonExileRegistry<EntityConfig>, IAutoGson<EntityConfig> {

    public EntityConfig() {

    }

    public EntityConfig(String id, float loot) {
        this.identifier = id;
        this.loot_multi = loot;
        this.exp_multi = loot;
    }

    public EntityConfig(EntityType type, float loot) {
        this.identifier = ForgeRegistries.ENTITY_TYPES.getKey(type).toString();
        this.loot_multi = loot;
        this.exp_multi = loot;

    }

    public boolean set_health_damage_override = false;

    public String identifier = "";

    public SpecialMobStats stats = new SpecialMobStats();

    public String set_rar = "";

    public double loot_multi = 1F;

    public double exp_multi = 1F;

    public int min_lvl = 1;

    public int max_lvl = 1000000;

    public double dmg_multi = 1;

    public double hp_multi = 1;

    public double stat_multi = 1;

    public boolean hasSpecificRarity() {
        return !this.set_rar.isEmpty();
    }

    @Override
    public String datapackFolder() {
        try {
            if (EntityTypeUtils.EntityClassification.valueOf(identifier.toUpperCase(Locale.ROOT)) != null) {
                return "mob_types/";
            }
        } catch (IllegalArgumentException e) {
        }

        if (identifier.contains(":")) {
            return "specific_mobs/";
        } else {
            return "all_mobs_in_mod/";
        }
    }


    @Override
    public Class<EntityConfig> getClassForSerialization() {
        return EntityConfig.class;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.ENTITY_CONFIGS;
    }

    @Override
    public String GUID() {
        return identifier;
    }

    @Override
    public int Weight() {
        return 100;
    }

    public static class SpecialMobStats {

        public List<OptScaleExactStat> stats = new ArrayList<>();

        public SpecialMobStats(OptScaleExactStat... stats) {
            this.stats.addAll(Arrays.asList(stats));
        }

        public SpecialMobStats() {

        }

        public SpecialMobStats(SpecialMobStats... stats) {
            for (SpecialMobStats stat : stats) {
                this.stats.addAll(stat.stats);
            }
        }
    }

}