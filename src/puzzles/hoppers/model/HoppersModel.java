package puzzles.hoppers.model;

import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Stores data needed to solve a Hoppers puzzle with a UI
 *
 * @author Nathan Klein nek7125@rit.edu
 */
public class HoppersModel {
    /** the collection of observers of this model */
    private final List<Observer<HoppersModel, String>> observers = new LinkedList<>();

    /** the current configuration */
    private HoppersConfig currentConfig;
    private HoppersConfig originalConfig;

    private int curRow;
    private int curCol;

    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<HoppersModel, String> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(String msg) {
        for (var observer : observers) {
            observer.update(this, msg);
        }
    }

    public HoppersModel(String filename) {
        try {
            currentConfig = new HoppersConfig(filename);
            originalConfig = currentConfig;
            this.curRow = -1;
            this.curCol = -1;
            alertObservers("Loaded: " + filename);
        } catch (Exception e) {
            alertObservers("Failed to load: " + filename);
        }
    }

    /**
     * attempts to solve the puzzle from the current state, updating the current state to the next step in the solution
     * if there is one, otherwise, alerts the observers that the puzzle is insolvable.
     */
    public void hint() {
        if (currentConfig.isSolution()) {
            alertObservers("Already solved!");
            return;
        }
        Solver solver = new Solver();
        LinkedList<Configuration> solution = solver.solve(currentConfig);
        if(solution.size() > 0){
            currentConfig = (HoppersConfig) solution.get(1);
            if(currentConfig.isSolution()){
                alertObservers("woohoo you won");
            } else {
                alertObservers("Next step!");
            }
        } else {
            alertObservers("Uh oh! No solution found...");
        }
    }

    /**
     * loads a new puzzle file, alerts observers
     * @param filename file to load and read from
     */
    public void load(String filename) {
        try {
            currentConfig = new HoppersConfig(filename);
            originalConfig = currentConfig;
            this.curRow = -1;
            this.curCol = -1;
            alertObservers("Loaded: " + filename);
        } catch (Exception e) {
            alertObservers("Failed to load: " + filename);
        }
    }

    /**
     * selects a frog to move, or an open space to move to
     * @param row int selected row
     * @param col int selected column
     */
    public void select(int row, int col) {
        if (row>=currentConfig.getRows() || col>=currentConfig.getCols() || row<0 || col<0) {
            alertObservers("Selection out of bounds!");
            return;
        }
        if (!((row%2==0 && col%2==0)||(row%2==1 && col%2==1))) { // both even or both odd
            alertObservers("Invalid selection >:(");
            return;
        }
        if (curRow != -1 && curCol != -1) {  // selecting to coords
            Collection<Configuration> validMoves = currentConfig.getMoves(curRow, curCol);
            HoppersConfig moved = new HoppersConfig(currentConfig, curRow, curCol, row, col);
            if (validMoves.contains(moved)) {
                this.currentConfig = moved;
                if (currentConfig.isSolution()) {
                    alertObservers("woohoo you won");
                } else {
                    alertObservers("Jumped from ("+curRow+", "+curCol+") to ("+row+", "+col+")");
                }
            } else {
                alertObservers("Invalid move bruh");
            }
            curRow = -1;
            curCol = -1;
        } else {                // selecting from coords
            if (currentConfig.isFrog(row, col)) {
                curRow = row;
                curCol = col;
                alertObservers("Selected ("+row+", "+col+")");
            } else {
                alertObservers("You gotta select a frog man");
            }
        }

    }

    /**
     * resets the puzzle to the original unsolved state
     */
    public void reset() {
        currentConfig = originalConfig;
        curRow = -1;
        curCol = -1;
        alertObservers("Puzzle reset!");
    }

    /**
     * Displays the Hoppers game board with row and column indicators
     * @return String
     */
    public String getDisplay() {
        return currentConfig.getDisplay();
    }

    /**
     * returns the number of rows in the Hoppers game board
     * @return int
     */
    public int getRows() {
        return currentConfig.getRows();
    }

    /**
     * returns the number of columns in the Hoppers game board
     * @return int
     */
    public int getCols() {
        return currentConfig.getCols();
    }

    /**
     * gets the value stored in the Hoppers game board at the given coordinates
     * @param row row
     * @param column column
     * @return char
     */
    public char getCell(int row, int column) {
        return currentConfig.getCell(row, column);
    }

}
