package general;

import java.util.PriorityQueue;
import java.util.Random;

public class SimAl {
	
	double initT; //this lets us re-do the search with minimal hassle.
	double T; //temperature
	int Ft; //target
	double dT; //change in temperature.
	SimAlState bestState; //I couldn't think of a reason for not doing this.
	SimAlState initialState;
	boolean verbose; //you know what this is.
	int ntg; //Neighbours to generate. Go team acronyms!
	Random rng; //rng rngggg
	int[] initialStateStats;
	
	//also there needs to be some data structure that represents the states.
	//no we're doing that on the actual states!
	//hoo-fucking-ray.
	
	
	public SimAl(double initTemp, int goal, double dT, SimAlState initialState, int n) {
		this.initT = initTemp;
		this.T = initTemp;
		this.Ft = goal;
		this.dT = dT;
		this.bestState = initialState.clone();
		this.initialState = bestState;
		this.ntg = n;
		this.rng = new Random();
	}
	
	public static void main(String args[]) {
		EggCarton derp = new EggCarton(20, 20, 3);
		SimAl herp = new SimAl(1.0, 10, 0.1, derp, 8);
		herp.setVerbosity(false);
		int[] stats = new int[100];
		int[] yesmygod = new int[80];
		for (int i = 0; i < stats.length; i++) {
			int d = herp.dueProcess();
			stats[i] = d;
			yesmygod[d]++; 
		}
		
		for (int i : yesmygod)
			System.out.print(i+" ");
		
	}
	public static void doPuzzles() {
		
	}
	
	
	
	public int dueProcess() {
		//haha am I the wittiest fellow ever or what?
		reset();
		double bestObjVal = bestState.evaluateObjectively();
		SimAlState pony = bestState;
		int[] om = new int[2];
		boolean b = false;
		while (bestObjVal < Ft && T > 0) { //Enter the realm of magic.
			pony = bestState;
			if (verbose) {
				System.out.println("Temp = " + this.T);
			}
			PriorityQueue<SimAlState> neighbours = bestState.generateNeighbours(ntg); 
				//used to hold generated neighbours and automatically sort them.
			double k = neighbours.peek().evaluateObjectively();

			//if x > p
			if (generateX() > getP(k, bestObjVal)) {
				bestState = neighbours.peek(); //Exploiting
				om[0]++;
				b = true;
			}
				
			else {
				bestState = pickRandomFromNeighbours(neighbours); //Exploring
				om[1]++;
				b = false;
			}
//			System.out.println("Iteration " + Math.round((1-T)*10) +": " +bestState.getNumberOfThings() + ", " + (b?"Exploited":"Explored"));
		
			T = T-dT;
		}//Beyond the realm of magic.
			//System.out.println(om[0] + ", " + om[1]);
		return bestState.getNumberOfThings();
		
	}
	public void reset() {
		this.T = this.initT;  
		this.bestState = this.initialState.clone();
		//well that was easy.
	}

	private SimAlState pickRandomFromNeighbours(PriorityQueue<SimAlState> list) {
		int a = rng.nextInt(list.size());
		//a is now a random integer in the range [0, ntg)
		//so now we just have to get the element with index = a in the list. 
		for (int i = 0; i < a; i++) //will perform a-1 iterations.
			list.iterator().next(); 
		return list.iterator().next();
	}
	private double generateX() {
		return rng.nextDouble();
		//This return value can be found in the range [0,1)
		//The algorithm calls for a random number in the range [0,1]
		//Let's hope no one notices.
	}

	private double getP(double i, double j) {
		//calculate q first.
		//i = F(P_max)
		//j = F(P)
		double q = (double) (i-j)/j;
		return Math.min(1, Math.exp(-q/T)); //gotta be careful with these parenthopodes
	}
	public void setVerbosity(boolean v) {
		this.verbose = v;
	}
	public static void tests() {
		int M = 9, N = 5;
		int[][] kk = new int[M][N];
		boolean doR, upR, doL, upL, V, H;
		doR = false;
		upR = false;
		doL = false;
		upL = false;
		V = false;
		H = false;
		
		
		if (H) {
			//for (int j = 0; j < this.N && i+j < this.M; j++)
			int cc = 0;
			for (int i = 0; i < M; i++) {
				cc++;
				for (int j = 0; j < N; j++)
					kk[i][j] = cc;
			}					
		}
		
		if (V) {
			int cc = 0;
			for (int j = 0; j < N; j++) {
				cc++;
				for (int i = 0; i < M; i++) 
					kk[i][j] = cc;
			}
		}
		
		
		//test of up-right
		if (upR) {
			int cc = 0;
			for (int i = 0; i < M; i++){
				cc++;
				for (int j = 0; j < N && i-j > -1; j++)
					kk[i-j][j] = cc;
			}
		}
		if (doR) {
			int cc = 0;
			for (int i = 0; i < M; i++) {
				cc++;
				for (int j = 0; j < N && i+j < M; j++)
					kk[i+j][j] = cc;
			}
		}
		if (doL) {
			int cc = 0;
			for (int i = 0; i < M; i++) {
				cc++;
				for (int j = N - 1, a = 0; j > -1 && i+a < M; j--, a++) {
					kk[i+a][j] = cc;
				}
					
			}
		}
		if (upL) {
			int cc = 0;
			for (int i = 0; i < M; i++) {
				cc++;
				for (int j = N - 1, a = 0; j > -1 && i-a > -1; j--, a++) {
					kk[i-a][j] = cc;
				}
			}
		}
		
		if (upL || doL || upR || doR || H || V) {
			for (int[] i : kk) {
				for (int j : i) 
					System.out.print(j + " ");
				System.out.println();
		}
		}
		
	}
}
