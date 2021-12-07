package AI;

import board.Board;
import board.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AbstractPathfinder implements Pathfinder {

    Color color;

    public AbstractPathfinder(Color color) {
        this.color = color;
    }

    @Override
    public Path getPath(Board board, int startRow, int startColumn, int finishRow, int finishColumn) {
        return null;
    }

    @Override
    public Color getColor() {
        return color;
    }

    protected Path getPathFromMap(HashMap<Tile,Tile> map, List<Tile> visited, Tile start, Tile finish) {
        List<Tile> list = new ArrayList<>();
        list.add(finish);
        while (!finish.equals(start)) {
            //System.out.println(finish.getRow() + ", " + finish.getColumn());
            finish = map.get(finish);
            list.add(finish);
        }
        Path path = new Path(new ArrayList<>(), color);
        for (int i = list.size() - 1; i >= 0;i--) {
            path.add(list.get(i));
        }
        path.setVisited(visited);
        return path;
    }

    protected void init(Board board, HashMap map, List<Tile> visited) {
        map.clear();
        visited.clear();
        for (int row=0;row < board.getRows();row++) {
            for (int col=0;col < board.getColumns();col++) {
                map.put(board.getTile(row,col), board.getTile(row, col));
            }
        }
    }
}
