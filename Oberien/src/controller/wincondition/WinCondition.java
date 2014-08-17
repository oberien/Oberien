package controller.wincondition;

import model.Player;
import controller.State;

public interface WinCondition {
	public boolean hasWon(State state, Player player);
	public boolean hasLost(State state, Player player);
}
