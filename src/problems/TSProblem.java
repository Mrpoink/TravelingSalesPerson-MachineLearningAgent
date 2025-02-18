package problems;

import java.util.ArrayList;
import java.util.Random;

public class TSProblem implements Problem<int[]> {

    private final int[][] board;
    public TSProblem(int[][] problem){
        this.board = problem;
    }

    public int[] getInitState(){
        int[] state = new int[board.length];
        for (int i = 0; i < board.length; i++){
            state[i] = i;
        }
        return state;
    }

    public int[] generateNewState(int[] current){
        int[] newState;
        Random rand = new Random(current.length);
        newState = swap(current, rand.nextInt(), rand.nextInt());
        return newState;
    }

    public int[] swap(int[] current, int index1, int index2){
        int city1 = current[index1];
        int city2 = current[index2];

        current[index2] = city1;
        current[index1] = city2;

        return current;
    }

    public int cost(int[] state){
        int cost = 0;
        for (int i = 0; i < state.length; i++){
            for (int j = 1; j < state.length - 1; j++){
                cost += this.board[i][j];
            }
        }
        return cost;
    }

    public void printState(int[] state){
        for (int i = 0; i < state.length; i++){
            System.out.print(state[i] + " ");
        }
        System.out.println();
        System.out.println(cost(state));
    }


}
