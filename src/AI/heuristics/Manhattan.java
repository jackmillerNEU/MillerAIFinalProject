package AI.heuristics;

import board.Tile;

import java.awt.*;

public class Manhattan extends AbstractHeuristic {


    public Manhattan() {
        super(Color.MAGENTA);
    }

    @Override
    public double apply(Tile tile) {
        return (Math.abs(endRow - tile.getRow()) + Math.abs(endCol - tile.getColumn()));
    }
}
