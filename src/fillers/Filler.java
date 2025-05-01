package fillers;

import java.awt.*;
import java.util.List;

public interface Filler {
    public void fill(
            Point click,
            Color fillColor
    );

    List<FilledPixel> getFilledPixels();

    public void reapplyFill();
    public void clearFilledPixels();
}