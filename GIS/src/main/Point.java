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
}
