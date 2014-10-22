package view.gui.controllers;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.Chat;
import de.lessvoid.nifty.controls.ChatTextSendEvent;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.render.batch.BatchRenderFont;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class ChatScreenController implements ScreenController {
	Nifty nifty;
	Screen screen;
	String user = "asdf";

	@Override
	public void bind(Nifty nifty, Screen screen) {
		this.nifty = nifty;
		this.screen = screen;
	}

	@Override
	public void onStartScreen() {}

	@Override
	public void onEndScreen() {}
	
	public void sendText(String text) {
		
	}
	
	public void back() {
		nifty.gotoScreen("start");
	}
	
}
