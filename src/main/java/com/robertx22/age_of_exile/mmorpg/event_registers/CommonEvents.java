package com.robertx22.age_of_exile.mmorpg.event_registers;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.capability.player.PlayerData;
import com.robertx22.age_of_exile.damage_hooks.*;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.AttributeStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.event_hooks.entity.OnMobSpawn;
import com.robertx22.age_of_exile.event_hooks.entity.OnTrackEntity;
import com.robertx22.age_of_exile.event_hooks.my_events.OnEntityTick;
import com.robertx22.age_of_exile.event_hooks.my_events.OnLootChestEvent;
import com.robertx22.age_of_exile.event_hooks.my_events.OnMobDeathDrops;
import com.robertx22.age_of_exile.event_hooks.my_events.OnPlayerDeath;
import com.robertx22.age_of_exile.event_hooks.ontick.OnServerTick;
import com.robertx22.age_of_exile.event_hooks.ontick.OnTickDungeonWorld;
import com.robertx22.age_of_exile.event_hooks.player.OnLogin;
import com.robertx22.age_of_exile.event_hooks.player.StopCastingIfInteract;
import com.robertx22.age_of_exile.mixin_methods.OnItemInteract;
import com.robertx22.age_of_exile.mixin_methods.OnItemStoppedUsingCastImbuedSpell;
import com.robertx22.age_of_exile.mmorpg.ForgeEvents;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.error_checks.base.ErrorChecks;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.Cached;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;

public class CommonEvents {

    public static void register() {

        OnItemStoppedUsingCastImbuedSpell.register();

        OnItemInteract.register();


// forge event gives us stack clone, making this a dupe
        /*
        ForgeEvents.registerForgeEvent(PlayerEvent.ItemPickupEvent.class, event -> {
            ItemStack stack = event.getStack();
            if (!stack.isEmpty()) {
                if (!event.getEntity().level().isClientSide) {
                    Load.backpacks(event.getEntity()).getBackpacks().tryAutoPickup(stack);
                }
            }
        });
         */

        ForgeEvents.registerForgeEvent(EntityJoinLevelEvent.class, event -> {
            OnMobSpawn.onLoad(event.getEntity());
        });

        ForgeEvents.registerForgeEvent(TickEvent.ServerTickEvent.class, event -> {
            if (event.phase == TickEvent.Phase.END) {
                OnServerTick.onEndTick(ServerLifecycleHooks.getCurrentServer());
            }
        });

        ForgeEvents.registerForgeEvent(TickEvent.LevelTickEvent.class, event -> {
            if (event.phase == TickEvent.Phase.END && event.level instanceof ServerLevel) {
                OnTickDungeonWorld.onEndTick((ServerLevel) event.level);
            }
        });

        ForgeEvents.registerForgeEvent(AttackEntityEvent.class, event -> {
            if (event.getEntity() instanceof ServerPlayer) {
                StopCastingIfInteract.interact(event.getEntity());
            }
        });

        ForgeEvents.registerForgeEvent(PlayerEvent.StartTracking.class, event -> {
            if (event.getEntity() instanceof ServerPlayer) {
                OnTrackEntity.onPlayerStartTracking((ServerPlayer) event.getEntity(), event.getTarget());
            }
        });

        ExileEvents.ON_CHEST_LOOTED.register(new OnLootChestEvent());
        ExileEvents.LIVING_ENTITY_TICK.register(new OnEntityTick());
        ExileEvents.MOB_DEATH.register(new OnMobDeathDrops());

        ExileEvents.DAMAGE_BEFORE_CALC.register(new OnNonPlayerDamageEntityEvent());

        ExileEvents.DAMAGE_BEFORE_CALC.register(new ScaleVanillaMobDamage());
        ExileEvents.DAMAGE_BEFORE_CALC.register(new ScaleVanillaPlayerDamage());

        ExileEvents.DAMAGE_AFTER_CALC.register(new OnPlayerDamageEntityEvent());

        ExileEvents.PLAYER_DEATH.register(new OnPlayerDeath());

        ForgeEvents.registerForgeEvent(LivingHurtEvent.class, event -> {
            // reduce enviro dmg based on total hp from formula
            try {
                if (event.getEntity() instanceof Player) {
                    if (LivingHurtUtils.isEnviromentalDmg(event.getSource())) {
                        EntityData data = Load.Unit(event.getEntity());
                        float reduction = Health.getInstance()
                                .getUsableValue((int) data.getUnit()
                                        .healthData()
                                        .getValue(), data.getLevel());

                        float multi = 1 - reduction;

                        event.setAmount(event.getAmount() * multi);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        ExileEvents.PLAYER_DEATH.register(new EventConsumer<ExileEvents.OnPlayerDeath>() {
            @Override
            public void accept(ExileEvents.OnPlayerDeath event) {
                if (event.player instanceof ServerPlayer) {
                    try {
                        PlayerData data = Load.playerRPGData(event.player);

                        data.deathStats.deathPos = event.player.blockPosition();
                        data.deathStats.deathDim = event.player.level().dimension()
                                .location()
                                .toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        ExileEvents.ON_PLAYER_LOGIN.register(new EventConsumer<ExileEvents.OnPlayerLogin>() {
            @Override
            public void accept(ExileEvents.OnPlayerLogin event) {
                OnLogin.onLoad(event.player);
            }
        });
        ExileEvents.AFTER_DATABASE_LOADED.register(new EventConsumer<ExileEvents.AfterDatabaseLoaded>() {
            @Override
            public void accept(ExileEvents.AfterDatabaseLoaded event) {
                Cached.reset();
                setupStatsThatAffectVanillaStatsList();
                ErrorChecks.getAll()
                        .forEach(x -> x.check());
            }
        });

    }

    private static void setupStatsThatAffectVanillaStatsList() {
        Cached.VANILLA_STAT_UIDS_TO_CLEAR_EVERY_STAT_CALC = new ArrayList<>();

        ExileDB.Stats()
                .getFilterWrapped(x -> x instanceof AttributeStat).list.forEach(x -> {
                    AttributeStat attri = (AttributeStat) x;
                    Cached.VANILLA_STAT_UIDS_TO_CLEAR_EVERY_STAT_CALC.add(ImmutablePair.of(attri.attribute, attri.uuid));
                });
    }
}
