package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;

public class Boundary {

	public ArrayList<Point> points;
	public ArrayList<int[]> lineCords;

	public Boundary(ArrayList<Point> points) {
		this.points = points;
		lineCords = new ArrayList<int[]>();
		remakeLoop();
		makeLines();
	}

	public void addPoint(Point p){
		points.add(p);
		remakeLoop();
		makeLines();
	}

	public void makeLines() {
		if (points.size() > 0) {
			lineCords.clear();
			for(int i = 0; i < points.size() - 1; i ++) {
				int[] lineCord = {points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y};
				lineCords.add(lineCord);
			}
			lineCords.add(new int[] {points.get(0).x, points.get(0).y, points.get(points.size() - 1).x, points.get(points.size() - 1).y});
		}
	}
	
	// Convex Hull using Jarvis' Algorithm
	// From Geeks for Geeks
	private void remakeLoop(){
		int n = points.size();
		if (n < 3) return;
		ArrayList<Point> hull = new ArrayList<Point>();

		int l = 0;
		for (int i = 1; i < n; i++)
			if (points.get(i).x < points.get(l).x)
				l = i;
		int p = l, q;
		do
		{
			hull.add(points.get(p));
			q = (p + 1) % n;
			for (int i = 0; i < n; i++)
			{
				if (Point.orientation(points.get(p), points.get(i), points.get(q)) == 2)
					q = i;
			}
			p = q;

		} while (p != l);
		System.out.println(hull);
		points = hull;
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

	public int getNumPoints() { return points.size(); }

	public String PointToString(Point p) {
		String output = "(";
		output += (int)(p.x) + "," + (int)(p.y);
		output += ")";
		return output;
	}

	public String PointsToString() {
		String output = "[";
		for (int i = 0; i < points.size()-1; i++) {
			output += PointToString(points.get(i));
			output += ",";
		}
		output += PointToString(points.get(points.size()-1));
		output+= "]";
		return output;
	}

	public String LinecoordsToString() {
		String output = "[";
		for (int i = 0; i < lineCords.size()-1; i++) {
			output += arrToString(lineCords.get(i)) + ",";
		}
		output += arrToString(lineCords.get(lineCords.size()-1)) + "]";
		return output;
	}

	private String arrToString(int[] arr) {
		String output = "[";
		for (int i = 0; i < arr.length-1; i++) {
			output += arr[i] + ",";
		}
		output += arr[arr.length-1];
		output += "]";
		return output;
	}
}
