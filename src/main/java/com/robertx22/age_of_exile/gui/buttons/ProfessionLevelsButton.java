package com.robertx22.age_of_exile.gui.buttons;

import com.robertx22.age_of_exile.database.data.profession.Profession;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.utils.TextUTIL;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class ProfessionLevelsButton extends ImageButton {

    public static int SX = 16;
    public static int SY = 16;

    Minecraft mc = Minecraft.getInstance();

    public ProfessionLevelsButton(int xPos, int yPos) {
        super(xPos, yPos, SX, SY, 0, 0, SY, new ResourceLocation("empty"), (button) -> {
        });

    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        setModTooltip();
        super.render(gui, mouseX, mouseY, delta);
    }

    @Override
    public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        gui.blit(SlashRef.guiId("profession/button"), getX(), getY(), SX, SX, SX, SX, SX, SX);

    }

    public void setModTooltip() {

        List<Component> list = new ArrayList<>();

        for (Profession prof : ExileDB.Professions().getList()) {
            var lvl = Load.player(ClientOnly.getPlayer()).professions.getLevel(prof.GUID());
            int exp = Load.player(ClientOnly.getPlayer()).professions.getExp(prof.GUID());
            int maxexp = Load.player(ClientOnly.getPlayer()).professions.getMaxExp(prof.GUID());

            list.add(prof.locName().append(":").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD));

            list.add(TooltipUtils.level(lvl).append(" Exp: " + exp + "/" + maxexp).withStyle(ChatFormatting.GREEN));

        }
        list.add(Component.empty());

        list.add(Component.literal("Rested Combat Exp: " + Load.player(mc.player).rested_xp.bonusCombatExp).withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
        list.add(Component.literal("Rested Profession Exp: " + Load.player(mc.player).rested_xp.bonusProfExp).withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD));

        this.setTooltip(Tooltip.create(TextUTIL.mergeList(list)));

    }


}