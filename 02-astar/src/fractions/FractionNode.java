package fractions;
import java.util.ArrayList;

import astar.*;

public class FractionNode extends PuzzleNode {
	int[] N, D;
	FractionNode parent;
	boolean isOpen;
	ArrayList<FractionNode> children;
	ArrayList<FractionNode> possParents;
	double f, g, h;
	String key;
	
	
	public FractionNode(int[] N, int[] D, FractionNode parent) {
		this.parent = parent;
		this.N = N;
		this.D = D;
		if (parent != null) 
			this.g = parent.g + arcCost(parent);
		this.key = this.getKey();
	}
	
	public void generateSuccessors() {
		if (this.children != null)
			return;
		//generates all possible successors of this node through the "swap" move
		this.children = new ArrayList<FractionNode>();
		int[] Dt;
		int[] Nt;
		int[] Xt = new int[D.length+N.length];
		int[] Yt;
		for (int i = 0; i < N.length; i++) {
			Xt[i] = N[i];
		}
		for (int i = 0; i < D.length; i++) 
			Xt[N.length+i] = D[i];
		for (int i = 0; i < Xt.length -1; i++) {
			for (int j = i; j < Xt.length; j++) {
				Yt = Xt.clone();
				int t = Yt[i];
				Yt[i] = Yt[j];
				Yt[j] = t;
				this.children.add(new FractionNode(new int[ ]{Yt[0], Yt[1], Yt[2], Yt[3]},
						new int[] {Yt[4], Yt[5], Yt[6], Yt[7], Yt[8]}, this));
			}
		}
		return;
		//the below code generated successors by swapping a number of N and D. I realized this does not properly generate all possible successor states.
//		for (int i = 0; i < D.length; i++) {
//			for (int j = 0; j < N.length; j++) {
//				Nt = N.clone();
//				Dt = D.clone();
//				int temp = Nt[j];
//				Nt[j] = Dt[i];
//				Dt[i] = temp;
//				this.children.add(new FractionNode(Nt, Dt, this));
//			}
//		}
	}// end generateSuccessors()
	
	public boolean considerUpdatingParent(FractionNode newParent) {
		//returns true if the parent was updated.
		if (this.parent.g > newParent.g) { //we want a lower g value!
			this.parent = newParent;
			this.updateG();
			return true;
		}
		return false; // we've not updated the parent.
	}
	private void updateG() {//updates the g value.
		this.g = this.parent.g + arcCost(this.parent);
		if (this.children == null)
			return;
		
		for (FractionNode pn : children)
			pn.updateG(); //horay for recursive propagation.
	}
	public int getDepth(int i) {
		if (this.parent == null)
			return i;
		return this.parent.getDepth(++i);
	}
	
	public String getAncestors(String s) {
		if (this.parent == null) 
			return s;
		return (s+= this.parent.getAncestors(s) + " => "+ this.parent.getKey());
		/*s += this.parent.getKey() + " => ";
		return s + this.parent.getAncestors(s) ;*/
	}
	public double getf() {
		if (this.g == 0)
			return this.h;
		else 
			return this.g + this.h;
	}
	
	public double evaluate() {
		//evaluates the fraction
		String n = "", d = "";	
		for (int i : N)
			n += Integer.toString(i);
		for (int i : D)
			d += Integer.toString(i);
		return (double) Integer.parseInt(n) / (double) Integer.parseInt(d);
	}
	
	
	public String getKey() {
		if (this.key != null)
			return this.key;
		String s = "";
		for (int i : N)
			s += Integer.toString(i);
		s += "/";
		for (int i : D)
			s += Integer.toString(i);
		return s;
	}
	
	public double arcCost(FractionNode otherNode) {
		//counts the number of differences between two nodes.
		//return 0;
		char[] s = this.getKey().toCharArray();
		char[] o = otherNode.getKey().toCharArray();
		int counter = 0;
		for (int i = 0; i < s.length; i++) {
			if (s[i] != o[i])
				counter++;
		}
		return counter/2;
		//arc cost is technically meaningless here.
	}

} //end FractionNode
