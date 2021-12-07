package display;

import game.Game;
import input.Input;

import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.*;

public class Display extends JFrame {
    private Canvas canvas;
    private Renderer renderer;

    public Display(int width, int height, Input input) {
        setTitle("Pathfinder");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        this.canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width + 350, height));
        canvas.setLocation(0, 0);
        canvas.setFocusable(false);
        add(canvas);
        pack();
        addKeyListener(input);
        canvas.createBufferStrategy(3);

        this.renderer = new Renderer();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void render(Game game) {

        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();

        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
        graphics.setColor(Color.PINK);
        graphics.fillRect(canvas.getWidth() - 350,0, 350, canvas.getHeight());

        renderer.render(game, graphics);

        graphics.dispose();
        bufferStrategy.show();
    }
}
