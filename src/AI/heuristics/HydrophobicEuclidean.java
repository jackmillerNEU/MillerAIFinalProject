package AI.heuristics;

import board.Tile;

import java.awt.*;

public class HydrophobicEuclidean extends AbstractHeuristic {
    public HydrophobicEuclidean() {
        super(new Color(255, 133, 0));
    }

    @Override
    public double apply(Tile tile) {
        int water = 0;
        if (tile.isWater()) {water += Tile.WATER_COST;}
        return Math.sqrt(Math.pow(endRow- tile.getRow(),2)
                + Math.pow(endCol - tile.getColumn(), 2))
                + water;
    }
}

