package Solutions;

import core_algorithms.SimulatedAnnealing;
import problems.TSProblem;

public class SA_TS_Problem extends SimulatedAnnealing<int[]> {

    private final static long INIT_TIME = 1;

    private final static double INIT_TEMP = 1e13;

    private final static long MAX_TIME = 100_000_000;

    public SA_TS_Problem(TSProblem problem) {
        super(INIT_TIME, INIT_TEMP, problem);
    }

    public double schedule(long time, double temp){
        return temp * (1 - time/(double)MAX_TIME);
    }

    public static void main(String[] args) {
        TSProblem problem = new TSProblem(new TSProblem());
        problem.search();
    }
}
