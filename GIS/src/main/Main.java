package main;

import java.awt.*;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.net.URL;

import javax.swing.*;

public class Main extends JPanel implements KeyListener, ActionListener {
    public SysInf sysinf = new SysInf();
    public Mouse mouse = new Mouse();

    public Main() {
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

    public void paint(Graphics g) {
        super.paintComponent(g);
        updateRoot();
        Graphics2D g2 = (Graphics2D) g;

        mouse.paint(g);

    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Main f = new Main();
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

}