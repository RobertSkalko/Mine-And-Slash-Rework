package com.robertx22.age_of_exile.gui.buttons;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.stats.IUsableStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.gui.screens.character_screen.MainHubScreen;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.NumberUtils;
import com.robertx22.library_of_exile.utils.TextUTIL;
import com.robertx22.library_of_exile.wrappers.ExileText;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class CharacterStatsButtons extends ImageButton {

    public static int BUTTON_SIZE_X = 16;
    public static int BUTTON_SIZE_Y = 16;

    MainHubScreen.StatType type;

    public CharacterStatsButtons(MainHubScreen.StatType type, int xPos, int yPos) {
        super(xPos, yPos, BUTTON_SIZE_X, BUTTON_SIZE_Y, 0, 0, BUTTON_SIZE_Y, type.getIcon(), (button) -> {

        });
        this.type = type;
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        setModTooltip();
        super.render(gui, mouseX, mouseY, delta);
    }

    @Override
    public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        gui.blit(type.getIcon(), getX(), getY(), BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X);

    }

    public void setModTooltip() {

        List<Component> tooltip = new ArrayList<>();

        Minecraft mc = Minecraft.getInstance();

        for (List<Stat> list : MainHubScreen.STAT_MAP.get(type)) {
            if (!tooltip.isEmpty()) {
                tooltip.add(ExileText.emptyLine().get());
            }

            for (Stat stat : list) {
                tooltip.add(stat.locName().append(": " + getStatString(stat, Load.Unit(mc.player))));
            }
        }


        this.setTooltip(Tooltip.create(TextUTIL.mergeList(tooltip)));


    }


    public static String getStatString(Stat stat, EntityData unitdata) {
        StatData data = unitdata.getUnit().getCalculatedStat(stat);

        String v1 = NumberUtils.formatForTooltip(data.getValue());

        String str = "";

        str = v1;

        if (stat.IsPercent()) {
            str += '%';
        }

        if (stat instanceof IUsableStat) {
            IUsableStat usable = (IUsableStat) stat;

            String value = NumberUtils.format(usable.getUsableValue((int) data.getValue(), unitdata.getLevel()) * 100);

            str += " (" + value + "%)";

        }
        return str;

    }

}
