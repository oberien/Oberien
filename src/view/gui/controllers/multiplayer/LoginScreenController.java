//package view.gui.controllers.multiplayer;
//
//import de.lessvoid.nifty.Nifty;
//import de.lessvoid.nifty.controls.TextField;
//import de.lessvoid.nifty.elements.render.TextRenderer;
//import de.lessvoid.nifty.screen.Screen;
//import de.lessvoid.nifty.screen.ScreenController;
//import multiplayer.client.Client;
//import multiplayer.exceptions.LoginException;
//import multiplayer.exceptions.MailNotValidatedException;
//import multiplayer.exceptions.MultiplayerException;
//import multiplayer.exceptions.ValidationException;
//
//import java.io.IOException;
//import java.security.NoSuchAlgorithmException;
//import java.security.spec.InvalidKeySpecException;
//
//public class LoginScreenController implements ScreenController {
//	Nifty nifty;
//	Screen screen;
//
//	@Override
//	public void bind(Nifty nifty, Screen screen) {
//		this.nifty = nifty;
//		this.screen = screen;
//	}
//
//	@Override
//	public void onStartScreen() {
//		screen.findNiftyControl("username", TextField.class).setText("Username");
//		screen.findNiftyControl("password", TextField.class).setText("Password");
//	}
//
//	@Override
//	public void onEndScreen() {
//	}
//
//	public void clearUserField() {
//		screen.findNiftyControl("username", TextField.class).setText("");
//	}
//
//	public void clearPwdField() {
//		screen.findNiftyControl("password", TextField.class).setText("");
//	}
//
//	public void login() {
//		String user = screen.findNiftyControl("username", TextField.class).getDisplayedText();
//		String pwd = screen.findNiftyControl("password", TextField.class).getRealText();
//		try {
//			String username = Client.login(user, pwd).getUsername();
//			System.out.println("chat");
//			nifty.gotoScreen("chat");
//		} catch(InvalidKeySpecException | NoSuchAlgorithmException e) {
//			screen.findElementById("errorMessage").getRenderer(TextRenderer.class).setText("Internal Client Error: " + e.getMessage());
//		} catch(IOException e) {
//			screen.findElementById("errorMessage").getRenderer(TextRenderer.class).setText("Connection to Server failed: " + e.getMessage());
//		} catch(ValidationException | MultiplayerException | LoginException e) {
//			screen.findElementById("errorMessage").getRenderer(TextRenderer.class).setText(e.getMessage());
//		} catch (MailNotValidatedException e) {
//			System.out.println("validateMail");
//			nifty.gotoScreen("validateMail");
//		}
//	}
//
//	public void register() {
//		nifty.gotoScreen("register");
//	}
//
//	public void back() {
//		nifty.gotoScreen("start");
//	}
//}
