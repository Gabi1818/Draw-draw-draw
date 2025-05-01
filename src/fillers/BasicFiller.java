package fillers;

import rasters.Raster;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BasicFiller implements Filler {

    private Raster raster;
    private List<FilledPixel> filledPixels;

    public BasicFiller(Raster raster) {
        this.raster = raster;
        filledPixels = new ArrayList<FilledPixel>();
    }

    @Override
    public void fill(Point click, Color fillColor) {
        int baseColor = raster.getPixel(click.x, click.y);
        if (baseColor != fillColor.getRGB()) {
            filledPixels.add(new FilledPixel(click.x, click.y, fillColor));
            iterativeFill(click.x, click.y, fillColor, baseColor); // jestli má barvu jako plátno, nezabarví :D
        }
    }

    @Override
    public List<FilledPixel> getFilledPixels() {
        return filledPixels;
    }

    @Override
    public void clearFilledPixels() {
        filledPixels = new ArrayList<>();
    }

    private void recursiveFill(int x, int y, Color fillColor, int baseColor) {
        if (x < 0 || x >= raster.getWidth() || y < 0 || y >= raster.getHeight()) {
            return;
        }

        if (raster.getPixel(x, y) != baseColor) {
            return;
        }

        //zabarvý pixely
        raster.setPixel(x, y, fillColor.getRGB());

        recursiveFill(x + 1, y, fillColor, baseColor); //vpravo
        recursiveFill(x - 1, y, fillColor, baseColor); // doleva
        recursiveFill(x, y + 1, fillColor, baseColor); //nahoru
        recursiveFill(x, y - 1, fillColor, baseColor); //dolů
    }

    private void iterativeFill(int startX, int startY, Color fillColor, int baseColor) {
        Stack<Point> stack = new Stack<>();
        stack.push(new Point(startX, startY));

        while (!stack.isEmpty()) {
            Point p = stack.pop();
            int x = p.x;
            int y = p.y;

            if (x < 0 || x >= raster.getWidth() || y < 0 || y >= raster.getHeight()) {
                continue;
            }

            if (raster.getPixel(x, y) != baseColor) {
                continue;
            }

            raster.setPixel(x, y, fillColor.getRGB());

            stack.push(new Point(x + 1, y));
            stack.push(new Point(x - 1, y));
            stack.push(new Point(x, y + 1));
            stack.push(new Point(x, y - 1));
        }
    }

    public void reapplyFill() {
        List<FilledPixel> copy = new ArrayList<>(filledPixels);
        for (FilledPixel p : copy) {
            fill(new Point(p.x, p.y), p.color);
        }
    }
}
