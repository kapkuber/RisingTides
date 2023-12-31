package tides;

import java.util.*;

/**
 * This class contains methods that provide information about select terrains 
 * using 2D arrays. Uses floodfill to flood given maps and uses that 
 * information to understand the potential impacts. 
 * Instance Variables:
 *  - a double array for all the heights for each cell
 *  - a GridLocation array for the sources of water on empty terrain 
 * 
 * @author Original Creator Keith Scharz (NIFTY STANFORD) 
 * @author Vian Miranda (Rutgers University)
 */
public class RisingTides {

    // Instance variables
    private double[][] terrain;     // an array for all the heights for each cell
    private GridLocation[] sources; // an array for the sources of water on empty terrain 

    /**
     * DO NOT EDIT!
     * Constructor for RisingTides.
     * @param terrain passes in the selected terrain 
     */
    public RisingTides(Terrain terrain) {
        this.terrain = terrain.heights;
        this.sources = terrain.sources;
    }

    /**
     * Find the lowest and highest point of the terrain and output it.
     * 
     * @return double[][], with index 0 and index 1 being the lowest and 
     * highest points of the terrain, respectively
     */
    public double[] elevationExtrema() {
        double[] min_max = new double[2];
        min_max[0] = Double.MAX_VALUE;
        min_max[1] = Double.MIN_VALUE;
        for (int i = 0; i < terrain.length; i++) {
            for (int j = 0; j < terrain[i].length; j++) {
                double currentElevation = terrain[i][j];
                min_max[0] = Math.min(min_max[0], currentElevation);
                min_max[1] = Math.max(min_max[1], currentElevation);
            }
        }
        return min_max;
    }

    /**
     * Implement the floodfill algorithm using the provided terrain and sources.
     * 
     * All water originates from the source GridLocation. If the height of the 
     * water is greater than that of the neighboring terrain, flood the cells. 
     * Repeat iteratively till the neighboring terrain is higher than the water 
     * height.
     * 
     * 
     * @param height of the water
     * @return boolean[][], where flooded cells are true, otherwise false
     */
    public boolean[][] floodedRegionsIn(double height) {
        boolean[][] flooded = new boolean[terrain.length][terrain[0].length];
        ArrayList<GridLocation> gridLocations = new ArrayList<>();
    
        int[][] directions = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
    
        for(GridLocation newsource : sources) {
            gridLocations.add(newsource);
            flooded[newsource.row][newsource.col] = true;
        }
    
        while(!gridLocations.isEmpty()) {
            GridLocation currentLocation = gridLocations.remove(0);
    
            for(int[] dir : directions) {
                int neighborX = currentLocation.row + dir[0];
                int neighborY = currentLocation.col + dir[1];
    
                if(neighborX >= 0 && neighborY >= 0 && neighborX < terrain.length && neighborY < terrain[0].length &&
                    terrain[neighborX][neighborY] <= height && !flooded[neighborX][neighborY]) {
                    flooded[neighborX][neighborY] = true;
                    gridLocations.add(new GridLocation(neighborX, neighborY));
                }
            }
        }
        return flooded;
    }

    /**
     * Checks if a given cell is flooded at a certain water height.
     * 
     * @param height of the water
     * @param cell location 
     * @return boolean, true if cell is flooded, otherwise false
     */
    public boolean isFlooded(double height, GridLocation cell) {
        boolean[][] flood = floodedRegionsIn(height);
        if(flood[cell.row][cell.col]==true) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Given the water height and a GridLocation find the difference between 
     * the chosen cells height and the water height.
     * 
     * If the return value is negative, the Driver will display "meters below"
     * If the return value is positive, the Driver will display "meters above"
     * The value displayed will be positive.
     * 
     * @param height of the water
     * @param cell location
     * @return double, representing how high/deep a cell is above/below water
     */
    public double heightAboveWater(double height, GridLocation cell) {
        return terrain[cell.row][cell.col] - height;
    }

    /**
     * Total land available (not underwater) given a certain water height.
     * 
     * @param height of the water
     * @return int, representing every cell above water
     */
    public int totalVisibleLand(double height) {
        int count = 0;
        boolean flooded[][] = floodedRegionsIn(height);
        for(int i = 0; i < terrain.length; i++) {
            for(int j = 0; j < terrain[0].length; j++){
                if(flooded[i][j] != true) {
                    count++;
                }
            }
        }
        return count;
    } 


    /**
     * Given 2 heights, find the difference in land available at each height. 
     * 
     * If the return value is negative, the Driver will display "Will gain"
     * If the return value is positive, the Driver will display "Will lose"
     * The value displayed will be positive.
     * 
     * @param height of the water
     * @param newHeight the future height of the water
     * @return int, representing the amount of land lost or gained
     */
    public int landLost(double height, double newHeight) {
        int previousLand = totalVisibleLand(height);
        int newLand = totalVisibleLand(newHeight);
        return previousLand-newLand;
    }

    /**
     * Count the total number of islands on the flooded terrain.
     * 
     * Parts of the terrain are considered "islands" if they are completely 
     * surround by water in all 8-directions. Should there be a direction (ie. 
     * left corner) where a certain piece of land is connected to another 
     * landmass, this should be considered as one island. A better example 
     * would be if there were two landmasses connected by one cell. Although 
     * seemingly two islands, after further inspection it should be realized 
     * this is one single island. Only if this connection were to be removed 
     * (height of water increased) should these two landmasses be considered 
     * two separate islands.
     * 
     * @param height of the water
     * @return int, representing the total number of islands
     */
    public int numOfIslands(double height) {   
    int rows = terrain.length;
    int cols = terrain[0].length;
    int[][] directions = {{1, 0}, {0, -1}, {1, 1}, {1, -1}};
    WeightedQuickUnionUF uf = new WeightedQuickUnionUF(rows, cols);
    boolean[][] Floods = floodedRegionsIn(height);
    for(int i = 0; i < rows; i++) {
        for(int j = 0; j < cols; j++) {
            for(int[] dir : directions) {
                int ni = i + dir[0];
                int nj = j + dir[1];
                if (ni >= 0 && nj >= 0 && ni < rows && nj < cols && Floods[i][j] != true && Floods[ni][nj] != true) {
                    uf.union(new GridLocation(i, j), new GridLocation(ni, nj));
                }
            }
        }
    }
    HashSet<GridLocation> uniqueNodes = new HashSet<GridLocation>();
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (Floods[i][j] != true) {
                uniqueNodes.add(uf.find(new GridLocation(i, j)));
            }
        }
    }
    return uniqueNodes.size();
}
}
