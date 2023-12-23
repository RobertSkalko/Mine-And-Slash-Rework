package com.robertx22.age_of_exile.saveclasses.unit;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.capability.player.helper.GemInventoryHelper;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.AttributeStat;
import com.robertx22.age_of_exile.database.data.stats.types.LearnSpellStat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.ICoreStat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.ITransferToOtherStats;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.Blood;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.BloodUser;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.event_hooks.damage_hooks.util.AttackInformation;
import com.robertx22.age_of_exile.event_hooks.my_events.CollectGearEvent;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.saveclasses.spells.SpellCastingData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.GearStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.MiscStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.IAffectsStatsInCalc;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.Cached;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.stat_calculation.CommonStatUtils;
import com.robertx22.age_of_exile.uncommon.stat_calculation.MobStatUtils;
import com.robertx22.age_of_exile.uncommon.stat_calculation.PlayerStatUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.EfficientMobUnitPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.EntityUnitPacket;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

// this stores data that can be lost without issue, stats that are recalculated all the time

public class Unit {


    private StatContainer stats = new StatContainer();


    public InCalcStatData getStatInCalculation(Stat stat) {
        return getStats().getStatInCalculation(stat);
    }

    public InCalcStatData getStatInCalculation(String stat) {
        return getStats().getStatInCalculation(stat);
    }

    public boolean isBloodMage() {
        return getCalculatedStat(BloodUser.getInstance())
                .getValue() > 0;
    }

    public StatContainer getStats() {
        if (stats == null) {
            stats = new StatContainer();
        }
        return stats;
    }

    public StatData getCalculatedStat(Stat stat) {
        return getCalculatedStat(stat.GUID());
    }

    public StatData getCalculatedStat(String guid) {
        if (getStats().stats == null) {
            this.initStats();
        }
        return getStats().stats.getOrDefault(guid, StatData.empty());
    }

    public Unit() {

    }

    public void initStats() {
        getStats().stats = new HashMap<String, StatData>();
    }


    // Stat shortcuts
    public Health health() {
        return Health.getInstance();
    }

    public Mana mana() {
        return Mana.getInstance();
    }

    public StatData healthData() {
        try {
            return getCalculatedStat(Health.GUID);
        } catch (Exception e) {
        }
        return StatData.empty();
    }

    public StatData bloodData() {
        try {
            return getCalculatedStat(Blood.GUID);
        } catch (Exception e) {
        }
        return StatData.empty();
    }

    public StatData energyData() {
        try {
            return getCalculatedStat(Energy.GUID);
        } catch (Exception e) {

        }
        return StatData.empty();
    }

    public StatData magicShieldData() {
        try {
            return getCalculatedStat(MagicShield.GUID);
        } catch (Exception e) {

        }
        return StatData.empty();
    }

    public StatData manaData() {
        try {
            return getCalculatedStat(Mana.GUID);
        } catch (Exception e) {

        }
        return StatData.empty();
    }

    public String randomRarity(int lvl, EntityData data) {

        List<MobRarity> rarities = ExileDB.MobRarities()
                .getList()
                .stream()
                .filter(x -> data.getLevel() >= x.minMobLevelForRandomSpawns() || data.getLevel() >= GameBalanceConfig.get().MAX_LEVEL)
                .collect(Collectors.toList());


        if (rarities.isEmpty()) {
            rarities.add(ExileDB.MobRarities().get(IRarity.COMMON_ID));
        }


        MobRarity finalRarity = RandomUtils.weightedRandom(rarities);


        return finalRarity.GUID();

    }

    private static class DirtyCheck {
        public int hp;

        public boolean isDirty(DirtyCheck newcheck) {

            if (newcheck.hp != hp) {
                return true;
            }

            return false;

        }
    }

    /**
     * @return checks if it should be synced to clients. Clients currently only see
     * health and status effects
     */
    private DirtyCheck getDirtyCheck() {

        if (getStats().stats == null || getStats().stats.isEmpty()) {
            this.initStats();
        }

        DirtyCheck check = new DirtyCheck();

        check.hp = (int) getCalculatedStat(Health.GUID).getValue();

        return check;
    }


