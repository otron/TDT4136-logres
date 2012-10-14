package linCheckers;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CheckersPuzzle {
	//best-first-search on a "puzzle"
	ArrayList<CheckersNode> agenda;
	Map<String, CheckersNode> closedNodes;
	CheckersNode root;
	Map<String, CheckersNode> nodeTable;
	ArrayList<CheckersNode> solutions;
	int[] goal;
	int nodesExpanded;

	
	public CheckersPuzzle(int K) {
		this.goal = new int[K+1];
		int[] initState = new int[K+1];
		for (int i = 0; i < K/2; i++) {
			initState[i] = 1; //1 denotes a checkers piece of some type.
			initState[i+1+K/2] = 2; //2 denotes a checkers piece of some other type.
		}
		for (int i = 0; i < initState.length; i++) {
			goal[i] = initState[initState.length - 1 - i]; //goal becomes mirror of the initial state
		}
		this.root = new CheckersNode(initState, null);
		this.agenda = new ArrayList<CheckersNode>();
		this.agenda.add(root);
		this.nodeTable = new HashMap<String, CheckersNode>();
		this.closedNodes = new HashMap<String, CheckersNode>();
		this.solutions = new ArrayList<CheckersNode>();
		nodesExpanded = 0;
		
	}
	
	public long agendaLoop() {
		if (root == null) {
			//create the root state.
			//although this gets taken care of in the constructor
		}
		Date now = new Date();
		CheckersNode result;
		do {
			result = processBestNode();
		} while (result == null);
		this.solutions.add(result);
		//when the loop ends we've got a solution!
		long d = (new Date().getTime()-now.getTime()); 
		System.out.println(result.getDepth(0) + " deep solution found for K = "+ (goal.length-1) +" after "+nodesExpanded+ " expansions in "+ d + "ms with arcCost=" + result.arcCost(null));
		//System.out.println(result.getAncestors(""));
		return d;
		//System.out.println(result.getAncestors(""));
		//System.out.println(result.getAncestors(null).size());
	}
	
	public CheckersNode processBestNode() {
		CheckersNode node = agenda.get(agenda.size() - 1);
		agenda.remove(agenda.size() - 1);
		closeNode(node);
		if (node == null)
			return null;
		if (isNodeSolution(node)) {
			return node;
		} else {
			expandNode(node);
		}
		return null;
	}

	public boolean isNodeSolution(CheckersNode node) {
		for (int i = 0; i < goal.length; i++)
			if (goal[i] != node.board[i])
				return false;
		return true;
	}
	public void expandNode(CheckersNode node) {
		nodesExpanded++;
		node.generateSuccessors();
		for (CheckersNode np : node.children) {
			heuristicEvaluation(np);
			//consider adding node
			considerAddingNode(np, node);
		}
		Collections.sort(agenda);
	}
	
	public boolean considerAddingNode(CheckersNode node, CheckersNode parent) {
		if (!nodeTable.containsKey(node.getKey())) { //node doesn't exist. WE MUST ADD IT.
			nodeTable.put(node.getKey(), node);
			this.agenda.add(node);
			heuristicEvaluation(node);
			return true;
		}
		if (node.considerUpdatingParent(parent)) {
			//propagate g-improvement
			//this is done on the node.
		}
		return false;
	}
	
	public void heuristicEvaluation(CheckersNode node) {
		//let's just use the sum of the manhattan distances, aight.
		//how the fuck am I supposed to calculate manhattan distance here
		//ooh I could check it up against the goal state!
		//let's hard-code a goal-state?
		//yeah, and then just 
		//aw man no this is f'ed up
		//because it only counts from left-to-right
		int res = 0;
		int[] temp = node.board.clone();
		int[] goalT = goal.clone();
		for (int i = 0; i < goalT.length; i++) {
			if (goalT[i] != temp[i])
				if (temp[i] == 1)
					res += goal.length - 1 -i;
				if (temp[i] == 2)
					res+= i;
		}//yeah no I don't know what I thought when I wrote this.
		
//		for (int i = 0; i < goalT.length; i++) {
//			if (goalT[i] > 0 && temp[i] != goalT[i]) {
//				//uh-oh there's a mismatch!
//				//i is the position of  the mismatching tile
//				for (int j = 0; j < goalT.length; j++) {
//					if (temp[j] == goalT[i]) {
//						if (temp[j] == 2) {
//							//2s belong to the left
//							res += goal.length - 1 -j;
//							temp[j] = -1;
//							goalT[i] = -1;
//						}
//						if (temp[j] == 1) {
//							//1s belong to the right
//							res += j;
//							temp[j] = -1;
//							goalT[i] = -1;
//						}
//					}
//				}
//			}
//		}
		node.h = res;
	}
	
	public void closeNode(CheckersNode node) {
		closedNodes.put(node.getKey(), node);
		node.isOpen = false;
	}

}

