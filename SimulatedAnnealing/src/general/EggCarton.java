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
		if (M >= N) {
			this.M = M;
			this.N = N;
		} else {
			this.M = N;
			this.N = M;
		}
		this.grid = new int[this.M][this.N];
		this.K = K;
		this.eggLocs = new ArrayList<int[]>();
	}
	public EggCarton(int[][] grid, int K, ArrayList<int[]> eggLocations) {
		this.K = K;
		this.M = grid.length;
		this.N = grid[0].length;
		this.grid = new int[this.M][this.N];
		this.eggLocs = eggLocations;
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
		while (!this.isFilled()) {
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
		for (int i = 0; i < this.N; i++) {
			counter = 0;
			for (int j = 0; j < this.M; j++)
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
		//wat is true if M >= N or false if M > N
		//that is to say:
		//	M >= N --> M==N or the grid is taller than it is wide
		//	M < N --> the grid is wider than it is taller
		//need to traverse the longest side here
		for (int i = 0; i < this.M; i++) {
			//alright so if wat then I is M
			//if not wat then I is N 
			counter = 0;
			int a = i;
			//iterate down-to-right
			for (int j = 0; j < this.N; j++) {
				try {
					if (this.grid[a][j] == EGG)
						counter++;
				} catch (IndexOutOfBoundsException e) {
					break;
				}
				a++;
			}
			//blocking
			if (counter >= this.K) {
				a = i;
				for (int j = 0; j < this.N; j++) {
					if (this.grid[a][j] == EMPTY)
						this.grid[a][j] = BLOCKED;
					a++;
				}
			}
			//iterate down-to-left
			counter = 0;
			a = i;
			for (int j = this.N-1; j > -1; j--) {
				try {
					if (this.grid[a][j] == EMPTY)
						counter++;
				} catch (IndexOutOfBoundsException e) {
					break;
				}
				a++;
			}
			//blocking
			if (counter >= this.K) {
				a = i;
				for (int j = this.N-1; j > -1; j--) {
					if (this.grid[a][j] == EMPTY)
						this.grid[a][j] = BLOCKED;
					a++;
				}
			}
			
			//iterate up-to-right
			counter = 0;
			a = i;
			for (int j = 0; j < this.N; j++) {
				try {
					if (this.grid[a][j] == EMPTY)
						counter++;
				} catch (IndexOutOfBoundsException e) {
					break;
				}
				a--;
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
		//n is the number of neighbours to generate
		double perc = 1.0/3.0; //percentage of placed eggs to remove
		int eggsToRemove = (int) (this.eggLocs.size() * (perc));
		if (eggsToRemove < 1)
			eggsToRemove = 1;
		Random rng = new Random();
		PriorityQueue<SimAlState> res = new PriorityQueue<SimAlState>();
		//need to create a copy of this' egg locations
		ArrayList<int[]> temp = new ArrayList<int[]>();
		for (int[] i : this.eggLocs)
			temp.add(i);
		//now we remove at least eggsToRemove eggs
		for (int i = 0; i < eggsToRemove; i++)
			temp.remove(rng.nextInt(temp.size()));
		
		//now we need to create n new solutions from the reduced version of this
		for (int i = 0; i < n; i++)
			res.add(new EggCarton(this.grid, this.K, temp));
		//this constructor takes care of filling the new states.
		
		return res;
	}
}
