package board;

import AI.Maze;
import AI.Path;
import AI.Pathfinder;
import core.Position;
import core.Size;
import entity.GameObject;
import entity.Monster;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board extends GameObject {
    List<List<Tile>> board;
    int rows, columns;
    List<Path> paths;
    int startRow=4, startCol=4, endRow=15, endCol=15;

    Monster monster;
    boolean autoMonster;

    String bestTimeSearch;
    double bestTime;
    int bestTimeVisits;
    int bestTimeDistance;
    int bestTimeTotalDistance;

    String fewestVisitedSearch;
    double fewestVisitedTime;
    int fewestVisited;
    int fewestVisitedLength;
    int fewestVisitedTotalLength;

    String currSearch;
    double currTimeSeconds;
    int currVisits;
    int currDistance;
    int currTotalDistance;

    public Board(int rows, int columns) {
        super(new Position(0,0), new Size(Tile.getSize() * columns, Tile.getSize() * rows));
        this.board = new ArrayList<>();
        this.rows = rows;
        this.columns = columns;

        this.monster = null;
        this.autoMonster = false;

        this.bestTimeSearch = "None";
        this.bestTime = 1000.0;
        this.bestTimeVisits = 0;
        this.bestTimeDistance = 0;
        this.bestTimeTotalDistance = 0;

        this.fewestVisitedSearch = "None";
        this.fewestVisitedTime = 0;
        this.fewestVisited = 10000;
        this.fewestVisitedLength = 0;
        this.fewestVisitedTotalLength = 0;

        this.currSearch = "None";
        this.currTimeSeconds = 1000.0;
        this.currVisits = 0;
        this.currDistance = 0;
        this.currTotalDistance = 0;

        this.paths = new ArrayList<Path>();

        for (int i = 0; i < rows; i++) {
            board.add(new ArrayList<Tile>());
            for (int j = 0; j < columns; j++) {
                board.get(i).add(new Tile(i,j,TileType.SAND));
            }
        }

        buildMap(1);
    }

    public Path getPath(int i) {
        return paths.get(i);
    }

    public void startAutoMonsterGame(int row, int column) {
        this.monster = new Monster(row, column, this, true);
        this.autoMonster = true;
    }

    public void startMonsterGame(int row, int column) {
        this.monster = new Monster(row, column, this, false);
        this.autoMonster = false;
    }

    public void endMonsterGame() {
        this.monster = null;
        this.autoMonster = false;
    }

    public void moveMonster() {
        if (this.monster != null) {
            List<Tile> list = this.getNeighbors(monster.getRow(), monster.getCol());
            monster.setLoc(list.get(new Random().nextInt(list.size())));
        }
    }

    public void moveMonster(String direction) {
        if (this.monster != null) {
            int row=0, col=0;
            List<Tile> neighbors = this.getNeighbors(monster.getRow(), monster.getCol());
            switch (direction) {
                case "up":
                    row = monster.getRow() - 1;
                    col = monster.getCol();
                    break;
                case "down":
                    row = monster.getRow() + 1;
                    col = monster.getCol();
                    break;
                case "left":
                    row = monster.getRow();
                    col = monster.getCol() - 1;
                    break;
                case "right":
                    row = monster.getRow();
                    col = monster.getCol() + 1;
                    break;
                default:
                    break;
            }
            Tile newLoc = getTile(row,col);
            if (newLoc != null && neighbors.contains(getTile(row,col))) {
                monster.setLoc(newLoc);
            }
        }
    }

    public void updateSearchData(String algo, long time, int visits, int distance, int totalCost) {
        //System.out.println(time);
        double timeSeconds = time / 1000000.0;
        if (timeSeconds < bestTime) {
            this.bestTimeSearch = algo;
            this.bestTime = timeSeconds;
            this.bestTimeVisits = visits;
            this.bestTimeDistance = distance;
            this.bestTimeTotalDistance = totalCost;
        }
        if (visits < fewestVisited) {
            this.fewestVisitedSearch = algo;
            this.fewestVisitedTime = timeSeconds;
            this.fewestVisited = visits;
            this.fewestVisitedLength = distance;
            this.fewestVisitedTotalLength = totalCost;
        }
        this.currSearch = algo;
        this.currTimeSeconds = timeSeconds;
        this.currVisits = visits;
        this.currDistance = distance;
        this.currTotalDistance = totalCost;
    }

    public String getBestTime() {
        if (bestTime == 1000) {
            return "None";
        }
        return bestTimeSearch + String.format("%.4f", bestTime);
    }

    public String getBestTimeVisits() {
        return "Cells Visited: " + bestTimeVisits;
    }

    public String getBestTimeDistance() {
        return "Path Length: " + bestTimeDistance + ", Cost: " + bestTimeTotalDistance;
    }

    public String getFewestVisitedTime() {
        if (bestTime == 1000) {
            return "None";
        }
        return fewestVisitedSearch + String.format("%.4f", fewestVisitedTime);
    }

    public String getFewestVisited() {
        return "Cells Visited: " + fewestVisited;
    }

    public String getFewestVisitedDistance() {
        return "Path Length: " + fewestVisitedLength + ", Cost: " + fewestVisitedTotalLength;
    }

    public String getCurr() {
        if (currTimeSeconds == 1000) {
            return "None";
        }
        return currSearch + String.format("%.4f", currTimeSeconds);
    }

    public String getCurrVisits() {
        return "Cells Visited: " + currVisits;
    }

    public String getCurrDistance() {
        return "Path Length: " + currDistance + ", Cost: " + currTotalDistance;
    }

    public void resetTimes() {
        this.bestTimeSearch = "None";
        this.bestTime = 1000.0;
        this.bestTimeVisits = 0;
        this.bestTimeDistance = 0;
        this.bestTimeTotalDistance = 0;

        this.fewestVisitedSearch = "None";
        this.fewestVisitedTime = 0;
        this.fewestVisited = 10000;
        this.fewestVisitedLength = 0;
        this.fewestVisitedTotalLength = 0;

        this.currSearch = "None";
        this.currTimeSeconds = 1000.0;
        this.currVisits = 0;
        this.currDistance = 0;
        this.currTotalDistance = 0;
    }

    public void buildMap(int mapID) {
        for (int r=0;r<rows;r++) {
            for (int c=0;c<columns;c++) {
                getTile(r,c).setType(TileType.SAND);
            }
        }
        switch (mapID) {
            case 1:
                buildBorder();
                setEndpoints();
                break;
            case 2:
                buildBorder();
                buildImpossible();
                break;
            case 3:
                buildBorder();
                buildWall1();
                break;
            case 4:
                buildBorder();
                buildWall2();
                break;
            case 5:
                buildBorder();
                setEndpoints();
                buildWall3();
                break;
            case 6:
                buildBorder();
                buildFunnel(TileType.WALL);
                break;
            case 7:
                buildBorder();
                setEndpoints();
                buildLakes1();
                break;
            case 8:
                buildBorder();
                setEndpoints();
                buildLakes1();
                buildLakes2();
                break;
            case 9:
                buildBorder();
                setEndpoints();
                buildFunnel(TileType.WATER);
                break;
            case 10:
                buildMazeX();
                break;
            default:
                for (int r=0;r<rows;r++) {
                    for (int c=0;c<columns;c++) {
                        getTile(r,c).setType(TileType.SAND);
                    }
                }
        }
    }

    private void setEndpoints() {
        startRow=4;
        startCol=4;
        endRow=15;
        endCol=15;
    }

    private void buildBorder() {
        for (int r = 0; r < rows; r++) {
            getTile(r, 0).setType(TileType.WALL);
            getTile(r, columns - 1).setType(TileType.WALL);
        }
        for (int c = 0; c < columns; c++) {
            getTile(0, c).setType(TileType.WALL);
            getTile(rows - 1, c).setType(TileType.WALL);
        }
    }

    public void buildMaze() {
        Maze maze = new Maze(rows+1, columns+1);
        boolean[][] map = maze.getMap();
        //System.out.println(maze);
        for (int r=0;r<rows;r++) {
            for (int c=0;c<columns;c++) {
                if (!map[r][c]) {
                    getTile(r,c).setType(TileType.WALL);
                } else {
                    getTile(r,c).setType(TileType.SAND);
                }
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setStart(int r, int c) {
        this.startRow = r;
        this.startCol = c;
    }
    public void setFinish(int r, int c) {
        this.endRow = r;
        this.endCol = c;
    }

    @Override
    public void update() {
        if (monster != null){
            monster.update();
        }
    }

    public void addPath(Pathfinder pathfinder) {
        try {
            Path path = pathfinder.getPath(this, startRow, startCol, endRow, endCol);
            if (path != null) {
                paths.add(path);
            }
        } catch (NullPointerException e) {

        }
    }

    public void clearPaths() {
        paths.clear();
    }

    @Override
    public Image getGraphics() {
        int tileSize = Tile.getSize();
        BufferedImage image = new BufferedImage(tileSize * columns, tileSize * rows, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0,0,tileSize * columns, tileSize * rows);

        int x = -tileSize,y = -tileSize;
        for (int i = 0; i < rows; i++) {
            y += tileSize;
            for (int j = 0; j < columns; j++) {
                x += tileSize;
                graphics.drawImage(board.get(i).get(j).getGraphics(), x, y, null);
                if (i == startRow && j == startCol) {
                    BufferedImage tile = new BufferedImage(Tile.getSize() - 2, Tile.getSize() - 2, BufferedImage.TRANSLUCENT);
                    graphics.setColor(new Color(252, 103, 3));
                    graphics.fillRect(x + 1, y + 1, Tile.getSize() - 2, Tile.getSize() - 2);
                    graphics.drawImage(tile, x, y, null);
                } else if (i == endRow && j == endCol) {
                    BufferedImage tile = new BufferedImage(Tile.getSize()-2, Tile.getSize()-2, BufferedImage.TRANSLUCENT);
                    graphics.setColor(new Color(36, 252, 3));
                    graphics.fillRect(x+1, y+1, Tile.getSize() - 2, Tile.getSize() - 2);
                    graphics.drawImage(tile, x, y, null);
                }
            }
            x = -tileSize;
        }

        for (Path path : paths) {
            if (path == null) {
                break;
            }
            x = -tileSize;
            y = -tileSize;
            for (int i = 0; i < rows; i++) {
                y += tileSize;
                for (int j = 0; j < columns; j++) {
                    x += tileSize;
                    if (!((i == startRow && j == startCol) || (i == endRow && j == endCol))) {
                        if (path.contains(board.get(i).get(j))) {
                            BufferedImage tile = new BufferedImage(Tile.getSize() - 2, Tile.getSize() - 2, BufferedImage.TRANSLUCENT);
                            graphics.setColor(path.getColor());
                            graphics.fillRect(x + 1, y + 1, Tile.getSize() - 2, Tile.getSize() - 2);
                            graphics.drawImage(tile, x, y, null);
                        } else if (path.getVisited().contains(board.get(i).get(j))) {
                            BufferedImage tile = new BufferedImage(Tile.getSize() - 2, Tile.getSize() - 2, BufferedImage.TRANSLUCENT);
                            graphics.setColor(path.getVisitedColor());
                            graphics.fillRect(x + 1, y + 1, Tile.getSize() - 2, Tile.getSize() - 2);
                            graphics.drawImage(tile, x, y, null);
                        }
                    }
                }
                x = -tileSize;
            }
        }


        if (monster != null) {
            x = monster.getCol() * tileSize;
            y = monster.getRow() * tileSize;
            BufferedImage monster = new BufferedImage(Tile.getSize() - 2, Tile.getSize() - 2, BufferedImage.TRANSLUCENT);
            graphics.setColor(new Color(179, 232, 56));
            graphics.fillOval(x + 2, y + 2, Tile.getSize() - 5, Tile.getSize() - 5);
            graphics.setColor(Color.BLACK);
            graphics.fillOval(x + 10, y + 8, 5, 5);
            graphics.fillOval(x + 20, y + 8, 5, 5);
            graphics.setColor(Color.RED);
            graphics.fillOval(x + 8, y + 19, 19, 5);
            graphics.drawImage(monster, x, y, null);
        }

        graphics.dispose();
        return image;
    }

    public Tile getTile(int row, int column) {
        if (row >= rows || column >= columns || row < 0 || column < 0) {
            return null;
        }
        return board.get(row).get(column);
    }

    public void setTile(int row, int column, TileType tileType) {
        board.get(row).get(column).setType(tileType);
    }

    public List<Tile> getNeighbors(int row, int column) {
        List<Tile> neighborsTemp = new ArrayList<>();
        List<Tile> neighbors = new ArrayList<>();
        neighborsTemp.add(getTile(row + 1, column));
        neighborsTemp.add(getTile(row - 1, column));
        neighborsTemp.add(getTile(row, column - 1));
        neighborsTemp.add(getTile(row, column + 1));

        for (Tile tile : neighborsTemp) {
            if (tile != null && !tile.isWall()) {
                neighbors.add(tile);
            }
        }
        return neighbors;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int r=0;r<rows;r++) {
            for (int c=0;c<columns;c++) {
                s.append("getTile("+r+", "+c+").setType(TileType." + getTile(r,c).getTileType() + ");\n");
            }
        }
        return s.toString();
    }

    private void buildImpossible() {
        startRow=9;
        startCol=4;
        endRow=9;
        endCol=14;
        getTile(9, 17).setType(TileType.WALL);
        getTile(8, 17).setType(TileType.WALL);
        getTile(7, 17).setType(TileType.WALL);
        getTile(6, 17).setType(TileType.WALL);
        getTile(6, 16).setType(TileType.WALL);
        getTile(6, 15).setType(TileType.WALL);
        getTile(6, 14).setType(TileType.WALL);
        getTile(6, 13).setType(TileType.WALL);
        getTile(6, 12).setType(TileType.WALL);
        getTile(6, 11).setType(TileType.WALL);
        getTile(7, 11).setType(TileType.WALL);
        getTile(8, 11).setType(TileType.WALL);
        getTile(9, 11).setType(TileType.WALL);
        getTile(10, 11).setType(TileType.WALL);
        getTile(11, 11).setType(TileType.WALL);
        getTile(12, 11).setType(TileType.WALL);
        getTile(12, 12).setType(TileType.WALL);
        getTile(12, 13).setType(TileType.WALL);
        getTile(12, 14).setType(TileType.WALL);
        getTile(12, 15).setType(TileType.WALL);
        getTile(12, 16).setType(TileType.WALL);
        getTile(12, 17).setType(TileType.WALL);
        getTile(11, 17).setType(TileType.WALL);
        getTile(10, 17).setType(TileType.WALL);
    }

    private void buildWall1() {
        startRow=9;
        startCol=4;
        endRow=9;
        endCol=15;
        getTile(14, 9).setType(TileType.WALL);
        getTile(13, 9).setType(TileType.WALL);
        getTile(12, 9).setType(TileType.WALL);
        getTile(11, 9).setType(TileType.WALL);
        getTile(10, 9).setType(TileType.WALL);
        getTile(9, 9).setType(TileType.WALL);
        getTile(8, 9).setType(TileType.WALL);
        getTile(7, 9).setType(TileType.WALL);
        getTile(6, 9).setType(TileType.WALL);
        getTile(5, 9).setType(TileType.WALL);

    }

    private void buildWall2() {
        startRow=2;
        startCol=2;
        endRow=16;
        endCol=16;
        getTile(1, 13).setType(TileType.WALL);
        getTile(2, 13).setType(TileType.WALL);
        getTile(3, 13).setType(TileType.WALL);
        getTile(4, 13).setType(TileType.WALL);
        getTile(5, 13).setType(TileType.WALL);
        getTile(6, 13).setType(TileType.WALL);
        getTile(7, 13).setType(TileType.WALL);
        getTile(8, 13).setType(TileType.WALL);
        getTile(10, 13).setType(TileType.WALL);
        getTile(11, 13).setType(TileType.WALL);
        getTile(12, 13).setType(TileType.WALL);
        getTile(13, 13).setType(TileType.WALL);
        getTile(14, 13).setType(TileType.WALL);
        getTile(15, 13).setType(TileType.WALL);
        getTile(16, 13).setType(TileType.WALL);
        getTile(17, 13).setType(TileType.WALL);
        getTile(18, 13).setType(TileType.WALL);
    }

    private void buildWall3() {
        getTile(5, 13).setType(TileType.WALL);
        getTile(6, 13).setType(TileType.WALL);
        getTile(7, 13).setType(TileType.WALL);
        getTile(8, 13).setType(TileType.WALL);
        getTile(9, 13).setType(TileType.WALL);
        getTile(10, 13).setType(TileType.WALL);
        getTile(11, 13).setType(TileType.WALL);
        getTile(12, 13).setType(TileType.WALL);
        getTile(13, 13).setType(TileType.WALL);
        getTile(13, 12).setType(TileType.WALL);
        getTile(13, 11).setType(TileType.WALL);
        getTile(13, 10).setType(TileType.WALL);
        getTile(13, 9).setType(TileType.WALL);
        getTile(13, 8).setType(TileType.WALL);
        getTile(13, 7).setType(TileType.WALL);
        getTile(13, 6).setType(TileType.WALL);
        getTile(13, 5).setType(TileType.WALL);
        getTile(13, 4).setType(TileType.WALL);
        getTile(4, 13).setType(TileType.WALL);
    }

    private void buildFunnel(TileType tileType) {
        startRow=8;
        startCol=2;
        endRow=11;
        endCol=17;
        for (int i=4;i<16;i++) {
            if (i==4 || i==16) {
                getTile(5,i).setType(tileType);
                getTile(13, i).setType(tileType);
            } else if (i==5 || i==15) {
                getTile(6,i).setType(tileType);
                getTile(12, i).setType(tileType);
            } else if (i==6 || i==14) {
                getTile(7,i).setType(tileType);
                getTile(11, i).setType(tileType);
            } else {
                getTile(8,i).setType(tileType);
                getTile(10, i).setType(tileType);
            }
        }
        getTile(1, 10).setType(TileType.WALL);
        getTile(2, 10).setType(TileType.WALL);
        getTile(3, 10).setType(TileType.WALL);
        getTile(5, 10).setType(TileType.WALL);
        getTile(6, 10).setType(TileType.WALL);
        getTile(7, 10).setType(TileType.WALL);
        getTile(11, 10).setType(TileType.WALL);
        getTile(12, 10).setType(TileType.WALL);
        getTile(13, 10).setType(TileType.WALL);
        getTile(14, 10).setType(TileType.WALL);
        getTile(15, 10).setType(TileType.WALL);
        getTile(16, 10).setType(TileType.WALL);
        getTile(17, 10).setType(TileType.WALL);
    }

    private void buildLakes1() {
        getTile(5,5).setType(TileType.WATER);
        getTile(6,5).setType(TileType.WATER);
        getTile(7,5).setType(TileType.WATER);
        getTile(8,5).setType(TileType.WATER);
        getTile(9,5).setType(TileType.WATER);

        getTile(6,6).setType(TileType.WATER);
        getTile(7,6).setType(TileType.WATER);
        getTile(8,6).setType(TileType.WATER);

        getTile(6,4).setType(TileType.WATER);
        getTile(7,4).setType(TileType.WATER);
        getTile(8,4).setType(TileType.WATER);

        getTile(10,9).setType(TileType.WATER);
        getTile(10,10).setType(TileType.WATER);
        getTile(10,11).setType(TileType.WATER);
        getTile(10,12).setType(TileType.WATER);
        getTile(10,13).setType(TileType.WATER);

        getTile(9,10).setType(TileType.WATER);
        getTile(9,11).setType(TileType.WATER);
        getTile(9,12).setType(TileType.WATER);

        getTile(11,10).setType(TileType.WATER);
        getTile(11, 11).setType(TileType.WATER);
        getTile(11,12).setType(TileType.WATER);

        getTile(9, 4).setType(TileType.WATER);
        getTile(8, 3).setType(TileType.WATER);
        getTile(7, 3).setType(TileType.WATER);
        getTile(9, 3).setType(TileType.WATER);
        getTile(10, 4).setType(TileType.WATER);
        getTile(13, 13).setType(TileType.WATER);
        getTile(13, 14).setType(TileType.WATER);
        getTile(13, 15).setType(TileType.WATER);
        getTile(13, 16).setType(TileType.WATER);
        getTile(13, 17).setType(TileType.WATER);
        getTile(14, 17).setType(TileType.WATER);
        getTile(15, 17).setType(TileType.WATER);
        getTile(16, 17).setType(TileType.WATER);
        getTile(17, 17).setType(TileType.WATER);
        getTile(17, 16).setType(TileType.WATER);
        getTile(17, 14).setType(TileType.WATER);
        getTile(17, 13).setType(TileType.WATER);
        getTile(16, 13).setType(TileType.WATER);
        getTile(15, 13).setType(TileType.WATER);
        getTile(14, 13).setType(TileType.WATER);
        getTile(13, 13).setType(TileType.WATER);
        getTile(14, 8).setType(TileType.WATER);
        getTile(14, 7).setType(TileType.WATER);
        getTile(14, 7).setType(TileType.WATER);
        getTile(14, 6).setType(TileType.WATER);
        getTile(15, 6).setType(TileType.WATER);
        getTile(15, 5).setType(TileType.WATER);
        getTile(15, 8).setType(TileType.WATER);
        getTile(15, 9).setType(TileType.WATER);
        getTile(16, 5).setType(TileType.WATER);
        getTile(16, 4).setType(TileType.WATER);
        getTile(17, 5).setType(TileType.WATER);
        getTile(18, 6).setType(TileType.WATER);
        getTile(18, 7).setType(TileType.WATER);
        getTile(18, 8).setType(TileType.WATER);
        getTile(18, 9).setType(TileType.WATER);
        getTile(17, 9).setType(TileType.WATER);
        getTile(17, 10).setType(TileType.WATER);
        getTile(16, 10).setType(TileType.WATER);
        getTile(16, 9).setType(TileType.WATER);
        getTile(15, 7).setType(TileType.WATER);
        getTile(16, 6).setType(TileType.WATER);
        getTile(18, 5).setType(TileType.WATER);
        getTile(17, 4).setType(TileType.WATER);
        getTile(18, 4).setType(TileType.WATER);
        getTile(18, 10).setType(TileType.WATER);
        getTile(17, 3).setType(TileType.WATER);
        getTile(18, 3).setType(TileType.WATER);
        getTile(17, 2).setType(TileType.WATER);
        getTile(17, 2).setType(TileType.WATER);
        getTile(18, 1).setType(TileType.WATER);
        getTile(18, 1).setType(TileType.WATER);
        getTile(18, 2).setType(TileType.WATER);
        getTile(15, 5).setType(TileType.WATER);
        getTile(15, 4).setType(TileType.WATER);
        getTile(14, 3).setType(TileType.WATER);
        getTile(13, 2).setType(TileType.WATER);
        getTile(13, 2).setType(TileType.WATER);
        getTile(13, 1).setType(TileType.WATER);
        getTile(13, 1).setType(TileType.WATER);
        getTile(15, 1).setType(TileType.WATER);
        getTile(14, 1).setType(TileType.WATER);
        getTile(14, 1).setType(TileType.WATER);
        getTile(14, 2).setType(TileType.WATER);
        getTile(15, 3).setType(TileType.WATER);
        getTile(16, 3).setType(TileType.WATER);
        getTile(15, 2).setType(TileType.WATER);
        getTile(16, 1).setType(TileType.WATER);
        getTile(17, 1).setType(TileType.WATER);
        getTile(16, 2).setType(TileType.WATER);
        getTile(5, 12).setType(TileType.WATER);
        getTile(5, 13).setType(TileType.WATER);
        getTile(5, 14).setType(TileType.WATER);
        getTile(5, 15).setType(TileType.WATER);
        getTile(5, 16).setType(TileType.WATER);
        getTile(5, 16).setType(TileType.WATER);
        getTile(6, 16).setType(TileType.WATER);
        getTile(6, 15).setType(TileType.WATER);
        getTile(6, 14).setType(TileType.WATER);
        getTile(7, 16).setType(TileType.WATER);
        getTile(7, 17).setType(TileType.WATER);
        getTile(6, 17).setType(TileType.WATER);
        getTile(5, 17).setType(TileType.WATER);
        getTile(5, 16).setType(TileType.WATER);
        getTile(4, 15).setType(TileType.WATER);
        getTile(4, 15).setType(TileType.WATER);
        getTile(4, 16).setType(TileType.WATER);
        getTile(4, 14).setType(TileType.WATER);
        getTile(4, 13).setType(TileType.WATER);
        getTile(3, 13).setType(TileType.WATER);
        getTile(3, 14).setType(TileType.WATER);
        getTile(3, 12).setType(TileType.WATER);
        getTile(4, 12).setType(TileType.WATER);
        getTile(4, 11).setType(TileType.WATER);
        getTile(3, 11).setType(TileType.WATER);
        getTile(2, 12).setType(TileType.WATER);
        getTile(2, 13).setType(TileType.WATER);
        getTile(6, 8).setType(TileType.WATER);
        getTile(7, 8).setType(TileType.WATER);
        getTile(8, 9).setType(TileType.WATER);
        getTile(8, 10).setType(TileType.WATER);
        getTile(8, 11).setType(TileType.WATER);
        getTile(9, 9).setType(TileType.WATER);
        getTile(7, 9).setType(TileType.WATER);
        getTile(6, 9).setType(TileType.WATER);
        getTile(6, 10).setType(TileType.WATER);
        getTile(7, 10).setType(TileType.WATER);
        getTile(5, 9).setType(TileType.WATER);
        getTile(2, 6).setType(TileType.WATER);
        getTile(2, 7).setType(TileType.WATER);
        getTile(3, 7).setType(TileType.WATER);
        getTile(3, 8).setType(TileType.WATER);
        getTile(2, 8).setType(TileType.WATER);
        getTile(2, 9).setType(TileType.WATER);
        getTile(1, 8).setType(TileType.WATER);
        getTile(1, 9).setType(TileType.WATER);
        getTile(1, 7).setType(TileType.WATER);
    }

    private void buildLakes2() {
        getTile(17, 15).setType(TileType.WATER);
    }

    private void buildMazeX() {
        startRow=1;
        startCol=1;
        endRow=17;
        endCol=7;
        getTile(0, 0).setType(TileType.WALL);
        getTile(0, 1).setType(TileType.WALL);
        getTile(0, 2).setType(TileType.WALL);
        getTile(0, 3).setType(TileType.WALL);
        getTile(0, 4).setType(TileType.WALL);
        getTile(0, 5).setType(TileType.WALL);
        getTile(0, 6).setType(TileType.WALL);
        getTile(0, 7).setType(TileType.WALL);
        getTile(0, 8).setType(TileType.WALL);
        getTile(0, 9).setType(TileType.WALL);
        getTile(0, 10).setType(TileType.WALL);
        getTile(0, 11).setType(TileType.WALL);
        getTile(0, 12).setType(TileType.WALL);
        getTile(0, 13).setType(TileType.WALL);
        getTile(0, 14).setType(TileType.WALL);
        getTile(0, 15).setType(TileType.WALL);
        getTile(0, 16).setType(TileType.WALL);
        getTile(0, 17).setType(TileType.WALL);
        getTile(0, 18).setType(TileType.WALL);
        getTile(0, 19).setType(TileType.WALL);
        getTile(1, 0).setType(TileType.WALL);
        getTile(1, 1).setType(TileType.SAND);
        getTile(1, 2).setType(TileType.WALL);
        getTile(1, 3).setType(TileType.SAND);
        getTile(1, 4).setType(TileType.SAND);
        getTile(1, 5).setType(TileType.SAND);
        getTile(1, 6).setType(TileType.SAND);
        getTile(1, 7).setType(TileType.SAND);
        getTile(1, 8).setType(TileType.SAND);
        getTile(1, 9).setType(TileType.SAND);
        getTile(1, 10).setType(TileType.SAND);
        getTile(1, 11).setType(TileType.SAND);
        getTile(1, 12).setType(TileType.SAND);
        getTile(1, 13).setType(TileType.SAND);
        getTile(1, 14).setType(TileType.WATER);
        getTile(1, 15).setType(TileType.SAND);
        getTile(1, 16).setType(TileType.SAND);
        getTile(1, 17).setType(TileType.SAND);
        getTile(1, 18).setType(TileType.SAND);
        getTile(1, 19).setType(TileType.WALL);
        getTile(2, 0).setType(TileType.WALL);
        getTile(2, 1).setType(TileType.SAND);
        getTile(2, 2).setType(TileType.WALL);
        getTile(2, 3).setType(TileType.SAND);
        getTile(2, 4).setType(TileType.WALL);
        getTile(2, 5).setType(TileType.SAND);
        getTile(2, 6).setType(TileType.WALL);
        getTile(2, 7).setType(TileType.WALL);
        getTile(2, 8).setType(TileType.WALL);
        getTile(2, 9).setType(TileType.SAND);
        getTile(2, 10).setType(TileType.WALL);
        getTile(2, 11).setType(TileType.WALL);
        getTile(2, 12).setType(TileType.WALL);
        getTile(2, 13).setType(TileType.WALL);
        getTile(2, 14).setType(TileType.WALL);
        getTile(2, 15).setType(TileType.SAND);
        getTile(2, 16).setType(TileType.WALL);
        getTile(2, 17).setType(TileType.WALL);
        getTile(2, 18).setType(TileType.SAND);
        getTile(2, 19).setType(TileType.WALL);
        getTile(3, 0).setType(TileType.WALL);
        getTile(3, 1).setType(TileType.SAND);
        getTile(3, 2).setType(TileType.SAND);
        getTile(3, 3).setType(TileType.SAND);
        getTile(3, 4).setType(TileType.WALL);
        getTile(3, 5).setType(TileType.SAND);
        getTile(3, 6).setType(TileType.WALL);
        getTile(3, 7).setType(TileType.SAND);
        getTile(3, 8).setType(TileType.WALL);
        getTile(3, 9).setType(TileType.SAND);
        getTile(3, 10).setType(TileType.WALL);
        getTile(3, 11).setType(TileType.SAND);
        getTile(3, 12).setType(TileType.WATER);
        getTile(3, 13).setType(TileType.SAND);
        getTile(3, 14).setType(TileType.SAND);
        getTile(3, 15).setType(TileType.SAND);
        getTile(3, 16).setType(TileType.WALL);
        getTile(3, 17).setType(TileType.SAND);
        getTile(3, 18).setType(TileType.SAND);
        getTile(3, 19).setType(TileType.WALL);
        getTile(4, 0).setType(TileType.WALL);
        getTile(4, 1).setType(TileType.WALL);
        getTile(4, 2).setType(TileType.WALL);
        getTile(4, 3).setType(TileType.WALL);
        getTile(4, 4).setType(TileType.WALL);
        getTile(4, 5).setType(TileType.WATER);
        getTile(4, 6).setType(TileType.WALL);
        getTile(4, 7).setType(TileType.SAND);
        getTile(4, 8).setType(TileType.WALL);
        getTile(4, 9).setType(TileType.SAND);
        getTile(4, 10).setType(TileType.WALL);
        getTile(4, 11).setType(TileType.SAND);
        getTile(4, 12).setType(TileType.WALL);
        getTile(4, 13).setType(TileType.SAND);
        getTile(4, 14).setType(TileType.WALL);
        getTile(4, 15).setType(TileType.WALL);
        getTile(4, 16).setType(TileType.WALL);
        getTile(4, 17).setType(TileType.WALL);
        getTile(4, 18).setType(TileType.SAND);
        getTile(4, 19).setType(TileType.WALL);
        getTile(5, 0).setType(TileType.WALL);
        getTile(5, 1).setType(TileType.WATER);
        getTile(5, 2).setType(TileType.WALL);
        getTile(5, 3).setType(TileType.WATER);
        getTile(5, 4).setType(TileType.WATER);
        getTile(5, 5).setType(TileType.SAND);
        getTile(5, 6).setType(TileType.SAND);
        getTile(5, 7).setType(TileType.SAND);
        getTile(5, 8).setType(TileType.WATER);
        getTile(5, 9).setType(TileType.SAND);
        getTile(5, 10).setType(TileType.SAND);
        getTile(5, 11).setType(TileType.SAND);
        getTile(5, 12).setType(TileType.SAND);
        getTile(5, 13).setType(TileType.SAND);
        getTile(5, 14).setType(TileType.WALL);
        getTile(5, 15).setType(TileType.SAND);
        getTile(5, 16).setType(TileType.SAND);
        getTile(5, 17).setType(TileType.SAND);
        getTile(5, 18).setType(TileType.SAND);
        getTile(5, 19).setType(TileType.WALL);
        getTile(6, 0).setType(TileType.WALL);
        getTile(6, 1).setType(TileType.WATER);
        getTile(6, 2).setType(TileType.WALL);
        getTile(6, 3).setType(TileType.WATER);
        getTile(6, 4).setType(TileType.WALL);
        getTile(6, 5).setType(TileType.SAND);
        getTile(6, 6).setType(TileType.WALL);
        getTile(6, 7).setType(TileType.WALL);
        getTile(6, 8).setType(TileType.WALL);
        getTile(6, 9).setType(TileType.WALL);
        getTile(6, 10).setType(TileType.WALL);
        getTile(6, 11).setType(TileType.WATER);
        getTile(6, 12).setType(TileType.WALL);
        getTile(6, 13).setType(TileType.WALL);
        getTile(6, 14).setType(TileType.WALL);
        getTile(6, 15).setType(TileType.SAND);
        getTile(6, 16).setType(TileType.WALL);
        getTile(6, 17).setType(TileType.WALL);
        getTile(6, 18).setType(TileType.WALL);
        getTile(6, 19).setType(TileType.WALL);
        getTile(7, 0).setType(TileType.WALL);
        getTile(7, 1).setType(TileType.WATER);
        getTile(7, 2).setType(TileType.WATER);
        getTile(7, 3).setType(TileType.WATER);
        getTile(7, 4).setType(TileType.WALL);
        getTile(7, 5).setType(TileType.SAND);
        getTile(7, 6).setType(TileType.WALL);
        getTile(7, 7).setType(TileType.SAND);
        getTile(7, 8).setType(TileType.SAND);
        getTile(7, 9).setType(TileType.SAND);
        getTile(7, 10).setType(TileType.WALL);
        getTile(7, 11).setType(TileType.WATER);
        getTile(7, 12).setType(TileType.WATER);
        getTile(7, 13).setType(TileType.SAND);
        getTile(7, 14).setType(TileType.WATER);
        getTile(7, 15).setType(TileType.SAND);
        getTile(7, 16).setType(TileType.SAND);
        getTile(7, 17).setType(TileType.SAND);
        getTile(7, 18).setType(TileType.SAND);
        getTile(7, 19).setType(TileType.WALL);
        getTile(8, 0).setType(TileType.WALL);
        getTile(8, 1).setType(TileType.SAND);
        getTile(8, 2).setType(TileType.WALL);
        getTile(8, 3).setType(TileType.WALL);
        getTile(8, 4).setType(TileType.WALL);
        getTile(8, 5).setType(TileType.WALL);
        getTile(8, 6).setType(TileType.WALL);
        getTile(8, 7).setType(TileType.SAND);
        getTile(8, 8).setType(TileType.WALL);
        getTile(8, 9).setType(TileType.WALL);
        getTile(8, 10).setType(TileType.WALL);
        getTile(8, 11).setType(TileType.WATER);
        getTile(8, 12).setType(TileType.WALL);
        getTile(8, 13).setType(TileType.SAND);
        getTile(8, 14).setType(TileType.WALL);
        getTile(8, 15).setType(TileType.WALL);
        getTile(8, 16).setType(TileType.SAND);
        getTile(8, 17).setType(TileType.WALL);
        getTile(8, 18).setType(TileType.SAND);
        getTile(8, 19).setType(TileType.WALL);
        getTile(9, 0).setType(TileType.WALL);
        getTile(9, 1).setType(TileType.WATER);
        getTile(9, 2).setType(TileType.WALL);
        getTile(9, 3).setType(TileType.WATER);
        getTile(9, 4).setType(TileType.SAND);
        getTile(9, 5).setType(TileType.SAND);
        getTile(9, 6).setType(TileType.WATER);
        getTile(9, 7).setType(TileType.SAND);
        getTile(9, 8).setType(TileType.SAND);
        getTile(9, 9).setType(TileType.SAND);
        getTile(9, 10).setType(TileType.WATER);
        getTile(9, 11).setType(TileType.WATER);
        getTile(9, 12).setType(TileType.WALL);
        getTile(9, 13).setType(TileType.SAND);
        getTile(9, 14).setType(TileType.WALL);
        getTile(9, 15).setType(TileType.SAND);
        getTile(9, 16).setType(TileType.SAND);
        getTile(9, 17).setType(TileType.WALL);
        getTile(9, 18).setType(TileType.SAND);
        getTile(9, 19).setType(TileType.WALL);
        getTile(10, 0).setType(TileType.WALL);
        getTile(10, 1).setType(TileType.SAND);
        getTile(10, 2).setType(TileType.WALL);
        getTile(10, 3).setType(TileType.WALL);
        getTile(10, 4).setType(TileType.WALL);
        getTile(10, 5).setType(TileType.WALL);
        getTile(10, 6).setType(TileType.WALL);
        getTile(10, 7).setType(TileType.WALL);
        getTile(10, 8).setType(TileType.WALL);
        getTile(10, 9).setType(TileType.SAND);
        getTile(10, 10).setType(TileType.WALL);
        getTile(10, 11).setType(TileType.WATER);
        getTile(10, 12).setType(TileType.WATER);
        getTile(10, 13).setType(TileType.SAND);
        getTile(10, 14).setType(TileType.WALL);
        getTile(10, 15).setType(TileType.SAND);
        getTile(10, 16).setType(TileType.WALL);
        getTile(10, 17).setType(TileType.WALL);
        getTile(10, 18).setType(TileType.WALL);
        getTile(10, 19).setType(TileType.WALL);
        getTile(11, 0).setType(TileType.WALL);
        getTile(11, 1).setType(TileType.WATER);
        getTile(11, 2).setType(TileType.WATER);
        getTile(11, 3).setType(TileType.WATER);
        getTile(11, 4).setType(TileType.WATER);
        getTile(11, 5).setType(TileType.WATER);
        getTile(11, 6).setType(TileType.SAND);
        getTile(11, 7).setType(TileType.SAND);
        getTile(11, 8).setType(TileType.WATER);
        getTile(11, 9).setType(TileType.SAND);
        getTile(11, 10).setType(TileType.WALL);
        getTile(11, 11).setType(TileType.WATER);
        getTile(11, 12).setType(TileType.WALL);
        getTile(11, 13).setType(TileType.SAND);
        getTile(11, 14).setType(TileType.WATER);
        getTile(11, 15).setType(TileType.SAND);
        getTile(11, 16).setType(TileType.SAND);
        getTile(11, 17).setType(TileType.SAND);
        getTile(11, 18).setType(TileType.SAND);
        getTile(11, 19).setType(TileType.WALL);
        getTile(12, 0).setType(TileType.WALL);
        getTile(12, 1).setType(TileType.WALL);
        getTile(12, 2).setType(TileType.WALL);
        getTile(12, 3).setType(TileType.WATER);
        getTile(12, 4).setType(TileType.WALL);
        getTile(12, 5).setType(TileType.WALL);
        getTile(12, 6).setType(TileType.WALL);
        getTile(12, 7).setType(TileType.WALL);
        getTile(12, 8).setType(TileType.WALL);
        getTile(12, 9).setType(TileType.SAND);
        getTile(12, 10).setType(TileType.WALL);
        getTile(12, 11).setType(TileType.WATER);
        getTile(12, 12).setType(TileType.WALL);
        getTile(12, 13).setType(TileType.SAND);
        getTile(12, 14).setType(TileType.WALL);
        getTile(12, 15).setType(TileType.WALL);
        getTile(12, 16).setType(TileType.WALL);
        getTile(12, 17).setType(TileType.WALL);
        getTile(12, 18).setType(TileType.SAND);
        getTile(12, 19).setType(TileType.WALL);
        getTile(13, 0).setType(TileType.WALL);
        getTile(13, 1).setType(TileType.WATER);
        getTile(13, 2).setType(TileType.WATER);
        getTile(13, 3).setType(TileType.WATER);
        getTile(13, 4).setType(TileType.SAND);
        getTile(13, 5).setType(TileType.SAND);
        getTile(13, 6).setType(TileType.SAND);
        getTile(13, 7).setType(TileType.WATER);
        getTile(13, 8).setType(TileType.SAND);
        getTile(13, 9).setType(TileType.SAND);
        getTile(13, 10).setType(TileType.WALL);
        getTile(13, 11).setType(TileType.WATER);
        getTile(13, 12).setType(TileType.WALL);
        getTile(13, 13).setType(TileType.SAND);
        getTile(13, 14).setType(TileType.SAND);
        getTile(13, 15).setType(TileType.SAND);
        getTile(13, 16).setType(TileType.WALL);
        getTile(13, 17).setType(TileType.SAND);
        getTile(13, 18).setType(TileType.SAND);
        getTile(13, 19).setType(TileType.WALL);
        getTile(14, 0).setType(TileType.WALL);
        getTile(14, 1).setType(TileType.SAND);
        getTile(14, 2).setType(TileType.WALL);
        getTile(14, 3).setType(TileType.WATER);
        getTile(14, 4).setType(TileType.WALL);
        getTile(14, 5).setType(TileType.WALL);
        getTile(14, 6).setType(TileType.WALL);
        getTile(14, 7).setType(TileType.WALL);
        getTile(14, 8).setType(TileType.WALL);
        getTile(14, 9).setType(TileType.SAND);
        getTile(14, 10).setType(TileType.WALL);
        getTile(14, 11).setType(TileType.WATER);
        getTile(14, 12).setType(TileType.WALL);
        getTile(14, 13).setType(TileType.SAND);
        getTile(14, 14).setType(TileType.WALL);
        getTile(14, 15).setType(TileType.WALL);
        getTile(14, 16).setType(TileType.WALL);
        getTile(14, 17).setType(TileType.SAND);
        getTile(14, 18).setType(TileType.WALL);
        getTile(14, 19).setType(TileType.WALL);
        getTile(15, 0).setType(TileType.WALL);
        getTile(15, 1).setType(TileType.SAND);
        getTile(15, 2).setType(TileType.WALL);
        getTile(15, 3).setType(TileType.WATER);
        getTile(15, 4).setType(TileType.WALL);
        getTile(15, 5).setType(TileType.WATER);
        getTile(15, 6).setType(TileType.WATER);
        getTile(15, 7).setType(TileType.WATER);
        getTile(15, 8).setType(TileType.WALL);
        getTile(15, 9).setType(TileType.SAND);
        getTile(15, 10).setType(TileType.SAND);
        getTile(15, 11).setType(TileType.SAND);
        getTile(15, 12).setType(TileType.WATER);
        getTile(15, 13).setType(TileType.SAND);
        getTile(15, 14).setType(TileType.SAND);
        getTile(15, 15).setType(TileType.SAND);
        getTile(15, 16).setType(TileType.WALL);
        getTile(15, 17).setType(TileType.SAND);
        getTile(15, 18).setType(TileType.SAND);
        getTile(15, 19).setType(TileType.WALL);
        getTile(16, 0).setType(TileType.WALL);
        getTile(16, 1).setType(TileType.SAND);
        getTile(16, 2).setType(TileType.WALL);
        getTile(16, 3).setType(TileType.WATER);
        getTile(16, 4).setType(TileType.WALL);
        getTile(16, 5).setType(TileType.WATER);
        getTile(16, 6).setType(TileType.WALL);
        getTile(16, 7).setType(TileType.WATER);
        getTile(16, 8).setType(TileType.WALL);
        getTile(16, 9).setType(TileType.WATER);
        getTile(16, 10).setType(TileType.WALL);
        getTile(16, 11).setType(TileType.WALL);
        getTile(16, 12).setType(TileType.WALL);
        getTile(16, 13).setType(TileType.WALL);
        getTile(16, 14).setType(TileType.WALL);
        getTile(16, 15).setType(TileType.WATER);
        getTile(16, 16).setType(TileType.WALL);
        getTile(16, 17).setType(TileType.WALL);
        getTile(16, 18).setType(TileType.SAND);
        getTile(16, 19).setType(TileType.WALL);
        getTile(17, 0).setType(TileType.WALL);
        getTile(17, 1).setType(TileType.WATER);
        getTile(17, 2).setType(TileType.WALL);
        getTile(17, 3).setType(TileType.WALL);
        getTile(17, 4).setType(TileType.WALL);
        getTile(17, 5).setType(TileType.WATER);
        getTile(17, 6).setType(TileType.WALL);
        getTile(17, 7).setType(TileType.SAND);
        getTile(17, 8).setType(TileType.WALL);
        getTile(17, 9).setType(TileType.WATER);
        getTile(17, 10).setType(TileType.WALL);
        getTile(17, 11).setType(TileType.SAND);
        getTile(17, 12).setType(TileType.SAND);
        getTile(17, 13).setType(TileType.SAND);
        getTile(17, 14).setType(TileType.WALL);
        getTile(17, 15).setType(TileType.SAND);
        getTile(17, 16).setType(TileType.SAND);
        getTile(17, 17).setType(TileType.SAND);
        getTile(17, 18).setType(TileType.SAND);
        getTile(17, 19).setType(TileType.WALL);
        getTile(18, 0).setType(TileType.WALL);
        getTile(18, 1).setType(TileType.WATER);
        getTile(18, 2).setType(TileType.WATER);
        getTile(18, 3).setType(TileType.WATER);
        getTile(18, 4).setType(TileType.WATER);
        getTile(18, 5).setType(TileType.WATER);
        getTile(18, 6).setType(TileType.WATER);
        getTile(18, 7).setType(TileType.SAND);
        getTile(18, 8).setType(TileType.SAND);
        getTile(18, 9).setType(TileType.SAND);
        getTile(18, 10).setType(TileType.SAND);
        getTile(18, 11).setType(TileType.SAND);
        getTile(18, 12).setType(TileType.WALL);
        getTile(18, 13).setType(TileType.SAND);
        getTile(18, 14).setType(TileType.SAND);
        getTile(18, 15).setType(TileType.SAND);
        getTile(18, 16).setType(TileType.WALL);
        getTile(18, 17).setType(TileType.WALL);
        getTile(18, 18).setType(TileType.SAND);
        getTile(18, 19).setType(TileType.WALL);
        getTile(19, 0).setType(TileType.WALL);
        getTile(19, 1).setType(TileType.WALL);
        getTile(19, 2).setType(TileType.WALL);
        getTile(19, 3).setType(TileType.WALL);
        getTile(19, 4).setType(TileType.WALL);
        getTile(19, 5).setType(TileType.WALL);
        getTile(19, 6).setType(TileType.WALL);
        getTile(19, 7).setType(TileType.WALL);
        getTile(19, 8).setType(TileType.WALL);
        getTile(19, 9).setType(TileType.WALL);
        getTile(19, 10).setType(TileType.WALL);
        getTile(19, 11).setType(TileType.WALL);
        getTile(19, 12).setType(TileType.WALL);
        getTile(19, 13).setType(TileType.WALL);
        getTile(19, 14).setType(TileType.WALL);
        getTile(19, 15).setType(TileType.WALL);
        getTile(19, 16).setType(TileType.WALL);
        getTile(19, 17).setType(TileType.WALL);
        getTile(19, 18).setType(TileType.WALL);
        getTile(19, 19).setType(TileType.WALL);

    }
}
