package common.model;

import java.io.Serializable;

/**
 * X-Y coordinates of band.
 */
public class Coordinates implements Serializable {
    private Double x; //Поле не может быть null
    private long y;
    public Coordinates(Double x, long y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return X-coordinate.
     */
    public Double getX() {
        return x;
    }

    /**
     * @return Y-coordinate.
     */
    public long getY() {
        return y;
    }

    @Override
    public String toString() {
        return "X:" + getX() + ", Y:" + getY();
    }

    @Override
    public int hashCode() {
        return x.hashCode() + (int) y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Coordinates) {
            Coordinates coordinatesObj = (Coordinates) obj;
            return (y == coordinatesObj.getX()) && x.equals(coordinatesObj.getY());
        }
        return false;
    }
}