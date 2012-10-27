import java.util.ArrayList;


public class Board {
	
	int[] Q; //used to store the positions of placed queens
	int K; //the size of the board, coincidentally also the number of queens in an optimal solution.
	
	
	public Board(int K) {
		this.Q = new int[K];
		
	}
	
	/**
	 * 
	 * @return returns true if the board has a Queen in every column, false if it does not.
	 */
	public boolean isOptimal() {
		return (this.countQueens() == this.K ? true : false);
	}
	
	public int countQueens() {
		int res = 0;
		for (int i : this.Q)
			if (i != -1)
				res++;
		return res;
	}
}
