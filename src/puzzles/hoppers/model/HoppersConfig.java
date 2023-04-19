package puzzles.hoppers.model;

import puzzles.common.solver.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class HoppersConfig implements Configuration{

    private char[][] grid;
    private static int rows;
    private static int cols;

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

    @Override
    public boolean isSolution() {
        for (int r=0; r<rows; r++) { // for each row
            char[] row = grid[r];
            for (int c=0; c<cols; c++) { // for each column in the row
                if (row[c] == 'G') {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Collection<Configuration> getNeighbors() {
        ArrayList<Configuration> neighbors = new ArrayList<>();
        for (int r=0; r<rows; r++) { // for each row
            char[] row = grid[r];
            for (int c=0; c<cols; c++) { // for each column in the row
                if (row[c] == 'G' || row[c] == 'R') {
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
            result.append(System.lineSeparator());
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HoppersConfig that = (HoppersConfig) o;

        return Arrays.deepEquals(grid, that.grid);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(grid);
    }

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
                W = grid[r][c-4];
                char mid = grid[r][c-2];
                if (W == '.' && (mid == 'G' || mid == 'R')) {
                    moves.add(new HoppersConfig(this, r, c, r, c-4));
                }
            }
            if (r > 1) {
                NW = grid[r-2][c-2];
                char NWMID = grid[r-1][c-1];
                if (NW == '.' && (NWMID == 'G' || NWMID == 'R')) {
                    moves.add(new HoppersConfig(this, r, c, r-2, c-2));
                }
            }
        }
        if (r > 1) {
            if (r > 3) {
                N = grid[r-4][c];
                char mid = grid[r-2][c];
                if (N == '.' && (mid == 'G' || mid == 'R')) {
                    moves.add(new HoppersConfig(this, r, c, r-4, c));
                }
            }
            if (c < cols-2) {
                NE = grid[r-2][c+2];
                char NEMID = grid[r-1][c+1];
                if (NE == '.' && (NEMID == 'G' || NEMID == 'R')) {
                    moves.add(new HoppersConfig(this, r, c, r-2, c+2));
                }
            }
        }
        if (c < cols-2) {
            if (c < cols-4) {
                E = grid[r][c+4];
                char mid = grid[r][c+2];
                if (E == '.' && (mid == 'G' || mid == 'R')) {
                    moves.add(new HoppersConfig(this, r, c, r, c+4));
                }
            }
            if (r < rows-2) {
                SE = grid[r+2][c+2];
                char SEMID = grid[r+1][c+1];
                if (SE == '.' && (SEMID == 'G' || SEMID == 'R')) {
                    moves.add(new HoppersConfig(this, r, c, r+2, c+2));
                }
            }
        }
        if (r < rows-2) {
            if (r < rows-4) {
                S = grid[r+4][c];
                char mid = grid[r+2][c];
                if (S == '.' && (mid == 'G' || mid == 'R')) {
                    moves.add(new HoppersConfig(this, r, c, r+4, c));
                }
            }
            if (c > 1) {
                SW = grid[r+2][c-2];
                char SWMID = grid[r+1][c-1];
                if (SW == '.' && (SWMID == 'G' || SWMID == 'R')) {
                    moves.add(new HoppersConfig(this, r, c, r+2, c-2));
                }
            }
        }
        return moves;
    }

}
