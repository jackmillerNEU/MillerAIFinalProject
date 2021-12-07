package AI.heuristics;

import board.Tile;

import java.awt.*;

public class HydrophobicManhattan extends AbstractHeuristic {
    public HydrophobicManhattan() {
        super(new Color(78, 78, 78));
    }

    @Override
    public double apply(Tile tile) {
        int water = 0;
        if (tile.isWater()) {water += Tile.WATER_COST;}
        return Math.abs(endRow - tile.getRow())
                + Math.abs(endCol - tile.getColumn())
                + water;
    }
}

