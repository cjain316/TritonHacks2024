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
		Point startingPoint = points.get(0);
		Point point = points.get(0);
		int numLines = 0;
		int finalNumLines = points.size() - 1;
		
		while(numLines < finalNumLines) {
			Point nextPoint;
			double lowDist = 0;
			
			for(int j = 0; j < points.size(); j ++) {
				nextPoint = points.get(j);
				
				if(point == nextPoint || point == nextPoint.nextPoint
				||(nextPoint == startingPoint && numLines != finalNumLines - 1)) {
					continue;
				}
				
				double xDist = point.x - nextPoint.x;
				double yDist = point.y - nextPoint.y;
				double dist = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
				
				if(j == 0) {
					lowDist = dist;
				}else if(dist < lowDist) {
					lowDist = dist;
					numLines ++;
					int[] lineCord = {point.x, point.y, nextPoint.x, nextPoint.y};
					lineCords.add(lineCord);
					break;
				}
			}
		}
	}
	
	void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		for(int[] cords : lineCords) {
			g2d.drawLine(cords[0], cords[1], cords[2], cords[3]);
		}
	}
}
