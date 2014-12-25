/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import model.Model;

import java.util.ArrayList;

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
