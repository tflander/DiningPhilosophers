package todd.java.stoner2;

import java.util.List;

public class HippyCircle {

	private final List<Stoner> stoners;
	private static HippyCircle instance;

	public HippyCircle(List<Stoner> stoners) {
		this.stoners = stoners;
		instance = this;
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

	public static HippyCircle getInstance() {
		if (instance == null) {
			throw new IllegalStateException("Hippy Circle Instance Not Initialized");
		}
		return instance;
	}
}
