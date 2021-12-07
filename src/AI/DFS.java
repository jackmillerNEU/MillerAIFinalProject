package AI;

import board.Board;
import board.Tile;

import java.awt.*;
import java.util.*;
import java.util.List;

public class DFS extends AbstractPathfinder {
    Stack<Tile> stack = new Stack<>();
    List<Tile> visited = new ArrayList<>();
    HashMap<Tile,Tile> map = new HashMap<>();
    boolean random;

    public DFS(boolean random) {
        super(Color.CYAN);
        this.random = random;
        if (random) {
            this.color = new Color(138,43,226);
        }
    }

    @Override
    public Path getPath(Board board, int startRow, int startColumn, int finishRow, int finishColumn) {
        init(board, map, visited);
        stack.clear();
        Tile start = board.getTile(startRow, startColumn);
        stack.add(start);
        while(!stack.isEmpty()) {
            Tile tile = stack.pop();

            if (tile.getRow() == finishRow && tile.getColumn() == finishColumn) {
                return getPathFromMap(map, visited, start, tile);
            }

            if(!visited.contains(tile))
            {
                visited.add(tile);
            }

            List<Tile> neighbors = board.getNeighbors(tile.getRow(),tile.getColumn());
            if (random) {
                Collections.shuffle(neighbors);
            }
            for (int i = 0; i < neighbors.size(); i++) {
                Tile neighbor=neighbors.get(i);
                if(!visited.contains(neighbor))
                {
                    stack.add(neighbor);
                    map.put(neighbor,tile);
                    //System.out.println(tile.getRow() + ", " + tile.getColumn() + " --- " +
                    //        neighbor.getRow() + ", " + neighbor.getColumn());
                }
            }
        }
        System.out.println("No path found");
        return null;
    }
}
