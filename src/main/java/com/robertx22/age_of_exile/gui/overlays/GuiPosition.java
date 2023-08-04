package com.robertx22.age_of_exile.gui.overlays;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.config.GuiPartConfig;
import com.robertx22.age_of_exile.saveclasses.PointData;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public enum GuiPosition {

    BOTTOM_CENTER {
        @Override
        public List<GuiPartConfig> getGuiConfig(EntityData data, PlayerEntity p) {
            List<GuiPartConfig> middle = new ArrayList<>();
            middle.add(new GuiPartConfig(BarGuiType.HEALTH, new PointData(-198, -22)));
            middle.add(new GuiPartConfig(BarGuiType.EXP, new PointData(-198, -11)));

            if (BarGuiType.MAGIC_SHIELD.shouldRender(data, p)) {
                middle.add(new GuiPartConfig(BarGuiType.MAGIC_SHIELD, new PointData(-198, -33)));
            }
            middle.add(new GuiPartConfig(BarGuiType.SHIELD, new PointData(90, -33)));
            middle.add(new GuiPartConfig(BarGuiType.MANA, new PointData(90, -22)));
            middle.add(new GuiPartConfig(BarGuiType.ENERGY, new PointData(90, -11)));

            return middle;
        }

        @Override
        public PointData getPos() {
            return new PointData(
                    mc().getWindow()
                            .getGuiScaledWidth() / 2,
                    mc().getWindow()
                            .getGuiScaledHeight());
        }
    },
    TOP_LEFT {
        @Override
        public List<GuiPartConfig> getGuiConfig(EntityData data, PlayerEntity p) {
            List<GuiPartConfig> topleft = new ArrayList<>();
            int x = 12;
            int y = 5;

            int yHeight = 11;

            topleft.add(new GuiPartConfig(BarGuiType.HEALTH, new PointData(x, y)));
            if (BarGuiType.MAGIC_SHIELD.shouldRender(data, p)) {
                topleft.add(new GuiPartConfig(BarGuiType.MAGIC_SHIELD, new PointData(x, y += yHeight)));
            }
            topleft.add(new GuiPartConfig(BarGuiType.MANA, new PointData(x, y += yHeight)));
            topleft.add(new GuiPartConfig(BarGuiType.ENERGY, new PointData(x, y += yHeight)));
            topleft.add(new GuiPartConfig(BarGuiType.EXP, new PointData(x, y += yHeight)));
            if (BarGuiType.SHIELD.shouldRender(data, p)) {
                topleft.add(new GuiPartConfig(BarGuiType.SHIELD, new PointData(x, y += yHeight)));
            }
            return topleft;
        }

        @Override
        public PointData getPos() {
            return new PointData(0, 0);
        }
    };


    public abstract List<GuiPartConfig> getGuiConfig(EntityData data, PlayerEntity p);

    public abstract PointData getPos();

    static Minecraft mc() {
        return Minecraft.getInstance();
    }
}
