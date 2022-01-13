import java.util.ArrayList;
import java.util.Comparator;

public class FastCollinearPoints {
    private LineSegment[] collinearSegments = null;
    private Point[] aux;

    /**
     * finds all line segments containing 4 points
     * @param points
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points shouldn't be null");
        }

        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("point shouldn't be null");
            }
        }

        ArrayList<Point[]> segLists = new ArrayList<>();
        for (int i = 0; i + 4 <= points.length; i++) {
            Point[] others = new Point[points.length - (i + 1)];
            for (int j = 0; j < others.length; j++) {
                others[j] = points[i + 1 + j];
            }

            sort(points[i].slopeOrder(), others);
            Point min = points[i];
            Point max = points[i];
            int beg = 0;
            for (int k = 1; k < others.length; k++) {
                double slope1 = points[i].slopeTo(others[k - 1]);
                double slope2 = points[i].slopeTo(others[k]);
                if (slope1 == Double.NEGATIVE_INFINITY || slope2 == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException("shouldn't contains a repeated point");
                } else if (slope1 != slope2) {
                    if (k - beg >= 3) {
                        addSegPoints(segLists, slope1, min, max, others, beg, k - 1);
                    }

                    beg = k;
                    min = points[i];
                    max = points[i];
                } else {
                    if ((k + 1 == others.length) && (k - beg + 1 >= 3)) {
                        addSegPoints(segLists, slope1, min, max, others, beg, k);
                    }
                }
            }
        }

        if (!segLists.isEmpty()) {
            collinearSegments = new LineSegment[segLists.size()];
            int idx = 0;
            for (Point[] segPoints: segLists) {
                collinearSegments[idx] = new LineSegment(segPoints[0], segPoints[1]);
                idx++;
            }
        }
    }

    private void addSegPoints(ArrayList<Point[]> segLists, double slope, Point min, Point max, Point[] others, int beg, int end) {
        for (int i = beg; i <= end; i++) {
            if (others[i].compareTo(min) < 0) {
                min = others[i];
            }

            if (others[i].compareTo(max) > 0) {
                max = others[i];
            }
        }

        boolean isAdd = true;
        for (Point[] segPoints : segLists) {
            if ((slope == segPoints[0].slopeTo(segPoints[1])) && (slope == min.slopeTo(segPoints[1]))) {
                isAdd = false;
                break;
            }
        }

        if (isAdd) {
            Point[] segPoints = new Point[2];
            segPoints[0] = min;
            segPoints[1] = max;
            segLists.add(segPoints);
        }
    }

    private void sort(Comparator<Point> comparator, Point[] others) {
        aux = new Point[others.length];
        mergeSort(comparator, others, 0, others.length - 1);
        aux = null;
    }

    private void mergeSort(Comparator<Point> comparator, Point[] points, int lo, int hi) {
        if (hi <= lo) return;

        int mid = lo + (hi - lo)/2;
        mergeSort(comparator, points, lo, mid); // Sort left half.
        mergeSort(comparator, points, mid+1, hi); // Sort right half.
        merge(comparator, points, lo, mid, hi); // Merge results.
    }

    private boolean less(Comparator<Point> c, Point q, Point r) {
        return c.compare(q, r) < 0;
    }

    private void merge(Comparator<Point> c, Point[] points, int lo, int mid, int hi) { // Merge a[lo..mid] with a[mid+1..hi]
        int i = lo, j = mid + 1;

        for (int k = lo; k <= hi; k++) {
            aux[k] = points[k];
        }

        for (int k = lo; k <= hi; k++) {
            if (i > mid)                        points[k] = aux[j++];
            else if (j > hi)                    points[k] = aux[i++];
            else if (less(c, aux[j], aux[i]))   points[k] = aux[j++];
            else                                points[k] = aux[i++];
        }
    }

    /**
     * @return the number of line segments
     */
    public int numberOfSegments() {
        return (collinearSegments != null) ? collinearSegments.length : 0;
    }

    public LineSegment[] segments() {
        return (collinearSegments != null) ? collinearSegments : (new LineSegment[0]);
    }
}
