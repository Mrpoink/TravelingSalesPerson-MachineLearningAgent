package Solutions;

import core_algorithms.GeneticAlgorithm;
import core_algorithms.Individual;
import problems.TSProblem;

import java.util.Arrays;
import java.util.Random;

public class GA_TS_Problem extends GeneticAlgorithm<int[]> {

    private final TSProblem problem;

    public GA_TS_Problem(int maxgen, double mrate, double elitism, TSProblem board) {
        super(maxgen, mrate, elitism);
        this.problem = board;
    }

    public Individual<int[]> reproduce(Individual<int[]> p, Individual<int[]> q) {
        Random rand = new Random();

        int start = rand.nextInt(p.getChromosome().length);
        int end = rand.nextInt(p.getChromosome().length);

        if (start > end){
            int t = start;
            start = end;
            end = t;
        }

        int[] childChromosome = Arrays.copyOf(p.getChromosome(), p.getChromosome().length);

        for (int i = 0; i < start; i++){
            childChromosome[i] = q.getChromosome()[i];
        }
        for (int i = end + 1; i < q.getChromosome().length; i++){
            childChromosome[i] = q.getChromosome()[i];
        }
        return new Individual<>(childChromosome, calcFitnessScore(childChromosome));
    }

    public Individual<int[]> mutate(Individual<int[]> indiv){
        int[] chromosome = problem.generateNewState(indiv.getChromosome());
        return new Individual<>(chromosome, calcFitnessScore(chromosome));
    }

    public double calcFitnessScore(int[] chromosome) {
        //replace getN with TS equivalent. GetN was made to just return the number of queens
        return problem.getN()*(problem.getN()-1)/(double)2 - problem.cost(chromosome);
    }
}
