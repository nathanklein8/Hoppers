package puzzles.chess.model;

import puzzles.common.solver.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


/**
 * Creates a Chess board with given pieces in their grid based off of given file, and creates all possible neighbors
 * for given board when asked.
 *
 * @author Madeline Mariano mam5090
 */
public class ChessConfig implements Configuration {
    /** number of rows on the board*/
    private static int row;
    /** number of columns on the board*/
    private static int col;
    /** 2D array grid representing the chess board game*/
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
     * Helper function for pawn movements
     * @param row current row where pawn is located
     * @param col current col where pawn is located
     * @return Collection of ChessConfigs of all possible valid pawn moves
     */
    public Collection<Configuration> pawnMoves(int row, int col){
        ArrayList<Configuration> result = new ArrayList<>();
        ChessConfig newC = new ChessConfig(this);
        if(row == 0){
            return result;
        }

        if(col == 0){
            if(game[row-1][col+1] != '.'){
                newC.game[row-1][col+1] = 'P';
                newC.game[row][col] = '.';
                result.add(newC);
            }
        }
        else if(col == ChessConfig.col-1){
            if(game[row-1][col-1] != '.'){
                newC.game[row-1][col-1] = 'P';
                newC.game[row][col] = '.';
                result.add(newC);
            }
        }
        else{
            if(game[row-1][col+1] != '.'){
                newC.game[row-1][col+1] = 'P';
                newC.game[row][col] = '.';
                result.add(newC);
            }
            ChessConfig newCh = new ChessConfig(this);
            if(game[row-1][col-1] != '.'){
                newCh.game[row-1][col-1] = 'P';
                newC.game[row][col] = '.';
                result.add(newCh);
            }
        }
        return result;
    }

