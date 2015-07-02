package todd.java.stoner;

import java.util.ArrayList;
import java.util.List;

public class HippyCircle {

	private StonerState state;
	private Stoner currentStoner;
	private List<SmokingItem> stashOnTable;
	private List<Stoner> stoners;
	private Joint joint;
	
	public HippyCircle() {
		state = StonerState.NotSmoking;
		stashOnTable = new ArrayList<>();
	}

	public StonerState getState() {
		return state;
	}

	public synchronized void setState(StonerState state) {
		this.state = state;
	}

	public synchronized List<SmokingItem> takeStashOnTable() {
		List<SmokingItem> stash = stashOnTable;
		stashOnTable = new ArrayList<>();
		return stash;
	}

	public synchronized void putStashOnTable(SmokingItem item) {
		stashOnTable.add(item);
	}

	public Stoner getCurrentStoner() {
		return currentStoner;
	}

	public void setCurrentStoner(Stoner currentStoner) {
		this.currentStoner = currentStoner;
	}

	public Stoner getNextStonerFrom(Stoner stoner) {
		int me = -1;
		for(int i = 0; i < stoners.size(); i++) {
			if(stoners.get(i) == stoner) {
				me = i;
				break;
			}
		}
		
		if(me == -1) {
			throw new IllegalStateException("bug getting next stoner");
		}
		
		int next = me + 1;
		if (next == stoners.size()) {
			next = 0;
		}
		return stoners.get(next);
	}

	public void setStoners(List<Stoner> stoners) {
		this.stoners = stoners;
	}

	public Joint getJoint() {
		return joint;
	}

	public synchronized void setJoint(Joint joint) {
		this.joint = joint;
	}
}
