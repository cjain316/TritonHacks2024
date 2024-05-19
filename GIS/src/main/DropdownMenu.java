package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DropdownMenu {
	public ArrayList<String> attributes;
	public boolean[] checks;
	public int x, y, buttonWidth, buttonHeight, hby;
	public boolean expanded;
	
	public DropdownMenu(int x, int y, int buttonWidth, int buttonHeight, ArrayList<Zone> zones) {
		this.x = x;
		this.y = y;
		this.buttonWidth = buttonWidth;
		this.buttonHeight = buttonHeight;
		attributes = new ArrayList<String>();
		attributes.add("Select");
		for(Zone z : zones) {
			ArrayList<String> attributes = z.attributes;
			for(String attribute : attributes) {
				for(String s : this.attributes) {
					if(attribute.equals(s)) {
						continue;
					}else {
						this.attributes.add(attribute);
					}
				}
			}
		}
		checks = new boolean[attributes.size()];
		hby = buttonHeight;
		expanded = false;
	}
	
	public ArrayList<String> attribute() {
		ArrayList<String> result = new ArrayList<String>();
		
		for(int i = 0; i < attributes.size(); i ++) {
			if(checks[i] == true) {
				result.add(attributes.get(i));
			}
		}
		
		return result;
	}
	
	public void expand() {
		expanded = true;
		hby = buttonHeight * (attributes.size() + 1);
	}
	
	public void collapse() {
		expanded = false;
		hby = buttonHeight;
	}
	
	public boolean checkClick(MouseEvent e) {
		boolean clicked = false;
		
		if(e.getX() > x && e.getX() < x + buttonWidth
		&& e.getY() > y && e.getY() < y + hby) {
			clicked = true;
			if(expanded) {
				if(e.getY() > buttonHeight) {
					checks[e.getY() / buttonHeight - 1] = !checks[e.getY() / buttonHeight - 1];
				}else {
					collapse();
				}
			}else {
				expand();
			}
		}
		
		return clicked;
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g.setColor(new Color(45,45,45));
		g.fillRect(x, y, buttonWidth, buttonHeight);
		g.setColor(new Color(255,255,255));
		g.drawRect(x, y, buttonWidth, buttonHeight);
		g2.drawString(attributes.get(0), x + 30, y + buttonHeight / 2);
		
		if(expanded == true) {
			for(int i = 1; i < attributes.size(); i ++) {
				if(checks[i - 1] == true) {
					g.setColor(new Color(45, 45, 45));
				}else {
					g.setColor(new Color(90, 90, 90));
				}
				g.fillRect(x, y + i * buttonHeight, buttonWidth, buttonHeight);
				g.setColor(new Color(255,255,255));
				g.drawRect(x, y + i * buttonHeight, buttonWidth, buttonHeight);
				g2.drawString(attributes.get(i), x + 30, y + i * buttonHeight + buttonHeight / 2);
			}
		}
	}
}
