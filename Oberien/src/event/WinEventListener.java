package event;

import model.player.Player;

public interface WinEventListener {
	public void hasWon(Player p);
	
	public void hasLost(Player p);
}
