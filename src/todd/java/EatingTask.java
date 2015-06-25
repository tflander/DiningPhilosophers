package todd.java;

import java.util.List;

public class EatingTask implements Runnable {

	private final Philosopher phil;
	private final List<Philosopher> phils;
	private final String meal;

	public EatingTask(String meal, Philosopher phil, List<Philosopher> phils) {
		this.meal = meal;
		this.phil = phil;
		this.phils = phils;
	}

	@Override
	public void run() {
		long threadId = Thread.currentThread().getId();
		Philosopher nextGuy = findNextGuyForFork();
		String name = phil.getName();
		System.out.println(name + " is eating " + meal + " " + threadId);
		System.out.println("  " + name + " got fork from " + nextGuy.getName());
		takeTimeToEat();
		System.out.println(name + " is done with " + meal + "." + threadId);
	}

	private void takeTimeToEat() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ignored) {
		}
	}

	private Philosopher findNextGuyForFork() {
		int me = -1;
		for (int i = 0; i < phils.size(); i++) {
			if (phils.get(i).getName().equals(phil.getName())) {
				me = i;
				break;
			}
		}

		 int nextGuy = me - 1;
		 if(nextGuy == -1) {
		 nextGuy = phils.size() - 1;
		 }

//		int nextGuy = me + 1;
//		if (nextGuy == phils.size()) {
//			nextGuy = 0;
//		}

		return phils.get(nextGuy);
	}

}