    /**
     * Helper function for king movements
     * @param row current row where king is located
     * @param col current col where king is located
     * @return Collection of ChessConfigs of all possible valid king moves
     */
    public Collection<Configuration> kingMoves(int row, int col) {
        ArrayList<Configuration> result = new ArrayList<>();
        if (row == 0) {
            if (game[row + 1][col] != '.') { // down one
                ChessConfig newCh = new ChessConfig(this);
                newCh.game[row + 1][col] = 'K';
                newCh.game[row][col] = '.';
                result.add(newCh);
            }
            if (col != ChessConfig.col - 1) {
                if (game[row][col + 1] != '.') { //to the right one
                    ChessConfig newC = new ChessConfig(this);
                    newC.game[row][col + 1] = 'K';
                    newC.game[row][col] = '.';
                    result.add(newC);
                }
                if (game[row + 1][col + 1] != '.') { // diag down right
                    ChessConfig newCh = new ChessConfig(this);
                    newCh.game[row + 1][col + 1] = 'K';
                    newCh.game[row][col] = '.';
                    result.add(newCh);
                }
            }
            if (col != 0) {
                if (game[row][col - 1] != '.') { // left one
                    ChessConfig newCh = new ChessConfig(this);
                    newCh.game[row][col - 1] = 'K';
                    newCh.game[row][col] = '.';
                    result.add(newCh);
                }
                if (game[row + 1][col - 1] != '.') { // diag down left
                    ChessConfig newCh = new ChessConfig(this);
                    newCh.game[row+1][col - 1] = 'K';
                    newCh.game[row][col] = '.';
                    result.add(newCh);
                }
            }
        }
        else if (row == ChessConfig.row - 1) {
            if (game[row - 1][col] != '.') { //up one
                ChessConfig newCh = new ChessConfig(this);
                newCh.game[row - 1][col] = 'K';
                newCh.game[row][col] = '.';
                result.add(newCh);
            }
            if (col != ChessConfig.col - 1) {
                if (game[row][col + 1] != '.') { //to the right one
                    ChessConfig newC = new ChessConfig(this);
                    newC.game[row][col + 1] = 'K';
                    newC.game[row][col] = '.';
                    result.add(newC);
                }
                if (game[row - 1][col + 1] != '.') { // diag up right
                    ChessConfig newCh = new ChessConfig(this);
                    newCh.game[row - 1][col + 1] = 'K';
                    newCh.game[row][col] = '.';
                    result.add(newCh);
                }
            }
            if (col != 0) {
                if (game[row][col - 1] != '.') { // left one
                    ChessConfig newCh = new ChessConfig(this);
                    newCh.game[row][col - 1] = 'K';
                    newCh.game[row][col] = '.';
                    result.add(newCh);
                }
                if (game[row - 1][col - 1] != '.') { // diag up left
                    ChessConfig newCh = new ChessConfig(this);
                    newCh.game[row - 1][col - 1] = 'K';
                    newCh.game[row][col] = '.';
                    result.add(newCh);
                }

            }
        }
        else {
                if (game[row - 1][col] != '.') { //up one
                    ChessConfig newCh = new ChessConfig(this);
                    newCh.game[row - 1][col] = 'K';
                    newCh.game[row][col] = '.';
                    result.add(newCh);
                }

                if (game[row + 1][col] != '.') { // down one
                    ChessConfig newCh = new ChessConfig(this);
                    newCh.game[row + 1][col] = 'K';
                    newCh.game[row][col] = '.';
                    result.add(newCh);
                }
            if (col != ChessConfig.col - 1) {
                if (game[row][col + 1] != '.') { //to the right one
                    ChessConfig newC = new ChessConfig(this);
                    newC.game[row][col + 1] = 'K';
                    newC.game[row][col] = '.';
                    result.add(newC);
                }
                if (game[row - 1][col + 1] != '.') { // diag up right
                    ChessConfig newCh = new ChessConfig(this);
                    newCh.game[row - 1][col + 1] = 'K';
                    newCh.game[row][col] = '.';
                    result.add(newCh);
                }
                if (game[row + 1][col + 1] != '.') { // diag down right
                    ChessConfig newCh = new ChessConfig(this);
                    newCh.game[row + 1][col + 1] = 'K';
                    newCh.game[row][col] = '.';
                    result.add(newCh);
                }
            }
            if (col != 0) {
                if (game[row][col - 1] != '.') { // left one
                    ChessConfig newCh = new ChessConfig(this);
                    newCh.game[row][col - 1] = 'K';
                    newCh.game[row][col] = '.';
                    result.add(newCh);
                }
                if (game[row - 1][col - 1] != '.') { // diag up left
                    ChessConfig newCh = new ChessConfig(this);
                    newCh.game[row - 1][col - 1] = 'K';
                    newCh.game[row][col] = '.';
                    result.add(newCh);
                }
                if (game[row + 1][col - 1] != '.') { // diag down left
                    ChessConfig newCh = new ChessConfig(this);
                    newCh.game[row+1][col - 1] = 'K';
                    newCh.game[row][col] = '.';
                    result.add(newCh);
                }
            }
        }
        return result;
    }

