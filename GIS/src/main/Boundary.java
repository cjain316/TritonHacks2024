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

		makeLines();
	}
	
	public void makeLines() {
		for(int i = 0; i < points.size() - 1; i ++) {
			int[] lineCord = {points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y};
			lineCords.add(lineCord);
		}
		lineCords.add(new int[] {points.get(0).x, points.get(0).y, points.get(points.size() - 1).x, points.get(points.size() - 1).y});
	}
	
	void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		for(int[] cords : lineCords) {
			g2d.drawLine(cords[0], cords[1], cords[2], cords[3]);
		}
	}
}
