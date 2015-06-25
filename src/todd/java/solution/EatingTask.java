package todd.java.solution;

import java.util.List;

public class EatingTask implements Runnable {

	private Philosopher phil;
	private List<Philosopher> phils;
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
//		synchronized(phils) {
			phil.getFork().lock();
//			shortWait();
			nextGuy.getFork().lock();
//		}
		String name = phil.getName();
		System.out.println(name + " is eating " + meal + " " + threadId);
		System.out.println("  " + name + " got fork from " + nextGuy.getName());
		takeTimeToEat();
		System.out.println(name + " is done with " + meal + "." + threadId);
		phil.getFork().unlock();
		nextGuy.getFork().unlock();
	}

	private void shortWait() {
		try {
			Thread.sleep(10);
		} catch (InterruptedException ignored) {
		}
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

//		 int nextGuy = me - 1;
//		 if(nextGuy == -1) {
//		 nextGuy = phils.size() - 1;
//		 }

		int nextGuy = me + 1;
		if (nextGuy == phils.size()) {
			nextGuy = 0;
		}

		return phils.get(nextGuy);
	}

}
