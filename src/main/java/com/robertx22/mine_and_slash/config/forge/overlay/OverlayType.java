package com.robertx22.mine_and_slash.config.forge.overlay;


import com.robertx22.mine_and_slash.saveclasses.PointData;
import net.minecraft.client.Minecraft;

// todo you should be able to choose from a bunch of presets
public enum OverlayType {
    SCREEN() {
        @Override
        public PointData getSize() {
            return new PointData(Minecraft.getInstance().getWindow().getGuiScaledWidth(), Minecraft.getInstance().getWindow().getGuiScaledHeight());
        }

        @Override
        public OverlayPresets.PresetEnum getDefaultConfig() {
            return null;
        }
    },
    SPELL_HOTBAR_HORIZONTAL {
        @Override
        public OverlayPresets.PresetEnum getDefaultConfig() {
            return OverlayPresets.PresetEnum.HOTBAR_HORIZONTAL;
        }
    },
    SPELL_HOTBAR_VERTICAL {
        @Override
        public OverlayPresets.PresetEnum getDefaultConfig() {
            return OverlayPresets.PresetEnum.HOTBAR_VERTICAL;
        }
    },
    EFFECTS_VERTICAL {
        @Override
        public OverlayPresets.PresetEnum getDefaultConfig() {
            return OverlayPresets.PresetEnum.EFFECTS_UNDER_VERTICAL_HOTBAR;
        }
    },
    EFFECTS_HORIZONTAL {
        @Override
        public OverlayPresets.PresetEnum getDefaultConfig() {
            return OverlayPresets.PresetEnum.EFFECTS_BOTTOM_RIGHT;
        }
    },
    SPELL_CAST_BAR {
        @Override
        public OverlayPresets.PresetEnum getDefaultConfig() {
            return OverlayPresets.PresetEnum.SPELL_CAST_BAR;
        }
    };

    public PointData getSize() {
        return OverlayPresets.SIZES.getOrDefault(this, new PointData(0, 0));
    }

    public abstract OverlayPresets.PresetEnum getDefaultConfig();
}
