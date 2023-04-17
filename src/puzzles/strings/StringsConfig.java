package puzzles.strings;

import puzzles.common.solver.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StringsConfig implements Configuration {

    private String current;
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
        StringBuilder left = new StringBuilder(current);
        StringBuilder right = new StringBuilder(current);
        int index = 0;
        while (current.charAt(index) == goal.charAt(index)) { index += 1; }
        int leftInt = (current.charAt(index)-1 < 65) ? 90 : current.charAt(index)-1;
        int rightInt = (current.charAt(index)+1 > 90) ? 65 : current.charAt(index)+1;
        left.setCharAt(index, (char) leftInt);
        right.setCharAt(index, (char) rightInt);
        return new ArrayList<>(List.of(new StringsConfig(left.toString()), new StringsConfig(right.toString())));
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
