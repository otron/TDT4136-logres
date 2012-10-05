package checkersPuzzle;

import java.util.ArrayList;

public class Board implements Comparable<Board>{
	int[] board; //this is the state
	int f; //rating
	int g; //weight due to ancestry
	int h; //weight due to heuristics
	boolean isOpen; //status
	boolean solution; //is this a solution?
	Board parent; //current best parent node
	ArrayList<Board> children;
	ArrayList<Board> possParents;
	
	public Board(int[] board) {
		this.board = board;
	}
	public Board(int K) { //K is the total amount of pieces, K+1 must thus the size of the board.
		this.board = new int[K+1];
		for (int i = 0; i < K/2; i++) {
			board[i] = 1; //1 is used to denote checkers of some type.
			board[i+1+K/2] = 2; //2 is used to denote checkers of some other type.
		}
		board[K/2] = 0; //0 is used to denote an empty space.			
	}
	
	public boolean goalState() {
		if (board[board.length/2] != 0) //is the center slot empty?
			return false;
		for (int i = 0; i < board.length/2; i++) //is the left-most half of the board occupied by checkers of some other type?
			if (board[i] != 2) //if not then well
				return false; //we're not quite there yet.
		for (int i = 1 + board.length/2; i < board.length; i++)
			if (board[i] != 1)
				return false;
		
		return true; // did we make it to the end, my friend?
	}
	public String getKey() {
		String s = "";
		for (int i : board)
			s += i;
		return s;
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
	public int heuristicEval(int[] goalState) {
		//let's just use the sum of the manhattan distances, aight.
		//how the fuck am I supposed to calculate manhattan distance here
		//ooh I could check it up against the goal state!
		//let's hard-code a goal-state?
		//yeah, and then just 
		int res = 0;
		int[] temp = board.clone();
		for (int i = 0; i < goalState.length; i++) {
			if (temp[i] != goalState[i] && temp[i] != -1) {
				for (int j = i; j < goalState.length; j++) {
					if (temp[j] == goalState[i]) {
						res += j;
						temp[j] = -1; //this is so that the manhattan distance doesn't get fudged up. Check it out.
						break;
					}
				}
			}
		}
		
		return 0;
	}
	
	public void generateSuccessors() {
		if (this.children != null) //don't re-generate children. Pls.
			return;
		
		this.children = new ArrayList<Board>();
		int j = getEmptyPosition();
		//there will never be more than four legal moves in any given state.
		//one of these will always be the parent node (excepting the root node/initial state)
		if (j > 1 && j < board.length - 2) {
			this.children.add(new Board(moveLeft(j)));
			this.children.add(new Board(moveRight(j)));
			this.children.add(new Board(moveLeftJump(j)));
			this.children.add(new Board(moveRightJump(j)));
			return;
		}
		if (j == 1) { //three legal moves!
			this.children.add(new Board(moveLeft(j)));
			this.children.add(new Board(moveLeftJump(j)));
			this.children.add(new Board(moveRight(j)));
			return;
		}
		if (j == 0) { //there are two legal moves
			this.children.add(new Board(moveLeft(j)));
			this.children.add(new Board(moveLeftJump(j)));
			return;
		}
		if (j == board.length - 2) {
			this.children.add(new Board(moveLeft(j)));
			this.children.add(new Board(moveRight(j)));
			this.children.add(new Board(moveRightJump(j)));
		}
		if (j == board.length - 1) {
			this.children.add(new Board(moveRight(j)));
			this.children.add(new Board(moveRightJump(j)));
		}
	}
	
	//all moves indicate a move towards the direction, not from it.
	//also all the move-methods are here to help. Do not be afraid
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
		Bt[zeroPos+1] = 0;
		return Bt;
	}
	private int[] moveRightJump(int zeroPos) {
		int[] Bt = this.board.clone();
		Bt[zeroPos] = this.board[zeroPos-2];
		Bt[zeroPos-2] = 0;
		return Bt;
	}

	@Override
	public int compareTo(Board o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
