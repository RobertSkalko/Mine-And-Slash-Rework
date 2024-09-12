package com.robertx22.mine_and_slash.config.forge.overlay;

import com.robertx22.mine_and_slash.saveclasses.PointData;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Locale;

public class OverlayConfig {

    public ForgeConfigSpec.EnumValue<OverlayAnchor.AttachmentPosition> ANCHOR_X;
    public ForgeConfigSpec.EnumValue<OverlayAnchor.AttachmentPosition> ANCHOR_Y;

    public ForgeConfigSpec.EnumValue<OverlayType> ANCHOR_TARGET;

    public ForgeConfigSpec.IntValue X_OFFSET;
    public ForgeConfigSpec.IntValue Y_OFFSET;


    public OverlayConfig(ForgeConfigSpec.Builder b, OverlayConfigBuilder data) {
        b.comment(data.presetName + " Overlay Preset").push(data.presetName.toLowerCase(Locale.ROOT).replace(" ", "_"));

        ANCHOR_TARGET = b.defineEnum("ANCHOR_TO", data.anchor);

        ANCHOR_X = b.defineEnum("ANCHOR_X", data.xAnchor);
        ANCHOR_Y = b.defineEnum("ANCHOR_Y", data.yAnchor);

        X_OFFSET = b.defineInRange("X_OFFSET", data.xoff, -50000, 50000);
        Y_OFFSET = b.defineInRange("Y_OFFSET", data.yoff, -50000, 50000);

        b.pop();
    }

    public PointData getPos() {
        var target = ANCHOR_TARGET.get();


        int x = ANCHOR_X.get().getPosOffset(target).x;
        int y = ANCHOR_Y.get().getPosOffset(target).y;

        x += X_OFFSET.get();
        y += Y_OFFSET.get();

        PointData pos = new PointData(x, y);

        return pos;
    }

}
