package todd.java.solution;

import java.util.concurrent.locks.ReentrantLock;

public class Philosopher {

	private final String name;
	private final ReentrantLock fork;

	public Philosopher(String name) {
		this.name = name;
		fork = new ReentrantLock();
	}

	public String getName() {
		return name;
	}

	public ReentrantLock getFork() {
		return fork;
	}

}
