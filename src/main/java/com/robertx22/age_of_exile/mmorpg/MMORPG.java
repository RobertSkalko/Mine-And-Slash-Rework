package com.robertx22.age_of_exile.mmorpg;

import com.robertx22.age_of_exile.a_libraries.curios.CurioEvents;
import com.robertx22.age_of_exile.a_libraries.neat.NeatForgeConfig;
import com.robertx22.age_of_exile.aoe_data.GeneratedData;
import com.robertx22.age_of_exile.aoe_data.database.boss_spell.BossSpells;
import com.robertx22.age_of_exile.aoe_data.database.prophecies.ProphecyStarts;
import com.robertx22.age_of_exile.aoe_data.database.stat_conditions.StatConditions;
import com.robertx22.age_of_exile.aoe_data.database.stat_effects.StatEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.AscensionStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.characters.PlayerStats;
import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.league.LeagueMechanics;
import com.robertx22.age_of_exile.database.data.loot_chest.base.LootChests;
import com.robertx22.age_of_exile.database.data.profession.ProfessionEvents;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.stats.layers.StatLayers;
import com.robertx22.age_of_exile.database.data.stats.priority.StatPriority;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.database.registrators.Currencies;
import com.robertx22.age_of_exile.database.registry.ExileDBInit;
import com.robertx22.age_of_exile.gui.SocketTooltip;
import com.robertx22.age_of_exile.maps.MapEvents;
import com.robertx22.age_of_exile.mmorpg.event_registers.CommonEvents;
import com.robertx22.age_of_exile.mmorpg.init.ClientInit;
import com.robertx22.age_of_exile.mmorpg.registers.client.KeybindsRegister;
import com.robertx22.age_of_exile.mmorpg.registers.client.RenderRegister;
import com.robertx22.age_of_exile.mmorpg.registers.client.S2CPacketRegister;
import com.robertx22.age_of_exile.mmorpg.registers.common.C2SPacketRegister;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashCapabilities;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashItemTags;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.SlashDeferred;
import com.robertx22.age_of_exile.tags.ModTags;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.action.StatEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition.StatCondition;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.utils.Watch;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import top.theillusivec4.curios.api.SlotTypeMessage;

import java.text.DecimalFormat;
import java.util.function.Consumer;

@Mod(SlashRef.MODID)
public class MMORPG {


    // DISABLE WHEN PUBLIC BUILD
    public static boolean RUN_DEV_TOOLS = true;


    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");


    public static boolean RUN_DEV_TOOLS_REMOVE_WHEN_DONE = RUN_DEV_TOOLS; // this exists to stop me from making dumb mistakes when testing and forgetting about it

    public static boolean combatLogEnabled() {
        return false; // todo, should this be a client config and have server send packets ?
    }


    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(SlashRef.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public MMORPG() {

        Watch watch = new Watch();


        ModLoadingContext.get()
                .registerConfig(ModConfig.Type.SERVER, ServerContainer.spec);

        ExileEvents.CHECK_IF_DEV_TOOLS_SHOULD_RUN.register(new EventConsumer<ExileEvents.OnCheckIsDevToolsRunning>() {
            @Override
            public void accept(ExileEvents.OnCheckIsDevToolsRunning event) {
                event.run = MMORPG.RUN_DEV_TOOLS;
            }
        });

        StatPriority.init();

        StackSaving.init();
        StatEffect.init();
        StatCondition.loadclass();

        ForgeEvents.registerForgeEvent(RegisterClientTooltipComponentFactoriesEvent.class, x -> {
            x.register(SocketTooltip.SocketComponent.class, SocketTooltip::new);
        });

        final IEventBus bus = FMLJavaModLoadingContext.get()
                .getModEventBus();

        ForgeEvents.registerForgeEvent(AddReloadListenerEvent.class, event -> {
            ExileRegistryType.registerJsonListeners(event);
        });


        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {

            NeatForgeConfig.register();

            ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfigs.clientSpec);
            bus.addListener(ClientInit::onInitializeClient);

            ForgeEvents.registerForgeEvent(RegisterKeyMappingsEvent.class, x -> {
                KeybindsRegister.register(x);
            });

            //bus.addListener(KeybindsRegister::register);
            FMLJavaModLoadingContext.get().getModEventBus().addListener((Consumer<EntityRenderersEvent.RegisterRenderers>) x -> {
                RenderRegister.regRenders(x);
            });

        });


        bus.addListener(this::commonSetupEvent);
        bus.addListener(this::interMod);

        CurioEvents.reg();


        ModTags.init();

        StatLayers.init();
        StatEffects.addSerializers();
        StatConditions.loadClass();
        Stats.loadClass();
        AscensionStats.init();
        ExileDBInit.initRegistries();
        SpecialStats.init();

        SlashDeferred.registerDefferedAtStartOfModLoading();

        MapField.init();
        EffectCondition.init();

        SlashItemTags.init();

        ExileDBInit.registerAllItems(); // after config registerAll

        PlayerStats.register();
        PlayerStats.initialize();

        CommonEvents.register();

        C2SPacketRegister.register();
        S2CPacketRegister.register();

        LifeCycleEvents.register();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(EventPriority.LOW, new Consumer<GatherDataEvent>() {
            @Override
            public void accept(GatherDataEvent x) {
                for (ExileRegistryType type : ExileRegistryType.getAllInRegisterOrder()) {
                    x.getGenerator().addProvider(true, type.getDatapackGenerator());
                }
            }
        });

        MapEvents.init();
        ProfessionEvents.init();
        DerivedRegistries.init();

        watch.print("Mine and slash mod initialization ");

    }


    public void interMod(InterModEnqueueEvent event) {


        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("ring").size(2)
                .build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("necklace").size(1)
                .build());
    }

    public void commonSetupEvent(FMLCommonSetupEvent event) {

        new Currencies().registerAll();

        GeneratedData.addAllObjectsToGenerate();

        BossSpells.init();
        LeagueMechanics.init();
        LootChests.init();

        new ProphecyStarts().registerAll();

        SlashCapabilities.register();


    }

    public static void devToolsLog(String string) {
        if (RUN_DEV_TOOLS) {
            System.out.println(string);
        }
    }

    public static void devToolsErrorLog(String string) {
        if (RUN_DEV_TOOLS) {
            try {
                throw new Exception(string);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void logError(String s) {
        try {
            throw new Exception(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
