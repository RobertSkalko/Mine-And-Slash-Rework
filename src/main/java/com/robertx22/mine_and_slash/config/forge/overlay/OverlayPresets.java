package com.robertx22.mine_and_slash.config.forge.overlay;

import com.robertx22.mine_and_slash.saveclasses.PointData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OverlayPresets {

    public static HashMap<OverlayType, PointData> SIZES = new HashMap<>();

    public enum PresetEnum {
        NONE() {
            @Override
            public OverlayType getType() {
                return null;
            }
        },
        SPELL_CAST_BAR() {
            @Override
            public OverlayType getType() {
                return OverlayType.SPELL_CAST_BAR;
            }
        },
        HOTBAR_HORIZONTAL {
            @Override
            public OverlayType getType() {
                return OverlayType.SPELL_HOTBAR_HORIZONTAL;
            }
        },
        HOTBAR_VERTICAL {
            @Override
            public OverlayType getType() {
                return OverlayType.SPELL_HOTBAR_VERTICAL;
            }
        },
        EFFECTS_BOTTOM_RIGHT {
            @Override
            public OverlayType getType() {
                return OverlayType.EFFECTS;
            }
        };

        public abstract OverlayType getType();
    }

    public static List<OverlayConfigBuilder> ALL_PRESETS = new ArrayList();

    public static OverlayConfigBuilder SPELL_CAST_BAR = OverlayConfigBuilder.of(PresetEnum.SPELL_CAST_BAR, "Spell Cast Bar", OverlayType.SCREEN, 172, 20)
            .anchor(OverlayAnchor.AttachmentPosition.MIDDLE, OverlayAnchor.AttachmentPosition.END)
            .offset(0, -200);

    public static OverlayConfigBuilder HOTBAR_HORIZONTAL = OverlayConfigBuilder.of(PresetEnum.HOTBAR_HORIZONTAL, "Horizontal Spell Hotbar", OverlayType.SCREEN, 162, 22)
            .anchor(OverlayAnchor.AttachmentPosition.MIDDLE, OverlayAnchor.AttachmentPosition.END)
            .offset(-162 / 2, -22 / 2 - 54);

    public static OverlayConfigBuilder HOTBAR_VERTICAL = OverlayConfigBuilder.of(PresetEnum.HOTBAR_VERTICAL, "Vertical Spell Hotbar", OverlayType.SCREEN, 22, 162)
            .anchor(OverlayAnchor.AttachmentPosition.START, OverlayAnchor.AttachmentPosition.MIDDLE)
            .offset(0, -162 / 2);

    public static OverlayConfigBuilder EFFECTS_BOTTOM_RIGHT = OverlayConfigBuilder.of(PresetEnum.EFFECTS_BOTTOM_RIGHT, "Status Effects Bottom Right", OverlayType.SCREEN, 20, 100)
            .anchor(OverlayAnchor.AttachmentPosition.MIDDLE, OverlayAnchor.AttachmentPosition.END)
            .offset(90, -30);

    public static void init() {

    }

}
