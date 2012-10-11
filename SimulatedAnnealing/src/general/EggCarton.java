package general;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

public class EggCarton extends SimAlState {
	
	int[][] grid;
	int K, M, N;
	ArrayList<int[]> eggLocs; //contains coords for eggs currently placed on grid.
	private int EGG = 1, EMPTY = 0, BLOCKED = 2; //enums are for wusses.
	
	
	public EggCarton(int M, int N, int K) {
		this.grid = new int[M][N];
		this.K = K;
		this.M = M;
		this.N = N;
	}
	public EggCarton(int[][] grid, int K) {
		this.grid = grid;
		this.K = K;
		this.M = grid.length;
		this.N = grid[0].length;
		this.update();
		this.fill();
	}
	@Override
	public double evaluateObjectively() {
		int i = this.getNumberOfEggs();
		return (i == 0 ? 0 : 1/i);
		//returns 0 if there are 0 eggs in place
		//otherwise returns 1/number of eggs
	}
	public void fill() {
		//fills the grid with eggs, whooo!
		while (this.isFilled() == false) {
			this.placeAnEgg();
		}
	}
	private boolean isFilled() {
		for (int i[] : this.grid)
			for (int j : i)
				if (j == EMPTY)
					return false;
		return true;
	}
	private void placeAnEgg() {
		//places an egg at a random empty coordinate and updates the grid.
		int[] newCoords = new int[2];
		boolean check = false;
		while (!check) {
			newCoords = this.generateRandomCoords();
			if (this.grid[newCoords[0]][newCoords[1]] == EMPTY) {
				check = true;
				this.grid[newCoords[0]][newCoords[1]] = EGG; //place dat egg.
				this.eggLocs.add(newCoords);
				this.update();
			}
		}
	}
	private void updateForEggAt(int[] coords) {
		//need to traverse the grid from the egg and check for other eggs in each line
		int cM = coords[0];
		int cN = coords[1];
		
		//horizontally
	}
	private void update() {
		//should update the state's variables to make sure of things.
		//because of reasons.
		this.grid = new int[M][N]; //wipe that state clean. Because that's the easiest way to do this.
		for (int[] i : this.eggLocs) {
			this.grid[i[0]][i[1]] = EGG;
		}
		
		//do the horizontal loop!
		int counter = 0;
		for (int i = 0; i < this.M; i++) {
			counter = 0;
			for (int j : this.grid[i])
				if (j == EGG)
					counter++;
			if (counter >= this.K) {
				//we need to block all the empty spaces on this line.
				for (int j = 0; j < this.N; j++) {
					if (this.grid[i][j] == EMPTY)
						this.grid[i][j] = BLOCKED;
				}
			}
		}
		
		//do the vertical loop!
		for (int i = 0; i < this.grid[0].length; i++) {
			counter = 0;
			for (int j = 0; j < this.grid.length; j++)
				if (this.grid[j][i] == EGG)
					counter++;
			if (counter >= this.K) {
				//we need to block all empty spaces on this vertical line.
				for (int j = 0; j < this.grid.length; j++)
					if (this.grid[j][i] == EMPTY)
						this.grid[j][i] = BLOCKED;
			}
		}
		
		//do the diagonal loops!
		boolean wat = (this.M >= this.N ? true : false);
		//wat is true if M >= N or false if M > N
		//that is to say:
		//	M >= N --> M==N or the grid is taller than it is wide
		//	M < N --> the grid is wider than it is taller
		int I = Math.max(this.M, this.N);
		int J = Math.min(this.M, this.N);
		//need to traverse the longest side here
		for (int i = 0; i < I; i++) {
			//alright so if wat then I is M
			//if not wat then I is N 
			counter = 0;
			for (int j = 0; j < J; j++) {
			}
			
		}	
	}
	private int[] generateRandomCoords() {
		Random rng = new Random();
		return (new int[] {rng.nextInt(this.M), rng.nextInt(this.N)});
	}
	private int getNumberOfEggs() {
		int res = 0;
		for (int[] i : this.grid)
			for (int j : i)
				if (j == EGG)
					res++;
		return res;
	}
	@Override
	public PriorityQueue<SimAlState> generateNeighbours(int n) {
		
		// TODO Auto-generated method stub
		return null;
	}
}
