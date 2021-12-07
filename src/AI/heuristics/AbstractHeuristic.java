package AI.heuristics;

import board.Tile;

import java.awt.*;

public abstract class AbstractHeuristic implements Heuristic {
    int endRow=0, endCol=0;
    Color color;

    public AbstractHeuristic(Color color) {
        this.color = color;
    }

    public void setFinish(int endRow, int endCol) {
        this.endRow = endRow;
        this.endCol = endCol;
    }

    public double apply(Tile tile) {
        return 0.0;
    }

    @Override
    public Color getColor() {
        return color;
    }
}
