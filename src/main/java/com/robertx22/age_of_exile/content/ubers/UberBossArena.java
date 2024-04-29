package com.robertx22.age_of_exile.content.ubers;

import com.robertx22.age_of_exile.database.data.league.LeaguePiecesList;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UberBossArena implements JsonExileRegistry<UberBossArena>, IAutoGson<UberBossArena>, IAutoLocName, IAutoLocDesc {

    public static UberBossArena SERIALIZER = new UberBossArena();

    public String id = "";
    public String name = "";
    public String desc = "";
    public int weight = 1000;

    public UberEnum getEnum() {
        return UberEnum.of(id);
    }

    //public HashMap<Integer, String> map_item_per_uber_tier = new HashMap<>();

    public LeaguePiecesList structure = new LeaguePiecesList(Arrays.asList());

    public List<String> possible_bosses = new ArrayList<>();

    public EntityType getRandomBoss() {
        return ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(RandomUtils.randomFromList(possible_bosses)));
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.UBER_BOSS;
    }

    @Override
    public Class<UberBossArena> getClassForSerialization() {
        return UberBossArena.class;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return weight;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.UBER_MAP;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".uber_map." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return name;
    }

    @Override
    public AutoLocGroup locDescGroup() {
        return AutoLocGroup.UBER_MAP;
    }

    @Override
    public String locDescLangFileGUID() {
        return SlashRef.MODID + ".uber_map.desc." + GUID();
    }

    @Override
    public String locDescForLangFile() {
        return desc;
    }
}
