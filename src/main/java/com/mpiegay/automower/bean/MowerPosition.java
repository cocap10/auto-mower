package com.mpiegay.automower.bean;

import java.util.Objects;

/**
 * Represents the mower position and orientation on the 2D lawn
 */
public class MowerPosition {
    /**
     * The horizontal (abscissa) axis coordinate of the mower on the lawn grid
     */
    private int x;
    /**
     * The vertical (ordinate) axis coordinate of the mower on the lawn grid
     */
    private int y;
    /**
     * The orientation of the mower
     */
    private Orientation orientation;

    public MowerPosition(int x, int y, Orientation orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    public MowerPosition(MowerPosition beforeStepPosition) {
        this(beforeStepPosition.getX(), beforeStepPosition.getY(), beforeStepPosition.getOrientation());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public String toString() {
        return "MowerPosition{" +
                "x=" + x +
                ", y=" + y +
                ", orientation='" + orientation + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MowerPosition that = (MowerPosition) o;
        return x == that.x &&
                y == that.y &&
                orientation == that.orientation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, orientation);
    }
}
