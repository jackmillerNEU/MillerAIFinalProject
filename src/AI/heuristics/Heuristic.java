package AI.heuristics;

import board.Tile;

import java.awt.*;

public interface Heuristic {
    double apply(Tile tile);
    void setFinish(int endRow, int endCol);
    Color getColor();
}
