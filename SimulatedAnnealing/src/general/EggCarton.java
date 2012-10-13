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
		this.fill();
	}
	public EggCarton(int[][] grid, int K, ArrayList<int[]> eggLocations) {
		this.K = K;
		this.M = grid.length;
		this.N = grid[0].length;
		this.grid = new int[this.M][this.N];
		this.eggLocs = eggLocations;
		this.randomize();
		this.update();
		this.fill();
	}
	@Override
	public double evaluateObjectively() {
		int i = this.getNumberOfEggs();
		return (i == 0 ? 0 : (1.0 - 1.0/(2.0*(double)i)));
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

	private void update() {
		//should update the state's variables to make sure of things.
		//because of reasons.
		this.grid = new int[M][N]; //wipe that state clean. Because that's the easiest way to do this.
			//uninitialized integers are set to 0
		
		//place all those delicious eggs.
		for (int[] i : this.eggLocs)
			this.grid[i[0]][i[1]] = EGG;

		//check if any blocking is necessary.
		
		//do the horizontal loop!
		int counter = 0;
		for (int i = 0; i < this.M; i++) {
			counter = 0;
			for (int j = 0; j < this.N; j++)
				if (this.grid[i][j] == EGG)
					counter++;
			if (counter >= this.K)
				for (int j = 0; j < this.N; j++)
					if (this.grid[i][j] == EMPTY)
						this.grid[i][j] = BLOCKED;
		}
		
		//do the vertical loop!
		for (int j = 0; j < this.N; j++) {
			counter = 0;
			for (int i = 0; i < this.M; i++)
				if (this.grid[i][j] == EGG)
					counter++;
			//blocking
			if (counter >= this.K)
				for (int i = 0; i < this.M; i++)
					if (this.grid[i][j] == EMPTY)
						this.grid[i][j] = BLOCKED;
		}
		
		//do the diagonal loops!
		//need to traverse the longest side here
		//which is always M because fuck you!
		for (int i = 0; i < this.M; i++) { 
			counter = 0;
			//down-right
			for (int j = 0; j < this.N && i+j < this.M; j++)
				//down-right is [i+j,j] as j++
				try {
					if (this.grid[i+j][j] == EGG)
						counter++;
				} catch (IndexOutOfBoundsException e) {
					break;
				}
				//blocking
			if (counter >= this.K)
				for (int j = 0; j < this.N && i+j < this.M; j++)
					if (this.grid[i+j][j] == EMPTY)
						this.grid[i+j][j] = BLOCKED;
			//alternatively a = (a==EMPTY ? BLOCKED : a) but a is so long it'll look worse.
			
			//up-right
			counter = 0;
			for (int j = 0; j < this.N && i-j > -1; j++)
				if (this.grid[i-j][j] == EGG)
					counter++;
				//blocking
			if (counter >= this.K)
				for (int j = 0; j < this.N && i-j > -1; j++)
					if (this.grid[i-j][j] == EMPTY)
						this.grid[i-j][j] = BLOCKED;
			
			//down-left
			counter = 0;
			for (int j = (this.N - 1), a = 0; j > -1 && i+a < this.M; j--, a++)
				if (this.grid[i+a][j] == EGG)
					counter++;
				//blocking
			
			if (counter >= this.K)
				for (int j = (this.N - 1), a = 0; j > -1 && i+a < this.M; j--, a++)
					if (this.grid[i+a][j] == EMPTY)
						this.grid[i+a][j] = BLOCKED;
			
			//up-left
			counter = 0;
			for (int j = (this.N - 1), a = 0; j > -1 && i-a > -1; j--, a++)
				if (this.grid[i-a][j] == EGG)
					counter++;
				//blocking
			if (counter >= this.K)
				for (int j = (this.N - 1), a = 0; j > -1 && i-a > -1; j--, a++)
					if (this.grid[i-a][j] == EMPTY)
						this.grid[i-a][j] = BLOCKED;
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
	public int getNumberOfThings() {
		return this.getNumberOfEggs();
	}
	@Override
	public EggCarton clone() {
		return new EggCarton(this.M, this.N, this.K);
	}
	@Override
	public PriorityQueue<SimAlState> generateNeighbours(int n) {
		//n is the number of neighbours to generate
		PriorityQueue<SimAlState> res = new PriorityQueue<SimAlState>();
		for (int i = 0; i < n; i++)
			res.add(new EggCarton(this.grid, this.K, this.eggLocs));
		//the constructor takes care of the rest.
//		
//		int eggsToRemove = (int) (this.eggLocs.size() * (perc));
//		if (eggsToRemove < 1)
//			eggsToRemove = 1;
//		Random rng = new Random();
//		//need to create a copy of this' egg locations
//		ArrayList<int[]> temp = new ArrayList<int[]>();
//		for (int[] i : this.eggLocs)
//			temp.add(i);
//		
//		//now we remove at least eggsToRemove eggs
//		for (int i = 0; i < eggsToRemove; i++)
//			temp.remove(rng.nextInt(temp.size()));
//		
//		//now we need to create n new solutions from the reduced version of this
//		for (int i = 0; i < n; i++)
//			res.add(new EggCarton(this.grid, this.K, temp));
//		//this constructor takes care of filling the new states.
//		
		return res;
	}
	private void randomize() {
		double perc = 0.01; //percentage of placed eggs to remove
		int eggsToRemove = (int) (this.eggLocs.size() * (perc));
		if (eggsToRemove < 1)
			eggsToRemove = 1;
		Random rng = new Random();
		
		for (int i = 0; i < eggsToRemove; i++) {
			//removes a randomly selected egg.
			this.eggLocs.remove(rng.nextInt(this.eggLocs.size()));
		}
	}
	@Override
	public void print() {
		this.update();
		for (int[] i : this.grid) {
			for (int j : i)
				System.out.print(j+" ");
			System.out.println();
		}
		System.out.println("Score: " + this.evaluateObjectively() + ". Eggs placed: " + this.getNumberOfEggs());
	}
	
	public void debugPrint() {
		//should update the state's variables to make sure of things.
				//because of reasons.
				this.grid = new int[M][N]; //wipe that state clean. Because that's the easiest way to do this.
					//uninitialized integers are set to 0
				
				//place all those delicious eggs.
				for (int[] i : this.eggLocs)
					this.grid[i[0]][i[1]] = EGG;

				for (int[] i : this.grid) {
					for (int j : i)
						System.out.print(j+" ");
					System.out.println();
				}
				System.out.println("Score: " + this.evaluateObjectively() + ". Eggs placed: " + this.getNumberOfEggs());
				//check if any blocking is necessary.
				int counter = 0;
				
				//do the vertical loop!
//				for (int j = 0; j < this.N; j++) {
//					counter = 0;
//					for (int i = 0; i < this.M; i++)
//						if (this.grid[i][j] == EGG)
//							counter++;
//					//blocking
//					if (counter >= this.K)
//						for (int i = 0; i < this.M; i++)
//							if (this.grid[i][j] == EMPTY)
//								this.grid[i][j] = BLOCKED;
//				}
				
				//do the horizontal loop!
//				for (int i = 0; i < this.M; i++) {
//					counter = 0;
//					for (int j = 0; j < this.N; j++)
//						if (this.grid[i][j] == EGG)
//							counter++;
//					if (counter >= this.K)
//						for (int j = 0; j < this.N; j++)
//							if (this.grid[i][j] == EMPTY)
//								this.grid[i][j] = BLOCKED;
//				}
				

				
				//do the diagonal loops!
				//need to traverse the longest side here
				//which is always M because fuck you!
				for (int i = 0; i < this.M; i++) { 
					counter = 0;
					//down-right
//					for (int j = 0; j < this.N && i+j < this.M; j++)
//						//down-right is [i+j,j] as j++
//						try {
//							if (this.grid[i+j][j] == EGG)
//								counter++;
//						} catch (IndexOutOfBoundsException e) {
//							break;
//						}
//						//blocking
//					if (counter >= this.K)
//						for (int j = 0; j < this.N && i+j < this.M; j++)
//							if (this.grid[i+j][j] == EMPTY)
//								this.grid[i+j][j] = BLOCKED;
					//alternatively a = (a==EMPTY ? BLOCKED : a) but a is so long it'll look worse.
					
					//up-right
//					counter = 0;
//					for (int j = 0; j < this.N && i-j > -1; j++)
//						if (this.grid[i-j][j] == EGG)
//							counter++;
//						//blocking
//					if (counter >= this.K)
//						for (int j = 0; j < this.N && i-j > -1; j++)
//							if (this.grid[i-j][j] == EMPTY)
//								this.grid[i-j][j] = BLOCKED;

					//down-left
					counter = 0;
//					for (int j = (this.N - 1), a = 0; j > -1 && i+a < this.M; j--, a++)
//						if (this.grid[i+a][j] == EGG)
//							counter++;
//						//blocking
//					
//					if (counter >= this.K)
//						for (int j = (this.N - 1), a = 0; j > -1 && i+a < this.M; j--, a++)
//							if (this.grid[i+a][j] == EMPTY)
//								this.grid[i+a][j] = BLOCKED;
					
					//up-left
					counter = 0;
					for (int j = (this.N - 1), a = 0; j > -1 && i-a > -1; j--, a++)
						if (this.grid[i-a][j] == EGG)
							counter++;
						//blocking
					if (counter >= this.K)
						for (int j = (this.N - 1), a = 0; j > -1 && i-a > -1; j--, a++)
							if (this.grid[i-a][j] == EMPTY)
								this.grid[i-a][j] = BLOCKED;
					
					for (int[] fuck : this.grid) {
						for (int shit : fuck)
							System.out.print(shit+" ");
						System.out.println();
					}
					System.out.println();
										
				}				
	}
}
