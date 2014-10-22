/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package event;

import java.util.ArrayList;

public class PlayerStatsAdapter implements PlayerStatsListener {
	private ArrayList<PlayerStatsListener> listener = new ArrayList<>();
	
	public void addPlayerStatsListener(PlayerStatsListener l) {
		listener.add(l);
	}
	
	public void removePlayerStatsListener(PlayerStatsListener l) {
		listener.remove(l);
	}

	@Override
	public void metalChanged(int metal) {
		for (PlayerStatsListener l : listener) {
			l.metalChanged(metal);
		}
	}

	@Override
	public void energyChanged(int energy) {
		for (PlayerStatsListener l : listener) {
			l.energyChanged(energy);
		}
	}

	@Override
	public void populationChanged(int population) {
		for (PlayerStatsListener l : listener) {
			l.populationChanged(population);
		}
	}
}
