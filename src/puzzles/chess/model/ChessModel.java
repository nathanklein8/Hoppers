package puzzles.chess.model;

import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.util.LinkedList;
import java.util.List;

/**
 * Model class that uses ChessConfig to carry out necessary actions to be used by the PTUI and the GUI
 *
 * @author Madeline Mariano mam5090
 */
public class ChessModel {
    /** the collection of observers of this model */
    private final List<Observer<ChessModel, String>> observers = new LinkedList<>();
    /** the current configuration */
    private ChessConfig currentConfig;
    /** the original configuration, for use if game reset */
    private ChessConfig origConfig;
    /** the current row selected, -1 if none */
    private int cR;
    /** the current column selected, -1 if none */
    private int cC;

    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<ChessModel, String> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(String data) {
        for (var observer : observers) {
            observer.update(this, data);
        }
    }

    /**
     * Creates a new ChessModel and sets that as the current and the original config from the given file
     */
    public ChessModel(String filename) {
        try {
            this.currentConfig = new ChessConfig(filename);
            this.origConfig = currentConfig;
            this.cR = -1;
            this.cC = -1;
        } catch (Exception e) {
            alertObservers("Could not load file: "+filename);
        }
    }

    /**
     * gets the string display of the current board with the labeled rows/columns
     * @return string as described above
     */
    public String getDisplay(){
        return currentConfig.getDisplay();
    }

    /**
     * gets the row dimensions
     * @return integer row total
     */
    public int getRow(){
        return ChessConfig.row;
    }

    /**
     * gets the column dimensions
     * @return integer column total
     */
    public int getCol(){
        return ChessConfig.col;
    }

    /**
     * gets the piece in the desired cell
     * @param r given row
     * @param c given col
     * @return character representation of piece
     */
    public char getCell(int r, int c){
        return currentConfig.getCell(r,c);
    }

    /**
     * loads a new game from given file
     * @param filename file with new chess info
     */
    public void loadNew(String filename) {
        try {
            this.currentConfig = new ChessConfig(filename);
            this.origConfig = currentConfig;
            alertObservers("Loaded: " +filename);
            cR = -1;
            cC = -1;
        } catch (Exception e) {
            alertObservers("Could not load file: "+filename);
        }
    }

    /**
     * command to select a new cell. If this is the second cell selected, will attempt the desired capture. If the first
     * cell selected, moves the current row and column values (cR and cC respectively) to that location. Informs user
     * if cell selected is out of bounds
     * @param r given row
     * @param c given column
     */
    public void selectCell(int r, int c){
        if(r < ChessConfig.row && r >=0 && c>= 0 && c < ChessConfig.col && cR == -1 && cC == -1){
            this.cR = r;
            this.cC = c;
            alertObservers("Selected (" +r + ", " + c + ")");
        } else if(r < ChessConfig.row && r >=0 && c>= 0 && c < ChessConfig.col && cR != -1 && cC != -1 ){
            capture(r,c);
        }
        else {
            alertObservers("Invalid selection (" +r + ", " + c + ")" );
            cR = -1;
            cC = -1;
        }
    }

    /**
     * resets the board to the original configuration
     */
    public void reset(){
        this.currentConfig = origConfig;
        cR = -1;
        cC = -1;
        alertObservers("Puzzle reset!");
    }

    /**
     * attempts a capture from the current row and column to the given end row and column. if valid will complete and
     * print the new board, informs user if not valid. If the given capture completes the game the user will also be
     * informed
     * @param endR end row
     * @param endC end column
     */
    public void capture( int endR, int endC){
        ChessConfig move = currentConfig.isValidCapture(cR,cC, endR, endC, currentConfig.getCell(cR,cC));
        if(move != null){
            currentConfig = move;
            if(move.isSolution()){
                alertObservers("Game completed, You won!");
            }
            else{
                alertObservers("Captured from (" + cR + ", " + cC + ") to (" + endR + ", " + endC + ")");
            }
        } else{
            alertObservers("Can't capture from (" + cR + ", " + cC + ") to (" + endR + ", " + endC + ")");
        }
        cR = -1;
        cC = -1;
    }

    /**
     * sets the current board to the next movement, if such a path to the solution exists. if none exist,
     * user is told there is no solution. If the given hint completes the game, the user is informed.
     */
    public void hint(){
        Solver solver = new Solver();
        LinkedList<Configuration> path = solver.solve(currentConfig);
        if(path.size() != 0){
            currentConfig = (ChessConfig) path.get(1);
            if(currentConfig.isSolution()){
            alertObservers("Game won! Quit/reset/load a new game :)");
            }
            else{
                alertObservers("Next step!");
            }
        } else{
            alertObservers("No solution found :(");
        }
    }
}
