package main;

import java.awt.*;

public class SysInf {
    private Dimension screenBounds;


    public SysInf() {
        screenBounds = Toolkit.getDefaultToolkit().getScreenSize();

    }

    public Dimension getScreenSize() { return screenBounds; }
}
