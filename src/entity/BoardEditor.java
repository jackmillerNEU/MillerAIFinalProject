package entity;

import AI.*;
import AI.heuristics.*;
import board.Board;
import board.Tile;
import board.TileType;
import controller.Controller;
import core.Position;
import core.Size;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class BoardEditor extends GameObject {
    private Controller controller;
    private Board board;
    private int row=0, rows, column=0, columns;
    private boolean editOn;
    private boolean monsterOn;
    private boolean monsterAutoOn;
    private int p = 0;
    private int b = 0;
    private Pathfinder[] paths;
    private final static String[] searches =  {
            "DFS (Not randomized): ",
            "DFS (Randomized): ",
            "BFS: ",
            "A* (Dijkstra): ",
            "A* (Manhattan): ",
            "A* (Euclidean): ",
            "A* (Wall Count): ",
            "A* (Right Favored): ",
            "A* (Random): ",
            "A* (Hydrophobic): ",
            "A* (Hydrophobic Manh.): ",
            "A* (Hydrophobic Eucl.): "
    };

    private final Pathfinder DFSfalse = new DFS(false);
    private final Pathfinder DFStrue = new DFS(true);
    private final Pathfinder BFS = new BFS();
    private final Pathfinder dijkstra = new aStar(new Dijkstra());
    private final Pathfinder manhattan = new aStar(new Manhattan());
    private final Pathfinder euclidean = new aStar(new Euclidean());
    private Pathfinder wallCount;
    private final Pathfinder inadmissible = new aStar(new Inadmissible());
    private final Pathfinder random = new aStar(new RandomHeuristic());
    private final Pathfinder hydrophobic1 = new aStar(new Hydrophobic());
    private final Pathfinder hydrophobic2 = new aStar(new HydrophobicManhattan());
    private final Pathfinder hydrophobic3 = new aStar(new HydrophobicEuclidean());


    StringBuilder bestSearch = new StringBuilder("None");
    double bestTime=-1;
    StringBuilder bestTimeString = new StringBuilder();


    public BoardEditor(Controller controller, Board board) {
        super(new Position(1,1), new Size(Tile.getSize()-2,Tile.getSize()-2));
        this.controller = controller;
        this.board = board;
        this.rows = board.getRows();
        this.columns = board.getColumns();
        this.editOn = false;
        this.monsterOn = false;
        this.monsterAutoOn = false;
        this.isVisible = false;
        wallCount = new aStar(new WallCount(board));
        this.paths = new Pathfinder[]{
                DFSfalse,
                DFStrue,
                BFS,
                dijkstra,
                manhattan,
                euclidean,
                wallCount,
                inadmissible,
                random,
                hydrophobic1,
                hydrophobic2,
                hydrophobic3
        };
    }

    public int cost(List<Tile> path) {
        int cost = 0;
        for (Tile tile : path) {
            if (tile.isSand()) {
                cost++;
            } else {
                cost+=2;
            }
        }
        return cost;
    }

    @Override
    public void update() {
        int deltaX = 0;
        int deltaY = 0;

        if (controller.isRequestingEdit()) {
            this.editOn = !editOn;
            this.isVisible = editOn;
        }

        if (!monsterAutoOn) {

            int[] pathToggle = controller.toggleSearch();
            if (pathToggle[0] == 1) {
                board.clearPaths();
                int pathID = pathToggle[1];
                long sTime = System.nanoTime();
                board.addPath(paths[pathID]);
                long fTime = System.nanoTime();
                try {
                    Path path = board.getPath(0);
                    board.updateSearchData(searches[pathID], fTime - sTime,
                            path.getVisits(), path.size(), cost(path));
                } catch (IndexOutOfBoundsException e) {

                }
            }

            int[] changeBoardRequest = controller.changeBoard();
            if (changeBoardRequest[0] == 1) {
                if (changeBoardRequest[1] == 1) {
                    if (b == 9) {
                        b = 0;
                    } else {
                        b++;
                    }
                } else {
                    if (b == 0) {
                        b = 9;
                    } else {
                        b--;
                    }
                }
                switch (b) {
                    case 0:
                        board.buildMap(1);
                        break;
                    case 1:
                        board.buildMap(2);
                        break;
                    case 2:
                        board.buildMap(3);
                        break;
                    case 3:
                        board.buildMap(4);
                        break;
                    case 4:
                        board.buildMap(5);
                        break;
                    case 5:
                        board.buildMap(6);
                        break;
                    case 6:
                        board.buildMap(7);
                        break;
                    case 7:
                        board.buildMap(8);
                        break;
                    case 8:
                        board.buildMap(9);
                        break;
                    case 9:
                        board.buildMap(10);
                        break;
                    default:
                        break;
                }
            }

            if (controller.addPath()) {
                long sTime;
                long fTime;
                try {
                    Path path;
                    switch (p) {
                        case 0:
                            board.clearPaths();
                            board.resetTimes();
                            sTime = System.nanoTime();
                            board.addPath(DFSfalse);
                            fTime = System.nanoTime();
                            path = board.getPath(0);
                            board.updateSearchData("DFS (Not randomized): ", fTime - sTime,
                                    path.getVisits(), path.size(), cost(path));
                            p++;
                            break;
                        case 1:
                            board.clearPaths();
                            sTime = System.nanoTime();
                            board.addPath(DFStrue);
                            fTime = System.nanoTime();
                            path = board.getPath(0);
                            board.updateSearchData("DFS (Randomized): ", fTime - sTime,
                                    path.getVisits(), path.size(), cost(path));
                            p++;
                            break;
                        case 2:
                            board.clearPaths();
                            sTime = System.nanoTime();
                            board.addPath(BFS);
                            fTime = System.nanoTime();
                            path = board.getPath(0);
                            board.updateSearchData("BFS: ", fTime - sTime,
                                    path.getVisits(), path.size(), cost(path));
                            p++;
                            break;
                        case 3:
                            board.clearPaths();
                            sTime = System.nanoTime();
                            board.addPath(dijkstra);
                            fTime = System.nanoTime();
                            path = board.getPath(0);
                            board.updateSearchData("A* (Dijkstra): ", fTime - sTime,
                                    path.getVisits(), path.size(), cost(path));
                            p++;
                            break;
                        case 4:
                            board.clearPaths();
                            sTime = System.nanoTime();
                            board.addPath(manhattan);
                            fTime = System.nanoTime();
                            path = board.getPath(0);
                            board.updateSearchData("A* (Manhattan): ", fTime - sTime,
                                    path.getVisits(), path.size(), cost(path));
                            p++;
                            break;
                        case 5:
                            board.clearPaths();
                            sTime = System.nanoTime();
                            board.addPath(euclidean);
                            fTime = System.nanoTime();
                            path = board.getPath(0);
                            board.updateSearchData("A* (Euclidean): ", fTime - sTime,
                                    path.getVisits(), path.size(), cost(path));
                            p++;
                            break;
                        case 6:
                            board.clearPaths();
                            sTime = System.nanoTime();
                            board.addPath(wallCount);
                            fTime = System.nanoTime();
                            path = board.getPath(0);
                            board.updateSearchData("A* (Wall Count): ", fTime - sTime,
                                    path.getVisits(), path.size(), cost(path));
                            p++;
                            break;
                        case 7:
                            board.clearPaths();
                            sTime = System.nanoTime();
                            board.addPath(inadmissible);
                            fTime = System.nanoTime();
                            path = board.getPath(0);
                            board.updateSearchData("A* (Right Favored): ", fTime - sTime,
                                    path.getVisits(), path.size(), cost(path));
                            p++;
                            break;
                        case 8:
                            board.clearPaths();
                            sTime = System.nanoTime();
                            board.addPath(random);
                            fTime = System.nanoTime();
                            path = board.getPath(0);
                            board.updateSearchData("A* (Random): ", fTime - sTime,
                                    path.getVisits(), path.size(), cost(path));
                            p++;
                            break;
                        case 9:
                            board.clearPaths();
                            sTime = System.nanoTime();
                            board.addPath(hydrophobic1);
                            fTime = System.nanoTime();
                            path = board.getPath(0);
                            board.updateSearchData("A* (Hydrophobic): ", fTime - sTime,
                                    path.getVisits(), path.size(), cost(path));
                            p++;
                            break;
                        case 10:
                            board.clearPaths();
                            sTime = System.nanoTime();
                            board.addPath(hydrophobic2);
                            fTime = System.nanoTime();
                            path = board.getPath(0);
                            board.updateSearchData("A* (Hydrophobic Manh.): ", fTime - sTime,
                                    path.getVisits(), path.size(), cost(path));
                            p++;
                            break;
                        case 11:
                            board.clearPaths();
                            sTime = System.nanoTime();
                            board.addPath(hydrophobic3);
                            fTime = System.nanoTime();
                            path = board.getPath(0);
                            board.updateSearchData("A* (Hydrophobic Eucl.): ", fTime - sTime,
                                    path.getVisits(), path.size(), cost(path));
                            p++;
                            break;
                        case 12:
                            board.clearPaths();
                            p = 0;
                            break;
                        default:
                            break;
                    }
                } catch (IndexOutOfBoundsException e) {

                }

            }

            if (controller.mazeify()) {
                board.buildMaze();
            }
        }

        if (editOn) {
            if (controller.isRequestingWall()) {
                //System.out.println("getTile("+row+", "+column+").setType(TileType.WALL);");
                this.board.setTile(row, column, TileType.WALL);
            }

            if (controller.isRequestingSand()) {
                this.board.setTile(row, column, TileType.SAND);
            }

            if (controller.isRequestingWater()) {
                //System.out.println("getTile("+row+", "+column+").setType(TileType.WATER);");
                this.board.setTile(row, column, TileType.WATER);
            }

            if (controller.requestAutoMonsterGame()) {
                this.monsterOn = true;
                this.monsterAutoOn = true;
                this.editOn = false;
                this.isVisible = false;
                this.board.startAutoMonsterGame(row, column);
            }

            if (controller.requestMonsterGame()) {
                this.monsterOn = true;
                this.monsterAutoOn = false;
                this.board.startMonsterGame(row, column);
            }

            if (controller.setStartPos()) {
                board.setStart(row, column);
            } else if (controller.setEndPos()) {
                board.setFinish(row, column);
            }
        }
        if (monsterOn && !monsterAutoOn) {
            if (controller.isRequestingUp()) {
                board.moveMonster("up");
            }
            if (controller.isRequestingDown()) {
                board.moveMonster("down");
            }
            if (controller.isRequestingLeft()) {
                board.moveMonster("left");
            }
            if (controller.isRequestingRight()) {
                board.moveMonster("right");
            }
        } else {
            if (controller.isRequestingUp() && inRowRange(row - 1)) {
                if (editOn) {
                    row--;
                    deltaY--;
                }
            }
            if (controller.isRequestingDown() && inRowRange(row + 1)) {
                if (editOn) {
                    row++;
                    deltaY++;
                }
            }
            if (controller.isRequestingLeft() && inColumnRange(column - 1)) {
                if (editOn) {
                    column--;
                    deltaX--;
                }
            }
            if (controller.isRequestingRight() && inColumnRange(column + 1)) {
                if (editOn) {
                    column++;
                    deltaX++;
                }
            }
        }

        if (controller.endMonsterGame()) {
            this.monsterOn = false;
            this.monsterAutoOn = false;
            this.board.endMonsterGame();
        }

        if (controller.reset()) {
            p = 0;
            b = 0;
            board.endMonsterGame();
            this.monsterOn = false;
            this.monsterAutoOn = false;
            board.buildMap(1);
            board.resetTimes();
            board.clearPaths();
        }

        if (controller.clear()) {
            board.clearPaths();
        }

        if (controller.printBoard()) {
            //System.out.println(board);
        }

        position = new Position(position.getX() + deltaX * Tile.getSize(), position.getY() + deltaY * Tile.getSize());

    }

    @Override
    public Image getGraphics() {
        int alpha;
        if (isVisible) {
            alpha = 170;
        } else {
            alpha = 0;
        }
        BufferedImage image = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TRANSLUCENT);
        Graphics2D graphics = image.createGraphics();
        //graphics.setColor(new Color(255,192,203, 160));
        graphics.setColor(new Color(0, 0, 0, alpha));
        graphics.fillRect(0, 0, size.getWidth(), size.getHeight());

        graphics.dispose();
        return image;
    }


    private boolean inRowRange(int i) {
        return i < rows && i >= 0;
    }
    private boolean inColumnRange(int i) {
        return i < columns && i >= 0;
    }
}
