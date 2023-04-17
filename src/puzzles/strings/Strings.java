package puzzles.strings;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.util.LinkedList;

public class Strings {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(("Usage: java Strings start finish"));
        } else {
            String start = args[0];
            String goal = args[1];
            System.out.println("Start: " + start + ", End: " + goal);
            Solver solver = new Solver();
            LinkedList<Configuration> solution = solver.solve(new StringsConfig(start, goal));
            System.out.println("Total configs: " + solver.getNumConfigs());
            System.out.println("Unique configs: " + solver.getUniqueConfigs());
            // print out the path, or no solution
            if (solution.size() > 0) {
                for (int i=0; i<solution.size(); i++) {
                    System.out.println("Step " + i + ": " + solution.get(i).toString());
                }
            } else {
                System.out.println("No Solution");
            }
        }
    }
}
