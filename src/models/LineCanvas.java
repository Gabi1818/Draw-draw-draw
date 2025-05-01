package models;

import java.util.ArrayList;

public class LineCanvas {

    private ArrayList<Line> lines;
    private ArrayList<Line> dottedLines;
    private ArrayList<Line> dashedLines;


    public LineCanvas(){
        this.lines = new ArrayList<>();
        this.dottedLines = new ArrayList<>();
        this.dashedLines = new ArrayList<>();
    }

    public ArrayList<Line> getAllLines(){
        ArrayList<Line> allLines = new ArrayList<Line>();
        allLines.addAll(lines);
        allLines.addAll(dottedLines);
        allLines.addAll(dashedLines);

        return allLines;
    }

    public void addLine(Line line){
        this.lines.add(line);
    }
    public void clearLines(){
        this.lines.clear();
    }
    public ArrayList<Line> getLines(){
        return lines;
    }

    public void addDottedLine(Line line){
        this.dottedLines.add(line);
    }
    public void clearDottedLines(){
        this.dottedLines.clear();
    }
    public ArrayList<Line> getDottedLines(){
        return dottedLines;
    }

    public void addDashedLine(Line line) { this.dashedLines.add(line); }
    public void clearDashedLines() { this.dashedLines.clear(); }
    public ArrayList<Line> getDashedLines() { return dashedLines; }

    public void removeLine(Line line){
        if (lines.contains(line)){
            lines.remove(line);
        }
    }

    public void removeDottedLine(Line line){
        if (dottedLines.contains(line)){
            dottedLines.remove(line);
        }
    }

    public void removeDashedLine(Line line){
        if (dashedLines.contains(line)){
            dashedLines.remove(line);
        }
    }
}
