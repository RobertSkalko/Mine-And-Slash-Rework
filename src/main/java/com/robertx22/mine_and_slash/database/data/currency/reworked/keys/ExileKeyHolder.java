package com.robertx22.mine_and_slash.database.data.currency.reworked.keys;

import com.google.common.base.Preconditions;
import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import com.robertx22.mine_and_slash.mmorpg.registers.deferred_wrapper.Def;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class ExileKeyHolder<T extends ExileRegistry<T>> {


    public List<ExileKeyHolderSection> sections = new ArrayList<>();

    public static class ItemIdProvider {

        Function<String, ResourceLocation> itemIdMaker = null;

        public ItemIdProvider(Function<String, ResourceLocation> itemIdMaker) {
            this.itemIdMaker = itemIdMaker;

        }
    }

    public static class ItemCreator<T extends ExileRegistry<T>> {
        Function<ExileKey<T, ?>, Item> maker = null;

        public ItemCreator(Function<ExileKey<T, ?>, Item> itemIdMaker) {
            this.maker = itemIdMaker;
        }
    }

    private ItemIdProvider itemIdProvider = new ItemIdProvider(null);

    private ItemCreator<T> itemCreator = new ItemCreator<T>(null);

    public HashMap<String, ExileKey<T, ?>> all = new HashMap<>();

    private List<Supplier<ExileKeyHolder<?>>> deps = new ArrayList<>();


    private boolean init = false;

    public ExileKeyHolder<T> dependsOn(Supplier<ExileKeyHolder<?>> dep) {
        this.deps.add(dep);
        return this;
    }

    public boolean hasItemProvider() {
        return itemIdProvider.itemIdMaker != null;
    }

    public ExileKeyHolder<T> itemIds(ItemIdProvider item) {
        this.itemIdProvider = item;
        return this;
    }

    public ExileKeyHolder<T> createItems(ItemCreator item) {
        this.itemCreator = item;
        return this;
    }

    // todo this will end up confusing unfinished and finished ids as they're created by mixing info and id..

    public ResourceLocation getItemId(String id) {
        Preconditions.checkNotNull(itemIdProvider.itemIdMaker);
        return this.itemIdProvider.itemIdMaker.apply(id);
    }

    public Item getItem(ResourceLocation id) {
        return VanillaUTIL.REGISTRY.items().get(id);
    }

    public Item getItem(ExileKey<T, ?> key) {
        var id = getItemId(key.GUID());
        return getItem(id);
    }

    public abstract void loadClass();

    public void init() {

        for (Supplier<ExileKeyHolder<?>> dep : deps) {
            if (dep.get() == null) {
                throw new RuntimeException("Dependency is null");
            }
            if (dep.get().init == false) {
                throw new RuntimeException("Dependency wasn't initialized");
            }
        }

        loadClass();


        for (ExileKey<T, ?> key : all.values()) {
            key.register();
        }

        for (ExileKeyHolderSection section : this.sections) {
            section.init();
        }

        init = true;

        this.initItems();
    }

    private void initItems() {

        if (this.itemCreator.maker != null && this.itemIdProvider.itemIdMaker != null) {
            for (ExileKey<T, ?> key : this.all.values()) {
                if (!key.isCancelItemCreation()) {
                    var id = this.getItemId(key.GUID());
                    var def = Def.item(id.getPath(), () -> this.itemCreator.maker.apply(key));
                    key.item = def;
                }
            }
        }
    }

}
