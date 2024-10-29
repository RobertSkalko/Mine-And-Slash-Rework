package com.robertx22.mine_and_slash.config.forge.overlay;

import com.robertx22.mine_and_slash.saveclasses.PointData;

public class OverlayAnchor {


    public enum AttachmentPosition {
        START() {
            @Override
            protected PointData getSizeOffset(OverlayType type) {
                return new PointData(0, 0);
            }
        }, END() {
            @Override
            protected PointData getSizeOffset(OverlayType type) {
                return type.getSize();
            }
        }, MIDDLE {
            @Override
            protected PointData getSizeOffset(OverlayType type) {
                return new PointData(type.getSize().x / 2, type.getSize().y / 2);
            }
        };

        protected abstract PointData getSizeOffset(OverlayType type);


        // todo this is looping in a bad way..

        public PointData getAnchorPos(OverlayType type) {

            var offset = getSizeOffset(type);

            //  var config = ClientConfigs.getConfig().getOverlayConfig(type);

            //   var pos = config.getPos();

            return offset;
            //  return new PointData(pos.x + offset.x, pos.y + offset.y);
        }

    }


}
