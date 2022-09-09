package elements;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class LineSegment {
    private List<Point> listPoint;  //list of points in the segment

    public LineSegment(Point[] points) {
        this.listPoint = Arrays.asList(points);
    }

}