package puzzles.strings;

import puzzles.common.solver.Configuration;

import java.util.ArrayList;
import java.util.Collection;

public class StringsConfig implements Configuration {

    private final String current;
    private static String goal;

    /**
     * configuration for a word in the string puzzle
     * @param start - String, the start, or current word in the configuration
     * @param finish - static String, the goal word
     */
    public StringsConfig(String start, String finish) {
        this.current = start;
        goal = finish;
    }

    public StringsConfig(String current) {
        this.current = current;
    }

    @Override
    public boolean isSolution() {
        return current.equals(goal);
    }

    @Override
    public Collection<Configuration> getNeighbors() {
        ArrayList<Configuration> neighbors = new ArrayList<>();
        for (int i = 0; i < current.length(); i++) {
            char cChar = current.charAt(i); //current
            char nChar = current.charAt(i); //new
            if (nChar == 'Z') {
                nChar = 'A';
            } else {
                nChar += 1;
            }
            StringsConfig plus = new StringsConfig(current.substring(0, i) + nChar +
                    current.substring(i + 1));
            neighbors.add(plus);

            if (cChar == 'A') {
                cChar = 'Z';
            } else {
                cChar -= 1;
            }
            StringsConfig minus = new StringsConfig(current.substring(0, i) + cChar +
                    current.substring(i + 1));
            neighbors.add(minus);
        }
        return neighbors;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof StringsConfig c) {
            return this.current.equals(c.current);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return current.hashCode();
    }

    @Override
    public String toString() {
        return current;
    }
}
