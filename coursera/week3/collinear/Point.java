import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
    private final int x; // x-coordinate
    private final int y; // y-coordinate

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be +0.0
     * if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (x == that.x && y == that.y) {
            return Double.NEGATIVE_INFINITY;
        } else if (x == that.x) {
            return Double.POSITIVE_INFINITY;
        } else if (y == that.y) {
            return 0.0;
        } else {
            return Double.valueOf(that.y - y) / (that.x - x);
        }
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *          point (x0 = x1 and y0 = y1);
     *          a negative integer if this point is less than the argument
     *          point; and a positive integer if this point is greater than the
     *          argument point
     */
    public int compareTo(Point that) {
        if (x == that.x && y == that.y) {
            return 0;
        } else if (y == that.y) {
            return x < that.x ? -1 : 1;
        } else {
            return y < that.y ? -1 : 1;
        }
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new SlopeComparator();
    }

    private class SlopeComparator implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            double slope1 = slopeTo(p1);
            double slope2 = slopeTo(p2);
            if (slope1 == slope2) {
                return 0;
            } else {
                return slope1 < slope2 ? -1 : 1;
            }
        }
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {
        Point[] points = {
            new Point(1, 3), new Point(-1, 5), new Point(2, 3)
        };

        points[0].drawTo(points[2]);
        System.out.println(points[0].toString());
    }
}