package linCheckers;

import java.util.ArrayList;

public class CheckersNode implements Comparable<CheckersNode>{
	int[] board;
	double f, g, h; //f is g = h
	boolean isOpen; //status
	CheckersNode parent; //current best parent
	ArrayList<CheckersNode> children;
	ArrayList<CheckersNode> possParents;
	
	
	public CheckersNode(int[] state, CheckersNode parent) {
		this.board = state;
		this.parent = parent;
		if (this.parent != null)
			this.g = this.parent.g + arcCost(parent);
	}
	public String getKey() {
		String s = "";
		for (int i : board)
			s+=i;
		return s;
	}
	
	public void generateSuccessors() {
		if (this.children != null)
			return; //don't re-generate children pls.
		this.children = new ArrayList<CheckersNode>();
//		this.children = new ArrayList<CheckersNode>();
		int j = getEmptyPosition();
		//there will never be more than four legal moves in any given state.
		//one of these will always be the parent node (excepting the root node/initial state)
		if (j > 1 && j < board.length - 2) {
			this.children.add(new CheckersNode(moveLeft(j), this));
			this.children.add(new CheckersNode(moveRight(j), this));
			this.children.add(new CheckersNode(moveLeftJump(j), this));
			this.children.add(new CheckersNode(moveRightJump(j), this));
			return;
		}
		if (j == 1) { //three legal moves!
			this.children.add(new CheckersNode(moveLeft(j), this));
			this.children.add(new CheckersNode(moveLeftJump(j), this));
			this.children.add(new CheckersNode(moveRight(j), this));
			return;
		}
		if (j == 0) { //there are two legal moves
			this.children.add(new CheckersNode(moveLeft(j), this));
			this.children.add(new CheckersNode(moveLeftJump(j), this));
			return;
		}
		if (j == board.length - 2) {
			this.children.add(new CheckersNode(moveLeft(j), this));
			this.children.add(new CheckersNode(moveRight(j), this));
			this.children.add(new CheckersNode(moveRightJump(j), this));
		}
		if (j == board.length - 1) {
			this.children.add(new CheckersNode(moveRight(j), this));
			this.children.add(new CheckersNode(moveRightJump(j), this));
		}
	}
	private int[] moveLeft(int zeroPos) {
		int[] Bt = this.board.clone();
		Bt[zeroPos] = this.board[zeroPos+1];
		Bt[zeroPos+1] = 0;
		return Bt;
	}
	private int[] moveLeftJump(int zeroPos) {
		int[] Bt = this.board.clone();
		Bt[zeroPos] = this.board[zeroPos+2];
		Bt[zeroPos+2] = 0;
		return Bt;
	}
	private int[] moveRight(int zeroPos) {
		int[] Bt = this.board.clone();
		Bt[zeroPos] = this.board[zeroPos-1];
		Bt[zeroPos-1] = 0;
		return Bt;
	}
	private int[] moveRightJump(int zeroPos) {
		int[] Bt = this.board.clone();
		Bt[zeroPos] = this.board[zeroPos-2];
		Bt[zeroPos-2] = 0;
		return Bt;
	}
	
	public boolean considerUpdatingParent(CheckersNode newParent) {
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
		
		for (CheckersNode pn : children)
			pn.updateG(); //horay for recursive propagation.
	}
	
	public double arcCost(CheckersNode otherNode) {
		return 0.2; // I don't even know.
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
	}
	public double getf() {
		return this.g + this.h;
	}

	@Override
	public int compareTo(CheckersNode arg0) {
		if (this.getf() == arg0.getf())
			return 0;
		return (this.getf() > arg0.getf() ? -1 : 1);
	}
	private int getEmptyPosition() {
		int j = 0;
		for (int i : board) {
			if (i == 0)
				return j;
			j++;
		}
		return -1;
	}

}