    public void recalculateStats(LivingEntity entity, EntityData data, AttackInformation dmgData, int skillGem) {

        try {
            if (entity.level().isClientSide) {
                return;
            }

            //data.setEquipsChanged(false);

            if (data.getUnit() == null) {
                data.setUnit(this);
            }

            List<GearData> gears = new ArrayList<>();
            new CollectGearEvent.CollectedGearStacks(entity, gears, dmgData);


            stats.stats.clear();
            stats.statsInCalc.clear();

            DirtyCheck old = getDirtyCheck();

            List<StatContext> statContexts = new ArrayList<>();


            statContexts.addAll(CommonStatUtils.addExactCustomStats(entity));
            statContexts.add(data.getStatusEffectsData().getStats(entity));
            statContexts.addAll(addGearStats(gears));
            CommonStatUtils.addMapAffixStats(entity);

            if (data.isSummon()) {
                statContexts.addAll(MobStatUtils.addSummonStats((TamableAnimal) entity));
            }

            if (entity instanceof Player p) {
                var playerData = Load.player(p);

                statContexts.addAll(PlayerStatUtils.addToolStats(p));

                statContexts.addAll(playerData.buff.getStatAndContext(p));

                statContexts.addAll(playerData.getSkillGemInventory().getAuraStats(entity));
                statContexts.addAll(playerData.getJewels().getStatAndContext(entity));
                playerData.statPoints.addStats(this);

                statContexts.addAll(PlayerStatUtils.AddPlayerBaseStats(entity));
                statContexts.addAll(PlayerStatUtils.addNewbieElementalResists(data));
                statContexts.addAll(playerData.talents.getStatAndContext(entity));
                statContexts.addAll(playerData.ascClass.getStatAndContext(entity));

                if (skillGem > -1 && skillGem <= GemInventoryHelper.MAX_SKILL_GEMS) {
                    var gem = playerData.getSkillGemInventory().getHotbarGem(skillGem);
                    for (SkillGemData d : gem.getSupportDatas()) {
                        if (d.getSupport() != null) {
                            statContexts.add(new MiscStatCtx(d.getSupport().GetAllStats(data, d)));
                        }
                    }
                    var spell = gem.getSpell();
                    if (spell != null) {
                        var stats = spell.getStats(p);
                        statContexts.add(new MiscStatCtx(stats));
                    }
                }
            } else {
                statContexts.addAll(MobStatUtils.getMobBaseStats(data, entity));
                statContexts.addAll(MobStatUtils.getAffixStats(entity));
                statContexts.addAll(MobStatUtils.getWorldMultiplierStats(entity));
                MobStatUtils.addMobBaseElementalBonusDamages(entity, data);
                MobStatUtils.addMapTierStats(entity);
                statContexts.addAll(MobStatUtils.getMobConfigStats(entity, data));
            }


            HashMap<StatContext.StatCtxType, List<StatContext>> map = new HashMap<>();
            for (StatContext.StatCtxType type : StatContext.StatCtxType.values()) {
                map.put(type, new ArrayList<>());
            }
            // create map
            statContexts.forEach(x -> {
                map.get(x.type).add(x);
            });
            // apply ctx modifier stats
            map.forEach((key, value) -> value
                    .forEach(v -> {
                        v.stats.forEach(s -> {
                            if (s.getStat() == null) {
                                //System.out.println(s.getStatId());
                            } else {
                                if (s.getStat().statContextModifier != null) {
                                    map.get(s.getStat().statContextModifier.getCtxTypeNeeded()).forEach(c -> s.getStat().statContextModifier.modify(s, c));
                                }
                            }
                        });
                    }));

            //apply all ctx stats to in-calc
            statContexts.forEach(x -> x.stats.forEach(s -> s.applyToStatInCalc(this)));

            addVanillaHpToStats(entity, data);

            // apply stats that add to others
            getStats().modifyInCalc(calcStat -> {
                if (calcStat.GetStat() instanceof IAffectsStatsInCalc aff) {
                    aff.affectStats(data, calcStat);
                }
            });
            // todo these should probably be after stats are out of calculation..? or they should be another phase
            // apply transfer stats
            getStats().modifyInCalc(calcStat -> {
                if (calcStat.GetStat() instanceof ITransferToOtherStats transfer) {
                    transfer.transferStats(data, calcStat);
                }
            });
            // apply core stats
            getStats().modifyInCalc(calcStat -> {
                if (calcStat.GetStat() instanceof ICoreStat core) {
                    core.addToOtherStats(data, calcStat);
                }
            });
            stats.calculate();


            DirtyCheck aftercalc = getDirtyCheck();

            Cached.VANILLA_STAT_UIDS_TO_CLEAR_EVERY_STAT_CALC.forEach(x -> {
                AttributeInstance in = entity.getAttribute(x.left);
                if (in.getModifier(x.right) != null) {
                    in.removeModifier(x.right);
                }
            });

            this.getStats().stats.values()
                    .forEach(x -> {
                        if (x.GetStat() instanceof AttributeStat) {
                            AttributeStat stat = (AttributeStat) x.GetStat();
                            stat.addToEntity(entity, x);
                        }
                    });


            if (old.isDirty(aftercalc)) {
                if (!Unit.shouldSendUpdatePackets((LivingEntity) entity)) {
                    return;
                }
                Packets.sendToTracking(getUpdatePacketFor(entity, data), entity);
            }

            if (entity instanceof Player p) {
                Load.player(p).spellCastingData.resetSpells();

                this.getStats().stats.values()
                        .forEach(x -> {
                            if (x.GetStat() instanceof LearnSpellStat learn) {
                                Load.player(p).spellCastingData.addSpell(new SpellCastingData.InsertedSpell(learn.spell.GUID(), (int) x.getValue()));
                            }
                        });

            
                Load.player(p).getSkillGemInventory().removeAurasIfCantWear(p);

                Packets.sendToClient((Player) entity, new EntityUnitPacket(entity));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addVanillaHpToStats(LivingEntity entity, EntityData data) {
        if (entity instanceof Player) {

            float maxhp = Mth.clamp(entity.getMaxHealth(), 0, 500);
            // all increases after this would just reduce enviro damage

            getStats().getStatInCalculation(Health.getInstance()).addAlreadyScaledFlat(maxhp);

            // add vanila hp to extra hp
        }
    }

    private List<StatContext> addGearStats(List<GearData> gears) {

        List<StatContext> ctxs = new ArrayList<>();

        gears.forEach(x -> {
            List<ExactStatData> stats = x.gear.GetAllStats();

            if (x.percentStatUtilization != 100) {
                // multi stats like for offfhand weapons
                float multi = x.percentStatUtilization / 100F;
                stats.forEach(s -> s.multiplyBy(multi));
            }
            ctxs.add(new GearStatCtx(x.gear, stats));

        });

        return ctxs;

    }

    public static boolean shouldSendUpdatePackets(LivingEntity en) {
        if (ServerContainer.get().DONT_SYNC_DATA_OF_AMBIENT_MOBS.get()) {
            return en.getType()
                    .getCategory() != MobCategory.AMBIENT && en.getType()
                    .getCategory() != MobCategory.WATER_AMBIENT;
        }
        return true;
    }

    public static MyPacket getUpdatePacketFor(LivingEntity en, EntityData data) {
        return new EfficientMobUnitPacket(en, data);
    }

}
