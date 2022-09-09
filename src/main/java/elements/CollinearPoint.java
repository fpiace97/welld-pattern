package elements;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Objects;

public class CollinearPoint {
    private LineSegment[] lineSegmentArray;
    private int segmentSize;
    private Point[] collinear;
    private int collinearSize;
    private final Point[] pointsClone;
    private static final Logger log = LogManager.getLogger("MainLogger");

    /**
     *
     * @param points = array of points in the space
     * @param numberOfPoint = min number of points that have to compose le line
     */
    public CollinearPoint(Point[] points, int numberOfPoint) {
        /*
          Sort points in ascendant order
         */
        log.info("Sorting array");
        Arrays.sort(points);
        lineSegmentArray = new LineSegment[1];
        segmentSize = 0;
        pointsClone = points.clone();
        for (int i = 0; i < pointsClone.length; i++) {
            // Sort pts array using comparator, according to the next point in ptsNullCheck.
            Arrays.sort(pointsClone, points[i].slopeOrder());
            /*
             *  Start From 1 to not compare point with it self
             */
            for (int j = 1; j < pointsClone.length; j++) {
                collinear = new Point[numberOfPoint];
                collinearSize = 0;
                double slopeA = pointsClone[0].slopeTo(pointsClone[j]);
                log.info("SlopeA {}", slopeA);
                add(pointsClone[0]);
                add(pointsClone[j]);

                /*
                 * If slope == slope A than add point to the collection
                 */
                while (++j < pointsClone.length && pointsClone[0].slopeTo(pointsClone[j]) == slopeA) {
                    add(pointsClone[j]);
                }
                j--;

                /*
                 * Remove eventual null point create by resize array
                 */
                collinear = Arrays.stream(collinear)
                        .filter(Objects::nonNull)
                        .toArray(Point[]::new);

                if (collinearSize >= numberOfPoint) {
                    if (eliminateDup(j)) {
                        add(new LineSegment(collinear));
                    }
                }
            }
        }

    }

    /**
     * Remove segment defined by the same point. We can have the same segment but with different
     * points order
     */
    private boolean eliminateDup(int j) {
        log.info("Remove double segment");
        for (int k = 0; k < collinearSize - 1; k++)
            if (pointsClone[0].compareTo(pointsClone[j - k]) < 0)
                return false;
        return true;
    }

    /**
     * Resizing array can couse null elements in array LineSegment, to prevent that return a copy of
     * the LineSegment array without null elements.
     */
    public LineSegment[] getLineSegmentArraySegments() {
        log.info("Return line segment");
        return Arrays.stream(lineSegmentArray)
                .filter(Objects::nonNull)
                .toArray(LineSegment[]::new);
    }

    /**
     * Add new LineSegment object to ResizingArray. Also doubles the length of lineSegmentArray when
     * it's full.
     */
    private void add(LineSegment x) {
        log.info("Add segment to array");
        if (x == null) throw new NullPointerException();
        if (lineSegmentArray.length == segmentSize)
            resize(lineSegmentArray, 2 * lineSegmentArray.length);
        lineSegmentArray[segmentSize++] = x;
    }


    /**
     * Add new Point object to ResizingArray. Also doubles the length of collinear when it's full.
     */
    private void add(Point x) {
        log.info("Add point to array");
        if (x == null) throw new NullPointerException();
        if (collinear.length == collinearSize)
            resize(collinear, 2 * collinear.length);
        collinear[collinearSize++] = x;
    }

    /**
     * Resizes the array lineSegmentArray to capacity.
     */
    private void resize(LineSegment[] x, int capacity) {
        log.info("Resize line segment array. Actual capacity {}", capacity);
        LineSegment[] copy = new LineSegment[capacity];
        System.arraycopy(x, 0, copy, 0, x.length);
        lineSegmentArray = copy;
    }

    /**
     * Resizes the array collinear to capacity.
     */
    private void resize(Point[] x, int capacity) {
        log.info("Resize point array. Actual capacity {}", capacity);
        Point[] copy = new Point[capacity];
        System.arraycopy(x, 0, copy, 0, x.length);
        collinear = copy;
    }

}