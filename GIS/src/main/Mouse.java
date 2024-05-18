package main;

import javax.swing.*;
import java.awt.*;
import java.awt.Point;

public class Mouse {
    public int x,y,clickx,clicky;
    public boolean mouseDown;

    public Mouse() {

    }

    public void setPosition(int x,int y) {
        this.x = x;
        this.y = y;
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x,y,10,10);
    }
}
