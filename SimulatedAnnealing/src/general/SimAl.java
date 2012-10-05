package general;

public abstract class SimAl {
	
	double initT; //this lets us re-do the search with minimal hassle.
	double T; //temperature
	int Ft; //target
	double dT; //change in temperature.
	
	//also there needs to be some data structure that represents the states.
	
	public SimAl(double initTemp, int goal, double dT) {
		this.initT = initTemp;
		this.T = initTemp;
		this.Ft = goal;
		this.dT = dT;
	}
	
	public void generateNeighbours() {
		
	}

}
