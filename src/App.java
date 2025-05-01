import fillers.BasicFiller;
import fillers.Filler;
import models.Line;
import models.LineCanvas;
import models.Point;
import rasterizers.DashedLineRasterizer;
import rasterizers.DottedLineRasterizer;
import rasterizers.Rasterizer;
import rasterizers.TrivialLineRasterizer;
import rasters.Raster;
import rasters.RasterBufferedImage;
import java.lang.Math;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import java.util.ArrayList;

public class App {

    private final JPanel panel;
    private final Raster raster;
    private MouseAdapter mouseAdapter;
    private KeyAdapter keyAdapter;

    private Point point;
    private Rasterizer rasterizer;
    private Rasterizer dottedRasterizer;
    private Rasterizer dashedRasterizer;

    private LineCanvas canvas;
    private Filler filler;

    private boolean fillMode = false;
    private boolean ctrlMode = false;
    private boolean shiftMode = false;
    private boolean dashedMode = false;
    private boolean circleMode = false;
    private boolean thickLineMode = false;
    private boolean polygonMode = false;
    private boolean deleteMode = false;
    private boolean squareMode = false;
    private boolean rectangleMode = false;
    private ArrayList<Point> polygonPoints;
    private Color color = Color.CYAN;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App(800, 600).start());
    }


    public void clear(int color) {
        raster.setClearColor(color);
        raster.clear();
    }

    private void clearCanvas(){
        canvas.clearLines();
        canvas.clearDottedLines();
        canvas.clearDashedLines();
        filler.clearFilledPixels();
        raster.clear();
        panel.repaint();
    }

    public void present(Graphics graphics) {
        raster.repaint(graphics);
    }

    public void start() {
        clear(0xaaaaaa);
        panel.repaint();
    }

    private JToolBar initToolBar(){
        JToolBar toolbar = new JToolBar();
        JButton clearButton = new JButton("Clear");
        JButton colorButton = new JButton("Color");

        JButton circleButton = new JButton("Circle");
        JButton squareButton = new JButton("Square");
        JButton rectangleButton = new JButton("Rectangle"); //obdelník
        JButton polygonButton = new JButton("Polygon");

        JButton widthLineButton = new JButton("Change Line Width");
        JButton dottedLineButton = new JButton("Dotted Line");
        JButton dashedLineButton = new JButton("Dashed Line");
        JButton shiftLineButton = new JButton("Shift");

        JButton fillButton = new JButton("Fill");
        JButton deleteButton = new JButton("Delete");

        //přesouvání pravým tlačítkem myši


        toolbar.add(clearButton);
        clearButton.addActionListener(e -> clearCanvas());
        colorButton.addActionListener(e -> {
            Color chosenColor = JColorChooser.showDialog(null, "Zvolte barvu", color);
            if (chosenColor != null) {
                color = chosenColor;
            }
        });
        toolbar.add(colorButton);

        toolbar.add(circleButton);
        circleButton.addActionListener(e -> switchCircleMode());
        toolbar.add(squareButton);
        squareButton.addActionListener(e -> switchSquareMode());
        toolbar.add(rectangleButton);
        rectangleButton.addActionListener(e -> switchRectangleMode());
        toolbar.add(polygonButton);
        polygonButton.addActionListener(e -> switchPolygonMode());

        toolbar.add(widthLineButton);
        widthLineButton.addActionListener(e -> switchWidthLineMode());
        toolbar.add(dottedLineButton);
        dottedLineButton.addActionListener(e -> switchDottedLineMode());
        toolbar.add(dashedLineButton);
        dashedLineButton.addActionListener(e -> switchDashedMode());
        toolbar.add(shiftLineButton);
        shiftLineButton.addActionListener(e -> switchShiftMode());

        toolbar.add(fillButton);
        fillButton.addActionListener(e -> switchFillMode());
        toolbar.add(deleteButton);
        deleteButton.addActionListener(e -> switchDeleteMode());

        return toolbar;
    }

    private void switchFillMode(){
        if(!fillMode){
            ctrlMode = false;
            shiftMode = false;
            dashedMode = false;
            circleMode = false;
            thickLineMode = false;
            polygonMode = false;
            deleteMode = false;
            squareMode = false;
            rectangleMode = false;
            fillMode = true;
        }
        else {
            fillMode = false;
        }
    }

    private void switchWidthLineMode(){
        if (!thickLineMode){
            circleMode = false;
            polygonMode = false;
            deleteMode = false;
            squareMode = false;
            rectangleMode = false;
            fillMode = false;
            thickLineMode = true;
        }
        else{
            thickLineMode = false;
        }
    }

    private void switchPolygonMode(){
        if (!polygonMode){
            circleMode = false;
            thickLineMode = false;
            deleteMode = false;
            squareMode = false;
            rectangleMode = false;
            fillMode = false;
            polygonMode = true;
        }
        else {
            //drawPolygon();
            polygonMode = false;
            drawPolygonLine(); //poslední čára
            polygonPoints.clear();
        }
    }

    private void switchDottedLineMode(){
        if (!ctrlMode){
            dashedMode = false;
            circleMode = false;
            thickLineMode = false;
            polygonMode = false;
            deleteMode = false;
            squareMode = false;
            rectangleMode = false;
            fillMode = false;
            ctrlMode = true;
        }
        else {
            ctrlMode = false;
        }
    }

    private void switchShiftMode(){
        if(!shiftMode){
            circleMode = false;
            thickLineMode = false;
            polygonMode = false;
            deleteMode = false;
            squareMode = false;
            rectangleMode = false;
            fillMode = false;
            shiftMode = true;
        }
        else {
            shiftMode = false;
        }
    }

    private void switchDashedMode(){
        if(!dashedMode){
            ctrlMode = false;
            circleMode = false;
            thickLineMode = false;
            polygonMode = false;
            deleteMode = false;
            squareMode = false;
            rectangleMode = false;
            fillMode = false;
            dashedMode = true;
        }
        else {
            dashedMode = false;
        }
    }

    private void switchCircleMode(){
        if(!circleMode){
            ctrlMode = false;
            shiftMode = false;
            dashedMode = false;
            circleMode = true;
            thickLineMode = false;
            polygonMode = false;
            deleteMode = false;
            squareMode = false;
            rectangleMode = false;
            fillMode = false;
        }
        else {
            circleMode = false;
        }
    }

    private void switchDeleteMode(){
        if(!deleteMode){
            ctrlMode = false;
            shiftMode = false;
            dashedMode = false;
            circleMode = false;
            thickLineMode = false;
            polygonMode = false;
            deleteMode = true;
            squareMode = false;
            rectangleMode = false;
            fillMode = false;
        }
        else {
            deleteMode = false;
        }
    }

    private void switchSquareMode(){
        if (!squareMode){
            shiftMode = false;
            circleMode = false;
            thickLineMode = false;
            polygonMode = false;
            deleteMode = false;
            squareMode = true;
            rectangleMode = false;
            fillMode = false;
        }
        else {
            squareMode = false;
        }

    }

    private void switchRectangleMode(){
        if (!rectangleMode){
            shiftMode = false;
            circleMode = false;
            thickLineMode = false;
            polygonMode = false;
            deleteMode = false;
            squareMode = false;
            rectangleMode = true;
            fillMode = false;
        }
        else {
            rectangleMode = false;
        }
    }


    public App(int width, int height) {
        JFrame frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        raster = new RasterBufferedImage(width, height);

        panel = new JPanel() {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };
        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        JToolBar toolbar = initToolBar();
        panel.add(toolbar, BorderLayout.NORTH);

        rasterizer = new TrivialLineRasterizer(raster);
        dottedRasterizer = new DottedLineRasterizer(raster);
        dashedRasterizer = new DashedLineRasterizer(raster);
        canvas = new LineCanvas();
        filler = new BasicFiller(raster);
        polygonPoints = new ArrayList<Point>();

        createAdapters();
        panel.addMouseListener(mouseAdapter);
        panel.addMouseMotionListener(mouseAdapter);
        panel.addKeyListener(keyAdapter);

        //panel focus
        panel.setFocusable(true);
        panel.requestFocusInWindow();
        frame.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                panel.requestFocusInWindow();
            }
        });
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                panel.requestFocusInWindow();
            }
        });
    }

    private void createAdapters() {
        mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)){
                    point = searchClosestPoint(e.getX(), e.getY());
                }
                else if (SwingUtilities.isLeftMouseButton(e)){
                    if (polygonMode){
                        polygonPoints.add(new Point(e.getX(), e.getY()));
                    }
                    else if (deleteMode){
                        Line line = searchClosestLine(e.getX(), e.getY());
                        canvas.removeDottedLine(line);
                        canvas.removeLine(line);
                        canvas.removeDashedLine(line);

                        paintCanvasSoFar();
                        panel.repaint();
                    }
                    else if (squareMode || rectangleMode){
                        point = new Point(e.getX(), e.getY());
                    }
                    else if (fillMode){
                        filler.fill(new java.awt.Point(e.getX(), e.getY()), color);
                        panel.repaint();
                    }

                    else{
                        point = new Point(e.getX(), e.getY());
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (point != null && !polygonMode && !circleMode && !deleteMode && !squareMode && !rectangleMode && !fillMode) {

                        Point point2 = adjustForShift(new Point(e.getX(), e.getY()));

                        Line line = new Line(point, point2, color);

                        raster.clear();
                        if (ctrlMode) {
                            canvas.addDottedLine(line);
                        } else if (dashedMode){
                            canvas.addDashedLine(line);
                        }
                        else {
                            canvas.addLine(line);
                        }

                    if (thickLineMode) {
                        ArrayList<Line> extraLines = createParallelLines(line, 1);
                        for (Line extra : extraLines) {
                            if (ctrlMode) {
                                canvas.addDottedLine(extra);
                            } else if (dashedMode){
                                canvas.addDashedLine(extra);
                            } else {
                                canvas.addLine(extra);
                            }
                        }
                    }

                        paintCanvasSoFar();
                        panel.repaint();
                }
                else if (polygonMode){
                    drawPolygonLine();
                }
                else if (circleMode) {
                    Point edgePoint = new Point(e.getX(), e.getY());
                    drawCircle(point, edgePoint);
                    point = null;
                }
                else if (squareMode) {
                    Point edge = new Point(e.getX(), e.getY());
                    drawSquare(point, edge);
                    point = null;
                }
                else if (rectangleMode) {
                    Point edge = new Point(e.getX(), e.getY());
                    drawRectangle(point, edge);
                    point = null;
                }

            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (fillMode){
                    return;
                }

                if (!polygonMode && !deleteMode){
                    Point point2 = adjustForShift(new Point(e.getX(), e.getY()));

                    Line line = new Line(point, point2, color);

                    paintCanvasSoFar();

                    if (ctrlMode) {
                        dottedRasterizer.rasterize(line);
                    }
                    else if (dashedMode){
                        dashedRasterizer.rasterize(line);
                    }
                    else {
                        rasterizer.rasterize(line);
                    }

                    panel.repaint();
                }
            }
        };

        keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    ctrlMode = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    shiftMode = true;
                }
                if (e.getKeyCode() ==  KeyEvent.VK_A){
                    polygonMode = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    ctrlMode = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    shiftMode = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_A){
                    polygonMode = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE){
                    clearCanvas();
                }
                if (e.getKeyCode() ==  KeyEvent.VK_A){
                    //drawPolygon();
                    polygonMode = false;
                    drawPolygonLine(); //poslední čára
                    polygonPoints.clear();
                }
            }
        };
    }

    private Point adjustForShift(Point currentPoint) {
        if (!shiftMode || point == null) {
            return currentPoint;
        }

        int dx = currentPoint.getX() - point.getX();
        int dy = currentPoint.getY() - point.getY();

        //diagonály
        if (Math.abs(dx) > Math.abs(dy)) {
            if (Math.abs(dy) * 2 >= Math.abs(dx)) {
                int delta = Math.min(Math.abs(dx), Math.abs(dy));
                int newX = point.getX() + (dx > 0 ? delta : -delta);
                int newY = point.getY() + (dy > 0 ? delta : -delta);
                return new Point(newX, newY);
            }
            //vodorovně
            return new Point(currentPoint.getX(), point.getY());
        } else {
            if (Math.abs(dx) * 2 >= Math.abs(dy)) { //diagonála - 45°
                int delta = Math.min(Math.abs(dx), Math.abs(dy));

                int newX = point.getX() + (dx > 0 ? delta : -delta);
                int newY = point.getY() + (dy > 0 ? delta : -delta);
                return new Point(newX, newY);
            }
            //svisle
            return new Point(point.getX(), currentPoint.getY());
        }
    }

    private Point searchClosestPoint(int mouseX, int mouseY){
        int closeNumber = 10; //radius, ve kterém hledá
        ArrayList<Line> lines = canvas.getAllLines();
        int point1x = 0;
        int point1y = 0;
        int point2x = 0;
        int point2y = 0;

        for (int i = 0; i < lines.size(); i++){
            Line line = lines.get(i);
            point1x = line.getPoint1().getX();
            point1y = line.getPoint1().getY();

            point2x = line.getPoint2().getX();
            point2y = line.getPoint2().getY();

            if (Math.abs(mouseX-point1x) < closeNumber && Math.abs(mouseY-point1y) < closeNumber){
                canvas.removeDottedLine(lines.get(i));
                canvas.removeLine(lines.get(i));
                canvas.removeDashedLine(lines.get(i));
                return line.getPoint2();
            }
            else if (Math.abs(mouseX-point2x) < closeNumber && Math.abs(mouseY-point2y) < closeNumber){
                canvas.removeDottedLine(lines.get(i));
                canvas.removeLine(lines.get(i));
                canvas.removeDashedLine(lines.get(i));
                return line.getPoint1();
            }
        }

        return null;
    }

    //vykreslí polygon, ale až nakonec
