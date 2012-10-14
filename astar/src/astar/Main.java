package astar;

import linCheckers.CheckersPuzzle;
import fractions.FractionPuzzle;

public class Main {
	
	public static void main(String[] args) {
		boolean doFractions = false;
		boolean doCheckers = true;
		double[] goals = {(double) 1/2, (double) 1/3, (double) 1/4,
				(double) 1/5, (double) 1/6, (double) 1/7,
				(double) 1/8, (double) 1/9};
		FractionPuzzle[] fracP = new FractionPuzzle[goals.length];
		for (int i = 0; i < goals.length; i++) {
			fracP[i] = new FractionPuzzle(goals[i]);
		}
		int k = 0;
		if (doFractions) {
			for (FractionPuzzle FP : fracP) {
				System.out.println("Doing puzzle #" + ++k +": " + goals[k-1]);
				FP.agendaLoop();
			}
		}
		int i = 6;
		long time = 0;

		if (doCheckers) {
			do {
				CheckersPuzzle cp = new CheckersPuzzle(i);
				time = cp.agendaLoop();
				i+=2;
			} while (time < 10000000);
		}
		
	}

}
