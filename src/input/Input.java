package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {

    private boolean[] pressed;
    private long[] timePressed;
    private long[] totalTimePressed;

    public Input() {
        pressed = new boolean[255];
        /*
        timePressed = new long[255];
        totalTimePressed = new long[255];

        for (int i=0;i<255;i++) {
            timePressed[i] = 0;
            totalTimePressed[i] = 0;
        }

         */
    }

    public boolean isPressed(int keyCode) {
        //boolean pressed = totalTimePressed[keyCode] > 10;
        //totalTimePressed[keyCode] = 0;
        //return pressed;
        boolean isPressed = pressed[keyCode];
        pressed[keyCode] = false;
        return isPressed;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //pressed[e.getKeyCode()] = true;
        //timePressed[e.getKeyCode()] = System.currentTimeMillis();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressed[e.getKeyCode()] = true;
        /*
        totalTimePressed[e.getKeyCode()] = System.currentTimeMillis() - timePressed[e.getKeyCode()];
        System.out.println(totalTimePressed[e.getKeyCode()]);
        timePressed[e.getKeyCode()] = 0;
         */
    }

    public void reset(int e) {
        pressed[e] = false;
    }
}
