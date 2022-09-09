package elements;

import lombok.Data;

import java.util.Comparator;

@Data
public class Point implements Comparable<Point> {
    private int x;     // x-coordinate of this point
    private int y;     // y-coordinate of this point

    /*
    * Empty constructor for Jackson
    * */
    public Point(){}

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the slope between two points.
     *
     * @param point2 the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point point2) {
        double slope;
        if (x == point2.x) {
            if (y == point2.y) slope = Double.NEGATIVE_INFINITY;
            else slope = Double.POSITIVE_INFINITY;
        } else if (y == point2.y) slope = +0.0;
        else slope = (double) (point2.y - y) / (double) (point2.x - x);
        return slope;
    }

    /**
     * Compares two points by y-coordinate.
     *
     * @param that the other point
     * @return 0 if equal, a negative integer if this point is less than the argument point; and a positive integer
     * if this point is greater than the argument point
     */
    public int compareTo(Point that) {
        if (y == that.y) return Integer.compare(x, that.x);
        else if (y < that.y) return -1;
        else return 1;
    }

    /**
     * Compares two points by the slope they make with this point. The slope is defined as in the
     * slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }

    private class SlopeOrder implements Comparator<Point> {
        @Override
        public int compare(Point p1, Point p2) {
            return Double.compare(slopeTo(p1), slopeTo(p2));
        }
    }

    public String toString() {

        return "(" + x + ", " + y + ")";
    }
}
