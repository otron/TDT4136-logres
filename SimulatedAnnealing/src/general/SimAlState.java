package general;

import java.util.PriorityQueue;

public class SimAlState implements Comparable<SimAlState>, Cloneable {
	//this is the outline of a type of a solution state used in the algorithm.
	
	int F; // target value? Best value?
	public double evaluateObjectively() {
		return 0;
	}
	//objectively evaluates the state and returns a score.
	public PriorityQueue<SimAlState> generateNeighbours(int n) {
		return null;
	}
	//generates some amount (n) of neighbours yo.
	
	@Override
	public int compareTo(SimAlState arg0) {
		if (this.evaluateObjectively() == arg0.evaluateObjectively())
			return 0;
		return (this.evaluateObjectively() < arg0.evaluateObjectively() ? -1 : 1);
		//returns:
			//-1 if this is better
			// 0 if they are equal
			// 1 if this is worse
		//I don't know if it should be reversed yet.
	}
	@Override
	public String toString() {
		return "You forgot to override this method!";
	}
	
	public void print() {
		
	}
	
	public void debugPrint() {
		
	}
	public SimAlState clone() {
		return null;
		
	}
	public int getNumberOfThings() {
		return 0;
	}
}
