package com.robertx22.age_of_exile.gui.inv_gui.actions.auto_salvage;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.gui.inv_gui.actions.GuiAction;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Gui;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ToggleAutoSalvageRarity extends GuiAction {

    public SalvageType type;
    public GearRarity rarity;

    public ToggleAutoSalvageRarity(SalvageType type, GearRarity rarity) {
        this.type = type;
        this.rarity = rarity;
    }

    @Override
    public List<Component> getTooltip(Player p) {
        var list = new ArrayList<Component>();

        var text = Component.literal("");

        if (Load.player(p).config.salvage.checkRaritySalvageConfig(type, rarity.GUID())) {
            text = Gui.SALVAGE_TIP_ON.locName(rarity.locName(), type.word.locName()).withStyle(ChatFormatting.GREEN);
        } else {
            text = Gui.SALVAGE_TIP_OFF.locName(rarity.locName(), type.word.locName()).withStyle(ChatFormatting.RED);
        }

        list.add(text);

        return list;
    }


    @Override
    public void doAction(Player p, Object data) {
        Load.player(p).config.salvage.toggleRaritySalvageConfig(type, rarity.GUID());
        Load.player(p).playerDataSync.setDirty();
    }

    @Override
    public void clientAction(Player p, Object obj) {

    }

    public ResourceLocation getIcon() {
        return SlashRef.id("textures/gui/inv_gui/icons/" + type.name().toLowerCase(Locale.ROOT) + ".png");
    }

    @Override
    public ResourceLocation getBackGroundIcon() {
        return rarity.getGlintTextureFull();
    }

    @Override
    public void saveExtraData(FriendlyByteBuf buf) {

    }

    @Override
    public Object loadExtraData(FriendlyByteBuf buf) {
        return null;
    }

    @Override
    public String GUID() {
        return "auto_sal_" + type.name().toLowerCase(Locale.ROOT) + rarity.GUID();
    }

    public enum SalvageType {
        GEAR(Words.Gear),
        JEWEL(Words.Jewel),
        SPELL(Words.SkillGem);


        public Words word;

        SalvageType(Words word) {
            this.word = word;
        }
    }


}
