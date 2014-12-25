/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import java.util.ArrayList;
import model.Model;

public class ModelClickedAdapter {

	private ArrayList<ModelClickedListener> listeners = new ArrayList<>();

	public void addModelClickedEventListener(ModelClickedListener l) {
		listeners.add(l);
	}

	public void removeModelClickedEventListener(ModelClickedListener l) {
		listeners.remove(l);
	}
	
	public void modelClicked(Model m) {
		for (ModelClickedListener l : listeners) {
			l.modelClicked(m);
		}
	}
}
