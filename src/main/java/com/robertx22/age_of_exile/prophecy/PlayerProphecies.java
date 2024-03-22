package com.robertx22.age_of_exile.prophecy;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlayerProphecies {

    public List<ProphecyData> offers = new ArrayList<>();
    public List<ProphecyData> taken = new ArrayList<>();

    private int totalTiers = 0;
    private int totalLvls = 0;

    private int lvlsAdded = 0;
    private int tiersAdded = 0;

    public float progress = 0;

    public boolean canTakeOffers() {
        return canClaim();
    }

    public int favor = 0;

    public boolean canClaim() {
        return progress >= 100;
    }

    public int getAverageTier() {
        return totalTiers / tiersAdded;
    }

    public int getAverageLevel() {
        return totalLvls / lvlsAdded;
    }


    public void onLoginRegenIfEmpty(Player p) {
        if (offers.isEmpty() && taken.isEmpty()) {
            this.regenerateNewOffers(p);
        }
    }

    public void onKillMobInMap(LivingEntity en) {

        int tier = Load.mapAt(en.level(), en.blockPosition()).map.tier;

        int lvl = Load.Unit(en).getLevel();


        totalTiers += tier;
        totalLvls += lvl;

        lvlsAdded++;
        tiersAdded++;

        progress += GameBalanceConfig.get().PROPHECY_PROGRESS_PER_MOB_KILL;

        if (progress > 100) {
            progress = 100;
        }
    }

    public int getRerollCost() {
        return GameBalanceConfig.get().PROPHECY_REROLL_COST;
    }

    public void regenerateNewOffers(Player p) {

        offers = new ArrayList<>();

        for (int i = 0; i < ServerContainer.get().PROPHECY_OFFERS_PER_REROLL.get(); i++) {
            offers.add(ProphecyGeneration.generate(p));
        }

    }

    public void onBarFinishGiveRewards(Player p) {

        for (ProphecyData data : taken) {
            for (ItemStack stack : data.generateRewards(p)) {
                PlayerUtils.giveItem(stack, p);
            }
        }

        this.offers = new ArrayList<>();

        this.taken = new ArrayList<>();

        this.progress = 0;
    }

    public void tryAcceptOffer(Player p, String uuid) {

        if (taken.size() >= 9) {
            p.sendSystemMessage(Chats.YOU_CANT_TAKE_ANY_MORE_PROPHECIES.locName().withStyle(ChatFormatting.RED));
            return;
        }

        if (!this.canTakeOffers()) {
            p.sendSystemMessage(Chats.YOU_NEED_FULL_BAR_TO_TAKE_PROPHECY.locName().withStyle(ChatFormatting.RED));
            return;
        }


        ProphecyData data = offers.stream().filter(x -> x.uuid.equals(uuid)).findAny().get();

        if (data != null) {

            if (favor < data.cost) {
                p.sendSystemMessage(Chats.NOT_ENOUGH_FAVOR_TO_BUY_PROPHECY.locName().withStyle(ChatFormatting.RED));
                return;
            }

            this.favor -= data.cost;


            SoundUtils.playSound(p, SoundEvents.EXPERIENCE_ORB_PICKUP);

            offers.removeIf(x -> x.uuid.equals(data.uuid)); // todo check if this works
            taken.add(data);
        }
    }
}
