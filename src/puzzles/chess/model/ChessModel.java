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
            Configuration initial = new ChessConfig(filename);
            System.out.println(initial);
            LinkedList<Configuration> path = solver.solve(initial);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
