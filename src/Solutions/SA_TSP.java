package Solutions;

import core_algorithms.SimulatedAnnealing;
import problems.TSP;

public class SA_TSP extends SimulatedAnnealing<int[]> {

    private static final long INIT_TIME = 1;
    private static final double INIT_TEMP = 1e13;
    private static final long MAX_TIME = 100_000_000;

    public SA_TSP(TSP problem) {
        super(INIT_TIME, INIT_TEMP, problem);
    }

    @Override
    public double schedule(long time, double temp) {
        return temp * (1 - time / (double) MAX_TIME);
    }

    public static void main(String[] args) {
        int[][] distances = {
                {0, 3, 4, 2, 7},
                {3, 0, 4, 6, 3},
                {4, 4, 0, 5, 8},
                {2, 6, 5, 0, 6},
                {7, 3, 8, 6, 0},
        };

        TSP problem = new TSP(distances);
        SA_TSP solver = new SA_TSP(problem);
        solver.search();
    }
}
