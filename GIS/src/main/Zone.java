package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Zone {
    public String name;
    public Boundary boundary;
    public Color color;
	public ArrayList<String> attributes;
    
    public Zone(String name, Boundary boundary, Color color, ArrayList<String> attributes) {
    	this.name = name;
    	this.boundary = boundary;
    	this.color = color;
		this.attributes = attributes;
    }

	public void paint(Graphics g) {
		int[] x = new int[boundary.points.size()];
		int[] y = new int[boundary.points.size()];

		for(int i = 0; i < boundary.points.size(); i ++) {
			x[i] = boundary.points.get(i).x;
			y[i] = boundary.points.get(i).y;
		}
		g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 50));
		g.fillPolygon(x, y, boundary.points.size());

		boundary.paint(g);
	}

	public boolean checkFilters(ArrayList<String> filters){
		for(String filter : filters) {
			if (!attributes.contains(filter)) return false;
		}
		return true;
	}

	// Create a new point on the closest line and return it
	public Point Onclick(Point p){
		if (boundary.points.size() < 3) {
			boundary.addPoint(p);
			return p;
		}
		int minI = 0; double minDist = Point.distFromLine(p, boundary.get(0), boundary.get(1));
		int n = boundary.size();
		for(int i = 1; i < n; i++){
			double dist = Point.distFromLine(boundary.get(i), boundary.get((i+1)%n), p);
			if(minDist > dist){
				minDist = dist;
				minI = i;
			}
		}
		System.out.println(boundary.get(minI) + " " + boundary.get((minI+1)%n));
		if(minDist > 10) return null;
		boundary.points.add((minI+1)%n, new Point(p.x, p.y));
		System.out.println(boundary.points);
		boundary.makeLines();
		return boundary.get((minI+1)%n);
	}
}
