package com.robertx22.age_of_exile.characters;

import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.Blood;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerStats {
    public static final ResourceLocation LEVELS_GAINED = new ResourceLocation(SlashRef.MODID, "levels_gained");
    public static final HashMap<String, ResourceLocation> REGISTERED_STATS = new HashMap<>();

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

    public static void addReg(String id){
        REGISTERED_STATS.put(id, new ResourceLocation(SlashRef.MODID, id));
    }

    public static void initialize() {
        addReg(Health.getInstance().GUID());
        addReg(Mana.getInstance().GUID());
        addReg(Blood.getInstance().GUID());
        addReg(Energy.getInstance().GUID());
        addReg(MagicShield.getInstance().GUID());
        addReg(DatapackStats.DEX.GUID());
        addReg(DatapackStats.INT.GUID());
        addReg(DatapackStats.STR.GUID());

        registerCustomStat(LEVELS_GAINED);
        for(ResourceLocation rl : REGISTERED_STATS.values())
            registerCustomStat(rl);
    }

}
