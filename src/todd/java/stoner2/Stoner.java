package todd.java.stoner2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Stoner implements Runnable {

	private final SmokingItem owned;
	private Stoner nextStoner;
	private final List<SmokingItem> notOwned;
	private final StonerMessageQueue queue;
	private static final Random random = new Random(System.currentTimeMillis());

	public Stoner(SmokingItem owned, StonerMessageQueue queue) {
		this.owned = owned;
		this.queue = queue;
		this.notOwned = new ArrayList<>();

		for (SmokingItem smokingItem : SmokingItem.values()) {
			if (smokingItem != owned) {
				notOwned.add(smokingItem);
			}
		}
	}

	public void handleTransitions() throws InterruptedException {
		if (nextStoner == null) {
			nextStoner = HippyCircle.getInstance().getNextStonerFrom(this);
		}
		
		StonerMessage message = queue.peek();
		if(message != null && isForMe(message)) {
			handleMessage(message);
		}
	}

	private void handleMessage(StonerMessage message) throws InterruptedException {
		queue.remove();		
		System.out.println("Stoner with " + owned + " got message " + message.getMessage() + " from " + message.getSender());
		String msg = message.getMessage();
		SmokingItem sender = message.getSender();
		if(msg.equals("roll")) {
			for (SmokingItem smokingItem : notOwned) {
				queue.sendMessage(new StonerMessage(smokingItem, owned, "giveStash"));
				StonerMessage takeStashMessage = queue.waitForMessage(new StonerMessage(owned, smokingItem, "takeStash"));
				System.out.println("Stoner with " + owned + " got message " + takeStashMessage.getMessage() + " from " + takeStashMessage.getSender());			
				queue.remove();
			}
			int joint = roll(); 
			tokeAndPass(joint);
		} else if (msg.equals("giveStash")) {
			queue.sendMessage(new StonerMessage(sender, owned, "takeStash"));
		} else if(msg.startsWith("toke")) {
			int remainingHits = Integer.parseInt(msg.substring(4));
			tokeAndPass(remainingHits);
		} else {
			System.out.println("invalid message");
			System.exit(1);
		}
	}
	
	private void tokeAndPass(int remainingHits) {
		--remainingHits;
		if (remainingHits > 0) {
			System.out.println("Stoner who owns " + this.owned
					+ " takes a hit (" + remainingHits
					+ " left) and passes to the " + nextStoner.getOwnedItem()
					+ " dude.");
			String message = "toke" + remainingHits;
			queue.sendMessage(new StonerMessage(nextStoner.getOwnedItem(), owned, message));
		} else {
			System.out.println("Stoner who owns " + this.owned
					+ " takes the last hit.");
			queue.sendMessage(new StonerMessage(owned, owned, "roll"));
		}
	}
	
	private int roll() {
		int remainingHits = random.nextInt(5) + 7;
		System.out.println("Stoner who owns " + this.owned + " rolls a "
				+ remainingHits + " hit joint.");		
		return remainingHits;
	}

	private boolean isForMe(StonerMessage message) {
		return message.getRecipient() == owned;
	}

	public SmokingItem getOwnedItem() {
		return owned;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			try {
				handleTransitions();
			} catch (InterruptedException e) {
				return;
			} catch (Throwable t) {
				t.printStackTrace();
				System.exit(1);
			}
		}
	}
}
