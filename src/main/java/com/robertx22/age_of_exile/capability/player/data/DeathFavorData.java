package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.MathHelper;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class DeathFavorData {

    private float favor = 250;

    public void set(Player p, float f) {
        GearRarity rar = getRarity();

        boolean upped = f > favor;

        this.favor = MathHelper.clamp(f, 0, Integer.MAX_VALUE);

        if (rar != getRarity()) {
            if (upped) {
                p.sendSystemMessage(Chats.FAVOR_UP.locName(getRarity().locName()));
            } else {
                p.sendSystemMessage(Chats.FAVOR_DOWN.locName(getRarity().locName()));
            }
        }
    }

    public void onSecond(Player p) {
        set(p, favor + getRarity().getFavorGainEverySecond());
    }

    public void onDeath(Player p) {
        float loss = ServerContainer.get().FAVOR_DEATH_LOSS.get().floatValue();
        set(p, favor - loss);

        p.sendSystemMessage(Chats.FAVOR_DEATH_MSG.locName((int) loss).withStyle(ChatFormatting.DARK_PURPLE));
    }

    public void onLootChest(Player p) {
        set(p, favor + ServerContainer.get().FAVOR_CHEST_GAIN.get().floatValue());
    }

    public GearRarity getRarity() {
        GearRarity rar = ExileDB.GearRarities().get(IRarity.COMMON_ID);
        while (rar.hasHigherRarity() && favor >= rar.getHigherRarity().favor_needed) {
            rar = rar.getHigherRarity();
        }
        
        return rar;
    }

    public List<Component> getTooltip() {
        List<Component> tooltip = new ArrayList<>();

        GearRarity rar = getRarity();

        tooltip.add(rar.locName().append(Words.Favor_In_GUI.locName()).withStyle(rar.textFormatting()));

        tooltip.add(Component.empty());

        tooltip.add(Words.Current_Favor.locName((int) favor).withStyle(ChatFormatting.YELLOW));

        tooltip.add(Component.empty());

        tooltip.add(Words.Loot_Exp_Multiplier.locName(getLootExpMulti()).withStyle(rar.textFormatting()));

        tooltip.add(Component.empty());

        if (this.getRarity().getFavorGainEverySecond() > 0) {
            int perhour = (int) (getRarity().getFavorGainEverySecond() * 60F * 60f);
            tooltip.add(Words.FAVOR_REGEN_PER_HOUR.locName(perhour));
        }
        tooltip.add(Words.FAVOR_PER_CHEST.locName(ServerContainer.get().FAVOR_CHEST_GAIN.get()));
        tooltip.add(Words.FAVOR_PER_DEATH.locName(ServerContainer.get().FAVOR_DEATH_LOSS.get()));

        return tooltip;
    }

    public ResourceLocation getTexture() {
        return SlashRef.guiId("favor/" + getRarity().item_tier);
    }

    public float getLootExpMulti() {
        return getRarity().favor_loot_multi;
    }
}
