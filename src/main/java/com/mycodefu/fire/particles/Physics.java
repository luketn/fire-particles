package com.mycodefu.fire.particles;

public class Physics {

    public static double calculateBounceAngle(WallType wallType, double angleDegrees) {
        if (wallType == WallType.Vertical) {
            if (angleDegrees <= 180) {
                return 180 - angleDegrees;
            } else {
                return 360 - (angleDegrees - 180);
            }
        } else {
            return 360 - angleDegrees;
        }
    }

    public enum WallType {
        Horizontal,
        Vertical
    }
}
