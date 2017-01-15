import java.util.ArrayList;
import java.util.Arrays;

import static jdk.nashorn.internal.objects.Global.Infinity;

public class BruteCollinearPoints {
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
        Point[] copy = Arrays.copyOf(points, points.length);
        Point prevPoint = null;
        for (int i = 0; i < copy.length; i++) {
            if (copy[i] == null) throw new NullPointerException();
            for (int j = i + 1; j < copy.length; j++) {
                if(copy[i].compareTo(copy[j]) == 0) throw new IllegalArgumentException();
            }
        }
        Arrays.sort(copy);
        ArrayList<LineSegment> found = new ArrayList<>();
        for (int i = 0; i < copy.length; i++) {
            for (int j = i + 1; j < copy.length; j++) {
                for (int q = j + 1; q < copy.length; q++) {
                    for (int p = q + 1; p < copy.length; p++) {
                        if (copy[i].slopeTo(copy[j]) == copy[j].slopeTo(copy[q]) &&
                                copy[j].slopeTo(copy[q]) == copy[q].slopeTo(copy[p])) {
                            found.add(new LineSegment(copy[i], copy[p]));
                        }
                    }
                }
            }
        }
        this.segments = found.toArray(new LineSegment[found.size()]);
    }    // finds all line segments containing 4 points

    public int numberOfSegments() {
        return segments.length;
    }        // the number of line segments

    public LineSegment[] segments() {
        return Arrays.copyOf(this.segments, numberOfSegments());
    }                // the line segments
}
