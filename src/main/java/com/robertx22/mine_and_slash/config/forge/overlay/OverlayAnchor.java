package com.robertx22.mine_and_slash.config.forge.overlay;

import com.robertx22.mine_and_slash.config.forge.ClientConfigs;
import com.robertx22.mine_and_slash.saveclasses.PointData;

public class OverlayAnchor {


    public enum AttachmentPosition {
        START() {
            @Override
            protected PointData getPosOffset(OverlayType type) {
                return new PointData(0, 0);
            }
        }, END() {
            @Override
            protected PointData getPosOffset(OverlayType type) {
                return type.getSize();
            }
        }, MIDDLE {
            @Override
            protected PointData getPosOffset(OverlayType type) {
                return new PointData(type.getSize().x / 2, type.getSize().y / 2);
            }
        };

        protected abstract PointData getPosOffset(OverlayType type);


        public PointData getPos(OverlayType type) {

            var offset = getPosOffset(type);

            var config = ClientConfigs.getConfig().getOverlayConfig(type);

            var pos = config.getPos();

            return new PointData(pos.x + offset.x, pos.y + offset.y);
        }

    }


}
