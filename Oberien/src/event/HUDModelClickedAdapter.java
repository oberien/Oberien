/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package event;

import java.util.ArrayList;
import model.Model;

public class HUDModelClickedAdapter {

	private final ArrayList<HUDModelClickedListener> listeners = new ArrayList<>();

	public void addHUDModelClickedEventListener(HUDModelClickedListener l) {
		listeners.add(l);
	}

	public void removeHUDModelClickedEventListener(HUDModelClickedListener l) {
		listeners.remove(l);
	}
	
	public void HUDModelClicked(Model m) {
		for (HUDModelClickedListener l : listeners) {
			l.HUDModelClicked(m);
		}
	}
}
