package com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class Def {

    public static <T extends Block> RegObj<T> block(String id, Supplier<T> block) {
        RegistryObject<T> reg = SlashDeferred.BLOCKS.register(id, block);
        RegObj<T> obj = new RegObj<T>(reg);
        return obj;
    }

    public static <T extends CreativeModeTab> RegObj<T> creativeTab(String id, Supplier<T> block) {
        RegistryObject<T> reg = SlashDeferred.TAB.register(id, block);
        RegObj<T> obj = new RegObj<T>(reg);
        return obj;
    }


    public static <T extends Item> RegObj<T> item(Supplier<T> object, String id) {
        return item(id, object);
    }

    public static <T extends Item> RegObj<T> item(String id, Supplier<T> object) {
        RegistryObject<T> reg = SlashDeferred.ITEMS.register(id, object);
        RegObj<T> wrapper = new RegObj<T>(reg);
        return wrapper;
    }

    public static <T extends ParticleType<?>> RegObj<T> particle(String id, Supplier<T> object) {
        RegistryObject<T> reg = SlashDeferred.PARTICLES.register(id, object);
        RegObj<T> wrapper = new RegObj<T>(reg);
        return wrapper;
    }

    public static <T extends MobEffect> RegObj<T> potion(String id, Supplier<T> object) {
        RegistryObject<T> reg = SlashDeferred.POTIONS.register(id, object);
        RegObj<T> wrapper = new RegObj<T>(reg);
        return wrapper;
    }

    public static <T extends BlockEntityType<?>> RegObj<T> blockEntity(String id, Supplier<T> object) {
        RegistryObject<T> reg = SlashDeferred.BLOCK_ENTITIES.register(id, object);
        RegObj<T> wrapper = new RegObj<T>(reg);
        return wrapper;
    }

    public static <T extends EntityType<?>> RegObj<T> entity(String id, Supplier<T> object) {
        RegistryObject<T> reg = SlashDeferred.ENTITIES.register(id, object);
        RegObj<T> wrapper = new RegObj<T>(reg);
        return wrapper;
    }

    public static <T extends RecipeSerializer<?>> RegObj<T> recipeSer(String id, Supplier<T> object) {
        RegistryObject<T> reg = SlashDeferred.RECIPE_SERIALIZERS.register(id, object);
        RegObj<T> wrapper = new RegObj<T>(reg);
        return wrapper;
    }

    public static <T extends RecipeType<?>> RegObj<T> recipeType(String id, Supplier<T> object) {
        RegistryObject<T> reg = SlashDeferred.RECIPE_TYPES.register(id, object);
        RegObj<T> wrapper = new RegObj<T>(reg);
        return wrapper;
    }


    public static <T extends MenuType<?>> RegObj<T> container(String id, Supplier<T> object) {
        RegistryObject<T> reg = SlashDeferred.CONTAINERS.register(id, object);
        RegObj<T> wrapper = new RegObj<T>(reg);
        return wrapper;
    }

    public static <T extends Feature<?>> RegObj<T> feature(String id, Supplier<T> object) {
        RegistryObject<T> reg = SlashDeferred.FEATURE.register(id, object);
        RegObj<T> wrapper = new RegObj<T>(reg);
        return wrapper;
    }


    public static RegObj<SoundEvent> sound(String id) {
        Supplier<SoundEvent> sup = () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(SlashRef.MODID, id), 16); // todo idk?
        RegistryObject<SoundEvent> reg = SlashDeferred.SOUNDS.register(id, sup);
        RegObj<SoundEvent> wrapper = new RegObj<SoundEvent>(reg);
        return wrapper;
    }
}