    /**
     * Helper function for bishop movements
     * @param row current row where bishop is located
     * @param col current col where bishop is located
     * @param queen boolean, true if being used as helper function for queen and places 'Q' not 'B'
     * @return Collection of ChessConfigs of all possible valid bishop moves
     */
    public Collection<Configuration> bishopMoves(int row, int col, boolean queen){
        ArrayList<Configuration> result = new ArrayList<>();
        if(row != ChessConfig.row-1) {
            int r = row + 1;
            int c = col + 1;
            if (c < ChessConfig.col) {
                while (r + 1 < ChessConfig.row && c + 1 < ChessConfig.col && game[r][c] == '.') { //diag down right
                    r++;
                    c++;
                }
                if (game[r][c] != '.') {
                    ChessConfig newC = new ChessConfig(this);
                    newC.game[r][c] = 'B';
                    newC.game[row][col] = '.';
                    if (queen) {
                        newC.game[r][c] = 'Q';
                    }
                    result.add(newC);
                }
            }

            r = row + 1;
            c = col - 1;
            if (c >= 0) {
                while (r + 1 < ChessConfig.row && c - 1 >= 0 && game[r][c] == '.') { //diag down left
                    r++;
                    c--;
                }
                if (game[r][c] != '.') {
                    ChessConfig newC = new ChessConfig(this);
                    newC.game[r][c] = 'B';
                    newC.game[row][col] = '.';
                    if (queen) {
                        newC.game[r][c] = 'Q';
                    }
                    result.add(newC);
                }
            }
        }
        if(row != 0) {
            int r = row - 1;
            int c = col + 1;
            if (c < ChessConfig.col) {
                while (r - 1 >= 0 && c + 1 < ChessConfig.col && game[r][c] == '.') { //diag up right
                    r--;
                    c++;
                }
                if (game[r][c] != '.') {
                    ChessConfig newC = new ChessConfig(this);
                    newC.game[r][c] = 'B';
                    newC.game[row][col] = '.';
                    if (queen) {
                        newC.game[r][c] = 'Q';
                    }
                    result.add(newC);
                }
            }

            r = row - 1;
            c = col - 1;
            if (c >= 0) {
                while (r - 1 >= 0 && c - 1 >= 0 && game[r][c] == '.') { //diag up left
                    r--;
                    c--;
                }
                if (game[r][c] != '.') {
                    ChessConfig newC = new ChessConfig(this);
                    newC.game[r][c] = 'B';
                    newC.game[row][col] = '.';
                    if (queen) {
                        newC.game[r][c] = 'Q';
                    }
                    result.add(newC);
                }
            }
        }
        return result;
    }

    /**
     * Helper function for rook movements
     * @param row current row where rook is located
     * @param col current col where rook is located
     * @param queen boolean, true if being used as helper function for queen and places 'Q' not 'R'
     * @return Collection of ChessConfigs of all possible valid rook moves
     */
    public Collection<Configuration> rookMoves(int row, int col, boolean queen){
        ArrayList<Configuration> result = new ArrayList<>();
        int r = row+1;
        int c = col;
        if(row != ChessConfig.row-1){ // down
            while(r+1 < ChessConfig.row && game[r][c] == '.'){
                r++;
            }
            if(game[r][c] != '.'){
                ChessConfig newC = new ChessConfig(this);
                newC.game[r][c] = 'R';
                newC.game[row][col] = '.';
                if(queen){
                    newC.game[r][c] = 'Q';
                }
                result.add(newC);
            }
        }
        if(row != 0) { //up
            r = row-1;
            while (r - 1 >= 0 && game[r][c] == '.' ) {
                r--;
            }
            if (game[r][c] != '.') {
                ChessConfig newC = new ChessConfig(this);
                newC.game[r][c] = 'R';
                newC.game[row][col] = '.';
                if(queen){
                    newC.game[r][c] = 'Q';
                }
                result.add(newC);
            }
        }

        if(col != ChessConfig.col-1){ //right
            r= row;
            c = col+1;
            while(c+1 <ChessConfig.col && game[r][c] == '.'){
                c++;
            }
            if(game[r][c] != '.'){
                ChessConfig newC = new ChessConfig(this);
                newC.game[r][c] = 'R';
                newC.game[row][col] = '.';
                if(queen){
                    newC.game[r][c] = 'Q';
                }
                result.add(newC);
            }
        }
        if(col != 0){ //left
            c= col-1;
            while(c-1 >= 0 && game[r][c] == '.'){
                c--;
            }
            if(game[r][c] != '.'){
                ChessConfig newC = new ChessConfig(this);
                newC.game[r][c] = 'R';
                newC.game[row][col] = '.';
                if(queen){
                    newC.game[r][c] = 'Q';
                }
                result.add(newC);
            }
        }
        return result;
    }

    /**
     * Helper function for Queen movements
     * @param row current row where queen is located
     * @param col current col where queen is located
     * @return Collection of ChessConfigs of all possible valid queen moves
     */
    public Collection<Configuration> queenMoves(int row, int col){
        ArrayList<Configuration> result = new ArrayList<>();
        result.addAll(bishopMoves(row,col, true));
        result.addAll(rookMoves(row,col, true));
        return result;
    }

