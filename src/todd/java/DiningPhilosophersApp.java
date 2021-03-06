package todd.java;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DiningPhilosophersApp {

	public static void main(String[] args) throws InterruptedException {
		List<Philosopher> phils = new ArrayList<>();
		phils.add(new Philosopher("Misam"));
		phils.add(new Philosopher("Ram"));
		phils.add(new Philosopher("Kannon"));
		phils.add(new Philosopher("Jim"));
		phils.add(new Philosopher("Siva"));

		ExecutorService executor = Executors.newFixedThreadPool(10);
		for (Philosopher phil : phils) {
			executor.submit(new ThinkingAndEatingTask("lunch", phil, phils));
		}
		Thread.sleep(4000);
		System.out.println("== lunch is over ==");
		
		for (Philosopher phil : phils) {
			phil.setTimeThinkingInTenthsOfASecond(0);
			executor.submit(new ThinkingAndEatingTask("dinner", phil, phils));
		}
		
		
		executor.shutdown();
		executor.awaitTermination(5, TimeUnit.MINUTES);
	}

}
