package frac;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Frac {
	ArrayList<FracNode> agenda;
	Map<String, FracNode> closedNodes;
	FracNode root;
	Map<String, FracNode> nodes;
	ArrayList<FracNode> solutions;
	double goal;
	public static void main(String[] args) {
		int[] N = {1, 2, 3, 4}; //initial numerator
		int[] D = {5, 6, 7, 8, 9}; //initial denominator
		double[] goals = {(double) 1/2, (double) 1/3, (double) 1/4,
				(double) 1/5, (double) 1/6, (double) 1/7,
				(double) 1/8, (double) 1/9};
		Frac[] puzzles = new Frac[8];
		for (int i = 0; i < goals.length; i++) {
			puzzles[i] = new Frac(goals[i], new FracNode (N, D, goals[i]));
		}
		puzzles[6].agendaLoop();
		puzzles[6].printSolutions();
//		for (Frac f : puzzles) {
//			f.agendaLoop();
//			f.printSolutions();
//		}
	}
	public Frac(double goal, FracNode root) {
		this.goal = goal;
		this.root = root;
		this.solutions = new ArrayList<FracNode>();
		this.agenda = new ArrayList<FracNode>();
		this.closedNodes = new HashMap<String, FracNode>();
		this.nodes = new HashMap<String, FracNode>();
	}
	public void agendaLoop() {
		root.isOpen = true;
		agenda.add(root);
		while (true) {
			if (processBest() != null)
				break;
		}
	}
	public FracNode processBest() {
		FracNode p = agenda.get(0);
		agenda.remove(0);
		if (closedNodes.keySet().contains(p.getKey())) {
			// the node has already been closed
			// why are we looking at it again
			// gosh.
			return null;
		}
		System.out.println(p.toString() + "--" + p.evaluate() + "--" +p.getDistanceFromGoal() + ";" + agenda.size());
		closedNodes.put(p.getKey(), p); //close the node.
		p.isOpen = false; //why am I doing this.
		if (p.isSolution(goal)) {
			solutions.add(p);
			return p;
		} else {
			expandNode(p);
			return null;
		}
		
	}
	public void expandNode(FracNode node) {
		node.generateChildren();
		for (FracNode n : node.children) {
			if (!closedNodes.keySet().contains(n.getKey())) { //node has not yet been looked at
				if (!nodes.keySet().contains(n.getKey())) {
					agenda.add(n);
					nodes.put(n.getKey(), n);
					//add node to agenda because it's new. Yay.
					//we don't have to do anything because this puzzle disregards the path to the solution
					//so this if-clause is this implementation's consider-adding-node
				}
			}
		}
		Collections.sort(agenda); //sort agenda so that the "most promising" node is first.
	}
	public void printSolutions() {
		for (FracNode n : solutions) {
			System.out.println(n.toString() + " -> " + n.evaluate());
		}
	}
}
