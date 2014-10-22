package view.gui.controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import controller.Options;
import controller.multiplayer.MultiplayerException;
import controller.multiplayer.client.Client;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class RegisterScreenController implements ScreenController {
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
		screen.findNiftyControl("password-confirmation", TextField.class).setText("Confirm password");
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
	
	public void clearPwdConField() {
		screen.findNiftyControl("password-confirmation", TextField.class).setText("");
	}
	
	public void register() {
		String user = screen.findNiftyControl("username", TextField.class).getDisplayedText();
		String pwd = screen.findNiftyControl("password", TextField.class).getRealText();
		String pwdConfirmation = screen.findNiftyControl("password-confirmation", TextField.class).getRealText();
		if(pwd.equals(pwdConfirmation)) {
			try {
				System.out.println(user + "    " + pwd + "    " + pwdConfirmation);
				Client.register(user, pwd);
			} catch(InvalidKeySpecException | NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			} catch(MultiplayerException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Passwords don't match");
		}
		
	}
	
	public void back() {
		nifty.gotoScreen("start");
	}
}
