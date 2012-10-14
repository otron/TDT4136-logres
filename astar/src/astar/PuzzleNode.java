package astar;

import java.util.ArrayList;

public class PuzzleNode implements Comparable<PuzzleNode>{
	Object state;
	double f, g, h; //f is g = h
	boolean isOpen; //status
	PuzzleNode parent; //current best parent
	ArrayList<PuzzleNode> children;
	ArrayList<PuzzleNode> possParents;
	
	public String getKey() {
		return null;
	}
	
	public void generateSuccessors() {
		if (this.children != null)
			return; //don't re-generate children pls.
		this.children = new ArrayList<PuzzleNode>();
		//also generate children.
		//remember to set this node as the children's parent!
	}
	
	public boolean isSolution(PuzzleNode goalState) {
		//check if this node is a solution.
		return false;
	}
	public boolean considerUpdatingParent(PuzzleNode newParent) {
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
		
		for (PuzzleNode pn : children)
			pn.updateG(); //horay for recursive propagation.
	}
	
	public double arcCost(PuzzleNode otherNode) {
		//calculates the arcCost between a node and its parent.
		return 0.0;
	}
	
	public double getf() {
		return this.g + this.h;
	}

	@Override
	public int compareTo(PuzzleNode arg0) {
		if (this.getf() == arg0.getf())
			return 0;
		return (this.getf() > arg0.getf() ? -1 : 1);
	}

}
