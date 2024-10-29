package com.robertx22.mine_and_slash.config.forge.overlay;

import com.robertx22.mine_and_slash.saveclasses.PointData;

public class OverlayConfigBuilder {

    public OverlayPresets.PresetEnum preset;
    public String presetName;

    public OverlayType anchor;

    public int xoff = 0;
    public int yoff = 0;


    public OverlayAnchor.AttachmentPosition xAnchor = OverlayAnchor.AttachmentPosition.START;
    public OverlayAnchor.AttachmentPosition yAnchor = OverlayAnchor.AttachmentPosition.START;

    private OverlayConfigBuilder(OverlayPresets.PresetEnum preset, String presetName, OverlayType anchor) {
        this.presetName = presetName;
        this.preset = preset;
        this.anchor = anchor;


    }

    public static OverlayConfigBuilder of(OverlayPresets.PresetEnum preset, String presetName, OverlayType type, int x, int y) {
        var b = new OverlayConfigBuilder(preset, presetName, type);
        OverlayPresets.ALL_PRESETS.add(b);
        OverlayPresets.SIZES.put(preset.getType(), new PointData(x, y));
        return b;
    }

    public OverlayConfigBuilder offset(int x, int y) {
        this.xoff = x;
        this.yoff = y;
        return this;
    }

    public OverlayConfigBuilder anchor(OverlayAnchor.AttachmentPosition x, OverlayAnchor.AttachmentPosition y) {
        this.xAnchor = x;
        this.yAnchor = y;
        return this;
    }


}
