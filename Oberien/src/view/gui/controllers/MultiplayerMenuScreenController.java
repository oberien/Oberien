package view.gui.controllers;

import controller.Options;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class MultiplayerMenuScreenController implements ScreenController {
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
