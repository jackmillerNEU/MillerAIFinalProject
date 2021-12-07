package entity;

import board.Board;
import board.Tile;
import core.Position;
import core.Size;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Stats extends GameObject {

    private Board board;
    private int width, height;
    private final static int LEFT_BORDER = 20;

    private final static String[] searches =  {
            "1: DFS (Not randomized)",
            "2: DFS (Randomized)",
            "3: BFS",
            "4: A* (Dijkstra)",
            "5: A* (Manhattan)",
            "6: A* (Euclidean)",
            "7: A* (Wall Count)",
            "8: A* (Right Favored)",
            "9: A* (Random)",
            "0: A* (Hydrophobic)",
            "-: A* (Hydrophobic Manh.)",
            "=: A* (Hydrophobic Eucl.)",
            "   A* (Monster + Water)"
    };

    private final static Color[] colors =  {
            Color.cyan,
            new Color(138,43,226),
            Color.red,
            Color.green,
            new Color(200, 0, 227),
            new Color(165,42,42),
            Color.orange,
            new Color(0, 114,0),
            Color.yellow,
            new Color(132, 203, 51),
            new Color(78, 78, 78),
            new Color(255, 133, 0),
            new Color(255, 64, 232)
    };

    public Stats(Board board) {
        super(new Position(board.getColumns() * Tile.getSize(), 0),
                new Size(350, board.getRows() * Tile.getSize()));
        this.width = 350;
        this.height = board.getRows() * Tile.getSize();
        this.board = board;
    }

    @Override
    public void update() {

    }

    @Override
    public Image getGraphics() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(Color.PINK);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(new Color(Color.PINK.getRed(), Color.PINK.getGreen()+45, Color.PINK.getBlue()+45));
        graphics.fillRect(10, 10, width-20, 375);

        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 25));
        graphics.drawString("Best Time:", LEFT_BORDER, 50);
        graphics.setFont(new Font(Font.SANS_SERIF,  Font.PLAIN, 22));
        graphics.drawString(board.getBestTime(), LEFT_BORDER, 80);
        graphics.drawString(board.getBestTimeVisits(), LEFT_BORDER, 105);
        graphics.drawString(board.getBestTimeDistance(), LEFT_BORDER, 130);

        graphics.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 25));
        graphics.drawString("Least Cells Visited:", LEFT_BORDER, 170);
        graphics.setFont(new Font(Font.SANS_SERIF,  Font.PLAIN, 22));
        graphics.drawString(board.getFewestVisitedTime(), LEFT_BORDER, 195);
        graphics.drawString(board.getFewestVisited(), LEFT_BORDER, 220);
        graphics.drawString(board.getFewestVisitedDistance(), LEFT_BORDER, 245);

        graphics.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 25));
        graphics.drawString("Current Search:", LEFT_BORDER, 285);
        graphics.setColor(Color.red);
        graphics.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 22));
        graphics.drawString(board.getCurr(), LEFT_BORDER, 310);
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font(Font.SANS_SERIF,  Font.PLAIN, 22));
        graphics.drawString(board.getCurrVisits(), LEFT_BORDER, 335);
        graphics.drawString(board.getCurrDistance(), LEFT_BORDER, 360);

        int y = 395;
        for (int i=0;i<13;i++) {
            graphics.setColor(colors[i]);
            graphics.fillRect(LEFT_BORDER-2, y + 3, 24, 24);

            y += 30;
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font(Font.SANS_SERIF,  Font.PLAIN, 22));
            graphics.drawString(searches[i], LEFT_BORDER + 35, y-7);
        }

        graphics.dispose();
        return image;
    }
}

