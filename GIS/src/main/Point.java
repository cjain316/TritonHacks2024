package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Point {

	public Point nextPoint;
	int x, y, width;
	boolean selected;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
		width = 10;
		selected = false;
	}
	
	public void select() {
		selected = true;
	}
	
	public void deselect() {
		selected = false;
	}
	
	public void setPoint(Point p) {
		nextPoint = p;
	}

	public static double dist(Point p1, Point p2){
		return Math.sqrt((int)Math.pow(p1.x - p2.x, 2) +
				(int)Math.pow(p1.y - p2.y, 2));
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

	static boolean isPointBetweenTwoPoints(Point p1, Point p2, Point p) {
		int x1 = p1.x, x2 = p2.x, x = p.x;
		int y1 = p1.y, y2 = p2.y, y = p.y;
		double distanceAC = Math.sqrt(Math.pow(x - x1, 2) + Math.pow(y - y1, 2));
		double distanceCB = Math.sqrt(Math.pow(x2 - x,2) + Math.pow(y2 - y, 2));
		double distanceAB = Math.sqrt(Math.pow(x2 - x1,2) + Math.pow(y2 - y1, 2));
		return Math.abs(distanceAC + distanceCB - distanceAB) < 1e-9;
	}

	public static double distFromLine(Point A, Point B, Point C){
		 double area = Math.abs(((B.x - A.x)*(C.y - A.y) -
						  (B.y - A.y)*(C.x - A.x)));
		 if(area == 0){
			 if(isPointBetweenTwoPoints(A, B, C)) return 0;
			 return 11;

		 }
		 double length = Math.sqrt(Math.pow(B.x-A.x, 2) + Math.pow(B.y-A.y, 2));
		 return area/length;
	}

	public String toString(){
		return "(" + x + "," + y + ")";
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillOval(x - width / 2, y - width / 2, width, width);
		
		if(selected == true) {
			g.drawOval(x - width, y - width, 2*width, 2*width);
		}
	}



}
