package controller.wincondition;

import java.io.Serializable;
import java.util.ArrayList;

import model.Model;
import model.building.base.Base;
import model.map.Coordinate;
import model.player.Player;
import controller.MyHashMap;
import controller.State;

/**
 * In the WinCondition "Conquest" a player will lose, if his base is destroyed. The last man standing has won.
 */
public class Conquest implements WinCondition, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public boolean hasWon(State state, Player player) {
		//get all required Variables for win-test
		MyHashMap<Coordinate, Model> models = state.getModels();
		Object[] keys = models.keySet().toArray();
		ArrayList<Base> bases = new ArrayList<Base>();
		for (Object o : keys) {
			Model m = models.get(o);
			if (m instanceof Base) {
				bases.add((Base)m);
			}
		}
		
		//test if currentPlayer has won
		if (bases.size() == 1 && bases.get(0).getPlayer().equals(player)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean hasLost(State state, Player player) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//get all required Variables for win-test
		MyHashMap<Coordinate, Model> models = state.getModels();
		Object[] keys = models.keySet().toArray();
		for (Object o : keys) {
			Model m = models.get(o);
			if (m instanceof Base) {
				if (((Base)m).getPlayer().equals(player)) {
					return false;
				}
			}
		}
		return true;
	}

}
