package entity;

import core.Position;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Square extends GameObject {



    @Override
    public void update() {
        this.position = new Position(position.getX() + 2, position.getY() + 4);
    }

    @Override
    public Image getGraphics() {
        BufferedImage image = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(Color.PINK);
        graphics.fillRect(0,0,size.getWidth(), size.getHeight());

        graphics.dispose();
        return image;
    }
}
