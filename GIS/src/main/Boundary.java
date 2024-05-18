package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Comparator;

public class Boundary {

	public ArrayList<Point> points;
	public ArrayList<int[]> lineCords;
	Point p0;

	class pointComparator implements Comparator<Point> {
		public int compare(Point p1, Point p2) {

			int o = Point.orientation(p0, p1, p2);
			if (o == 0)
				return (Point.dist(p0, p2) >= Point.dist(p0, p1))? -1 : 1;
			return (o == 2)? -1: 1;
		}
	}

	public Boundary(ArrayList<Point> points) {
		this.points = points;
		lineCords = new ArrayList<int[]>();

		makeLines();
	}
	
	public void makeLines() {
		for(int i = 0; i < points.size() - 1; i ++) {
			int[] lineCord = {points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y};
			lineCords.add(lineCord);
		}
		lineCords.add(new int[] {points.get(0).x, points.get(0).y, points.get(points.size() - 1).x, points.get(points.size() - 1).y});
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
		p0 = points.getFirst();
		points.sort(new pointComparator());
		System.out.println(points);
	}

	private void swap(int i, int j){
		Point temp = points.get(i);
		points.set(i, points.get(j));
		points.set(j, temp);
	}
	
	void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		for(int[] cords : lineCords) {
			g2d.drawLine(cords[0], cords[1], cords[2], cords[3]);
		}
	}
}
