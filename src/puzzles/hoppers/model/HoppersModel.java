package puzzles.hoppers.model;

import puzzles.chess.model.ChessConfig;
import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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

    public HoppersModel(String filename) throws IOException {
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

    public void hint() {
        Solver solver = new Solver();
        LinkedList<Configuration> solution = solver.solve(currentConfig);
        if(solution.size() > 0){
            currentConfig = (HoppersConfig) solution.get(1);
            if(currentConfig.isSolution()){
                alertObservers("Game completed! Quit or load a new game :)");
            } else {
                alertObservers("Next step!");
            }
        } else {
            alertObservers("Uh oh! No solution found...");
        }
    }

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

    public void select(int row, int col) {
        if (row>=currentConfig.getRows() || col>=currentConfig.getCols()) {
            alertObservers("Selection out of bounds!");
            return;
        }
        if (row == -1 && col == -1) {
            curRow = -1;
            curCol = -1;
            alertObservers("de-selected... Choose a new froggy to move");
            return;
        }
        // make sure row/col are both odd or even
        if ((row%2!=0 && col%2!=0)||(row%2!=1 && col%2!=1)) {
            if (curRow != -1 && curCol != -1) {  // selecting to coords
                Collection<Configuration> validMoves = currentConfig.getMoves(curRow, curCol);
                HoppersConfig moved = new HoppersConfig(currentConfig, curRow, curCol, row, col);
                if (validMoves.contains(moved)) {
                    this.currentConfig = moved;
                } else {
                    alertObservers("Invalid move bruh");
                    return;
                }
                if (currentConfig.isSolution()) {
                    curCol = -1;
                    curRow = -1;
                    alertObservers("woohoo you won");
                } else {
                    curCol = -1;
                    curRow = -1;
                    alertObservers("Jumped from ("+curRow+", "+curCol+") to ("+row+", "+col+")");
                }
            } else {                // selecting from coords
                if (currentConfig.isFrog(row, col)) {
                    curRow = row;
                    curCol = col;
                    alertObservers("Selected ("+row+", "+col+")");
                } else {
                    alertObservers("You gotta select a frog man");
                }
            }
        } else {
            alertObservers("Invalid Coordinates! >:(");
        }

    }

    public void reset() {
        currentConfig = originalConfig;
        curRow = -1;
        curCol = -1;
        alertObservers("Puzzle reset!");
    }

    public String getDisplay() {
        return currentConfig.getDisplay();
    }

}