//    private void drawPolygon() {
//        if (polygonPoints.size() < 3) {
//            return;
//        }
//
//        for (int i = 0; i < polygonPoints.size(); i++) {
//            Point p1 = polygonPoints.get(i);
//            Point p2 = polygonPoints.get((i + 1) % polygonPoints.size());
//            Line line = new Line(p1, p2, Color.cyan);
//            canvas.addLine(line);
//        }
//
//        raster.clear();
//        rasterizer.rasterizeArray(canvas.getLines());
//        dottedRasterizer.rasterizeArray(canvas.getDottedLines());
//        panel.repaint();
//        polygonPoints.clear();
//    }


    private void drawPolygonLine(){
        if (polygonPoints.size() < 2) {
            return;
        }

        Point p1;
        Point p2;

        if (!polygonMode){ //poslední čára
            p1 = polygonPoints.get(0);
            p2 = polygonPoints.get(polygonPoints.size() - 1);
        }
        else {
            p1 = polygonPoints.get(polygonPoints.size() - 2);
            p2 = polygonPoints.get(polygonPoints.size() - 1);
        }

        Line line = new Line(p1, p2, color);

        canvas.addLine(line);

        paintCanvasSoFar();
        panel.repaint();
    }

    private void drawCircle(Point center, Point edge) {
        int centerX = center.getX();
        int centerY = center.getY();

        int dx = edge.getX() - centerX;
        int dy = edge.getY() - centerY;
        int radius = (int) Math.round(Math.sqrt(dx * dx + dy * dy));

        int steps = 360; //stupně
        for (int angle = 0; angle < steps; angle++) {
            double theta1 = Math.toRadians(angle);
            double theta2 = Math.toRadians(angle + 1);

            int x1 = centerX + (int) (radius * Math.cos(theta1));
            int y1 = centerY + (int) (radius * Math.sin(theta1));
            int x2 = centerX + (int) (radius * Math.cos(theta2));
            int y2 = centerY + (int) (radius * Math.sin(theta2));

            Line segment = new Line(new Point(x1, y1), new Point(x2, y2), color);
            canvas.addLine(segment);
        }

        paintCanvasSoFar();
        panel.repaint();
    }

    private void drawSquare(Point p1, Point p2) {
        int dx = p2.getX() - p1.getX();
        int dy = p2.getY() - p1.getY();
        int size = Math.max(Math.abs(dx), Math.abs(dy));

        int x2 = p1.getX() + (dx >= 0 ? size : -size);
        int y2 = p1.getY() + (dy >= 0 ? size : -size);

        Point topRight = new Point(x2, p1.getY());
        Point bottomRight = new Point(x2, y2);
        Point bottomLeft = new Point(p1.getX(), y2);

        canvas.addLine(new Line(p1, topRight, color));
        canvas.addLine(new Line(topRight, bottomRight, color));
        canvas.addLine(new Line(bottomRight, bottomLeft, color));
        canvas.addLine(new Line(bottomLeft, p1, color));

        paintCanvasSoFar();
        panel.repaint();
    }

    private void drawRectangle(Point p1, Point p2) {
        Point topRight = new Point(p2.getX(), p1.getY());
        Point bottomLeft = new Point(p1.getX(), p2.getY());

        canvas.addLine(new Line(p1, topRight, color));
        canvas.addLine(new Line(topRight, p2, color));
        canvas.addLine(new Line(p2, bottomLeft, color));
        canvas.addLine(new Line(bottomLeft, p1, color));

        paintCanvasSoFar();
        panel.repaint();
    }

    private ArrayList<Line> createParallelLines(Line line, int offset) {
        ArrayList<Line> lines = new ArrayList<>();

        Point p1 = line.getPoint1();
        Point p2 = line.getPoint2();

        int dx = p2.getX() - p1.getX();
        int dy = p2.getY() - p1.getY();
        double length = Math.sqrt(dx * dx + dy * dy);

        if (length == 0) return lines; //žádný posun

        //normálový vektor
        double nx = -dy / length;
        double ny = dx / length;

        //posun o 1 pixel nahoru a dolů (vlevo a vpravo kolmo na čáru)
        int offsetX = (int)Math.round(nx * offset);
        int offsetY = (int)Math.round(ny * offset);

        lines.add(new Line(
                new Point(p1.getX() + offsetX, p1.getY() + offsetY),
                new Point(p2.getX() + offsetX, p2.getY() + offsetY),
                line.getColor()
        ));
        lines.add(new Line(
                new Point(p1.getX() - offsetX, p1.getY() - offsetY),
                new Point(p2.getX() - offsetX, p2.getY() - offsetY),
                line.getColor()
        ));

        return lines;
    }

    private Line searchClosestLine(int mouseX, int mouseY) {
        int closestNumber = 10;
        ArrayList<Line> lines = canvas.getAllLines();
        Line closestLine = null;
        double closestDistance = Double.MAX_VALUE;

        for (Line line : lines) {
            Point p1 = line.getPoint1();
            Point p2 = line.getPoint2();

            double distance = distanceFromPointToLine(mouseX, mouseY, p1.getX(), p1.getY(), p2.getX(), p2.getY());

            if (distance < closestNumber && distance < closestDistance) {
                closestDistance = distance;
                closestLine = line;
            }
        }

        return closestLine;
    }

    private double distanceFromPointToLine(int px, int py, int x1, int y1, int x2, int y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;

        if (dx == 0 && dy == 0) {
            return Math.hypot(px - x1, py - y1);
        }

        double t = ((px - x1) * dx + (py - y1) * dy) / (dx * dx + dy * dy);
        t = Math.max(0, Math.min(1, t));

        double closestX = x1 + t * dx;
        double closestY = y1 + t * dy;

        return Math.hypot(px - closestX, py - closestY);
    }

    private void paintCanvasSoFar(){
        //překreslí plátno do původního stavu, který má uložený
        raster.clear();
        rasterizer.rasterizeArray(canvas.getLines());
        dottedRasterizer.rasterizeArray(canvas.getDottedLines());
        dashedRasterizer.rasterizeArray(canvas.getDashedLines());
        filler.reapplyFill();
    }

}
