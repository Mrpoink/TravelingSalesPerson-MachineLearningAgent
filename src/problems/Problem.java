package problems;

//State parameter of data type state
public interface Problem<S> {
    //Given current state, generate random child state(node)
    public S generateNewState(S current);

    //Must be able to determine cost of a given state
    public int cost(S state);

    //get initial state
    public S getInitState();

    //Calculation for finding the cost of the goal state

    public void printState(S state);

}