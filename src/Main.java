import game.Game;
import game.GameLoop;

public class Main {
    public static void main(String[] args) {
        new Thread((new GameLoop(new Game(20, 20)))).start();
    }
}