    /**
     * Helper function for Knight movements
     * @param row current row where knight is located
     * @param col current col where knight is located
     * @return Collection of ChessConfigs of all possible valid knight moves
     */
    public Collection<Configuration> knightMoves(int row, int col){
        ArrayList<Configuration> result = new ArrayList<>();

        if(row+2<ChessConfig.row && col - 1 >= 0) { //2 down 1left
            if ( game[row + 2][col - 1] != '.') {
                ChessConfig newC = new ChessConfig(this);
                newC.game[row + 2][col - 1] = 'N';
                newC.game[row][col] = '.';
                result.add(newC);
            }
        }
        if(row+2<ChessConfig.row && col+1 <ChessConfig.col) { //2 down 1right
            if( game[row+2][col+1] != '.'){
                ChessConfig newC = new ChessConfig(this);
                newC.game[row+2][col+1] = 'N';
                newC.game[row][col] = '.';
                result.add(newC);
            }
        }
        if(row-2 >=0 && col - 1 >= 0) { //2up, 1left
            if ( game[row - 2][col - 1] != '.') {
                ChessConfig newC = new ChessConfig(this);
                newC.game[row - 2][col - 1] = 'N';
                newC.game[row][col] = '.';
                result.add(newC);
            }
        }
        if(row-2 >=0 && col+1 <ChessConfig.col ){ //2up, 1right
            if(game[row-2][col+1] != '.'){
                ChessConfig newC = new ChessConfig(this);
                newC.game[row-2][col+1] = 'N';
                newC.game[row][col] = '.';
                result.add(newC);
            }
        }
        if(col-2 >=0 && row - 1 >= 0 ) {//2left, 1up
            if (game[row - 1][col - 2] != '.') {
                ChessConfig newC = new ChessConfig(this);
                newC.game[row - 1][col - 2] = 'N';
                newC.game[row][col] = '.';
                result.add(newC);
            }
        }
        if(col-2 >=0 && row+1 <ChessConfig.row ){ //2left, 1down
            if(game[row+1][col-2] != '.'){
                ChessConfig newC = new ChessConfig(this);
                newC.game[row+1][col-2] = 'N';
                newC.game[row][col] = '.';
                result.add(newC);
            }
        }
        if(col+2 < ChessConfig.col){ //2right, 1up
            if(row-1 >=0 && game[row-1][col+2] != '.'){
                ChessConfig newC = new ChessConfig(this);
                newC.game[row-1][col+2] = 'N';
                newC.game[row][col] = '.';
                result.add(newC);
            }
            if(row+1 <ChessConfig.row && game[row+1][col+2] != '.'){ //2right, 1down
                ChessConfig newC = new ChessConfig(this);
                newC.game[row+1][col+2] = 'N';
                newC.game[row][col] = '.';
                result.add(newC);
            }
        }
        return result;
    }

    /**
     * Gets the Neighbors of the current configuration
     * @return ArrayList of ChessConfigs of all neighbors.
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        ArrayList<Configuration> result = new ArrayList<>();
        for(int r = 0; r< row; r++){
            for(int c = 0; c<col; c++){
                if(game[r][c] == 'B'){
                    result.addAll(bishopMoves(r,c, false));
                }
                else if(game[r][c] == 'K'){
                    result.addAll(kingMoves(r,c));
                }
                else if(game[r][c] == 'N'){
                    result.addAll(knightMoves(r,c));
                }
                else if(game[r][c] == 'P'){
                    result.addAll(pawnMoves(r,c));
                }
                else if(game[r][c] == 'Q'){
                    result.addAll(queenMoves(r,c));
                }
                else if(game[r][c] == 'R'){
                    result.addAll(rookMoves(r,c, false));
                }
            }
        }
        return result;
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

    /**
     * Creates and returns a string representation of the chess board
     * @return String of chess board
     */
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

    /**
     * Compares given chess board to the current chess board
     * @param other other board being compared to
     * @return true if the same false if not
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof ChessConfig ch) {
            for(int r = 0; r< row; r++){
                for(int c = 0; c<col; c++){
                    if(game[r][c] != ch.game[r][c]){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Creates hash code of current given board
     * @return Integer hash code
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
