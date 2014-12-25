package event;


import java.util.ArrayList;

public class NiftyMenuUpdateAdapter {
	ArrayList<NiftyMenuUpdateListener> listeners = new ArrayList<>();

	public void addNiftyMenuUpdateListener(NiftyMenuUpdateListener l) {
		listeners.add(l);
	}

	public void removeNiftyMenuUpdateListener(NiftyMenuUpdateListener l) {
		listeners.remove(l);
	}

	public void update() {
		for (NiftyMenuUpdateListener l : listeners) {
			l.update();
		}
	}
}
