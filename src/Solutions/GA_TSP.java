package Solutions;

import core_algorithms.GeneticAlgorithm;
import core_algorithms.Individual;
import problems.TSP;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GA_TSP extends GeneticAlgorithm<List<Integer>> {

    private final double[][] distances;
    private final int numCities;

    public GA_TSP(int MAX_GEN, double MUTATION, double ELITISM, double[][] distances) {
        super(MAX_GEN, MUTATION, ELITISM);
        this.distances = distances;
        this.numCities = distances.length;
    }

    @Override
    public Individual<List<Integer>> reproduce(Individual<List<Integer>> p, Individual<List<Integer>> q) {
        return orderedCrossover(p, q);
    }

    @Override
    public Individual<List<Integer>> mutate(Individual<List<Integer>> indiv) {
        return swapMutation(indiv);
    }

    @Override
    public double calcFitnessScore(List<Integer> chromosome) {
        return 1.0 / computeTourDistance(chromosome);
    }

    private double computeTourDistance(List<Integer> tour) {
        double totalDistance = 0.0;
        for (int i = 0; i < tour.size(); i++) {
            int from = tour.get(i);
            int to = tour.get((i + 1) % tour.size());
            totalDistance += distances[from][to];
        }
        return totalDistance;
    }

    private Individual<List<Integer>> orderedCrossover(Individual<List<Integer>> p, Individual<List<Integer>> q) {
        Random rand = new Random();
        int start = rand.nextInt(numCities);
        int end = rand.nextInt(numCities - start) + start;

        List<Integer> childChromosome = new ArrayList<>(Collections.nCopies(numCities, -1));
        List<Integer> parent2Genes = new ArrayList<>(q.getChromosome());

        for (int i = start; i <= end; i++) {
            childChromosome.set(i, p.getChromosome().get(i));
            parent2Genes.remove(p.getChromosome().get(i));
        }

        int index = 0;
        for (int i = 0; i < numCities; i++) {
            if (childChromosome.get(i) == -1) {
                childChromosome.set(i, parent2Genes.get(index++));
            }
        }

        return new Individual<>(childChromosome, calcFitnessScore(childChromosome));
    }

    private Individual<List<Integer>> swapMutation(Individual<List<Integer>> indiv) {
        List<Integer> mutatedChromosome = new ArrayList<>(indiv.getChromosome());
        Random rand = new Random();
        int i = rand.nextInt(numCities);
        int j = rand.nextInt(numCities);
        Collections.swap(mutatedChromosome, i, j);
        return new Individual<>(mutatedChromosome, calcFitnessScore(mutatedChromosome));
    }

    private List<Individual<List<Integer>>> generateInitialPopulation(int populationSize) {
        List<Individual<List<Integer>>> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            List<Integer> tour = generateRandomTour();
            population.add(new Individual<>(tour, calcFitnessScore(tour)));
        }
        return population;
    }

    private List<Integer> generateRandomTour() {
        List<Integer> tour = new ArrayList<>();
        for (int i = 0; i < numCities; i++) {
            tour.add(i);
        }
        Collections.shuffle(tour);
        return tour;
    }

    public static void main(String[] args) {
        int numCities = 5;
        double[][] distances = {
                {0, 2, 9, 10, 7},
                {2, 0, 6, 4, 3},
                {9, 6, 0, 8, 5},
                {10, 4, 8, 0, 6},
                {7, 3, 5, 6, 0}
        };

        GA_TSP gaTSP = new GA_TSP(1000, 0.1, 0.2, distances);
        List<Individual<List<Integer>>> initialPopulation = gaTSP.generateInitialPopulation(50);
        Individual<List<Integer>> bestSolution = gaTSP.evolve(initialPopulation);

        System.out.println("Best Tour Found: " + bestSolution.getChromosome());
        System.out.println("Tour Distance: " + (1.0 / bestSolution.getFitnessScore()));
    }
}
