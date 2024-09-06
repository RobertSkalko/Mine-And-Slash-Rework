package com.robertx22.mine_and_slash.gui.inv_gui.actions;

import com.robertx22.mine_and_slash.capability.player.data.PlayerConfigData;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GuiConfigToggle extends GuiAction {

    PlayerConfigData.Config config;

    public GuiConfigToggle(PlayerConfigData.Config config) {
        this.config = config;
    }

    @Override
    public void saveExtraData(FriendlyByteBuf buf) {
        buf.writeUtf(config.id);
    }

    @Override
    public ResourceLocation getIcon() {
        var p = ClientOnly.getPlayer();
        if (p != null) {
            if (Load.player(p).config.isConfigEnabled(config)) {
                return SlashRef.id("textures/gui/inv_gui/icons/config_on.png");
            }
        }
        return SlashRef.id("textures/gui/inv_gui/icons/config.png");

    }

    @Override
    public Object loadExtraData(FriendlyByteBuf buf) {
        return null;
    }

    @Override
    public List<Component> getTooltip(Player p) {
        List<Component> t = new ArrayList<>();

        t.add(config.title.locName().withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.BOLD));

        t.addAll(TooltipUtils.splitLongText(config.word.locName()));

        var text = Component.literal("");
        if (Load.player(p).config.isConfigEnabled(config)) {
            text = Words.ENABLED.locName().withStyle(ChatFormatting.GREEN);
        } else {
            text = Words.DISABLED.locName().withStyle(ChatFormatting.RED);
        }
        t.add(text);


        return t;
    }

    @Override
    public void doAction(Player p, Object obj) {
        boolean bo = !Load.player(p).config.isConfigEnabled(config);
        Load.player(p).config.configs.put(config.id, bo);
        Load.player(p).playerDataSync.setDirty();
    }

    @Override
    public void clientAction(Player p, Object obj) {

        // ClientOnly.closeScreen();
    }

    @Override
    public String GUID() {
        return config.id;
    }
}
