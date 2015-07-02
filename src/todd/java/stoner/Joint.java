package todd.java.stoner;

import java.util.Random;

public class Joint {

	private int remainingHits;
	private static Random random = new Random(System.currentTimeMillis());
	
	public Joint() {
		remainingHits = random.nextInt(5) + 7;
	}

	public int getRemainingHits() {
		return remainingHits;
	}
	
	public synchronized void takeHit() {
		--remainingHits;
	}
}
