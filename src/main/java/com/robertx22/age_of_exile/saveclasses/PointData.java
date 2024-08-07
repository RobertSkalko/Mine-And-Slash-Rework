package com.robertx22.age_of_exile.saveclasses;

public class PointData {
    public int x;
    public int y;

    protected PointData() {

    }

    public PointData(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public String toString() {
        return x + "_" + y;
    }


    public PointData up() {
        return new PointData(x, y + 1);
    }

    public PointData down() {
        return new PointData(x, y - 1);
    }

    public PointData left() {
        return new PointData(x - 1, y);
    }

    public PointData right() {
        return new PointData(x + 1, y);
    }

    @Override
    public int hashCode() {
        long bitsX = Double.doubleToLongBits(x);
        long bitsY = Double.doubleToLongBits(y);

        long h = bitsX ^ (bitsY * 31);
        h = (h >>> 32) ^ h;
        h = h * 0x27d4eb2d;
        h = h ^ (h >>> 33);
        h = h * 0x165667b1;
        h = h ^ (h >>> 31);

        return (int) (h ^ (h >>> 32));
    }



    @Override
    public boolean equals(Object obj) { // otherwise hashmaps dont work
        if (obj instanceof PointData) {
            PointData pt = (PointData) obj;
            return (x == pt.x) && (y == pt.y);
        }
        return false;
    }


}
