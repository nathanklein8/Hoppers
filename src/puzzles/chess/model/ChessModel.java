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
    private int cR;
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

    public ChessModel(String filename) throws IOException {
        //TODO
        try {
            this.currentConfig = new ChessConfig(filename);
            this.origConfig = currentConfig;
            this.cR = -1;
            this.cC = -1;
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
            alertObservers("Could not load file: "+filename);
        }
    }

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

    public void reset(){
        this.currentConfig = origConfig;
        alertObservers("Puzzle reset!");
    }

    public void capture( int endR, int endC){
        ChessConfig move = currentConfig.isValidCapture(cR,cC, endR, endC, currentConfig.getCell(cR,cC));
        if(move != null){
            currentConfig = move;
            alertObservers("Captured from (" + cR + ", " + cC + ") to (" + endR + ", " + endC + ")");

        } else{
            alertObservers("Can't capture from (" + cR + ", " + cC + ") to (" + endR + ", " + endC + ")");
        }
        cR = -1;
        cC = -1;
    }
    public void hint(){
        Solver solver = new Solver();
        LinkedList<Configuration> path = solver.solve(currentConfig);
        if(path.size() != 0){
        currentConfig = (ChessConfig) path.get(1);
        alertObservers("Next step!");
        }
        else{
            alertObservers("No solution found :(");
        }
    }
}
