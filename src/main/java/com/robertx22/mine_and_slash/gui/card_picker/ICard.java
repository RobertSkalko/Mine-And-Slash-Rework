package com.robertx22.mine_and_slash.gui.card_picker;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public interface ICard {

    ResourceLocation getIcon();

    void onClick(Player p);

    List<MutableComponent> getTooltip(Player p);

    List<MutableComponent> getScreenText(Player p);

    MutableComponent getName();
}
