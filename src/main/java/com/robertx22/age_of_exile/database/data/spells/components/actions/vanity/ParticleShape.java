package com.robertx22.age_of_exile.database.data.spells.components.actions.vanity;

import com.robertx22.library_of_exile.utils.geometry.MyPosition;

public enum ParticleShape {
    CIRCLE {
        @Override
        public MyPosition getPosition(MyPosition middle, float radius, float angleMulti) {
            double x = middle.x;
            double y = middle.y;
            double z = middle.z;
            double radModX = Math.random();
            double radModY = Math.random();
            double radModZ = Math.random();
            double u = Math.random();
            double v = Math.random();
            double theta = 6.283185307179586 * u;
            double phi = Math.acos(2.0 * v - 1.0);
            double xpos = x + radModX * (double) radius * Math.sin(phi) * Math.cos(theta);
            double ypos = y + radModY * (double) radius * Math.sin(phi) * Math.sin(theta);
            double zpos = z + radModZ * (double) radius * Math.cos(phi);
            return new MyPosition(xpos, ypos, zpos);
        }
    },
    CIRCLE_EDGE {
        @Override
        public MyPosition getPosition(MyPosition middle, float radius, float angleMulti) {
            double x = middle.x;
            double y = middle.y;
            double z = middle.z;
            double u = Math.random();
            double v = Math.random();
            double theta = 6.283185307179586 * u;
            double phi = Math.acos(2.0 * v - 1.0);
            double xpos = x + (double) radius * Math.sin(phi) * Math.cos(theta);
            double ypos = y + (double) radius * Math.sin(phi) * Math.sin(theta);
            double zpos = z + (double) radius * Math.cos(phi);
            return new MyPosition(xpos, ypos, zpos);
        }
    },
    CIRCLE_2D {
        @Override
        public MyPosition getPosition(MyPosition middle, float radius, float angleMulti) {
            double x = middle.x;
            double y = middle.y;
            double z = middle.z;
            double u = Math.random();
            double v = Math.random();
            double theta = 6.283185307179586 * u;
            double phi = Math.acos(2.0 * v - 1.0);
            double xpos = x + (double) radius * Math.sin(phi) * Math.cos(theta);
            double zpos = z + (double) radius * Math.cos(phi);
            return new MyPosition(xpos, y, zpos);
        }

    },
    CIRCLE_2D_EDGE {
        @Override
        public MyPosition getPosition(MyPosition middle, float radius, float angleMulti) {
            double x = middle.x;
            double y = middle.y;
            double z = middle.z;
            double angle = (double) angleMulti * Math.PI * 2.0;
            double xpos = x + Math.cos(angle) * (double) radius;
            double zpos = z + Math.sin(angle) * (double) radius;
            return new MyPosition(xpos, y, zpos);
        }
    };


    public abstract MyPosition getPosition(MyPosition middle, float radius, float f);
}
