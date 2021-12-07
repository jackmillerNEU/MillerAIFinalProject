package entity;

import AI.aStar;
import AI.heuristics.HydrophobicEuclidean;
import AI.heuristics.MonsterHeuristic;
import board.Board;
import board.Tile;
import core.Position;
import core.Size;

import java.awt.*;

public class Monster extends GameObject {
    int row, col, tick;
    boolean auto;
    Board board;

    public Monster(int row, int column, Board board, boolean auto) {
        super(new Position(row * Tile.getSize(), column * Tile.getSize()),
                new Size(Tile.getSize() - 2,Tile.getSize() - 2));
        this.row = row;
        this.col = column;
        this.auto = auto;
        this.board = board;
        this.tick = 0;
    }

    public void setLoc(Tile tile) {
        this.row = tile.getRow();
        this.col = tile.getColumn();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getTick() {
        return tick;
    }

    @Override
    public void update() {
        if (tick == 30) {
            tick = 0;
            if (auto) {
                board.moveMonster();
            }
            board.clearPaths();
            board.addPath(new aStar(new MonsterHeuristic(this)));
        } else {
            tick++;
        }
    }

    @Override
    public Image getGraphics() {
        return null;
    }
}
