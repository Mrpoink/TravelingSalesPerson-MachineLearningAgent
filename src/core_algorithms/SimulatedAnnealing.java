package core_algorithms;
import problems.Problem;

import java.util.Random;

//S is the state data type
//Declared abstract due to abstract class
public abstract class SimulatedAnnealing <S>{

    //Key Items needed
    private long time;
    private double temperature;

    private final Problem<S> problem;

    public SimulatedAnnealing(long initTime, double initTemp, Problem<S> problem) {
        //Constructor defining private variables
        this.time = initTime;
        this.temperature = initTemp;
        this.problem = problem;
    }

    //abstract since multiple schedules could be made (multiple ways to do this)
    public abstract double schedule (long time, double temperature);

    public boolean accept(double delta, double temp){
        if(delta > 0){
            //if ΔΕ is greater than zero, we're good
            return true;
        }
        else{
            //Algorithm for finding the true probability, so
            //we check if a random value is greater than
            //as a random value within a probability makes sense
            //difficult to describe, think of the normalization curve
            double probability = Math.exp(delta/temp);
            assert probability >= 0 && probability <= 1;
            Random rand = new Random();

            return probability > rand.nextDouble();
        }
    }

    public void search(){
        //Search algorithm
        S currentState = problem.getInitState();                                //Start with initial state
        while (temperature>0){

            if(problem.cost(currentState) == problem.goalCost()){
                break;
            }

            S newState = problem.generateNewState(currentState);
            double delta_e = problem.cost(currentState) - problem.cost(newState);
            if(accept(delta_e, temperature)){
                currentState = newState;
            }
            time ++;
            temperature = schedule(time, temperature);
        }
        problem.printState(currentState);
        System.out.println("Final cost: " + problem.cost(currentState));

    }
}

