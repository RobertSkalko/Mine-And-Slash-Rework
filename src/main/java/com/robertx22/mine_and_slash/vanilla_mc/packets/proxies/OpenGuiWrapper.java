package com.robertx22.mine_and_slash.vanilla_mc.packets.proxies;

import com.robertx22.library_of_exile.main.ExileLog;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.gui.card_picker.CardPickScreen;
import com.robertx22.mine_and_slash.gui.card_picker.ICard;
import com.robertx22.mine_and_slash.gui.card_picker.MapUpgradeCard;
import com.robertx22.mine_and_slash.gui.card_picker.ProphecyCurseCard;
import com.robertx22.mine_and_slash.gui.screens.character_screen.MainHubScreen;
import com.robertx22.mine_and_slash.gui.screens.map.MapScreen;
import com.robertx22.mine_and_slash.gui.wiki.BestiaryGroup;
import com.robertx22.mine_and_slash.gui.wiki.reworked.NewWikiScreen;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import net.minecraft.world.entity.player.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OpenGuiWrapper {

    public static void openMainHub() {
        net.minecraft.client.Minecraft.getInstance().setScreen(new MainHubScreen());
    }

    public static void openMapScreen() {
        net.minecraft.client.Minecraft.getInstance().setScreen(new MapScreen());
    }

    public static CardPickScreen openMapUpgradePicker() {
        Player p = ClientOnly.getPlayer();

        List<ICard> cards = Arrays.asList(
                new MapUpgradeCard(MapUpgradeCard.MapOption.UPGRADE),
                new MapUpgradeCard(MapUpgradeCard.MapOption.KEEP_RARITY_AND_REROLL),
                new MapUpgradeCard(MapUpgradeCard.MapOption.DOWNGRADE)
        );
        if (cards.size() == 3) {
            return new CardPickScreen(cards);
        }

        return null;
    }

    public static CardPickScreen getProphecyCardsScreen() {
        Player p = ClientOnly.getPlayer();

        List<ICard> cards = Load.player(p).prophecy.affixOffers.stream().map(x -> new ProphecyCurseCard(ExileDB.MapAffixes().get(x))).collect(Collectors.toList());

        if (cards.size() == 3) {
            return new CardPickScreen(cards);
        }
        return null;
    }

    public static void openMapUpgradeScreen() {
        net.minecraft.client.Minecraft.getInstance().setScreen(OpenGuiWrapper.openMapUpgradePicker());
    }

    public static void openProphecyCards() {
        Player p = ClientOnly.getPlayer();

        List<ICard> cards = Load.player(p).prophecy.affixOffers.stream().map(x -> new ProphecyCurseCard(ExileDB.MapAffixes().get(x))).collect(Collectors.toList());

        if (cards.size() == 3) {
            net.minecraft.client.Minecraft.getInstance().setScreen(new CardPickScreen(cards));
        } else {
            ExileLog.get().warn("Must be exactly 3 cards. Have: " + cards.size());
        }
    }

    public static void openWikiRunewords() {

        var sc = new NewWikiScreen();
        net.minecraft.client.Minecraft.getInstance().setScreen(sc);
        sc.setGroup(BestiaryGroup.RUNEWORD);

    }
}
