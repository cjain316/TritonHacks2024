package main;

import main.coordinateHandler.Coordinate;
import main.coordinateHandler.Latitude;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.*;

//moldova coordinates
//top left     47 33 28 N 29 00 29 E
//bottom right 46 58 58 N 30 17 07 E

public class Main extends JPanel implements KeyListener, ActionListener, MouseListener {
	public SysInf sysinf = new SysInf();
    public Mouse mouse = new Mouse();
    public Map moldova = new Map("resources\\imgresources\\moldova.png",
            new Coordinate(new Latitude(47,33,28),new Latitude(29,00,29)),
            new Coordinate(new Latitude(46,58,58),new Latitude(30,17,7))
    );
    
    //transform graphics stuff
    int x = 0, y = 0;
    //borders
    int width = 5370, height = 3490;
    
    //selecting points stuff
    boolean pointSelected = false;
    int pointIndex;

    public ArrayList<Button> buttons = new ArrayList<Button>();

    public ArrayList<Zone> zones = new ArrayList<Zone>();
    public Zone tempzone = new Zone("zonertron",new Boundary(new ArrayList<Point>()),new Color(90,250,90),new ArrayList<String>());
    ArrayList<Point> points = new ArrayList<Point>();
    ArrayList<String> defaultAttributes = new ArrayList<String>();
    ArrayList<String> Filters = new ArrayList<String>();

    public ZoneHandler zoneHandler = new ZoneHandler();
    
    public DropdownMenu menu;

    public Main() {
        defaultAttributes.add("NativePlants");
        
        //IMPORTANT HISTORY DO NOT DELETE
//        ArrayList<String> attributes = new ArrayList<String>();
//        attributes.add("flat");
//        attributes.add("water");
//        attributes.add("freaky 👅");
        zones = zoneHandler.parseZones();
        menu = new DropdownMenu(0, 0, 100, sysinf.getScreenSize().height/18, zones);


        buttonSetup();

        jFrameSetup();

        tempzone.attributes.add("Flowing Water");
        tempzone.attributes.add("Shallow");
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        updateRoot();
        
        //make sure can't scroll past boundaries
        int maxX = sysinf.getScreenSize().width - width;
        int maxY = sysinf.getScreenSize().height - height;
        
        //System.out.println("maxX: " + maxX);
		//github.com/cjain316/TritonHacks2024.git
                
	    x = x < maxX ? maxX : x;
	    y = y < maxY ? maxY : y;
        x = x > 0 ? 0 : x;
        y = y > 0 ? 0 : y;
        
        //translate when drag
        int xdist = mouse.x - mouse.clickx;
    	int ydist = mouse.y - mouse.clicky;
    	
        if(mouse.mouseDown) {
        	if(x + xdist > 0) {
        		xdist = -x;
        	}
        	
        	if(y + ydist > 0) {
        		ydist = -y;
        	}
        	
        	if(x + xdist < maxX) {
        		x = maxX;
        		xdist = 0;
        	}
        	
        	if(y + ydist < maxY) {
        		y = maxY;
        		ydist = 0;
        	}
        	
        	g.translate(xdist, ydist);
        }
        
        g.translate(x, y);
                
        moldova.paint(g);
        if (tempzone.boundary.points.size() > 0) {
            tempzone.paint(g);
        }
        paintZones(g);
        
        buttonHandler();
        
        //revert drag changes lmao
        if(mouse.mouseDown) {
        	g.translate(- xdist, - ydist);
        }

        g.translate(-x, -y);

        paintButtons(g);
        menu.paint(g);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Main f = new Main();
    }

    public void paintButtons(Graphics g) {
        //paint top bar
        g.setColor(new Color(90,90,90));
        g.fillRect(0,0,sysinf.getScreenSize().width,sysinf.getScreenSize().height/18);
        for (int i = 0; i < buttons.size(); i++) {
            String uuid = buttons.get(i).getUUID();


            if (uuid.equals("exitbutton")) {
                Button b = buttons.get(i);
                g.setColor(new Color(45,45,45));
                g.fillRect(b.getX(),b.getY(),b.getSize().width,b.getSize().height);

                g.setColor(new Color(200,200,200));
                g.drawLine( //top left to bottom right
                        b.getX()+(b.getSize().width/4),
                        b.getY()+(b.getSize().height/4),
                        b.getX()+((b.getSize().width/4)*3),
                        b.getY()+((b.getSize().height/4)*3)
                );
                g.drawLine( //top right to bottom left
                        b.getX()+((b.getSize().width/4)*3),
                        b.getY()+(b.getSize().height/4),
                        b.getX()+(b.getSize().width/4),
                        b.getY()+((b.getSize().height/4)*3)
                );

            }

            if (uuid.equals("fullscreenbutton")) {
                Button b = buttons.get(i);
                g.setColor(new Color(45,45,45));
                g.fillRect(b.getX(),b.getY(),b.getSize().width,b.getSize().height);

                g.setColor(new Color(200,200,200));
                g.drawRect( //top left to bottom right
                        b.getX()+(b.getSize().width/4),
                        b.getY()+(b.getSize().height/4),
                        b.getSize().width/2,
                        b.getSize().height/2
                );
            }
        }
    }

