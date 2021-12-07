package AI.heuristics;

import board.Tile;

import java.awt.*;

public class Inadmissible extends AbstractHeuristic {

    public Inadmissible() {
        super(new Color(0,55,0));
    }

    @Override
    public double apply(Tile tile) {
        return (Math.sqrt(Math.pow(Math.abs(endRow-tile.getRow()),2)
                + Math.pow(Math.abs(endCol - tile.getColumn() + 10), 2)));
        //return Math.abs(endRow - row) + Math.abs(endCol - col + 10);
    }
}
