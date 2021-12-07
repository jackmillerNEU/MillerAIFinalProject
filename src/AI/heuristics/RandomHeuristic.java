package AI.heuristics;

import board.Tile;

import java.awt.*;

public class RandomHeuristic extends AbstractHeuristic {

    public RandomHeuristic() {
        super(Color.YELLOW);
    }

    public double apply(Tile tile) {
        return Math.random() * 10;
    }
}
