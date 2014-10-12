package view.gui.controllers;

import controller.Options;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class MultiplayerLoginScreenController implements ScreenController {
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

	public void gameSetup() {
		nifty.gotoScreen("gameSetup");
	}

	public void login() {
		String user = screen.findNiftyControl("username", TextField.class).getDisplayedText();
		String pwd = screen.findNiftyControl("password", TextField.class).getRealText();
	}

	public void back() {
		nifty.gotoScreen("start");
	}
}
