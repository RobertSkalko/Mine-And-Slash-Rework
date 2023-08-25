package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.MathHelper;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
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

        this.favor = MathHelper.clamp(f, 0, Integer.MAX_VALUE);

        if (rar != getRarity()) {
            p.sendSystemMessage(Component.literal("Your favor is now ").append(getRarity().locName()));
        }
    }

    public void onSecond(Player p) {
        set(p, favor + getRarity().getFavorGainEverySecond());
    }

    public void onDeath(Player p) {
        set(p, favor - ServerContainer.get().FAVOR_DEATH_LOSS.get().floatValue());
    }

    public void onLootChest(Player p) {
        set(p, favor + ServerContainer.get().FAVOR_CHEST_GAIN.get().floatValue());
    }

    public GearRarity getRarity() {
        GearRarity rar = ExileDB.GearRarities().get(IRarity.COMMON_ID);
        if (rar.hasHigherRarity() && favor >= rar.getHigherRarity().favor_needed) {
            rar = rar.getHigherRarity();
        }
        return rar;
    }

    public List<Component> getTooltip() {
        List<Component> tooltip = new ArrayList<>();

        GearRarity rar = getRarity();

        tooltip.add(rar.locName().append(" Favor").withStyle(rar.textFormatting()));

        tooltip.add(Component.empty());

        tooltip.add(Component.literal("Current: " + (int) favor).withStyle(ChatFormatting.YELLOW));

        tooltip.add(Component.empty());


        tooltip.add(Component.literal("Loot/Exp Multiplier: " + getLootExpMulti() + "x").withStyle(rar.textFormatting()));


        return tooltip;
    }

    public ResourceLocation getTexture() {
        return SlashRef.guiId("favor/" + getRarity().item_tier);
    }

    public float getLootExpMulti() {
        return getRarity().favor_loot_multi;
    }
}
