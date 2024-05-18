package main;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.*;

public class Main extends JPanel implements KeyListener, ActionListener, MouseListener {
    public SysInf sysinf = new SysInf();
    public Mouse mouse = new Mouse();

    public ArrayList<Button> buttons = new ArrayList<Button>();

    ArrayList<Point> points = new ArrayList<Point>();
    ArrayList<String> defaultAttributes = new ArrayList<String>();
    Boundary bounds;

    public Main() {
        defaultAttributes.add("Balls1");

        for (var i = 0; i < 10; i++) {
            points.add(new Point(randInt(0,1920),randInt(0,1080),defaultAttributes));
        }

        //bounds = new Boundary(points);

        buttonSetup();

        jFrameSetup();
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        updateRoot();
        Graphics2D g2 = (Graphics2D) g;

        mouse.paint(g);
        //bounds.paint(g);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Main f = new Main();
    }

    public void ButtonHandler() {
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

        for (int i = 0; i < uuids.size(); i++) {
            String curUUID = uuids.get(i);
            if (curUUID == "exitbutton") {
                System.exit(0);
            }
        }
    }


    public void buttonSetup() {
        buttons.add(new Button(
                sysinf.getScreenSize().width-(sysinf.getScreenSize().width/10),
                0,
                sysinf.getScreenSize().height/10,
                sysinf.getScreenSize().width/10,
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
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouse.mouseDown = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}