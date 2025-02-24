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
        TSP.Sample[] samples = {
                TSP.Sample.SAMPLE_5,
                TSP.Sample.SAMPLE_6,
                TSP.Sample.SAMPLE_17,
                TSP.Sample.SAMPLE_26
        };

        int[] sampleNumbers = {5, 6, 17, 26};

        for (int i = 0; i < samples.length; i++) {
            System.out.println("\nRunning SA_TSP on SAMPLE_" + sampleNumbers[i]);

            TSP problem = new TSP(samples[i].distanceMatrix());
            SA_TSP solver = new SA_TSP(problem);
            solver.search();
        }
    }


}
