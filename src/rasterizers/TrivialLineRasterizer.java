package rasterizers;

import models.Line;
import rasters.Raster;

import java.util.ArrayList;

public class TrivialLineRasterizer implements Rasterizer {

    private Raster raster;

    public TrivialLineRasterizer(Raster raster) {
        this.raster = raster;
    }

    @Override
    public void rasterize(Line line) {
        int x1 = line.getPoint1().getX();
        int y1 = line.getPoint1().getY();
        int x2 = line.getPoint2().getX();
        int y2 = line.getPoint2().getY();

        if (!isPointInBounds(x1, y1) || !isPointInBounds(x2, y2)) {
            return;
        }

        if (line.getPoint1() == null || line.getPoint2() == null) {
            System.err.println("Varování: neplatná čára při rasterizaci");
            return;
        }

        if (x1 == x2) {
            if (y1 > y2) {
                int temp = y1;
                y1 = y2;
                y2 = temp;
            }
            for (int y = y1; y <= y2; y++) {
                if (isPointInBounds(x1, y)) {
                    raster.setPixel(x1, y, line.getColor().getRGB());
                }
            }
        } else {
            float k = (float) (y2 - y1) / (x2 - x1);
            float q = y1 - (k * x1);

            if (Math.abs(k) < 1) {
                if (x1 > x2) {
                    int temp = x1;
                    x1 = x2;
                    x2 = temp;
                }
                for (int x = x1; x <= x2; x++) {
                    int y = Math.round(k * x + q);
                    if (isPointInBounds(x, y)) {
                        raster.setPixel(x, y, line.getColor().getRGB());
                    }
                }
            } else {
                if (y1 > y2) {
                    int temp = y1;
                    y1 = y2;
                    y2 = temp;
                }
                for (int y = y1; y <= y2; y++) {
                    int x = Math.round((y - q) / k);
                    if (isPointInBounds(x, y)) {
                        raster.setPixel(x, y, line.getColor().getRGB());
                    }
                }
            }
        }
    }

    @Override
    public void rasterizeArray(ArrayList<Line> lines){
        for(Line line : lines){
            rasterize(line);
        }
    }

    private boolean isPointInBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < raster.getWidth() && y < raster.getHeight();
    }
}