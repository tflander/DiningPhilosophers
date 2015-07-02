package todd.java.stoner;

import java.util.ArrayList;
import java.util.List;

public class Stoner implements Runnable {

	private final SmokingItem owned;
	private Stoner nextStoner;
	private final List<SmokingItem> notOwned;
	private HippyCircle circle;

	public Stoner(SmokingItem owned, HippyCircle circle) {
		this.owned = owned;
		this.circle = circle;
		this.notOwned = new ArrayList<>();

		for (SmokingItem smokingItem : SmokingItem.values()) {
			if (smokingItem != owned) {
				notOwned.add(smokingItem);
			}
		}
	}

	public void handleTransitions() throws InterruptedException {
		if (nextStoner == null) {
			nextStoner = circle.getNextStonerFrom(this);
		}

		switch (circle.getState()) {
		case NotSmoking:
			if (isMyTurn()) {
				if (necessaryItemsOnTable()) {
					rollJoint();
				}
			} else {
				putItemOnTableIfNecessary();
			}
			break;
		case Smoking:
			if (isMyTurn()) {
				tokeAndPass();
			}
			break;
		}
	}

	private void putItemOnTableIfNecessary() {
		if (!circle.getItemsOnTable().contains(owned)) {
			System.out.println("Putting " + owned + " on the table");
			circle.putStashOnTable(owned);
		}
	}

	private boolean necessaryItemsOnTable() {
		List<SmokingItem> itemsOnTable = circle.getItemsOnTable();
		return !itemsOnTable.contains(owned) && itemsOnTable.size() == 2;
	}

	private void rollJoint() {
		List<SmokingItem> stash = circle.takeStashOnTable();
		Joint joint = new Joint();
		System.out.println("Stoner who owns " + this.owned + " takes " + stash
				+ " and adds his " + owned + " to roll a "
				+ joint.getRemainingHits() + " hit joint.");
		circle.setJoint(joint);
		circle.setState(StonerState.Smoking);
	}

	private void tokeAndPass() {
		Joint joint = circle.getJoint();
		joint.takeHit();
		if (joint.getRemainingHits() > 0) {
			System.out.println("Stoner who owns " + this.owned
					+ " takes a hit (" + joint.getRemainingHits()
					+ " left) and passes to the " + nextStoner.getOwnedItem()
					+ " dude.");
			circle.setCurrentStoner(nextStoner);
		} else {
			System.out.println("Stoner who owns " + this.owned
					+ " takes the last hit.");
			circle.setState(StonerState.NotSmoking);
		}
	}

	private boolean isMyTurn() {
		return circle.getCurrentStoner() == this;
	}

	public SmokingItem getOwnedItem() {
		return owned;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			try {
				synchronized(circle) {
					handleTransitions();
				}
			} catch (InterruptedException e) {
				return;
			} catch (Throwable t) {
				t.printStackTrace();
				System.exit(1);
			}
		}
	}
}
