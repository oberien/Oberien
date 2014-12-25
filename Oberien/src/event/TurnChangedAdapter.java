/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import java.util.ArrayList;

public class TurnChangedAdapter {

	private final ArrayList<TurnChangedListener> listener = new ArrayList<>();
	
	public void addTurnChangedListener(TurnChangedListener l) {
		listener.add(l);
	}
	
	public void removeTurnChangedListener(TurnChangedListener l) {
		listener.remove(l);
	}
	
	public void roundChanged(int turn) {
		for (TurnChangedListener l : listener) {
			l.roundChanged(turn);
		}
	}
	
	public void playernameChanged(String name) {
		for (TurnChangedListener l : listener) {
			l.playernameChanged(name);
		}
	}
}
