package AI.heuristics;

import board.Board;
import board.Tile;

import java.awt.*;

public class WallCount extends AbstractHeuristic {
    Board board;

    public WallCount(Board board) {
        super(Color.orange);
        this.board = board;
    }

    @Override
    public double apply(Tile tile) {
        int count=0;
        for (int r=0;r<Math.abs(endRow-tile.getRow());r++) {
            for (int c=0;c<Math.abs(endCol-tile.getColumn());c++) {
                if (board.getTile(r,c).isWall()) {
                    count++;
                }
            }
        }
        return count;
    }


}
