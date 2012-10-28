import java.io.IOException;

//import java.util.Date;

//this is just for organizational purposes.
public class Tests {
	
	/**
	 * finds the maximum K that can be solved for in less than a minute.
	 */
	public static void timeTrial() {
		
		long maxTime = 60000,
			 timeElapsed = 0;
		int counter = 4,
			maxSteps = 100;
		Board b = null;
		while (timeElapsed < maxTime) {
			b = new Board(counter, maxSteps);
			b.doSteps();
			timeElapsed = b.getTimeTaken();
			System.out.println("K="+counter+"; "+b.isOptimal()+"; "+timeElapsed +" ms");
			counter++;
		}
		System.out.println("K="+counter+"; "+b.isOptimal()+"; "+timeElapsed +" ms");
	}

	public static void timeTests(int startK, int maxSteps, int n, long maxTime) {
		System.err.println("Printing average ");
		long time = 0;
		for (int i = startK; time < maxTime; i+= 100) {
			time = 0;
			for (int j = 0; j < n; j++) {
				Board b;
				do {
					b = new Board(i, maxSteps);
					b.doSteps();
					time += b.getTimeTaken();
				} while (!b.isOptimal());
				//time = time until the first correct solution was found
			}
			System.out.println(i+","+(double)time/n);
		}
	}
	public static void correctnessTest(int K, int times, int maxSteps) {
		System.err.println("Doing correctness test with K="+K+", maxSteps="+maxSteps+", n="+times);
		int a = 0, b =0;
		int steps = 0;
		for (int i = 0; i < times; i++) {
			Board board = new Board(K, maxSteps);
			board.doSteps();
			if (board.isOptimal()) {
				a++;
			}
			else
				b++;
			steps+=board.getSteps();
		}
		System.out.println("#optimals: "+a);
		System.out.println("#!optimal: "+b);
		System.out.println();
		System.out.println("avg steps: "+(double) steps/times);
	}
	
	public static void timeSteps(int K, int n) {
		System.err.println("Doing timeSteps with K="+K+", n="+n);
		int maxSteps = 1000;
		long steps = 0,
			time = 0;
		for (int i = 0; i < n; i++) {
			Board b = new Board(K, maxSteps);
			b.doSteps();
			steps += b.getSteps();
			time += b.getTimeTaken();
		}
		System.out.println("Steps: "+steps+", time: "+time);
		System.out.println("time/step: "+(double)time/steps);
	}
	
	public static void timeStepsForKRange(int n, int maxK) {
		int maxSteps = 10;
		long steps, time;
		long totalSteps = 0, totalTime = 0;
		StringBuilder sb = new StringBuilder();
		for (int i = 4; i < maxK+1; i++) {
			time = 0;
			steps = 0;
			for (int j = 0; j < n; j++) {
				Board b = new Board(i, maxSteps);
				b.doSteps();
				steps += b.getSteps();
				time += b.getTimeTaken();
			}
			totalSteps += steps;
			totalTime += time;
			sb.append("K="+i+";"+(double)time/steps+"\n");
		}
		System.out.println(sb.toString());
	}
	
	public static void findMaxTime(int maxSteps, long maxTime) {
		long time = 0;
		int K = 10000;
		do {
			Board b = new Board(K, maxSteps);
			b.doSteps();
			time = b.getTimeTaken();
			System.out.println("K="+K+";"+time);
			K += 100000;
		} while (time < maxTime);
	}
}
