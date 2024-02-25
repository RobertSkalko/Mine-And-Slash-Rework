package com.robertx22.age_of_exile.gui.inv_gui.actions;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GuiConfigToggle extends GuiAction {

    String config;

    public GuiConfigToggle(String config) {
        this.config = config;
    }

    @Override
    public void saveExtraData(FriendlyByteBuf buf) {
        buf.writeUtf(config);
    }

    @Override
    public ResourceLocation getIcon() {
        return SlashRef.id("textures/gui/inv_gui/icons/config.png");
    }

    @Override
    public Object loadExtraData(FriendlyByteBuf buf) {
        return null;
    }

    @Override
    public List<Component> getTooltip(Player p) {
        List<Component> t = new ArrayList<>();
        t.add(Component.literal(config));

        var text = Component.literal("");
        if (Load.player(p).config.isConfigEnabled(config)) {
            text = Component.literal("On").withStyle(ChatFormatting.GREEN);
        } else {
            text = Component.literal("Off").withStyle(ChatFormatting.RED);
        }
        t.add(text);


        return t;
    }

    @Override
    public void doAction(Player p, Object obj) {

        boolean bo = !Load.player(p).config.isConfigEnabled(config);

        Load.player(p).config.configs.put(config, bo);
    }

    @Override
    public void clientAction(Player p, Object obj) {

        
    }

    @Override
    public String GUID() {
        return config;
    }
}
