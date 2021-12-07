package AI;

import board.Board;
import board.Tile;

import java.awt.*;
import java.util.List;

public interface Pathfinder {
    Path getPath(Board board, int startRow, int startColumn, int finishRow, int finishColumn);
    Color getColor();
}
