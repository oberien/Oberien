package view.gui.controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import controller.multiplayer.MultiplayerException;
import controller.multiplayer.client.Client;
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
		screen.findNiftyControl("username", TextField.class).setText("Username");
		screen.findNiftyControl("password", TextField.class).setText("Password");
	}

	@Override
	public void onEndScreen() {
	}
	
	public void clearUserField() {
		screen.findNiftyControl("username", TextField.class).setText("");
	}
	
	public void clearPwdField() {
		screen.findNiftyControl("password", TextField.class).setText("");
	}

	public void login() {
		String user = screen.findNiftyControl("username", TextField.class).getDisplayedText();
		String pwd = screen.findNiftyControl("password", TextField.class).getRealText();
		try {
			String username = Client.login(user, pwd);
			if(username != null) {
				nifty.gotoScreen("chat");
			} else {
				System.out.println("FAILED");
			}
		} catch(InvalidKeySpecException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} catch(MultiplayerException e) {
			e.printStackTrace();
		}
	}
	
	public void register() {
		nifty.gotoScreen("register");
	}
	
	public void back() {
		nifty.gotoScreen("start");
	}
}
