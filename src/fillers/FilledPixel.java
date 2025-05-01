package fillers;

import java.awt.*;

public class FilledPixel {
    //pamatuje si pixel s barvou pro fill
    public int x, y;
    public Color color;

    public FilledPixel(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
}
