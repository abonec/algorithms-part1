import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

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

        ArrayList<Double> usedSlopes = new ArrayList<>();
        ArrayList<LineSegment> segments = new ArrayList<>();
        for (int i = 0; i < copy.length; i++) {
            Point point = copy[i];
            System.out.print("origin: ");
            System.out.println(point.toString());
            Comparator<Point> comparator = point.slopeOrder();
            Point[] sub = Arrays.copyOf(copy, copy.length);
            Arrays.sort(sub, comparator);

            int collinearCounter = 0;
            Point prevPoint = null;
            double prevSlope = -999999999;
            for (Point p : sub) {
                double currentSlope = point.slopeTo(p);
                System.out.print("compare: ");
                System.out.print(p.toString());
                System.out.print(" with slope: ");
                System.out.println(currentSlope);
                if (usedSlopes.contains(currentSlope)) continue;

                if (currentSlope == prevSlope) {
                    System.out.println("equal");
                    prevPoint = p;
                    collinearCounter++;
                } else {
                    if (collinearCounter >= 2) {
                        segments.add(new LineSegment(point, prevPoint));
                        usedSlopes.add(prevSlope);
                    }
                    prevPoint = null;
                    collinearCounter = 0;
                }
                prevSlope = currentSlope;
            }
            if (collinearCounter >= 2) {
                segments.add(new LineSegment(point, prevPoint));
                usedSlopes.add(prevSlope);
            }
        }

        this.segments = segments.toArray(new LineSegment[segments.size()]);

//        this.segments = segments.toArray(new LineSegment[segments.size()]);
    }

    public int numberOfSegments() {
        return segments.length;

    }       // the number of line segments

    public LineSegment[] segments() {
        return Arrays.copyOf(this.segments, this.segments.length);
    }      // the line segments

    private void checkDubs(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            boolean supressWarning = true;
            if (points[i] == null) throw new NullPointerException();
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
            }
        }
    }

    public static void main(String[] args) {
        Point[] points = {
//                new Point(1, 1),
////                new Point(2,2),
//                new Point(4, 4),
//                new Point(3, 3),
//                new Point(5, 5),
//                new Point(9, 1),
//                new Point(8, 2),
//                new Point(7, 3),
//                new Point(6, 4),
                new Point(10000, 0),
                new Point(0, 10000),
                new Point(3000, 7000),
                new Point(7000, 3000),
                new Point(20000, 21000),
                new Point(3000, 4000),
                new Point(14000, 15000),
                new Point(6000, 7000),
        };
        FastCollinearPoints alg = new FastCollinearPoints(points);
        System.out.println(alg.numberOfSegments());
        for (LineSegment segment : alg.segments()) {
            System.out.println(segment.toString());
        }
    }
//public static void main(String[] args) {
//
//    // read the n points from a file
//    In in = new In(args[0]);
//    int n = in.readInt();
//    Point[] points = new Point[n];
//    for (int i = 0; i < n; i++) {
//        int x = in.readInt();
//        int y = in.readInt();
//        points[i] = new Point(x, y);
//    }
//
//    // draw the points
//    StdDraw.enableDoubleBuffering();
//    StdDraw.setXscale(0, 32768);
//    StdDraw.setYscale(0, 32768);
//    for (Point p : points) {
//        p.draw();
//    }
//    StdDraw.show();
//
//    // print and draw the line segments
//    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
//    for (LineSegment segment : collinear.segments()) {
//        StdOut.println(segment);
//        segment.draw();
//    }
//    StdDraw.show();
//}
}
