package puzzles.hoppers.solver;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.hoppers.model.HoppersConfig;

import java.util.LinkedList;

public class Hoppers {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Hoppers filename");
        } else {
            String file = args[0];
            System.out.println("File: " + file);
            Solver solver = new Solver();
            try {
                Configuration initial = new HoppersConfig(file);
                System.out.println(initial);
                LinkedList<Configuration> path = solver.solve(initial);
                System.out.println("Total configs: " + solver.getNumConfigs());
                System.out.println("Unique configs: " + solver.getUniqueConfigs());
                if (path.size() > 0) {
                    for (int i=0; i<path.size(); i++) {
                        System.out.println("Step: " + i);
                        System.out.println(path.get(i).toString() + "\n");
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
