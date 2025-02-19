package core_algorithms;

//G represents the data type of the chromosome (for n-queens it's an array)
public class Individual<G> implements Comparable<Individual<G>> {

    private final G chromosome;
    private final double fitnessScore;

    public Individual(G chromosome, double fitnessScore) {
        this.chromosome = chromosome;
        this.fitnessScore = fitnessScore;
    }

    public G getChromosome() { return chromosome; }

    public double getFitnessScore() { return fitnessScore; }

    @Override
    public String toString(){
        return "Individual{" +
                "chromosome=" + chromosome +
                ", fitnessScore" + fitnessScore +
                '}';
    }

    public int compareTo(Individual<G> o) {
        return Double.compare(o.fitnessScore, fitnessScore);
    }


}
