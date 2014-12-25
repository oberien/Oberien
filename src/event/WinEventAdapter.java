package event;

import model.player.Player;

import java.util.ArrayList;

public class WinEventAdapter {
	private ArrayList<WinEventListener> listener = new ArrayList<WinEventListener>();
	
	public void addWinEventListener(WinEventListener l) {
		listener.add(l);
	}
	
	public void removeWinEventListener(WinEventListener l) {
		listener.remove(l);
	}
	
	public void hasWon(Player p) {
		for (int i = 0; i < listener.size(); i++) {
			listener.get(i).hasWon(p);
		}
	}
	
	public void hasLost(Player p) {
		for (int i = 0; i < listener.size(); i++) {
			listener.get(i).hasLost(p);
		}
	}
}
