package astar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Puzzle {
	//best-first-search on a "puzzle"
	ArrayList<PuzzleNode> agenda;
	Map<String, PuzzleNode> closedNodes;
	PuzzleNode root;
	Map<String, PuzzleNode> nodeTable;
	ArrayList<PuzzleNode> solutions;
	PuzzleNode goal;
	
	public Puzzle() {
		//create a new puzzle and start it up, bro.
		
	}
	
	public void agendaLoop() {
		if (root == null) {
			//create the root state.
			//although this gets taken care of in the constructor
		}
		PuzzleNode result;
		do {
			result = processBestNode();
		} while (result == null);
		this.solutions.add(result);
		//when the loop ends we've got a solution!
		System.out.println("Solution found! => " + result.getKey());
	}
	
	public PuzzleNode processBestNode() {
		PuzzleNode node = agenda.get(agenda.size() - 1);
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

	public boolean isNodeSolution(PuzzleNode node) {
		return (node == goal ? true : false);
	}
	public void expandNode(PuzzleNode node) {
		node.generateSuccessors();
		for (PuzzleNode np : node.children) {
			heuristicEvaluation(np);
			//consider adding node
			considerAddingNode(np, node);
		}
		Collections.sort(agenda);
	}
	
	public boolean considerAddingNode(PuzzleNode node, PuzzleNode parent) {
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
	
	public void heuristicEvaluation(PuzzleNode node) {
		//make those heuristics, baby.
		//using math and science!
		// node.h = some value ok;
	}
	
	public void closeNode(PuzzleNode node) {
		closedNodes.put(node.getKey(), node);
		node.isOpen = false;
	}

}
