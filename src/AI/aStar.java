package AI;

import AI.heuristics.Dijkstra;
import AI.heuristics.Heuristic;
import AI.heuristics.Manhattan;
import board.Board;
import board.Tile;

import java.awt.*;
import java.util.*;
import java.util.List;

public class aStar extends AbstractPathfinder {

    List<Tile> O = new ArrayList<>();
    List<Double> g = new ArrayList<>();
    List<Double> f = new ArrayList<>();
    HashMap<Tile,Tile> map = new HashMap<>();
    List<Tile> C = new ArrayList<>();
    Heuristic h;
    List<Tile> visited = new ArrayList<>();

    public aStar() {
        super(Color.MAGENTA);
        this.h = new Dijkstra();
    }

    public aStar(Heuristic h) {
        super(h.getColor());
        this.h = h;
    }

    @Override
    public Path getPath(Board board, int startRow, int startColumn, int finishRow, int finishColumn) {
        h.setFinish(finishRow, finishColumn);
        return getPathWithHeuristic(board, h, startRow, startColumn, finishRow, finishColumn);
    }

    public Path getPathWithHeuristic(Board board, Heuristic func, int startRow, int startColumn, int finishRow, int finishColumn) {
        init(board, map, visited);
        O = new ArrayList<>();
        g = new ArrayList<>();
        f = new ArrayList<>();
        C = new ArrayList<>();

        Tile start = board.getTile(startRow, startColumn);
        Tile target = board.getTile(finishRow, finishColumn);
        O.add(start);
        g.add(0.0);
        f.add(0.0 + func.apply(start));
        visited.add(start);

        while (!O.isEmpty()) {
            int mindex = f.indexOf(Collections.min(f));
            Tile tile = O.get(mindex);

            if (tile.equals(target)) {
                return getPathFromMap(map, visited, start, target);
            }

            for (Tile n : board.getNeighbors(tile.getRow(), tile.getColumn())) {
                double totalWeight = g.get(mindex) + 1;

                if (!O.contains(n) && !C.contains(n)) {
                    map.put(n, tile);
                    //System.out.println(tile.getRow() + ", " + tile.getColumn() + " --- " +
                    //       n.getRow() + ", " + n.getColumn());
                    g.add(totalWeight);
                    f.add(totalWeight + func.apply(n));
                    O.add(n);
                } else {
                    //for (int i=0; i<O.size();i++) {
                    //    System.out.println(O.get(i).getRow() + "," + O.get(i).getColumn() + ": g=" + g.get(i) + ", f=" + f.get(i));
                    //}
                    int ndex = O.indexOf(n);
                    if (ndex != -1 && totalWeight < g.get(ndex)) {
                        map.put(n, tile);
                        g.add(ndex+1,totalWeight);
                        g.remove(ndex);
                        f.add(ndex+1,totalWeight+func.apply(n));
                        f.remove(ndex);

                        if (C.contains(n)) {
                            C.remove(ndex);
                            g.remove(ndex);
                            f.remove(ndex);
                            O.add(n);
                            g.add(totalWeight);
                            f.add(totalWeight+func.apply(n));
                        }
                    }
                }
            }
            int tdex = O.indexOf(tile);
            O.remove(tdex);
            g.remove(tdex);
            f.remove(tdex);
            C.add(tile);
            visited.add(tile);
        }

        System.out.println("No path found");
        return null;
    }
}
