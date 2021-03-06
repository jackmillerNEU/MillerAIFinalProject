package display;

import game.Game;

import java.awt.*;

public class Renderer {
    public void render(Game game, Graphics graphics) {
        game.getGameObjects().forEach(gameObject -> graphics.drawImage(
                gameObject.getGraphics(),
                gameObject.getPosition().getX(),
                gameObject.getPosition().getY(),
                null
        ));
    }
}
