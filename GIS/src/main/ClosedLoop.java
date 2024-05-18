package main;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class ClosedLoop {


    ArrayList<Point> points;
    Point p0;

    class pointComparator implements Comparator<Point> {
        public int compare(Point p1, Point p2) {

            int o = Point.orientation(p0, p1, p2);
            if (o == 0)
                return (Point.dist(p0, p2) >= Point.dist(p0, p1))? -1 : 1;
            return (o == 2)? -1: 1;
        }
    }

    public ClosedLoop(){
        points = new ArrayList<Point>();
    }
    public ClosedLoop(Point point){
        this();
        points.add(point);
    }
    public ClosedLoop(ArrayList<Point> points){
        this.points = new ArrayList<Point>(points);
        remakeLoop();
    }

    public void addPoint(Point newPoint){
        points.add(newPoint);
        remakeLoop();
    }

    private void remakeLoop(){
        if(points.size() < 2) return;
        int n = points.size();
        int ymin = points.get(0).y, min = 0;
        for (int i = 1; i < n; i++) {
            int y = points.get(i).y;
            if ((y < ymin) || (ymin == y && points.get(i).x < points.get(min).x))
                ymin = points.get(i).y; min = i;
        }

        swap(0, min);
        p0 = points.get(0);
        points.sort(new pointComparator());
        System.out.println(points);
    }

    private void swap(int i, int j){
        Point temp = points.get(i);
        points.set(i, points.get(j));
        points.set(j, temp);
    }

}
