package rasterizers;

import models.Line;

import java.awt.*;
import java.util.ArrayList;

public interface Rasterizer {

    void rasterize(Line line);

    void rasterizeArray(ArrayList<Line> lines);
}
