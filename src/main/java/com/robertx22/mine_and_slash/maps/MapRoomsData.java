package com.robertx22.mine_and_slash.maps;

public class MapRoomsData {

    public HasDoneData rooms = new HasDoneData();
    public HasDoneData chests = new HasDoneData();
    public HasDoneData mobs = new HasDoneData();

    public boolean isDoneGenerating() {
        if (rooms.total <= 0) {
            return false;
        }
        return rooms.done >= rooms.total;
    }

    public int getMapCompletePercent() {
        if (!isDoneGenerating()) {
            return 0;
        }
        int mobs = this.mobs.getPercentDone();
        int chests = this.chests.getPercentDone();
        return (mobs + chests) / 2;
    }
}
