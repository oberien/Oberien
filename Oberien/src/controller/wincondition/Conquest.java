package controller.wincondition;

import java.io.Serializable;
import java.util.ArrayList;

import logger.ErrorLogger;
import model.Model;
import model.building.base.Base;
import model.map.Coordinate;
import model.player.Player;
import controller.MyHashMap;
import controller.State;
import event.ModelEventListener;
import event.WinEventAdapter;

/**
 * In the WinCondition "Conquest" a player will lose, if his base is destroyed. The last man standing has won.
 */
public class Conquest extends WinCondition implements ModelEventListener, Serializable {
	private static final long serialVersionUID = 1L;
	private State state;
	
	public Conquest(State s) {
		this.state = s;
	}

	@Override
	public void modelRemoved(Coordinate c, Model m) {
		if (m instanceof Base) {
			//get all required Variables for win-test
			MyHashMap<Coordinate, Model> models = state.getModels();
			Object[] keys = models.keySet().toArray();
			ArrayList<Base> bases = new ArrayList<Base>();
			//gather all bases that are alive
			//test if player of the base that just died has another base
			boolean hasAnotherBase = false;
			for (Object o : keys) {
				Model currentModel = models.get(o);
				if (currentModel instanceof Base) {
					Base b = (Base)currentModel;
					bases.add(b);
					if (b.getPlayer().equals(m.getPlayer())) {
						hasAnotherBase = true;
					}
				}
			}
			
			//test if player of m has lost
			if (!hasAnotherBase) {
				hasLost(m.getPlayer());
			}
			
			//test if a player has won
			if (bases.size() == 1) {
				hasWon(bases.get(0).getPlayer());
			}
		}
	}
	
	@Override
	public void modelMoved(Coordinate from, Coordinate to) {}
	@Override
	public void modelAttacked(Coordinate attacker, Coordinate defender) {}
	@Override
	public void modelIsBuild(Coordinate model, int x, int y, String name) {}
	@Override
	public void modelAddedToBuild(Coordinate builder, Coordinate model) {}
	@Override
	public void modelAdded(int x, int y, String name) {}
}
