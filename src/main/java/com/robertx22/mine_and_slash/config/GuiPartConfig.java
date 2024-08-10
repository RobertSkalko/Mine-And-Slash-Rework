package com.robertx22.mine_and_slash.config;

import com.robertx22.mine_and_slash.config.forge.ClientConfigs;
import com.robertx22.mine_and_slash.gui.overlays.BarGuiType;
import com.robertx22.mine_and_slash.saveclasses.PointData;

public class GuiPartConfig {

    public BarGuiType type = BarGuiType.HEALTH;
    PointData position_offset = new PointData(0, 0);
    public boolean enabled = true;
    public IconRenderer icon_renderer = IconRenderer.LEFT; // todo

    public PointData getPosition() {
        PointData pos = ClientConfigs.getConfig().GUI_POSITION.get()
                .getPos();

        return new PointData(pos.x + position_offset.x, pos.y + position_offset.y);
    }

    public GuiPartConfig(BarGuiType type, PointData position_offset) {
        this.type = type;
        this.position_offset = position_offset;
    }

    public GuiPartConfig iconOnRight() {
        this.icon_renderer = IconRenderer.RIGHT;
        return this;
    }

    public GuiPartConfig() {
    }

    public enum IconRenderer {
        NONE, LEFT, RIGHT
    }
}
