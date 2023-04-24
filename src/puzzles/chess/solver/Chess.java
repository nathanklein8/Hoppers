package puzzles.chess.solver;

import puzzles.chess.model.ChessConfig;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;


import java.util.LinkedList;

public class Chess {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Chess filename");
        }
        else{
            String file = args[0];
            System.out.println("File: " + file);
            Solver solver = new Solver();
            try {
                Configuration start = new ChessConfig(file);
                System.out.println(start);
                LinkedList<Configuration> path = solver.solve(start);
                System.out.println("Total configs: " + solver.getNumConfigs());
                System.out.println("Unique configs: " + solver.getUniqueConfigs() + "\n");
                if (path.size() > 0) {
                    for (int i=0; i<path.size(); i++) {
                        System.out.println("Step: " + i);
                        System.out.print(path.get(i).toString() + "\n");
                    }
                } else {
                    System.out.println("No Solution");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

