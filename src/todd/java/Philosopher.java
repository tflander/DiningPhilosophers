package todd.java;

import java.util.concurrent.locks.ReentrantLock;

public class Philosopher {

	private final String name;
	private final ReentrantLock fork;
	private int timeThinkingInTenthsOfASecond;
	private static final int requiredThinkingTimeInSeconds = 2;

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

	public int getTimeThinkingInTenthsOfASecond() {
		return timeThinkingInTenthsOfASecond;
	}

	public void setTimeThinkingInTenthsOfASecond(
			int timeThinkingInTenthsOfASecond) {
		this.timeThinkingInTenthsOfASecond = timeThinkingInTenthsOfASecond;
	}
	
	public boolean thinkForATenthOfASecond() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException ignored) {
		}
		++timeThinkingInTenthsOfASecond;
		return (timeThinkingInTenthsOfASecond >= requiredThinkingTimeInSeconds*10);
	}

}
