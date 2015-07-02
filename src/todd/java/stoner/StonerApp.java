package todd.java.stoner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StonerApp {

	public static void main(String[] args) throws InterruptedException {

		ExecutorService executor = Executors.newFixedThreadPool(10);

		HippyCircle circle = new HippyCircle();
		List<Stoner> stoners = new ArrayList<>();
		stoners.add(new Stoner(SmokingItem.Matches, circle));
		stoners.add(new Stoner(SmokingItem.Paper, circle));
		stoners.add(new Stoner(SmokingItem.Weed, circle));
		circle.setCurrentStoner(stoners.get(0));
		circle.setStoners(stoners);
		System.out.println("Trying to Smoke...");

		for (Stoner smoker : stoners) {
			executor.submit(smoker);
		}

		executor.awaitTermination(2, TimeUnit.SECONDS);
		executor.shutdownNow();

		System.out.println("done.");
	}

}


