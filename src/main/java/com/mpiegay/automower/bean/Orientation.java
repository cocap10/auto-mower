package com.mpiegay.automower.bean;

/**
 * Enum of possible mower orientation
 */
public enum Orientation {
    N,
    E,
    S,
    W;

    /**
     * Gives the mower orientation after applying a left action
     * @param currentOrientation the current orientation of the mower before the rotation
     * @return the orientation after a left rotation
     */
    public static Orientation toTheLeft(Orientation currentOrientation) {
        if (currentOrientation == N) {
            return W;
        }
        return Orientation.values()[currentOrientation.ordinal() - 1];
    }

    /**
     * Gives the mower orientation after applying a right action
     * @param currentOrientation the current orientation of the mower before the rotation
     * @return the orientation after a right rotation
     */
    public static Orientation toTheRight(Orientation currentOrientation) {
        return Orientation.values()[(currentOrientation.ordinal() + 1) % 4];
    }
}
