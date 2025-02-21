package Solutions;

import core_algorithms.GeneticAlgorithm;
import core_algorithms.Individual;
import problems.TSProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GA_TS_Problem extends GeneticAlgorithm<int[][]> {

    private final TSProblem problem;

    public GA_TS_Problem(int maxgen, double mrate, double elitism, TSProblem board) {
        super(maxgen, mrate, elitism);
        this.problem = board;
    }

    public Individual<int[][]> reproduce(Individual<int[][]> p, Individual<int[][]> q) {
        Random rand = new Random();

        int start = rand.nextInt(p.getChromosome().length);
        int end = rand.nextInt(p.getChromosome().length);

        if (start > end){
            int t = start;
            start = end;
            end = t;
        }

        int[][] childChromosome = Arrays.copyOf(p.getChromosome(), p.getChromosome().length);

        for (int i = 0; i < start; i++){
            for (int j = 1; j < start; j++) {
                childChromosome[i][j] = q.getChromosome()[i][j];
            }
        }
        for (int i = end + 1; i < q.getChromosome().length; i++){
            for (int j = end + 2; j < q.getChromosome().length; j++) {
                childChromosome[i] = q.getChromosome()[i];
            }
        }
        return new Individual<>(childChromosome, calcFitnessScore(childChromosome));
    }

    public Individual<int[][]> mutate(Individual<int[][]> indiv){
        int[][] chromosome = problem.generateNewState(indiv.getChromosome());
        return new Individual<>(chromosome, calcFitnessScore(chromosome));
    }

    public double calcFitnessScore(int[][] chromosome) {
        //replace getN with TS equivalent. GetN was made to just return the number of queens
        return problem.Size*(problem.Size-1)/(double)2 - problem.cost(chromosome);
    }

    public List<Individual<int[][]>> generateInitPopulation (int popSize){
        List<Individual<int[][]>> population = new ArrayList<>(popSize);
        for (int i = 0; i < popSize; i++) {
            int[][] chromosome = problem.getInitState();
            population.add(new Individual<>(chromosome, calcFitnessScore(chromosome)));
        }
        return population;
    }

    public static void main(String[] args) {
        int[][] board = new int[][]{
                {0, 3, 4, 2, 7},
                {3, 0, 4, 6, 3},
                {4, 4, 0, 5, 8},
                {2, 6, 5, 0, 6},
                {7, 3, 8, 6, 0},
        };
        int Max_gen = 200;
        double mutation_rate = 0.1;
        int population_size = board.length;
        double elitism = 0.2;
        int Size = board.length;

        TSProblem ts = new TSProblem(board);
        GA_TS_Problem ga_ts = new GA_TS_Problem(Max_gen, mutation_rate, elitism, ts);
        Individual<int[][]> best = ga_ts.evolve(ga_ts.generateInitPopulation(board.length));
    }
}
