package com.robertx22.age_of_exile.mmorpg.event_registers;

import com.robertx22.age_of_exile.capability.player.PlayerData;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.AttributeStat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.event_hooks.damage_hooks.LivingHurtUtils;
import com.robertx22.age_of_exile.event_hooks.damage_hooks.reworked.NewDamageMain;
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
import com.robertx22.age_of_exile.mmorpg.ForgeEvents;
import com.robertx22.age_of_exile.mmorpg.ModErrors;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashPotions;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.OnMobKilledByDamageEvent;
import com.robertx22.age_of_exile.uncommon.error_checks.base.ErrorChecks;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.Cached;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.*;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;

public class CommonEvents {

    public static void register() {

        ForgeEvents.registerForgeEvent(EntityAttributeCreationEvent.class, x -> {
            x.put(SlashEntities.SPIRIT_WOLF.get(), Wolf.createAttributes().add(Attributes.MOVEMENT_SPEED, 0.4).build());
            x.put(SlashEntities.SKELETON.get(), Skeleton.createAttributes().add(Attributes.MOVEMENT_SPEED, 0.3).build());
            x.put(SlashEntities.SPIDER.get(), Spider.createAttributes().add(Attributes.MOVEMENT_SPEED, 0.5).build());
            x.put(SlashEntities.ZOMBIE.get(), Zombie.createAttributes().add(Attributes.MOVEMENT_SPEED, 0.4).build());
            x.put(SlashEntities.THORNY_MINION.get(), Zombie.createAttributes().add(Attributes.MOVEMENT_SPEED, 0.2).build());
            x.put(SlashEntities.EXPLODE_MINION.get(), Zombie.createAttributes().add(Attributes.MOVEMENT_SPEED, 0.3).build());

            x.put(SlashEntities.FIRE_GOLEM.get(), Zombie.createAttributes().add(Attributes.MOVEMENT_SPEED, 0.45).build());
            x.put(SlashEntities.COLD_GOLEM.get(), Zombie.createAttributes().add(Attributes.MOVEMENT_SPEED, 0.45).build());
            x.put(SlashEntities.LIGHTNING_GOLEM.get(), Zombie.createAttributes().add(Attributes.MOVEMENT_SPEED, 0.45).build());

        });


        OnItemInteract.register();


        // instant bows
        ForgeEvents.registerForgeEvent(ArrowLooseEvent.class, event -> {
            if (event.getEntity().hasEffect(SlashPotions.INSTANT_ARROWS.get())) {
                event.setCharge(100);
            }
        });
        ForgeEvents.registerForgeEvent(ArrowNockEvent.class, event -> {
            if (event.getEntity().hasEffect(SlashPotions.INSTANT_ARROWS.get())) {
                event.setAction(InteractionResultHolder.pass(event.getBow()));
                event.getBow().releaseUsing(event.getLevel(), event.getEntity(), 2000);
                int cd = 20 - event.getEntity().getEffect(SlashPotions.INSTANT_ARROWS.get()).getAmplifier();

                if (cd < 3) {
                    cd = 3;
                }
                event.getEntity().getCooldowns().addCooldown(event.getBow().getItem(), cd); // todo
            }
        });
        ForgeEvents.registerForgeEvent(TickEvent.PlayerTickEvent.class, event -> {
            if (!event.player.level().isClientSide) {
                if (event.player.hasEffect(SlashPotions.INSTANT_ARROWS.get())) {
                    if (event.player.getMainHandItem().getItem() instanceof BowItem) {
                        event.player.getMainHandItem().getOrCreateTag().putBoolean("instant", true);
                    }
                }
            }
        });

        ForgeEvents.registerForgeEvent(TickEvent.PlayerTickEvent.class, event -> {
            if (!event.player.level().isClientSide) {
                var data = Load.player(event.player).prophecy;
                if (data.canTakeOffers() && data.offers.isEmpty()) {
                    data.regenerateNewOffers(event.player);
                }
            }
        });

        // instant bows


        ForgeEvents.registerForgeEvent(LivingDeathEvent.class, event -> {

            if (event.getEntity() != null) {
                if (event.getSource().getEntity() instanceof Player p) {
                    LivingEntity target = event.getEntity();
                    if (!Load.Unit(target).getCooldowns().isOnCooldown("death")) {
                        DamageEvent dmg = Load.Unit(target).lastDamageTaken;
                        if (dmg != null) {
                            // make absolutely sure this isn't called twice somehow
                            Load.Unit(target).getCooldowns().setOnCooldown("death", Integer.MAX_VALUE);
                            OnMobKilledByDamageEvent e = new OnMobKilledByDamageEvent(dmg);
                            e.Activate();
                        }
                    }
                }
            }
        });


        ForgeEvents.registerForgeEvent(EntityJoinLevelEvent.class, event -> {
            OnMobSpawn.onLoad(event.getEntity());
        });


        ForgeEvents.registerForgeEvent(EntityItemPickupEvent.class, event -> {
            if (event.getEntity() instanceof ServerPlayer player) {
                if (!player.level().isClientSide) {
                    ItemStack stack = event.getItem().getItem();
                    if (!stack.isEmpty()) {

                        if (!player.level().isClientSide) {
                            if (Load.player(player).config.salvage.trySalvageOnPickup(player, stack)) {
                                stack.shrink(100);
                            } else {
                                Load.backpacks(player).getBackpacks().tryAutoPickup(event.getEntity(), stack);
                            }
                        }
                    }
                }
            }
        });
        ForgeEvents.registerForgeEvent(PlayerEvent.Clone.class, event -> {
            try {
                if (event.getEntity() instanceof ServerPlayer p) {
                    if (!p.level().isClientSide) {
                        var data = Load.player(p);
                        data.spellCastingData.cancelCast(p); // so player doesn't continue casting spell after reviving
                    }
                }
            } catch (Exception e) {
                ModErrors.print(e);
            }
        });


        ForgeEvents.registerForgeEvent(TickEvent.PlayerTickEvent.class, event -> {
            if (!event.player.level().isClientSide) {
                if (event.phase == TickEvent.Phase.END) {
                    OnServerTick.onEndTick((ServerPlayer) event.player);
                }
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

        NewDamageMain.init();


        // ExileEvents.DAMAGE_BEFORE_CALC.register(new ScaleVanillaMobDamage()); todo this doesnt seem needed..?
        //ExileEvents.DAMAGE_BEFORE_CALC.register(new ScaleVanillaPlayerDamage()); todo same


        ExileEvents.PLAYER_DEATH.register(new OnPlayerDeath());


        ForgeEvents.registerForgeEvent(LivingHurtEvent.class, event -> {
            try {
                if (event.getEntity() instanceof Player == false) {
                    if (LivingHurtUtils.isEnviromentalDmg(event.getSource())) {
                        if (WorldUtils.isMapWorldClass(event.getEntity().level())) {
                            event.setAmount(0);
                            event.setCanceled(true);
                        }
                    }
                } else {
                    if (LivingHurtUtils.isEnviromentalDmg(event.getSource())) {

                        // spend magic shield on envi dmg
                        float dmg = event.getAmount();
                        float multi = dmg / event.getEntity().getMaxHealth();
                        float spend = Load.Unit(event.getEntity()).getUnit().magicShieldData().getValue() * multi;
                        Load.Unit(event.getEntity()).getResources().spend(event.getEntity(), ResourceType.magic_shield, spend);

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
                        PlayerData data = Load.player(event.player);

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
