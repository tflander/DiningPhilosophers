package todd.java.stoner2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StonerApp {

	public static void main(String[] args) throws InterruptedException {

		ExecutorService executor = Executors.newFixedThreadPool(10);

		StonerMessageQueue queue = new StonerMessageQueue();
		List<Stoner> stoners = new ArrayList<>();
		stoners.add(new Stoner(SmokingItem.Matches, queue));
		stoners.add(new Stoner(SmokingItem.Paper, queue));
		stoners.add(new Stoner(SmokingItem.Weed, queue));
		new HippyCircle(stoners);
		System.out.println("Trying to Smoke...");
		queue.sendMessage(new StonerMessage(SmokingItem.Matches, null, "roll"));

		for (Stoner smoker : stoners) {
			executor.submit(smoker);
		}

		executor.awaitTermination(2, TimeUnit.SECONDS);
		executor.shutdownNow();

		System.out.println("done.");
	}

}


