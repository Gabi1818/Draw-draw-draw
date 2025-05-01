package rasterizers;

import models.Line;
import rasters.Raster;

import java.util.ArrayList;

public class DashedLineRasterizer implements Rasterizer {

    private Raster raster;
    private final int dashLength = 5;
    private final int gapLength = 3;

    public DashedLineRasterizer(Raster raster) {
        this.raster = raster;
    }

    @Override
    public void rasterize(Line line) {
        int x1 = line.getPoint1().getX();
        int y1 = line.getPoint1().getY();
        int x2 = line.getPoint2().getX();
        int y2 = line.getPoint2().getY();

        //Bresenham-like algoritmus
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        int dashCounter = 0;
        boolean draw = true;

        while (true) {
            if (isPointInBounds(x1, y1) && draw) {
                raster.setPixel(x1, y1, line.getColor().getRGB());
            }

            if (x1 == x2 && y1 == y2) break;

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }

            dashCounter++;

            if (draw && dashCounter >= dashLength) {
                draw = false;
                dashCounter = 0;
            } else if (!draw && dashCounter >= gapLength) {
                draw = true;
                dashCounter = 0;
            }
        }
    }

    @Override
    public void rasterizeArray(ArrayList<Line> lines) {
        for (Line line : lines) {
            rasterize(line);
        }
    }

    private boolean isPointInBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < raster.getWidth() && y < raster.getHeight();
    }
}
