package puzzles.chess.model;

import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ChessModel {
    /** the collection of observers of this model */
    private final List<Observer<ChessModel, String>> observers = new LinkedList<>();

    /** the current configuration */
    private ChessConfig currentConfig;
    private ChessConfig origConfig;

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

    public ChessModel(String filename) throws IOException {
        //TODO
        Solver solver = new Solver();
        try {
            this.currentConfig = new ChessConfig(filename);
            this.origConfig = currentConfig;
            alertObservers("Loaded: " +filename);
            LinkedList<Configuration> path = solver.solve(currentConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDisplay(){
        return currentConfig.getDisplay();
    }

    public void loadNew(String filename) throws IOException{
        try {
            this.currentConfig = new ChessConfig(filename);
            this.origConfig = currentConfig;
            alertObservers("Loaded: " +filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectCell(int r, int c){
        alertObservers("Selected (" +r + ", " + c + ")");
    }

//    public void capture(){
//
//    }

    public void reset(){
        this.currentConfig = origConfig;
        alertObservers("Puzzle reset!");
    }
}
