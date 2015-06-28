package todd.java;

import java.util.List;

public class ThinkingAndEatingTask implements Runnable {

	private Philosopher phil;
	private List<Philosopher> phils;
	private final String meal;

	public ThinkingAndEatingTask(String meal, Philosopher phil,
			List<Philosopher> phils) {
		this.meal = meal;
		this.phil = phil;
		this.phils = phils;
	}

	@Override
	public void run() {
		try {
			eatAndThink();
		} catch (Throwable t) {
			System.out.println("*** error " + t.getMessage());
			t.printStackTrace();
		}
	}

	private void eatAndThink() {
		boolean doneThinking = false;
		boolean doneEating = false;

		while (!doneEating | !doneThinking) {
			if (!doneEating) {
				doneEating = tryToEat();
			}
			if (!doneThinking) {
				long threadId = Thread.currentThread().getId();
				System.out.println("..." + phil.getName()
						+ " is thinking during " + meal + " " + threadId);
				doneThinking = phil.thinkForATenthOfASecond();
			}
		}
	}

	private boolean tryToEat() {
		Philosopher nextGuy = findNextGuyForFork();
		long threadId = Thread.currentThread().getId();
		String name = phil.getName();
		if (grabFork() && grabNeighborsFork(nextGuy)) {
			System.out.println(name + " is eating " + meal + " " + threadId);
			System.out.println("  " + name + " got fork from "
					+ nextGuy.getName());
			takeTimeToEat();
			System.out.println(name + " is done with " + meal + "." + threadId);
			return true;
		}
		return false;
	}

	private boolean grabNeighborsFork(Philosopher nextGuy) {
		shortWait();
		return true;
	}

	private boolean grabFork() {
		shortWait();
		return true;
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

		// int nextGuy = guyToMyRight(me);
		int nextGuy = guyToMyLeft(me);

		return phils.get(nextGuy);
	}

	private int guyToMyRight(int me) {
		int nextGuy = me - 1;
		if (nextGuy == -1) {
			nextGuy = phils.size() - 1;
		}
		return nextGuy;
	}

	private int guyToMyLeft(int me) {
		int nextGuy = me + 1;
		if (nextGuy == phils.size()) {
			nextGuy = 0;
		}
		return nextGuy;
	}

}
