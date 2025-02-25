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
                {0, 633, 257, 91, 412, 150, 80, 134, 259, 505, 353, 324, 70, 211, 268, 246, 121},
                {633, 0, 390, 661, 227, 488, 572, 530, 555, 289, 282, 638, 567, 466, 420, 745, 518},
                {257, 390, 0, 228, 169, 112, 196, 154, 372, 262, 110, 437, 191, 74, 53, 472, 142},
                {91, 661, 228, 0, 383, 120, 77, 105, 175, 476, 324, 240, 27, 182, 239, 237, 84},
                {412, 227, 169, 383, 0, 267, 351, 309, 338, 196, 61, 421, 346, 243, 199, 528, 297},
                {150, 488, 112, 120, 267, 0, 63, 34, 264, 360, 208, 329, 83, 105, 123, 364, 35},
                {80, 572, 196, 77, 351, 63, 0, 29, 232, 444, 292, 297, 47, 150, 207, 332, 29},
                {134, 530, 154, 105, 309, 34, 29, 0, 249, 402, 250, 314, 68, 108, 165, 349, 36},
                {259, 555, 372, 175, 338, 264, 232, 249, 0, 495, 352, 95, 189, 326, 383, 202, 236},
                {505, 289, 262, 476, 196, 360, 444, 402, 495, 0, 154, 578, 439, 336, 240, 685, 390},
                {353, 282, 110, 324, 61, 208, 292, 250, 352, 154, 0, 435, 287, 184, 140, 542, 238},
                {324, 638, 437, 240, 421, 329, 297, 314, 95, 578, 435, 0, 254, 391, 448, 157, 301},
                {70, 567, 191, 27, 346, 83, 47, 68, 189, 439, 287, 254, 0, 145, 202, 289, 55},
                {211, 466, 74, 182, 243, 105, 150, 108, 326, 336, 184, 391, 145, 0, 57, 426, 96},
                {268, 420, 53, 239, 199, 123, 207, 165, 383, 240, 140, 448, 202, 57, 0, 483, 153},
                {246, 745, 472, 237, 528, 364, 332, 349, 202, 685, 542, 157, 289, 426, 483, 0, 336},
                {121, 518, 142, 84, 297, 35, 29, 36, 236, 390, 238, 301, 55, 96, 153, 336, 0}
        };

        GA_TSP gaTSP = new GA_TSP(1000, 0.1, 0.2, distances);
        List<Individual<List<Integer>>> initialPopulation = gaTSP.generateInitialPopulation(50);
        Individual<List<Integer>> bestSolution = gaTSP.evolve(initialPopulation);

        System.out.println("Best Tour Found: " + bestSolution.getChromosome());
        System.out.println("Tour Distance: " + (1.0 / bestSolution.getFitnessScore()));
    }
}
