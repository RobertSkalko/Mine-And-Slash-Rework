package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemUtils {
    public static Item.Properties getDefaultGearProperties() {

        Item.Properties prop = new Item.Properties();

        return prop;
    }

    public static void tryAnnounceItem(ItemStack stack, Player player) {

        try {
            if (player == null) {
                return;
            }
            if (!ServerContainer.get().ENABLE_LOOT_ANNOUNCEMENTS.get()) {
                return;
            }

            
            GearItemData gear = StackSaving.GEARS.loadFrom(stack);

            if (gear != null) {

                GearRarity rar = (GearRarity) gear.getRarity();

                if (rar.announce_in_chat) {

                    MutableComponent name = Component.literal("").append(player.getName())
                            .withStyle(ChatFormatting.BOLD)
                            .withStyle(ChatFormatting.LIGHT_PURPLE);

                    Component txt = name
                            .append(" found a ")
                            .append(rar.locName()
                                    .withStyle(rar.textFormatting())
                                    .withStyle(ChatFormatting.BOLD))
                            .append(" item!");

                    player.getServer()
                            .getPlayerList()
                            .getPlayers()
                            .forEach(x -> x.displayClientMessage(txt, false));

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
