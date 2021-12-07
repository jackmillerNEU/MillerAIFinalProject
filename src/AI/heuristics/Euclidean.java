package AI.heuristics;

import board.Tile;

import java.awt.*;

public class Euclidean extends AbstractHeuristic {
    public Euclidean() {
        super(new Color(165,42,42));
    }

    @Override
    public double apply(Tile tile) {
        return Math.sqrt(Math.pow(endRow- tile.getRow(),2) + Math.pow(endCol - tile.getColumn(), 2));
    }
}
