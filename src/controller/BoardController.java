package controller;

import input.Input;

import java.awt.event.KeyEvent;

public class BoardController implements Controller {
    private Input input;

    public BoardController(Input input) {
        this.input = input;
    }

    @Override
    public boolean isRequestingSand() {
        return input.isPressed(KeyEvent.VK_Q);
    }
    @Override
    public boolean isRequestingWater() {
        return input.isPressed(KeyEvent.VK_W);
    }
    @Override
    public boolean isRequestingWall() {
        return input.isPressed(KeyEvent.VK_A);
    }

    @Override
    public boolean setStartPos() {
        return input.isPressed(KeyEvent.VK_S);
    }
    @Override
    public boolean setEndPos() {
        return input.isPressed(KeyEvent.VK_F);
    }

    @Override
    public boolean isRequestingEdit() {
        return input.isPressed(KeyEvent.VK_E);
    }

    @Override
    public boolean reset() {
        return input.isPressed(KeyEvent.VK_R);
    }

    @Override
    public boolean clear() {
        return input.isPressed(KeyEvent.VK_C);
    }

    @Override
    public boolean requestAutoMonsterGame() {
        return input.isPressed(KeyEvent.VK_ENTER);
    }

    @Override
    public boolean requestMonsterGame() {
        return input.isPressed(KeyEvent.VK_SHIFT);
    }

    @Override
    public boolean endMonsterGame() {
        return input.isPressed(KeyEvent.VK_BACK_SPACE);
    }

    @Override
    public int[] changeBoard() {
        if (input.isPressed(KeyEvent.VK_O)) {
            return new int[] {1,1};
        } else if (input.isPressed(KeyEvent.VK_I)) {
            return new int[] {1,0};
        } else {
            return new int[] {0,0};
        }
    }

    @Override
    public int[] toggleSearch() {
        if (input.isPressed(KeyEvent.VK_1)) {
            return new int[] {1, 0};
        } else if (input.isPressed(KeyEvent.VK_2)) {
            return new int[] {1, 1};
        } else if (input.isPressed(KeyEvent.VK_3)) {
            return new int[] {1, 2};
        } else if (input.isPressed(KeyEvent.VK_4)) {
            return new int[] {1, 3};
        } else if (input.isPressed(KeyEvent.VK_5)) {
            return new int[] {1, 4};
        } else if (input.isPressed(KeyEvent.VK_6)) {
            return new int[] {1, 5};
        } else if (input.isPressed(KeyEvent.VK_7)) {
            return new int[] {1, 6};
        } else if (input.isPressed(KeyEvent.VK_8)) {
            return new int[] {1, 7};
        } else if (input.isPressed(KeyEvent.VK_9)) {
            return new int[] {1, 8};
        } else if (input.isPressed(KeyEvent.VK_0)) {
            return new int[] {1, 9};
        } else if (input.isPressed(KeyEvent.VK_MINUS)) {
            return new int[] {1, 10};
        } else if (input.isPressed(KeyEvent.VK_EQUALS)) {
            return new int[] {1, 11};
        }
        else {
            return new int[] {0, 0};
        }
    }

    @Override
    public boolean printBoard() {
        return input.isPressed(KeyEvent.VK_L);
    }

    @Override
    public boolean isRequestingUp() {
        return input.isPressed(KeyEvent.VK_UP);
    }
    @Override
    public boolean isRequestingDown() {
        return input.isPressed(KeyEvent.VK_DOWN);
    }
    @Override
    public boolean isRequestingLeft() {
        return input.isPressed(KeyEvent.VK_LEFT);
    }
    @Override
    public boolean isRequestingRight() {
        return input.isPressed(KeyEvent.VK_RIGHT);
    }

    @Override
    public boolean addPath() {
        return input.isPressed(KeyEvent.VK_P);
    }

    @Override
    public boolean mazeify() {
        return input.isPressed(KeyEvent.VK_M);
    }
}
