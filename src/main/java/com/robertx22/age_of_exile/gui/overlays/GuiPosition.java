package com.robertx22.age_of_exile.gui.overlays;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.config.GuiPartConfig;
import com.robertx22.age_of_exile.saveclasses.PointData;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public enum GuiPosition {

    BOTTOM_CENTER {
        @Override
        public List<GuiPartConfig> getGuiConfig(EntityData data, Player p) {
            
            List<GuiPartConfig> middle = new ArrayList<>();
            middle.add(new GuiPartConfig(BarGuiType.HEALTH, new PointData(-198, -22)));
            middle.add(new GuiPartConfig(BarGuiType.EXP, new PointData(-198, -11)));

            if (BarGuiType.MAGIC_SHIELD.shouldRender(data, p)) {
                middle.add(new GuiPartConfig(BarGuiType.MAGIC_SHIELD, new PointData(-198, -33)));
            }
            middle.add(new GuiPartConfig(BarGuiType.MANA, new PointData(90, -22)).iconOnRight());
            middle.add(new GuiPartConfig(BarGuiType.ENERGY, new PointData(90, -11)).iconOnRight());

            return middle;
        }

        @Override
        public PointData getPos() {
            return new PointData(mc().getWindow().getGuiScaledWidth() / 2, mc().getWindow().getGuiScaledHeight());
        }
    },
    OVER_VANILLA {
        @Override
        public List<GuiPartConfig> getGuiConfig(EntityData data, Player p) {
            List<GuiPartConfig> middle = new ArrayList<>();

            middle.add(new GuiPartConfig(BarGuiType.HEALTH, new PointData(-91, -33)));
            // middle.add(new GuiPartConfig(BarGuiType.EXP, new PointData(-115, -41)));

            if (BarGuiType.MAGIC_SHIELD.shouldRender(data, p)) {
                middle.add(new GuiPartConfig(BarGuiType.MAGIC_SHIELD, new PointData(-91, -43)));
            }
            middle.add(new GuiPartConfig(BarGuiType.MANA, new PointData(0, -33)).iconOnRight());
            middle.add(new GuiPartConfig(BarGuiType.ENERGY, new PointData(0, -43)).iconOnRight());

            for (GuiPartConfig aa : middle) {
                aa.icon_renderer = GuiPartConfig.IconRenderer.NONE;
            }

            return middle;
        }

        @Override
        public PointData getPos() {
            return new PointData(mc().getWindow().getGuiScaledWidth() / 2, mc().getWindow().getGuiScaledHeight());
        }
    },
    TOP_LEFT {
        @Override
        public List<GuiPartConfig> getGuiConfig(EntityData data, Player p) {
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

            return topleft;
        }

        @Override
        public PointData getPos() {
            return new PointData(0, 0);
        }
    };


    public abstract List<GuiPartConfig> getGuiConfig(EntityData data, Player p);

    public abstract PointData getPos();

    static Minecraft mc() {
        return Minecraft.getInstance();
    }
}
