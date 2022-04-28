package gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayDeque;
import java.util.Deque;



public class CanvasLineChart {
    private GraphicsContext g;
    private Color color;

    private static double oldX = 13;
    private static double oldY = -1;

    private Deque<Double> buffer = new ArrayDeque<>();


    public CanvasLineChart(GraphicsContext g, Color color) {
        this.g = g;
        this.color = color;
    }

    public void update(double value) {

        buffer.addLast(value);

        if (buffer.size() > 10){
            buffer.removeFirst();
        }

        g.setStroke(color);

        buffer.forEach(y -> {
            if (oldY > -1){
                g.strokeLine(oldX, oldY*200, oldX + 70, y*200);
                oldX = oldX +70;
            }
            oldY = y;
        });
        oldX = 12;
        oldY = -1;
    }

    public double getLast(){
        return buffer.getLast();
    }
}
