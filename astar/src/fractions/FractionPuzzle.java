package fractions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import astar.*;

public class FractionPuzzle extends Puzzle {
	double goal;
	ArrayList<FractionNode> agenda;
	Map<String, FractionNode> closedNodes;
	FractionNode root;
	Map<String, FractionNode> nodeTable;
	ArrayList<FractionNode> children;
	ArrayList<FractionNode> possParents;
	ArrayList<FractionNode> solutions;
	int nodesExpanded;

	public FractionPuzzle(double goal) {
		this.goal = goal;
		this.root = new FractionNode(new int[] {1, 2, 3, 4},new int[] {5, 6, 7, 8, 9}, null);
		this.solutions = new ArrayList<FractionNode>();
		this.agenda = new ArrayList<FractionNode>();
		this.agenda.add(root);
		this.nodeTable = new HashMap<String, FractionNode>();
		this.closedNodes = new HashMap<String, FractionNode>();
		nodesExpanded = 0;
	}
	public void agendaLoop() {
		if (root == null) {
			//create the root state.
			//although this gets taken care of in the constructor
		}
		Date now = new Date();
		FractionNode result;
		do {
			result = processBestNode();
		} while (result == null);
		this.solutions.add(result);
		//when the loop ends we've got a solution!
		long time = new Date().getTime() - now.getTime();
		System.out.println(result.getDepth(0) +" deep solution found: " + result.getKey() + " for goal = "+goal+", after " + nodesExpanded + " expansions in " + time +"ms");
		System.out.println(result.getAncestors(""));
	}
	
	public FractionNode processBestNode() {
		FractionNode node = agenda.get(agenda.size() - 1);
		agenda.remove(agenda.size() - 1);
		closeNode(node);
		//System.out.println(node.getKey() + "--" + node.getf() +"--"+ node.evaluate());
		if (node == null)
			return null;
		if (isNodeSolution(node)) {
			return node;
		} else {
			expandNode(node);
		}
		return null;
	}
	public void expandNode(FractionNode node) {
		node.generateSuccessors();
		nodesExpanded++;
		for (FractionNode np : node.children) {
			heuristicEvaluation(np);
			//consider adding node
			considerAddingNode(np, node);
		}
		Collections.sort(agenda);
	}
	public boolean considerAddingNode(FractionNode node, FractionNode parent) {
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
	
	public void heuristicEvaluation(FractionNode node) {
		//estimates distance to goal
		node.h = 350 * (Math.abs(goal - node.evaluate()));
	}
	
	public boolean isNodeSolution(FractionNode node) {
		return ((node.evaluate()-goal) == 0 ? true : false);
	}
	public void closeNode(FractionNode node) {
		closedNodes.put(node.getKey(), node);
		node.isOpen = false;
	}

}
