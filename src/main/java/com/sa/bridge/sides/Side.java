package com.sa.bridge.sides;

/**
 * Created by sergeiakimov
 */
public enum Side {
    LEFT, RIGHT, NONE;

    public static Side randomSide() {
        if (Math.random() > 0.5) {
            return Side.LEFT;
        } else {
            return Side.RIGHT;
        }
    }
}
