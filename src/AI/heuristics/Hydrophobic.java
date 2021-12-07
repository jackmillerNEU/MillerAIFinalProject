package AI.heuristics;

import board.Tile;

import java.awt.*;

public class Hydrophobic extends AbstractHeuristic {
    public Hydrophobic() {
        super(new Color(132, 203, 51));
    }

    public double apply(Tile tile) {
        return tile.isWater() ? Tile.getSize() : 0;
    }
}
