import java.util.Date;

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

	public static void correctnessTest(int K, int times, int maxSteps) {
		System.err.println("Doing correctness test with K="+K+", maxSteps="+maxSteps+", n="+times);
		int a = 0, b =0;
		int steps = 0;
		for (int i = 0; i < times; i++) {
			Board board = new Board(K, maxSteps);
			board.doSteps();
			if (board.isOptimal()) {
				a++;
				steps+=board.getSteps();
			}
			else
				b++;
		}
		System.out.println("#optimals: "+a);
		System.out.println("#!optimal: "+b);
		System.out.println("avg steps: "+(double) steps/times);
	}
}
