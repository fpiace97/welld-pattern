package controller;

import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ManageResponse;
import elements.CollinearPoint;
import elements.LineSegment;
import elements.Point;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
public class SpringControllerPattern {

    private static final Logger log = LogManager.getLogger("MainLogger");

    private final List<Point> pointList = new ArrayList<>();

    @RequestMapping(value = "/point", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "Add point", notes = "Add point from coordinate - the coordinate have to be in int type")
    public ResponseEntity<ManageResponse> addPoint(@RequestBody Point point) {
        log.info("ADD POINT");
        if (!pointList.contains(point) && Objects.nonNull(point)) {
            log.info("Point x {}, point y {}", point.getX(), point.getY() );
            pointList.add(point);
        }
        else {
            log.info("Point is not valid");
            return ResponseEntity.ok(new ManageResponse("KO", "Point cannot be added"));
        }
        log.info("Point added correctly");
        return ResponseEntity.ok(new ManageResponse("OK", "Point added correctly"));

    }

    @RequestMapping(value = "/space", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get point", notes = "Get a list of point")
    public ResponseEntity<ManageResponse> getSpace() {
        log.info("GET SPACE");
        return ResponseEntity.ok(new ManageResponse("OK", pointList.toString()));
    }

    @RequestMapping(value = "/lines/{n}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Return lines", notes = "Get lines compose of n minimum point - lines are return like a list of point")
    public ResponseEntity<Object> getLines(@PathVariable("n") int numberOfPoint) {
        log.info("GET LINES");
        try {
            log.info("Number of point {}", numberOfPoint);
            if (numberOfPoint > 1) {
                Point[] points = pointList.toArray(pointList.toArray(new Point[0]));
                // print and draw the line segments
                CollinearPoint collinear = new CollinearPoint(points, numberOfPoint);
                log.info("Getting collinear");
                return ResponseEntity.ok(new ManageResponse("OK", Arrays.toString(collinear.getLineSegmentArraySegments())));
            } else {
                log.info("Number of point <= 1");
                return ResponseEntity.ok(new ManageResponse("KO", "Segment are compose of 2 or more points"));
            }
        } catch (Exception e) {
            log.error("Exception during the process");
            return ResponseEntity.ok(new ManageResponse("KO", "An exception occurred during the process"));
        }
    }

    @RequestMapping(value = "space", method = RequestMethod.DELETE, produces = "application/json")
    @ApiOperation(value = "Delete all points")
    public ResponseEntity<ManageResponse> deleteSpace() {
        log.info("DELETE SPACE");
        try {
            pointList.clear();
            log.info("Space delete correctly");
            return ResponseEntity.ok(new ManageResponse("OK", "All points deleted"));
        } catch (Exception e) {
            log.error("Exception while deleting space");
            return ResponseEntity.ok(new ManageResponse("KO", "Exception occure"));
        }

    }

}


