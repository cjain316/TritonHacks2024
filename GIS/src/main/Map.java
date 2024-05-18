package main;

import main.coordinateHandler.Coordinate;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Map {
    private String imagePath;
    private Coordinate topLeft,bottomRight;

    public Map(String path,Coordinate topLeft, Coordinate bottomRight) {
        this.imagePath = path;
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public void paint(Graphics2D g2) {
        AffineTransform tx = AffineTransform.getTranslateInstance(0, 0);
        Image Sprite = getImage(imagePath);
        g2.drawImage(Sprite, tx, null);
    }

    protected Image getImage(String path) {

        Image tempImage = null;
        try {
            URL imageURL = Map.class.getResource(path);
            tempImage    = Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (Exception e) {e.printStackTrace();}
        return tempImage;
    }
}
