import java.util.ArrayList;

/**
 * 
 * @author Odd
 *
 */
public class Board {
	
	int[] Q; //used to store the positions of placed queens
	int K; //the size of the board, coincidentally also the number of queens in an optimal solution.
	StringBuilder debugInfo; //I have no idea if this will work.
	int maxSteps; //part of the MIN-CONFLICTS algorithm
	
	
	public Board(int K, int maxSteps) {
		this.Q = new int[K];
		for (int i = 0; i < K; i++)
			this.Q[i] = -1; //hmm this idea of mine might've not been all that great.
		this.debugInfo = new StringBuilder();
	}
	
	/**
	 * 
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
