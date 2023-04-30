package puzzles.hoppers.model;

import puzzles.common.solver.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Stores data needed to solve a Hoppers puzzle
 *
 * @author Nathan Klein nek7125@rit.edu
 */
public class HoppersConfig implements Configuration{

    private char[][] grid;
    private static int rows;
    private static int cols;

    /**
     * creates a new HoppersConfig from a file
     * @param filename file
     * @throws IOException fileNotFoundException
     */
    public HoppersConfig(String filename) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            String[] dims = in.readLine().split(" ");
            rows = Integer.parseInt(dims[0]);
            cols = Integer.parseInt(dims[1]);
            grid = new char[rows][cols];
            // populates grid w/ thingies
            for (int r=0; r<rows; r++) { // for each row
                String[] row = in.readLine().split(" ");
                for (int c=0; c<cols; c++) { // for each column in the row
                    grid[r][c] = row[c].charAt(0);
                }
            }
        }
    }

    /**
     * copy Constructor to create a HoppersConfig with a frog moved
     * @param other HoppersConfig to copy
     * @param fromRow row to jump from
     * @param fromCol column to jump from
     * @param toRow row to jump to
     * @param toCol column to jump to
     */
    public HoppersConfig(HoppersConfig other, int fromRow, int fromCol, int toRow, int toCol) {
        this.grid = new char[rows][cols];
        for (int r=0; r<rows; r++) {
            System.arraycopy(other.grid[r], 0, this.grid[r], 0, cols);
        }
        char frog = this.grid[fromRow][fromCol];
        this.grid[fromRow][fromCol] = '.';                      // from cell
        this.grid[(fromRow+toRow)/2][(fromCol+toCol)/2] = '.';  // middle cell
        this.grid[toRow][toCol] = frog;                         // to cell
    }

    /**
     * checks if the HoppersConfig is a valid solution
     * @return boolean
     */
    @Override
    public boolean isSolution() {
        for (int r=0; r<rows; r++) { // for each row
            for (int c=0; c<cols; c++) { // for each column in the row
                if (grid[r][c] == 'G') { return false; }
            }
        }
        return true;
    }

    /**
     * Returns a Collection of all possible moves that can be made from this Config
     * @return Collection<Configuration> all possible moves
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        ArrayList<Configuration> neighbors = new ArrayList<>();
        for (int r=0; r<rows; r++) { // for each row
            for (int c=0; c<cols; c++) { // for each column in the row
                if (grid[r][c] == 'G' || grid[r][c] == 'R') {
                    neighbors.addAll(getMoves(r,c));
                }
            }
        }
        return neighbors;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int r=0; r<rows; r++) {
            char[] row = grid[r];
            for (int c=0; c<cols; c++) {
                result.append(row[c]);
                result.append(' ');
            }
            if (r<rows-1) { result.append(System.lineSeparator()); }
        }
        return result.toString();
    }

    /**
     * Displays the Hoppers game board with row and column indicators
     * @return String
     */
    public String getDisplay() {
        StringBuilder result = new StringBuilder(" ");

        result.append(" ");
        for (int c=0; c<cols; ++c) {
            result.append(c).append(" ");
        }

        result.append(System.lineSeparator()).append("  ");
        result.append(("-").repeat(Math.max(0, cols  * 2-1)));
        result.append(System.lineSeparator());

        for (int r=0; r<rows; ++r) {
            result.append(r).append('|');
            for (int c = 0; c<cols; ++c) {
                if (c != cols-1) {
                    result.append(grid[r][c]).append(" ");
                } else {
                    result.append(grid[r][c]).append(System.lineSeparator());
                }
            }
        }

        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof HoppersConfig c) {
            return Arrays.deepEquals(this.grid, c.grid);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(grid);
    }

    /**
     * checks if there is a frog present at the given coordinates
     * @param r row
     * @param c column
     * @return boolean
     */
    public boolean isFrog(int r, int c) {
        return (grid[r][c] == 'G') || (grid[r][c] == 'R');
    }

    /**
     * returns the number of rows in the hoppers grid
     * @return int
     */
    public int getRows() {
        return rows;
    }

    /**
     * returns the number of columns in the hoppers grid
     * @return int
     */
    public int getCols() {
        return cols;
    }

    /**
     * gets the char value on the hoppers game board at the given coordinates
     * @param row row
     * @param column column
     * @return char
     */
    public char getCell(int row, int column) {
        return grid[row][column];
    }

    /**
     * Returns a Collection of all possible moves that can be made
     * @return Collection<Configuration> all possible moves
     */
    public Collection<Configuration> getMoves(int r, int c) {
        ArrayList<Configuration> moves = new ArrayList<>();
        char NE = '*';
        char SE = '*';
        char NW = '*';
        char SW = '*';
        char N = '*';
        char S = '*';
        char E = '*';
        char W = '*';
        if (c > 1) {
            if (c > 3) {
                W = grid[r][c-4]; // square to jump to
                char mid = grid[r][c-2]; // square you're jumping over
                if (W == '.' && mid == 'G') {
                    moves.add(new HoppersConfig(this, r, c, r, c-4));
                }
            }
            if (r > 1) {
                NW = grid[r-2][c-2];
                char NWMID = grid[r-1][c-1];
                if (NW == '.' && NWMID == 'G') {
                    moves.add(new HoppersConfig(this, r, c, r-2, c-2));
                }
            }
        }
        if (r > 1) {
            if (r > 3) {
                N = grid[r-4][c];
                char mid = grid[r-2][c];
                if (N == '.' && mid == 'G') {
                    moves.add(new HoppersConfig(this, r, c, r-4, c));
                }
            }
            if (c < cols-2) {
                NE = grid[r-2][c+2];
                char NEMID = grid[r-1][c+1];
                if (NE == '.' && NEMID == 'G') {
                    moves.add(new HoppersConfig(this, r, c, r-2, c+2));
                }
            }
        }
        if (c < cols-2) {
            if (c < cols-4) {
                E = grid[r][c+4];
                char mid = grid[r][c+2];
                if (E == '.' && mid == 'G') {
                    moves.add(new HoppersConfig(this, r, c, r, c+4));
                }
            }
            if (r < rows-2) {
                SE = grid[r+2][c+2];
                char SEMID = grid[r+1][c+1];
                if (SE == '.' && SEMID == 'G') {
                    moves.add(new HoppersConfig(this, r, c, r+2, c+2));
                }
            }
        }
        if (r < rows-2) {
            if (r < rows-4) {
                S = grid[r+4][c];
                char mid = grid[r+2][c];
                if (S == '.' && mid == 'G') {
                    moves.add(new HoppersConfig(this, r, c, r+4, c));
                }
            }
            if (c > 1) {
                SW = grid[r+2][c-2];
                char SWMID = grid[r+1][c-1];
                if (SW == '.' && SWMID == 'G') {
                    moves.add(new HoppersConfig(this, r, c, r+2, c-2));
                }
            }
        }
        return moves;
    }

}
