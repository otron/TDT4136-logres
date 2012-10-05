package general;

import java.util.PriorityQueue;

public abstract class SimAlState implements Comparable<SimAlState> {
	//this is the outline of a type of a solution state used in the algorithm.
	
	int F; // 
	public abstract double evaluateObjectively();
	//objectively evaluates the state and returns a score.
	public abstract PriorityQueue<SimAlState> generateNeighbours(int n);
	//generates some amount (n) of neighbours yo.
}
