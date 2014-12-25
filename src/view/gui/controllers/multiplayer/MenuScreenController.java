package view.gui.controllers.multiplayer;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class MenuScreenController implements ScreenController {
	Nifty nifty;
	Screen screen;

	@Override
	public void bind(Nifty nifty, Screen screen) {
		this.nifty = nifty;
		this.screen = screen;
	}

	@Override
	public void onStartScreen() {
	}

	@Override
	public void onEndScreen() {
	}
	
	public void matchmaking() {
		
	}
	
	public void createLobby() {
		
	}
	
	public void joinChat() {
		nifty.gotoScreen("chat");
	}

	public void back() {
		nifty.gotoScreen("start");
	}
}
