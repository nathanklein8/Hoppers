package puzzles.clock;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.util.LinkedList;

public class Clock {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(("Usage: java Clock hours stop end"));
        } else {
            int hours = Integer.parseInt(args[0]);
            int start = Integer.parseInt(args[1]);
            int end = Integer.parseInt(args[2]);
            System.out.println("Hours: " + hours + ", Start: " + start + ", End: " + end);
            Solver solver = new Solver();
            LinkedList<Configuration> solution = solver.solve(new ClockConfig(hours, start, end));
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
