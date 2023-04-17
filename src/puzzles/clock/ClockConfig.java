package puzzles.clock;

import puzzles.common.solver.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClockConfig implements Configuration {

    private int current;
    private static int end;
    private static int hours;

    /**
     * Configuration for a clock in the clock puzzle
     * @param hours - static int, how many hours are on the clock
     * @param start - int, what the start, or current time on the clock is
     * @param end - static int, what the goal of the clock is
     */
    public ClockConfig(int hours, int start, int end) {
        ClockConfig.hours = hours;
        this.current = start;
        ClockConfig.end = end;
    }

    public ClockConfig(int current) {
        this.current = current;
    }

    @Override
    public boolean isSolution() {
        return current == end;
    }

    @Override
    public Collection<Configuration> getNeighbors() {
        int l = (current-1<1) ? (current-1)+hours : current-1;
        int r = (current+1>hours) ? (current+1)-hours : current+1;
        ClockConfig left = new ClockConfig(l);
        ClockConfig right = new ClockConfig(r);
        return new ArrayList<>(List.of(left, right));
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ClockConfig c) {
            return this.current == c.current;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return current;
    }

    @Override
    public String toString() {
        return "" + current;
    }
}
