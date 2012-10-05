package general;

public abstract class SimAlState {
	//this is the outline of a type of a solution state used in the algorithm.
	
	
	public abstract int evaluateObjectively();
	//objectively evaluates the state and returns a score.
	public abstract void generateNeighbours();
	//generates some amount of neighbours yo.
}
