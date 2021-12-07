package AI;

import board.Board;
import board.Tile;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BFS extends AbstractPathfinder {
    LinkedList<Tile> queue = new LinkedList<>();
    List<Tile> visited = new ArrayList<>();
    HashMap<Tile,Tile> map = new HashMap<>();

    public BFS() {
        super(Color.red);
    }

    @Override
    public Path getPath(Board board, int startRow, int startColumn, int finishRow, int finishColumn) {
        init(board, map, visited);
        queue.clear();
        Tile start = board.getTile(startRow, startColumn);
        queue.add(start);
        while(!queue.isEmpty()) {
            Tile tile = queue.get(0);
            queue.remove(0);

            if (tile.getRow() == finishRow && tile.getColumn() == finishColumn) {
                return getPathFromMap(map, visited, start, tile);
            }

            if(!visited.contains(tile))
            {
                visited.add(tile);
            }

            List<Tile> neighbors = board.getNeighbors(tile.getRow(),tile.getColumn());
            for (int i = 0; i < neighbors.size(); i++) {
                Tile neighbor=neighbors.get(i);
                if(!queue.contains(neighbor) && !visited.contains(neighbor))
                {
                    queue.add(neighbor);
                    map.put(neighbor,tile);
                    //System.out.println(tile.getRow() + ", " + tile.getColumn() + " --- " +
                    //       neighbor.getRow() + ", " + neighbor.getColumn());
                }
            }
        }
        System.out.println("No path found");
        return null;
    }
}
