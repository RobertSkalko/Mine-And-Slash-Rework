package com.robertx22.age_of_exile.prophecy;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.league.LeagueMechanics;
import com.robertx22.age_of_exile.database.data.map_affix.MapAffix;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.maps.AffectedEntities;
import com.robertx22.age_of_exile.maps.MapItemData;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.MathHelper;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerProphecies implements IStatCtx {

    public List<ProphecyData> rewardOffers = new ArrayList<>();


    public List<String> affixOffers = new ArrayList<>();


    public String mapid = "";

    public List<String> affixesTaken = new ArrayList<>();


    public int numMobAffixesCanAdd = 0;

    private int totalTiers = 0;
    private int totalLvls = 0;

    private int lvlsAdded = 0;
    private int tiersAdded = 0;

    private int favor = 0;


    public void clearIfNewMap(MapItemData map) {

        this.mapid = map.uuid;

        totalLvls = 0;
        lvlsAdded = 0;
        totalTiers = 0;
        tiersAdded = 0;

        favor = 0;

        numMobAffixesCanAdd = 0;
        affixesTaken.clear();

        affixOffers.clear();
        rewardOffers.clear();
    }

    public int getCurrency() {
        return favor;
    }

    public void regenAffixOffers() {
        this.affixOffers.clear();

        for (int i = 0; i < 3; i++) {
            MapAffix affix = ExileDB.MapAffixes().getFilterWrapped(x -> x.req.equals(LeagueMechanics.PROPHECY.GUID()) && x.affected == AffectedEntities.Players).random();
            affixOffers.add(affix.GUID());
        }
    }

    public void gainFavor(int num) {
        this.favor += num * affixesTaken.size();
    }

    public void forceSetCurrency(int num) {
        this.favor = num;
    }

    public int getAverageTier() {
        if (tiersAdded < 1) {
            return 0;
        }
        return totalTiers / tiersAdded;
    }

    public int getAverageLevel() {
        if (lvlsAdded < 1) {
            return 1;
        }

        return totalLvls / lvlsAdded;
    }


    public void OnDeath(Player p) {
        if (WorldUtils.isDungeonWorld(p.level())) {
            this.favor = MathHelper.clamp(favor - GameBalanceConfig.get().PROPHECY_CURRENCY_LOST_ON_MAP_DEATH, 0, favor);
            p.sendSystemMessage(Chats.PROPHECY_MAP_DEATHCURRENCY.locName(GameBalanceConfig.get().PROPHECY_CURRENCY_LOST_ON_MAP_DEATH).withStyle(ChatFormatting.RED));
        }
    }


    public void onKillMobInMap(Player p, LivingEntity en) {

        int tier = Load.mapAt(en.level(), en.blockPosition()).map.tier;

        int lvl = Load.Unit(en).getLevel();

        totalTiers += tier;
        totalLvls += lvl;

        lvlsAdded++;
        tiersAdded++;

        this.gainFavor(1);
    }


    public void regenerateNewOffers(Player p) {

        rewardOffers = new ArrayList<>();

        for (int i = 0; i < ServerContainer.get().PROPHECY_OFFERS_PER_REROLL.get(); i++) {
            rewardOffers.add(ProphecyGeneration.generate(p));
        }

    }


    public void tryAcceptReward(Player p, String uuid) {


        ProphecyData data = rewardOffers.stream().filter(x -> x.uuid.equals(uuid)).findAny().get();

        if (data != null) {

            if (favor < data.cost) {
                p.sendSystemMessage(Chats.NOT_ENOUGH_FAVOR_TO_BUY_PROPHECY.locName().withStyle(ChatFormatting.RED));
                return;
            }

            this.favor -= data.cost;


            SoundUtils.playSound(p, SoundEvents.EXPERIENCE_ORB_PICKUP);

            rewardOffers.removeIf(x -> x.uuid.equals(data.uuid)); // todo check if this works

            for (ItemStack stack : data.generateRewards(p)) {
                PlayerUtils.giveItem(stack, p);
            }

        }
    }

    @Override
    public List<StatContext> getStatAndContext(LivingEntity en) {
        List<ExactStatData> list = new ArrayList<>();

        if (WorldUtils.isMapWorldClass(en.level())) {
            for (String s : this.affixesTaken) {
                list.addAll(ExileDB.MapAffixes().get(s).getStats(100, Load.Unit(en).getLevel()));
            }
        }

        var ctx = new SimpleStatCtx(StatContext.StatCtxType.PROPHECY_CURSE, list);

        return Arrays.asList(ctx);
    }
}
