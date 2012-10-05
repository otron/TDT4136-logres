package checkersPuzzle;

import java.util.ArrayList;
import java.util.Map;

public class Checkers { //BestFS
	ArrayList<Board> agenda; // the open nodes
	Map<String, Board> closedNodes;
	Board root;
	Map<String, Board> nodeTable;
	ArrayList<Board> solutions;
	int[] goalState;
	
	
	public Checkers(int K) {
		//creates a new checkers-puzzle of size K.
		this.goalState = new int[K+1];
		int[] initialState = new int[K+1];
		for (int i = 0; i < K/2; i++) {
			initialState[i] = 1; //1 is used to denote checkers of some type.
			initialState[i+1+K/2] = 2; //2 is used to denote checkers of some other type.
		}
		for (int i = 0; i < initialState.length; i++)
			goalState[i] = initialState[initialState.length - 1 - i]; //goalstate becomes a mirror of the initial state.
		this.root = new Board(initialState);
	}
	
	public void agendaLoop() {
		//we've already created a root node.
		root.isOpen = true;
		agenda.add(root);
		while (true) { //the loop
			Board result = processBest();
			if (result != null) //probably a solution.
				break;
			//do process
			processBest();
		}
	}
	
	public Board processBest() {
		Board node = agenda.get(agenda.size()-1);
		agenda.remove(agenda.size()-1);
		closedNodes.put(node.getKey(), node); //close the node
		node.isOpen = false;  //aaand close it again ????
		if (node.solution)
			return node; // okay so we found a solution that's nice I guess.
		else
			expandNode(node);
		return null;
	}
	
	public void expandNode(Board node) {
		//how do we generate successor states?
		//find all legal moves I guess.
		node.generateSuccessors();
		for (Board B : node.children) {
			//consider adding node
		}
	}
	
	public void considerAddingNode(Board node) {
		if (nodeTable.containsKey(node.getKey())) {//node exists in table
			//consider updating parent???
		}
	}
	public void considerUpdatingParent() {
		
	}
}
