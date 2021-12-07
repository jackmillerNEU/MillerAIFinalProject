package AI.heuristics;

import board.Tile;
import entity.Monster;

import java.awt.*;

public class MonsterHeuristic extends AbstractHeuristic {
    Monster monster;
    public MonsterHeuristic(Monster monster) {
        super(new Color(255, 64, 232));
        this.monster = monster;
    }

    @Override
    public double apply(Tile tile) {
        int waterCost = 0;
        int monsterCost = 0;
        if (tile.isWater()) {waterCost += 30;}
        if (nearMonster(tile)) {monsterCost += 35;}
        return Math.sqrt(Math.pow(endRow- tile.getRow(),2)
                + Math.pow(endCol - tile.getColumn(), 2))
                + waterCost + monsterCost;
    }

    private boolean nearMonster(Tile tile) {
        int mRow = monster.getRow(), mCol = monster.getCol();
        int tRow = tile.getRow(), tCol = tile.getColumn();
        int distanceRow = Math.abs(mRow - tRow);
        int distanceCol = Math.abs(mCol - tCol);

        return distanceRow < 2 && distanceCol < 2;
    }
}
