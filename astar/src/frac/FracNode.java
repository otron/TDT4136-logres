package frac;

import java.util.ArrayList;

public class FracNode implements Comparable<FracNode> {
	boolean isOpen;
	double f;
	FracNode parent;
	ArrayList<FracNode> children;
	ArrayList<FracNode> possParents;
	int[] N, D;
	double goal;
	
	public FracNode(int[] N, int[] D, double goal) {
		this.N = N;
		this.D = D;
		f = evaluate(); //find value of node.
		this.goal = goal;
	}
	
	public void generateChildren() { // this should generate all possible children of the node
		int[] Dt;
		int[] Nt;
		ArrayList<FracNode> childs = new ArrayList<FracNode>();
		for (int i = 0; i < D.length; i++) {
			for (int j = 0; j < N.length; j++) {
				Nt = N.clone();
				Dt = D.clone();
				int temp = Nt[j];
				Nt[j] = Dt[i];
				Dt[i] = temp; //now we have swapped two elements yay.
				childs.add(new FracNode(Nt, Dt, this.goal));
			}
		}
		this.children = childs;
	}
	public boolean isSolution(double goal) {
		return (evaluate() == goal ? true : false);
	}
	public double evaluate() {
		String d = "";
		String n = "";
		for (int i = 0; i < D.length; i++)
			d += Integer.toString(D[i]);
		
		for (int i = 0; i < N.length; i++)
			n += Integer.toString(N[i]);
	
		return (double) Integer.parseInt(n) / (double) Integer.parseInt(d);
	}
	
	public String getKey() {
		String s = "";
		for (int i : N)
			s += Integer.toString(i);
		for (int i : D)
			s += Integer.toString(i);
		return s;
	//	return Integer.parseInt(s);
	}
	
	public double getDistanceFromGoal() { //returns the "distance" from this node to the goal
		return (Math.abs(goal - evaluate()));
	}
	public String toString() {
		String s = "";
		for (int i : N)
			s += Integer.toString(i);
		s += "/";
		for (int i : D)
			s += Integer.toString(i);
		return s;
	}

	@Override
	public int compareTo(FracNode o) {
		if (this.getDistanceFromGoal() == o.getDistanceFromGoal())
			return 0;
		return (this.getDistanceFromGoal() < o.getDistanceFromGoal() ? -1 : 1);
		//returns 0 if they are equal.
		//returns -1 if this is closer, 0 if they are equally far apart, 1 if this is further away.
	}
}