    public void buttonHandler() {
        ArrayList<String> uuids = new ArrayList<String>();
        Rectangle mouseHitbox = new Rectangle(mouse.x, mouse.y, 1,1);
        for (int i = 0; i < buttons.size(); i++) {
            Button tempButton = buttons.get(i);
            Rectangle buttonHitbox = new Rectangle(
                    tempButton.getX(), tempButton.getY(),
                    tempButton.getSize().width, tempButton.getSize().height
            );
            if (mouseHitbox.intersects(buttonHitbox)) {
                uuids.add(tempButton.getUUID());
            }
        }
        if (mouse.mouseDown) {
            for (int i = 0; i < uuids.size(); i++) {
                String curUUID = uuids.get(i);
                if (curUUID.equals("exitbutton")) {
                    System.exit(0);
                }
                if (curUUID.equals("fullscreenbutton")) {
                    f.dispose();
                    mouse.mouseDown = false;
                    f.setUndecorated(!f.isUndecorated());
                    f.setResizable(!f.isResizable());
                    f.setVisible(true);
                }
            }
        }
    }


    public void buttonSetup() {
        buttons.add(new Button(
                sysinf.getScreenSize().width-(sysinf.getScreenSize().width/32),
                0,
                sysinf.getScreenSize().height/18,
                sysinf.getScreenSize().width/32,
                "exitbutton"
        ));
        buttons.add(new Button(
                sysinf.getScreenSize().width-((sysinf.getScreenSize().width/32)*2),
                0,
                sysinf.getScreenSize().height/18,
                sysinf.getScreenSize().width/32,
                "fullscreenbutton"
        ));
    }

    public void paintZones(Graphics g) {
        for (Zone z: zones) {
            if (z.boundary.getNumPoints() > 0 && z.checkFilters(Filters)) {
                z.paint(g);
            }
        }
    }

    JFrame f = new JFrame("GIS");

    public void jFrameSetup() {
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(
                sysinf.getScreenSize().width,
                sysinf.getScreenSize().height
        );
        f.add(this);
        f.addKeyListener(this);
        f.addMouseListener(this);
        f.setUndecorated(true);
        f.setResizable(false);

        t = new Timer(7, this);
        t.start();
        f.setVisible(true);
    }

    public void updateMousePosition() {
        PointerInfo p = MouseInfo.getPointerInfo();
        java.awt.Point point = p.getLocation();
        SwingUtilities.convertPointFromScreen(point, getFocusCycleRootAncestor());
        mouse.setPosition((int)(point.getX()),(int)(point.getY()));
    }

    public void updateRoot() {
        updateMousePosition();
    }

    public int randInt(int low, int high) {
        int range = high-low;
        return (int)((Math.random()*range)+low);
    }

    public void parseZones() {

    }

    public void addZone() {

    }


    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        repaint();

    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub
        System.out.println(arg0.getExtendedKeyCode());
        switch(arg0.getExtendedKeyCode()) {
            case 17:
                zoneHandler.save(tempzone);
        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    Timer t;

    protected Image getImage(String path) {

        Image tempImage = null;
        try {
            URL imageURL = Main.class.getResource(path);
            tempImage    = Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (Exception e) {e.printStackTrace();}
        return tempImage;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    	
    }

    @Override
    public void mousePressed(MouseEvent e) {
    	switch(e.getButton()) {
    	case 1:
    		mouse.mouseDown = true;
            mouse.clickx = e.getX();
            mouse.clicky = e.getY();
            menu.checkClick(e);
            break;
    	}
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    	switch(e.getButton()) {
    	case 1:
	    	mouse.mouseDown = false;
	    	x += mouse.x - mouse.clickx;
	    	y += mouse.y - mouse.clicky;
	        break;
	        
    	case 3:
    		//restructure for scalability
    		if(pointSelected == false) {
    			pointFinger:
	    		for(int i = 0; i < tempzone.boundary.points.size(); i ++) {
	    			Point p = tempzone.boundary.points.get(i);
	    			int xdist = mouse.x + x - p.x;
	    			int ydist = mouse.y + y - p.y;
	    			double dist = Math.sqrt(Math.pow(xdist, 2) + Math.pow(ydist, 2));
	    			
	    			if(dist < p.width / 2 + 10) {
	    				pointIndex = i;
	    				pointSelected = true;
	    				p.select();
	    				System.out.println("Point selected");
	    				break pointFinger;
	    			}
	    		}
    			
    			//THIS MAKES POINTS
    			if(pointSelected == false) {
    				tempzone.boundary.addPoint(new Point(mouse.x - x, mouse.y - y));
    			}
    		}else {
    			//relocate point
    			Point p = tempzone.boundary.points.get(pointIndex);
    			int xdist = mouse.x + x - p.x;
    			int ydist = mouse.y + y - p.y;
    			double dist = Math.sqrt(Math.pow(xdist, 2) + Math.pow(ydist, 2));
    			
    			p.deselect();
    			if(dist >= p.width / 2 + 10) {
    				pointSelected = false;
    				System.out.println("Point relocated");
    				p.x = mouse.x + x;
    				p.y = mouse.y + y;
    				tempzone.boundary.makeLines();
    			}
    		}
    		break;
    	}
    	
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}