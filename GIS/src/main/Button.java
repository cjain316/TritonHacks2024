package main;

import java.awt.*;

public class Button {
    private int x,y;
    private Dimension size;
    private String UUID;

    public Button(int x, int y, int height, int width, String UUID) {
        this.x = x;
        this.y = y;
        this.size = new Dimension(width,height);
        this.UUID = UUID;
    }

    public String getUUID() { return UUID; }
    public int getX() { return x; }
    public int getY() { return y; }
    public Dimension getSize() { return size; }
}
