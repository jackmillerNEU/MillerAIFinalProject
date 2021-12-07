package game;

import board.Board;
import board.Tile;
import controller.BoardController;
import display.Display;
import entity.BoardEditor;
import entity.GameObject;
import entity.Stats;
import input.Input;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Display display;
    private List<GameObject> gameObjects;
    private Input input;
    private Board board;

    public Game(int boardRows, int boardColumns) {
        this.input = new Input();
        this.display = new Display(boardRows * Tile.getSize(), boardColumns * Tile.getSize(), input);
        this.board = new Board(boardRows, boardColumns);
        this.gameObjects = new ArrayList<>();
        this.gameObjects.add(board);
        this.gameObjects.add(new BoardEditor(new BoardController(input), board));

        this.gameObjects.add(new Stats(board));
    }


    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void update() {
        gameObjects.forEach(gameObject -> gameObject.update());
    }

    public void render() {
        display.render(this);
    }
}
