import java.awt.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * 
 * @author Odd
 *
 */
public class Board {
	
	public static void main(String args[]) {
		Tests.timeTrial();
	}
	
	int[] Q; //used to store the positions of placed queens. 
		//Q[i] = k --> i is the row#, k is the column#
	final int K; //the size of the board, coincidentally also the number of queens in an optimal solution.
	StringBuilder debugInfo; //I have no idea if this will work.
	final int maxSteps; //part of the MIN-CONFLICTS algorithm
	int stepsDone;
	long timeTaken;
	
	
	public Board(int K, int maxSteps) {
		this.K = K;
		this.Q = new int[this.K];
		this.maxSteps = maxSteps;
		this.debugInfo = new StringBuilder();
		
		this.initialize();
	}
	
	public void doSteps() {
		long startTime = new Date().getTime();
		int counter = 0;
		Random rng = new Random();
		while (counter < this.maxSteps && !this.isOptimal()) {
			//First we select a random queen.
			int q = rng.nextInt(this.K); //(a)
			
			//Find conflicts for each cell in row q.
			int[] rows = this.findConflictsForRow(q);
			
			//find the lowest conflict count
			int min = Integer.MAX_VALUE;
			for (int i : rows)
				min = (i < min ? i : min);
			
			//find which cells has this conflict count
			ArrayList<Integer> ls = new ArrayList<Integer>();
			for (int i = 0; i < this.K; i++) {
				if (rows[i] == min)
					ls.add(i);
			}
			
			//is there only one cell with the minimum conflict count and does this happen to be the one the queen is already in?
			if (min > rows[q] && ls.size() == 1) {
				//remain as you were, gentlequeens.
			} else { //oh okay.
				//pick a random one to put the queen in
				int a = rng.nextInt(ls.size());
				this.Q[q] = ls.get(a);
			}
			//yeah I'm sure there's nothing that could go wrong here.
			counter++;
		}
		this.stepsDone = counter;
		this.timeTaken = (new Date().getTime()) - startTime;
	}
	
	/**
	 * 
	 * @param q The row to find conflicts for
	 * @return An array with the number of conflicts
	 */
	private int[] findConflictsForRow(int n) {
		int[] res = new int[this.K];
		for (int i = 0; i < this.K; i++) {
			if (i != n) { //we are not doing this for the queen on the row we are finding collisions for
				/** Math that junk up.
				 * 
				 * All right. Listen up:
				 * queen_i at [j][c] can cross row n at a maximum of three points:
				 * [n][c] (straight line)
				 * [n][c+d] (down-right if n>i, up-left if n<i)
				 * [n][c-d] (down-left if n>i, up-right if n<i)
				 * d = |n-i|
				 * 
				 * So what we are going to do is see if any of these three points exist on the grid
				 * then just increase the value of the appropriate cell in res by one if they are.
				 */
				int c = this.Q[i],
					d = Math.abs(n-i);
				res[c]++; 		// [n][c]
				if (c-d > -1)
					res[c-d]++; // [n][c-d]
				if (c+d < this.K)
					res[c+d]++;	// [n][c+d]
				//that was a lot easier than I expected
			}
		}
		return res;
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
	 * 
	 * @return returns true if there are no conflicting queens on the board.
	 */
	public boolean isOptimal() {
		for (int i = 0; i < this.K; i++) {
			int[] derp = this.findConflictsForRow(i);
			if (derp[this.Q[i]] > 0) { //Are there any other queens that conflict with this one?
				return false;
			}
		}
		return true;
		// This could be done on a queen-by-queen basis. I think that would be more elegant or whatevs.
		// Whatevs.
	}
	
	public String getResults() {
		StringBuilder sb = new StringBuilder();
		sb.append("Solution is optimal? ");
		sb.append(this.isOptimal());
		
		sb.append("\n");
		sb.append("Solution found in ");
		sb.append(this.stepsDone + "/" + this.maxSteps);
		
		return sb.toString();
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		boolean[][] k = new boolean[this.K][this.K];
		for (int i = 0; i < this.K; i++)
			k[i][this.Q[i]] = true;
		
		for (int i = 0; i < this.K; i++) {
			for (int j = 0; j < this.K; j++) {
				sb.append(k[i][j] ? "Q" : "X");
				if (j+1 != this.K)
					sb.append(" ");
			}
			if (i+1 != this.K)
				sb.append("\n");
		}
		return sb.toString();
	}
	
	public long getTimeTaken() {
		return this.timeTaken;
	}
	
	/**
	 * 
	 * @return returns the number of queens currently in place on the board.
	 */
	public int countQueens() {
		return this.K;
		//due to the initialization step there will always be K queens in place on the board.
	}
}
