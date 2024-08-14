package com.robertx22.mine_and_slash.vanilla_mc.packets.proxies;

import com.robertx22.library_of_exile.main.ExileLog;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.gui.card_picker.CardPickScreen;
import com.robertx22.mine_and_slash.gui.card_picker.ICard;
import com.robertx22.mine_and_slash.gui.card_picker.ProphecyCurseCard;
import com.robertx22.mine_and_slash.gui.screens.character_screen.MainHubScreen;
import com.robertx22.mine_and_slash.gui.wiki.BestiaryGroup;
import com.robertx22.mine_and_slash.gui.wiki.BestiaryScreen;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.stream.Collectors;

public class OpenGuiWrapper {

    public static void openMainHub() {

        net.minecraft.client.Minecraft.getInstance().setScreen(new MainHubScreen());

    }

    public static CardPickScreen getProphecyCardsScreen() {
        Player p = ClientOnly.getPlayer();

        List<ICard> cards = Load.player(p).prophecy.affixOffers.stream().map(x -> new ProphecyCurseCard(ExileDB.MapAffixes().get(x))).collect(Collectors.toList());

        if (cards.size() == 3) {
            return new CardPickScreen(cards);
        }
        return null;
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

        var sc = new BestiaryScreen();
        net.minecraft.client.Minecraft.getInstance().setScreen(sc);
        sc.setGroup(BestiaryGroup.RUNEWORD);

    }
}
