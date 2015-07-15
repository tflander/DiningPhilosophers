package todd.java.stoner2;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class StonerMessageQueue {

	private final Queue<StonerMessage> queue;
	
	public StonerMessageQueue() {
		this.queue = new ArrayBlockingQueue<>(100);
	}
	
	public StonerMessage peek() {
		return queue.peek();
	}
	
	public StonerMessage remove() {
		return queue.remove();
	}

	public void sendMessage(StonerMessage stonerMessage) {
		queue.add(stonerMessage);
	}

	public StonerMessage waitForMessage(StonerMessage stonerMessage) throws InterruptedException {
		do {
			StonerMessage message = peek();
			if (message != null) {
				if(message.getRecipient().equals(stonerMessage.getRecipient())) {
					return message;				
				}
			} else {
				Thread.sleep(50);
			}
		} while (true);
	}
}
