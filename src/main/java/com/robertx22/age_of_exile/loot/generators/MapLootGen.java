package com.robertx22.age_of_exile.loot.generators;


import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.MapBlueprint;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
import net.minecraft.world.item.ItemStack;

public class MapLootGen extends BaseLootGen<MapBlueprint> {

    public MapLootGen(LootInfo info) {
        super(info);
    }

    @Override
    public float baseDropChance() {

        float chance = (float) ServerContainer.get().MAP_DROPRATE.get().floatValue();

        return chance;

    }

    @Override
    public LootType lootType() {
        return LootType.Map;
    }

    @Override
    public boolean condition() {
        if (ServerContainer.get().MAPS_DONT_DROP_IN_MAPS.get()) {
            if (info.isMapWorld) {
                return false;
            }
        }
        if (ServerContainer.get().MIN_LEVEL_MAP_DROPS.get() >= info.level) {
            return false;
        }
        return true;
    }

    @Override
    public boolean hasLevelDistancePunishment() {
        return false;
    }

    @Override
    public ItemStack generateOne() {
        MapBlueprint blueprint = new MapBlueprint(info);

        return blueprint.createStack();
    }

}
