/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package event;

import java.util.ArrayList;

public class PlayerStatsAdapter {
	private ArrayList<PlayerStatsListener> listener = new ArrayList<>();
	
	public void addPlayerStatsListener(PlayerStatsListener l) {
		listener.add(l);
	}
	
	public void removePlayerStatsListener(PlayerStatsListener l) {
		listener.remove(l);
	}

	public void moneyChanged(float metal) {
		for (PlayerStatsListener l : listener) {
			l.moneyChanged(metal);
		}
	}

	public void energyChanged(float energy) {
		for (PlayerStatsListener l : listener) {
			l.energyChanged(energy);
		}
	}

	public void populationChanged(float population) {
		for (PlayerStatsListener l : listener) {
			l.populationChanged(population);
		}
	}
}
