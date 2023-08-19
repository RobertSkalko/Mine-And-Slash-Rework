package com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper;

import com.robertx22.age_of_exile.mechanics.harvest.HarvestBlocks;
import com.robertx22.age_of_exile.mechanics.harvest.HarvestItems;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.registers.common.*;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.*;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SlashDeferred {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SlashRef.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SlashRef.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SlashRef.MODID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SlashRef.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SlashRef.MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, SlashRef.MODID);
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, SlashRef.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, SlashRef.MODID);
    public static final DeferredRegister<MobEffect> POTIONS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SlashRef.MODID);
    public static final DeferredRegister<CreativeModeTab> TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SlashRef.MODID);
    public static final DeferredRegister<Feature<?>> FEATURE = DeferredRegister.create(Registries.FEATURE, SlashRef.MODID);


    public static void registerDefferedAtStartOfModLoading() {
        IEventBus bus = FMLJavaModLoadingContext.get()
                .getModEventBus();

        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
        SOUNDS.register(bus);
        ENTITIES.register(bus);
        CONTAINERS.register(bus);
        PARTICLES.register(bus);
        POTIONS.register(bus);
        RECIPE_SERIALIZERS.register(bus);
        TAB.register(bus);
        FEATURE.register(bus);

        SlashTabs.init();
        SlashPotions.init();
        SlashRecipeTypes.init();
        SlashSounds.init();
        SlashRecipeSers.init();

        SlashParticles.init();
        SlashEntities.init();
        SlashBlocks.init();
        HarvestBlocks.init();
        SlashBlockEntities.init();
        SlashContainers.init();

        //items
        CurrencyItems.init();
        RarityItems.init();
        SkillGemsItems.init();
        SlashItems.init();
        SlashItems.GearItems.init();
        RuneItems.init();
        GemItems.init();
        HarvestItems.init();

        SlashFeatures.init();


    }

}
