package general;

import java.util.PriorityQueue;
import java.util.Random;

public class SimAl {
	
	double initT; //this lets us re-do the search with minimal hassle.
	double T; //temperature
	int Ft; //target
	double dT; //change in temperature.
	SimAlState bestState; //I couldn't think of a reason for not doing this.
	boolean verbose; //you know what this is.
	int ntg; //Neighbours to generate. Go team acronyms!
	Random rng; //rng rngggg
	
	//also there needs to be some data structure that represents the states.
	//no we're doing that on the actual states!
	//hoo-fucking-ray.
	
	
	public SimAl(double initTemp, int goal, double dT, SimAlState initialState, int n) {
		this.initT = initTemp;
		this.T = initTemp;
		this.Ft = goal;
		this.dT = dT;
		this.bestState = initialState;
		this.ntg = n;
		this.rng = new Random();
	}
	
	
	public void dueProcess() {
		//haha am I the wittiest fellow ever or what?
		reset();
		double bestObjVal = bestState.evaluateObjectively();
		while (bestObjVal < Ft) { //Enter the realm of magic.
			PriorityQueue<SimAlState> neighbours = bestState.generateNeighbours(ntg); 
				//used to hold generated neighbours and automatically sort them.
			double k = neighbours.peek().evaluateObjectively();

			//if x > p
			if (generateX() > getP(k, bestObjVal))
				bestState = neighbours.peek(); //Exploiting
			else
				bestState = pickRandomFromNeighbours(neighbours); //Exploring
			
			T = T-dT;
		}//Beyond the realm of magic.
		
	}
	public void reset() {
		this.T = this.initT;
		//well that was easy.
	}

	private SimAlState pickRandomFromNeighbours(PriorityQueue<SimAlState> list) {
		int a = rng.nextInt(ntg);
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
}
