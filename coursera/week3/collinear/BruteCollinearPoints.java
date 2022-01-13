import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] collinearSegments = null;

    /**
     * finds all line segments containing 4 points
      * @param points
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points shouldn't be null");
        }

        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("point shouldn't be null");
            }
        }

        ArrayList<LineSegment> segLists = new ArrayList<>();
        for (int p = 0; p < points.length; p++) {
            for (int q = p + 1; q < points.length; q++) {
                for (int r = q + 1; r < points.length; r++) {
                    for (int s = r + 1; s < points.length; s++) {
                        double pqSlope = points[p].slopeTo(points[q]);
                        double prSlope = points[p].slopeTo(points[r]);
                        double psSlope = points[p].slopeTo(points[s]);
                        if (pqSlope == Double.NEGATIVE_INFINITY ||
                            prSlope == Double.NEGATIVE_INFINITY ||
                            psSlope == Double.NEGATIVE_INFINITY) {
                            throw new IllegalArgumentException("shouldn't contains a repeated point");
                        } else if (pqSlope == prSlope && pqSlope == psSlope) {
                            Point[] segPoints = new Point[4];
                            segPoints[0] = points[p];
                            segPoints[1] = points[q];
                            segPoints[2] = points[r];
                            segPoints[3] = points[s];
                            Point min = null;
                            Point max = null;
                            for (Point point : segPoints) {
                                if (min == null && max == null) {
                                    min = point;
                                    max = point;
                                } else {
                                    if (point.compareTo(min) < 0) {
                                        min = point;
                                    }

                                    if (point.compareTo(max) > 0) {
                                        max = point;
                                    }
                                }
                            }

                            segLists.add(new LineSegment(min, max));
                        }
                    }
                }
            }
        }

        if (!segLists.isEmpty()) {
            collinearSegments = new LineSegment[segLists.size()];
            int idx = 0;
            for (LineSegment segment : segLists) {
                collinearSegments[idx] = segment;
                idx++;
            }
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
