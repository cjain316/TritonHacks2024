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
}
