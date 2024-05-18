package main;

import java.util.ArrayList;

public class Point {

	public ArrayList<String> attributes;
	public Point nextPoint;
	int x, y;
	
	public Point(int x, int y, ArrayList<String> attributes) {
		this.x = x;
		this.y = y;
		this.attributes = attributes;
	}
	
	public void setPoint(Point p) {
		nextPoint = p;
	}

	public static int dist(Point p1, Point p2){
		return (int)Math.pow(p1.x - p2.x, 2) +
				(int)Math.pow(p1.y - p2.y, 2);
	}

	// 0 --> Collinear
	// 1 --> Clockwise
	// 2 --> Counterclockwise
	public static int orientation(Point p, Point q, Point r){
		int val = (q.y - p.y) * (r.x - q.x) -
				(q.x - p.x) * (r.y - q.y);
		if (val == 0) return 0;  // collinear
		return (val > 0)? 1: 2; // clockwise or counterclock wise
	}

	public String toString(){
		return "(" + x + "," + y + ")";
	}

}
