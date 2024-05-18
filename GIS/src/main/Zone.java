package main;

import java.awt.Graphics;

public class Zone {
    public String name;
    public Boundary boundary;
    
    public Zone(String name, Boundary boundary) {
    	this.name = name;
    	this.boundary = boundary;
    }
    
    public void paint(Graphics g) {
    	int[] x = new int[boundary.points.size()];
    	int[] y = new int[boundary.points.size()];
    	
    	for(int i = 0; i < boundary.points.size(); i ++) {
    		x[i] = boundary.points.get(i).x;
    		y[i] = boundary.points.get(i).y;
    	}
    	
    	g.fillPolygon(x, y, boundary.points.size());
    }
    
}
