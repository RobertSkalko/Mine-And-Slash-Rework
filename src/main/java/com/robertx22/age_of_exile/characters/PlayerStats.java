package com.robertx22.age_of_exile.characters;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerStats {

    public static final ResourceLocation LEVELS_GAINED = new ResourceLocation(SlashRef.MODID, "levels_gained");
    public static final ResourceLocation TOTAL_MANA = new ResourceLocation(SlashRef.MODID, "total_mana");
    public static final ResourceLocation TOTAL_ENERGY = new ResourceLocation(SlashRef.MODID, "total_energy");
    public static final ResourceLocation TOTAL_MAGIC_SHIELD = new ResourceLocation(SlashRef.MODID, "total_magic_shield");
    public static final ResourceLocation TOTAL_BLOOD = new ResourceLocation(SlashRef.MODID, "total_blood");
    public static final ResourceLocation TOTAL_HEALTH = new ResourceLocation(SlashRef.MODID, "total_health");

    private static class Registrations {
        public final List<ResourceLocation> customStats = new ArrayList<>();

        @SubscribeEvent
        public void commonSetup(FMLCommonSetupEvent event) {
            event.enqueueWork(() -> customStats.forEach(it -> {
                Registry.register(BuiltInRegistries.CUSTOM_STAT, it.getPath(), it);
                Stats.CUSTOM.get(it, StatFormatter.DEFAULT);
            }));
        }
    }

    private static final Map<String, Registrations> registrations = new ConcurrentHashMap<>();

    public static void registerCustomStat(ResourceLocation identifier) {
        getActiveRegistrations().customStats.add(identifier);
    }

    public static void register() {
        FMLJavaModLoadingContext.get().getModEventBus().register(getActiveRegistrations());
    }

    private static Registrations getActiveRegistrations() {
        return registrations.computeIfAbsent(ModLoadingContext.get().getActiveNamespace(), it -> new Registrations());
    }
    public static void initialize() {
        registerCustomStat(LEVELS_GAINED);
        registerCustomStat(TOTAL_MANA);
        registerCustomStat(TOTAL_ENERGY);
        registerCustomStat(TOTAL_MAGIC_SHIELD);
        registerCustomStat(TOTAL_BLOOD);
        registerCustomStat(TOTAL_HEALTH);
    }

}
