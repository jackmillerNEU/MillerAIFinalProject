package AI;

import board.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Path extends ArrayList {
    List<Tile> path;
    List<Tile> visited;
    Color color;
    public Path(List<Tile> path, Color color) {
        this.path = path;
        this.visited = new ArrayList<>();
        this.color = color;
    }

    public List<Tile> getPath() {
        return path;
    }

    public List<Tile> getVisited() {
        return visited;
    }

    public int getVisits() {
        return visited.size();
    }



    public void setVisited(List<Tile> visited) {
        this.visited = visited;
    }

    public Color getColor() {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), 220);
    }

    public Color getVisitedColor() {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), 70);
    }
}
