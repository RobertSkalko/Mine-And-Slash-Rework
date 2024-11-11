package com.robertx22.mine_and_slash.mmorpg;

import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.utils.Watch;
import com.robertx22.mine_and_slash.a_libraries.curios.CurioEvents;
import com.robertx22.mine_and_slash.a_libraries.curios.RefCurio;
import com.robertx22.mine_and_slash.a_libraries.neat.NeatForgeConfig;
import com.robertx22.mine_and_slash.aoe_data.GeneratedData;
import com.robertx22.mine_and_slash.aoe_data.database.boss_spell.BossSpells;
import com.robertx22.mine_and_slash.aoe_data.database.prophecies.ProphecyStarts;
import com.robertx22.mine_and_slash.aoe_data.database.stat_conditions.StatConditions;
import com.robertx22.mine_and_slash.aoe_data.database.stat_effects.StatEffects;
import com.robertx22.mine_and_slash.aoe_data.database.stats.AscensionStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.Stats;
import com.robertx22.mine_and_slash.characters.PlayerStats;
import com.robertx22.mine_and_slash.config.forge.ClientConfigs;
import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.database.data.currency.reworked.ExileCurrencies;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemMods;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.ItemReqs;
import com.robertx22.mine_and_slash.database.data.league.LeagueMechanics;
import com.robertx22.mine_and_slash.database.data.loot_chest.base.LootChests;
import com.robertx22.mine_and_slash.database.data.profession.ProfessionEvents;
import com.robertx22.mine_and_slash.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.mine_and_slash.database.data.spells.map_fields.MapField;
import com.robertx22.mine_and_slash.database.data.stats.layers.StatLayers;
import com.robertx22.mine_and_slash.database.data.stats.priority.StatPriority;
import com.robertx22.mine_and_slash.database.data.stats.types.special.SpecialStats;
import com.robertx22.mine_and_slash.database.registry.ExileDBInit;
import com.robertx22.mine_and_slash.gui.SocketTooltip;
import com.robertx22.mine_and_slash.maps.MapEvents;
import com.robertx22.mine_and_slash.mixin_ducks.tooltip.ItemTooltipsRegister;
import com.robertx22.mine_and_slash.mmorpg.event_registers.CommonEvents;
import com.robertx22.mine_and_slash.mmorpg.init.ClientInit;
import com.robertx22.mine_and_slash.mmorpg.registers.client.KeybindsRegister;
import com.robertx22.mine_and_slash.mmorpg.registers.client.RenderRegister;
import com.robertx22.mine_and_slash.mmorpg.registers.client.S2CPacketRegister;
import com.robertx22.mine_and_slash.mmorpg.registers.common.C2SPacketRegister;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashCapabilities;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashItemTags;
import com.robertx22.mine_and_slash.mmorpg.registers.deferred_wrapper.SlashDeferred;
import com.robertx22.mine_and_slash.tags.ModTags;
import com.robertx22.mine_and_slash.uncommon.coins.Coin;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.action.StatEffect;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.condition.StatCondition;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.VanillaRarities;
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
import java.text.NumberFormat;
import java.util.function.Consumer;

@Mod(SlashRef.MODID)
public class MMORPG {

    // DISABLE WHEN PUBLIC BUILD
    public static boolean RUN_DEV_TOOLS = false;

    public static String formatNumber(float num) {

        if (num < 10) {
            return DECIMAL_FORMAT.format(num);
        } else {
            return ((int) num) + "";
        }
    }

    // todo test
    public static String formatBigNumber(float num) {
        return NumberFormat.getInstance().format(num);
    }


    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");


    public static boolean RUN_DEV_TOOLS_REMOVE_WHEN_DONE = RUN_DEV_TOOLS; // this exists to stop me from making dumb mistakes when testing and forgetting about it


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
                .registerConfig(ModConfig.Type.SERVER, ServerContainer.spec, NeatForgeConfig.defaultConfigName(ModConfig.Type.SERVER, "mine_and_slash"));

        ExileEvents.CHECK_IF_DEV_TOOLS_SHOULD_RUN.register(new EventConsumer<ExileEvents.OnCheckIsDevToolsRunning>() {
            @Override
            public void accept(ExileEvents.OnCheckIsDevToolsRunning event) {
                event.run = MMORPG.RUN_DEV_TOOLS;
            }
        });

        VanillaRarities.init();

        Coin.init();

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

            ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfigs.clientSpec, NeatForgeConfig.defaultConfigName(ModConfig.Type.CLIENT, "mine_and_slash"));
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

        ItemTooltipsRegister.init();

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

        initLazyExileRegistries();

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

    static void initLazyExileRegistries() {

        ItemReqs.INSTANCE.init();
        ItemMods.INSTANCE.init();
        ExileCurrencies.INSTANCE.init();

    }


    public void interMod(InterModEnqueueEvent event) {

        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder(RefCurio.RING).size(2).build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder(RefCurio.NECKLACE).size(1).build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder(RefCurio.OMEN).size(1).build());

    }

    public void commonSetupEvent(FMLCommonSetupEvent event) {

        GeneratedData.addAllObjectsToGenerate();

        BossSpells.init();
        LeagueMechanics.init();
        LootChests.init();

        new ProphecyStarts().registerAll();

        SlashCapabilities.register();

    }


}
