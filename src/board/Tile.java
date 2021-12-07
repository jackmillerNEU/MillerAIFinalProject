package board;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
    private static final int size = 40;
    public static int WATER_COST = 10;
    private int row;

    public TileType getTileType() {
        return tileType;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    private int column;
    private TileType tileType;

    public Tile(int row, int column, TileType tyleType) {
        this.row = row;
        this.column = column;
        this.tileType = tyleType;
    }

    public boolean isSand() {
        return this.tileType.equals(TileType.SAND);
    }

    public boolean isWater() {
        return this.tileType.equals(TileType.WATER);
    }

    public boolean isWall() {
        return this.tileType.equals(TileType.WALL);
    }

    public boolean isCreature() {
        return this.tileType.equals(TileType.CREATURE);
    }

    public static int getSize() {
        return size;
    }

    public Image getGraphics() {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRect(0,0,size, size);

        if (tileType == TileType.SAND) {
            graphics.setColor(Color.WHITE);
        } else if (tileType == TileType.WATER) {
            graphics.setColor(Color.BLUE);
        } else if (tileType == TileType.WALL) {
            graphics.setColor(Color.DARK_GRAY.darker());
        }
        graphics.fillRect(1,1,size - 2, size - 2);

        graphics.dispose();
        return image;
    }

    public boolean equals(Tile tile) {
        return this.row == tile.getRow() && this.column == tile.getColumn();
    }


    public void setType(TileType tileType) {
        this.tileType = tileType;
    }
}