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
		while (timeElapsed < maxTime) {
			Board b = new Board(counter, maxSteps);
			b.doSteps();
			timeElapsed = b.getTimeTaken();
		}
		
		
	}

}
