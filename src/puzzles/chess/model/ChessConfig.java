package puzzles.chess.model;

import puzzles.common.solver.Configuration;
import puzzles.strings.StringsConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

// TODO: implement your ChessConfig for the common solver

/**
 * description TBD
 *
 * @author Madeline Mariano mam5090
 */
public class ChessConfig implements Configuration {
    private static int row;
    private static int col;
    private final char[][] game;


    /**
     * Creates a new ChessConfig board based off of given file
     * @param filename file being iterated over to create new board
     * @throws IOException if file is not found
     */
    public ChessConfig(String filename) throws IOException {
        try(BufferedReader in = new BufferedReader((new FileReader(filename)))){
            String[] fields = in.readLine().split("\\s+");
            row = Integer.parseInt(fields[0]);
            col = Integer.parseInt(fields[1]);
            this.game = new char[row][col];
            for(int r = 0; r< row; r++){
                fields = in.readLine().split("\\s+");
                for(int c = 0; c<col; c++){
                    game[r][c] = fields[c].charAt(0);
                }
            }
        }
    }

    /**
     * Copy constructor for getNeighbors
     * @param other ChessConfig to be copied
     */
    public ChessConfig(ChessConfig other){
        this.game = new char[row][col];
        for(int r = 0; r< row; r++){
            if (col >= 0) System.arraycopy(other.game[r], 0, game[r], 0, col);
        }
    }

    /**
     * Checks whether current Config is a solution (only one piece is remaining)
     * @return True if solution, false if not
     */
    @Override
    public boolean isSolution() {
        int pieces = 0;
        for(int r = 0; r< row; r++){
            for(int c = 0; c<col; c++){
                if(game[r][c] != '.'){
                    pieces++;
                }
            }
        }
        return pieces == 1;
    }

    public Collection<Configuration> pawnMoves(int row, int col){
        ArrayList<Configuration> result = new ArrayList<>();
        if(row == 0){
            return result;
        }

        if(col == 0){
            if(game[row+1][col+1] != '.'){
                ChessConfig newC = new ChessConfig(this);
                newC.game[row+1][col+1] = 'P';
                result.add(newC);
            }
        }
        else if(col == ChessConfig.col){
            if(game[row+1][col-1] != '.'){
                ChessConfig newC = new ChessConfig(this);
                newC.game[row+1][col+1] = 'P';
                result.add(newC);
            }
        }
        else{
            ChessConfig newC = new ChessConfig(this);
            if(game[row+1][col+1] != '.'){
                newC.game[row+1][col+1] = 'P';
                result.add(newC);
            }
            if(game[row+1][col-1] != '.'){
                newC.game[row+1][col-1] = 'P';
                result.add(newC);
            }
        }
        return result;
    }

//    public Collection<Configuration> knightMoves(int row, int col){
//    }
//
//    public Collection<Configuration> rookMoves(int row, int col){
//    }
//
//    public Collection<Configuration> bishopMoves(int row, int col){
//    }
//
//    public Collection<Configuration> kingMoves(int row, int col){
//    }
//    public Collection<Configuration> queenMoves(int row, int col){
//    }

    /**
     * Gets the Neighbors of the current configuration
     * @return ArrayList of ChessConfigs of all neighbors.
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        ArrayList<Configuration> result = new ArrayList<>();
//        for(int r = 0; r< row; r++){
//            for(int c = 0; c<col; c++){
//                if(game[r][c] == 'B'){
//                }
//                else if(game[r][c] == 'K'){
//                }
//                else if(game[r][c] == 'N'){
//                }
//                else if(game[r][c] == 'P'){
//                    result.addAll(pawnMoves(r,c));
//                }
//                else if(game[r][c] == 'Q'){
//                }
//                else if(game[r][c] == 'R'){
//                }
//            }
//        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(int r = 0; r< row; r++){
            for(int c = 0; c<col; c++){
                result.append(game[r][c]);
                result.append(" ");
            }
            result.append(System.lineSeparator());
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ChessConfig ch) {
            return this.game == ch.game;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
