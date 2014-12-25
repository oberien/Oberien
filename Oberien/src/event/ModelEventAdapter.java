package event;

import java.util.ArrayList;

import model.Model;
import model.map.Coordinate;

public class ModelEventAdapter {
	private ArrayList<ModelEventListener> listeners = new ArrayList<ModelEventListener>();
	
	public void addModelEventListener(ModelEventListener l) {
		listeners.add(l);
	}
	
	public void removeModelEventListener(ModelEventListener l) {
		listeners.remove(l);
	}
	
	public void modelMoved(Coordinate from, Coordinate to) {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).modelMoved(from, to);
		}
	}
	
	public void modelAttacked(Coordinate attacker, Coordinate defender) {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).modelAttacked(attacker, defender);
		}
	}
	
	public void modelIsBuild(Coordinate model, int x, int y, String name) {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).modelIsBuild(model, x, y, name);
		}
	}
	
	public void modelAddedToBuild(Coordinate builder, Coordinate model) {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).modelAddedToBuild(builder, model);
		}
	}
	
	public void modelAdded(int x, int y, String name) {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).modelAdded(x, y, name);
		}
	}
	
	public void modelRemoved(Coordinate c, Model m) {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).modelRemoved(c, m);
		}
	}
}
