import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
        checkDubs(points);
        Point[] copy = Arrays.copyOf(points, points.length);
        Arrays.sort(copy);
        ArrayList<LineSegment> segments = new ArrayList<>();
        for (int i = 0; i < copy.length; i++) {
            Point point = copy[i];
            Comparator<Point> comparator = point.slopeOrder();
            int from = i + 1;
            int to = copy.length;
            if ((to - from) < 3) break;
            Point[] localOrder = Arrays.copyOfRange(copy, from, to);
            Arrays.sort(localOrder, 0, localOrder.length);
            double prevSlope = point.slopeTo(localOrder[0]);
            Point prevPoint = localOrder[0];
            boolean collinear = true;
            for (int j = 1; j < localOrder.length; j++) {
                if (prevSlope != point.slopeTo(localOrder[j])) {
                    collinear = false;
                    break;
                }

                prevPoint = localOrder[j];
                prevSlope = point.slopeTo(prevPoint);
            }
            if (collinear) {
                segments.add(new LineSegment(point, prevPoint));
            }
        }
        this.segments = segments.toArray(new LineSegment[segments.size()]);
    }

    public int numberOfSegments() {
        return segments.length;

    }       // the number of line segments

    public LineSegment[] segments() {
        return Arrays.copyOf(this.segments, this.segments.length);
    }      // the line segments

    private void checkDubs(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            double supress = 1;
            if (points[i] == null) throw new NullPointerException();
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
            }
        }
    }

    public static void main(String[] args) {
        Point[] points = {
                new Point(2,2),
                new Point(4,4),
                new Point(3,3),
                new Point(1,1),
                new Point(5,5),
        };
        FastCollinearPoints alg = new FastCollinearPoints(points);
        System.out.println(alg.numberOfSegments());
        for(LineSegment segment: alg.segments()){
            System.out.println(segment.toString());
        }
    }
}
