import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author Odd
 *
 */
public class Board {
	
	int[] Q; //used to store the positions of placed queens. 
		//Q[i] = k --> i is the row#, k is the column#
	int K; //the size of the board, coincidentally also the number of queens in an optimal solution.
	StringBuilder debugInfo; //I have no idea if this will work.
	int maxSteps; //part of the MIN-CONFLICTS algorithm
	
	
	public Board(int K, int maxSteps) {
		this.K = K;
		this.Q = new int[this.K];
		this.maxSteps = maxSteps;
		this.debugInfo = new StringBuilder();
		
		this.initialize();
	}
	
	public void doSteps() {
		int counter = 0;
		Random rng = new Random();
		while (counter < this.maxSteps && !this.isOptimal()) {
			//First we select a random queen.
			int q = rng.nextInt(this.K);
			
			//now we need to generate the number of conflicts for each cell in q's row.
			
			counter++;
		}
	}
	
	/**
	 * 
	 * @param q The row to find conflicts for
	 * @return An array with the number of conflicts
	 */
	private int[] getConflictsForRow(int q) {
		int[] res = new int[this.K];
		
		return null;
	}
	/**
	 * Generates the initial board-state per the assignment's specifications
	 */
	private void initialize() {
		Random rng = new Random();
		//Randomly places one queen in each row.
		for (int i = 0; i < this.K; i++)
			this.Q[i] = rng.nextInt(this.K);
	}
	
	/**
	 * TODO: fix because this won't work
	 * @return returns true if the board has a Queen in every column, false if it does not.
	 */
	public boolean isOptimal() {
		return (this.countQueens() == this.K ? true : false);
	}
	
	/**
	 * 
	 * @return returns the number of queens currently in place on the board.
	 */
	public int countQueens() {
		int res = 0;
		for (int i : this.Q)
			if (i != -1)
				res++;
		return res;
	}
}
