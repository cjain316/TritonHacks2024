package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Boundary {

	public ArrayList<Point> points;
	public ArrayList<int[]> lineCords;
	
	public Boundary(ArrayList<Point> points) {
		this.points = points;
		lineCords = new ArrayList<int[]>();
	}
	
	public void makeLines() {
		for(int i = 0; i < points.size(); i ++) {
			for(int j = 0; j < points.size(); j ++) {
				if(i == j) {continue;}
				
				
			}
		}
	}
	
	void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
	}
}
