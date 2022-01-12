public class FastCollinearPoints {
    private LineSegment[] collinearSegments = null;

    /**
     * finds all line segments containing 4 points
     * @param points
     */
    public FastCollinearPoints(Point[] points) {

    }

    /**
     * @return the number of line segments
     */
    public int numberOfSegments() {
        return (collinearSegments != null) ? collinearSegments.length : 0;
    }

    public LineSegment[] segments() {
        return collinearSegments;
    }
}
