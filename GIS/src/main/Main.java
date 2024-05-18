package main;

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
    int x = 0, y = 0;

    public ArrayList<Button> buttons = new ArrayList<Button>();

    ArrayList<Point> points = new ArrayList<Point>();
    ArrayList<String> defaultAttributes = new ArrayList<String>();
    Boundary bounds;

    public Main() {
        defaultAttributes.add("Balls1");


        points.add(new Point(0,100,defaultAttributes));
        points.add(new Point(100,100,defaultAttributes));
        points.add(new Point(100,200,defaultAttributes));
        points.add(new Point(50,300,defaultAttributes));
        points.add(new Point(0,200,defaultAttributes));

        bounds = new Boundary(points);

        buttonSetup();

        jFrameSetup();
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        updateRoot();

        if(mouse.mouseDown == true) {
        	g.translate(mouse.x - mouse.clickx, mouse.y - mouse.clicky);
        }
        g.translate(x, y);
        
        bounds.paint(g);
        
        buttonHandler();
        if(mouse.mouseDown == true) {
        	g.translate(-(mouse.x - mouse.clickx), -(mouse.y - mouse.clicky));
        }
        g.translate(-x, -y);
        
        mouse.paint(g);

        paintButtons(g);
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
    }

    public void jFrameSetup() {
    	JFrame f = new JFrame("GIS");
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
        mouse.mouseDown = true;
        mouse.clickx = e.getX();
        mouse.clicky = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    	mouse.mouseDown = false;
    	x += mouse.x - mouse.clickx;
    	y += mouse.y - mouse.clicky;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}