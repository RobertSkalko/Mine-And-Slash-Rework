package com.robertx22.mine_and_slash.prophecy;

import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.mine_and_slash.database.data.league.LeagueMechanics;
import com.robertx22.mine_and_slash.database.data.map_affix.MapAffix;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.maps.AffectedEntities;
import com.robertx22.mine_and_slash.maps.MapItemData;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IStatCtx;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.mine_and_slash.uncommon.MathHelper;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.WorldUtils;
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


    private int favor = 0;


    public void clearIfNewMap(MapItemData map) {

        this.mapid = map.uuid;

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
        this.favor += num;
    }

    public void forceSetCurrency(int num) {
        this.favor = num;
    }


    public void OnDeath(Player p) {
        if (WorldUtils.isDungeonWorld(p.level())) {
            this.favor = MathHelper.clamp(favor - GameBalanceConfig.get().PROPHECY_CURRENCY_LOST_ON_MAP_DEATH, 0, favor);
            p.sendSystemMessage(Chats.PROPHECY_MAP_DEATHCURRENCY.locName(GameBalanceConfig.get().PROPHECY_CURRENCY_LOST_ON_MAP_DEATH).withStyle(ChatFormatting.RED));
        }
    }


    public void onKillMobInMap(Player p, LivingEntity en) {

        int add = GameBalanceConfig.get().PROPHECY_GAIN_PER_MOB_UNCURSED;
        if (!affixesTaken.isEmpty()) {
            add = GameBalanceConfig.get().PROPHECY_GAIN_PER_MOB * affixesTaken.size();
        }
        add *= Load.Unit(en).getMobRarity().loot_multi;
        this.gainFavor(add);
    }


    public void regenerateNewOffers(Player p) {

        rewardOffers = new ArrayList<>();

        for (int i = 0; i < ServerContainer.get().PROPHECY_OFFERS_PER_REROLL.get(); i++) {
            rewardOffers.add(ProphecyGeneration.generate(p));
        }

    }


    public void tryAcceptReward(Player p, String uuid) {

        var map = Load.mapAt(p.level(), p.blockPosition());

        if (map == null) {
            p.sendSystemMessage(Chats.MUST_BE_IN_MAP_TO_ACCEPT_PROPHECY.locName().withStyle(ChatFormatting.RED));
            return;
        }
        if (!WorldUtils.isDungeonWorld(p.level()) || !map.map.uuid.equals(this.mapid)) {
            // this is to stop people gathering points in lvl 1 maps and going into lvl 100 tier 100 maps to claim rewards
            p.sendSystemMessage(Chats.MUST_BE_IN_MAP_TO_ACCEPT_PROPHECY.locName().withStyle(ChatFormatting.RED));
            return;
        }
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
