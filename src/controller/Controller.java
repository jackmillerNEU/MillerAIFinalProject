package controller;

public interface Controller {
    boolean isRequestingUp();
    boolean isRequestingDown();
    boolean isRequestingLeft();
    boolean isRequestingRight();
    boolean isRequestingSand();
    boolean isRequestingWater();
    boolean isRequestingWall();

    boolean setStartPos();
    boolean setEndPos();

    boolean isRequestingEdit();
    boolean addPath();
    boolean mazeify();

    boolean reset();
    boolean clear();

    boolean requestMonsterGame();
    boolean requestAutoMonsterGame();
    boolean endMonsterGame();

    int[] changeBoard();
    int[] toggleSearch();

    boolean printBoard();
}
