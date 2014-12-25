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
//public class RegisterScreenController implements ScreenController {
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
//		screen.findNiftyControl("password-confirmation", TextField.class).setText("Confirm password");
//		screen.findNiftyControl("mail", TextField.class).setText("E-Mail");
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
//	public void clearPwdConField() {
//		screen.findNiftyControl("password-confirmation", TextField.class).setText("");
//	}
//
//	public void clearMailField() {
//		screen.findNiftyControl("mail", TextField.class).setText("");
//	}
//
//	public void register() {
//		String user = screen.findNiftyControl("username", TextField.class).getDisplayedText();
//		String pwd = screen.findNiftyControl("password", TextField.class).getRealText();
//		String pwdConfirmation = screen.findNiftyControl("password-confirmation", TextField.class).getRealText();
//		String mail = screen.findNiftyControl("mail", TextField.class).getDisplayedText();
//		if(pwd.equals(pwdConfirmation)) {
//			try {
//				Client.register(user, pwd, mail);
//				nifty.gotoScreen("login");
//			} catch(InvalidKeySpecException | NoSuchAlgorithmException e) {
//				screen.findElementById("errorMessage").getRenderer(TextRenderer.class).setText("Internal Client Error: " + e.getMessage());
//			} catch(IOException e) {
//				screen.findElementById("errorMessage").getRenderer(TextRenderer.class).setText("Connection to Server failed: " + e.getMessage());
//			} catch(ValidationException | MultiplayerException | LoginException e) {
//				screen.findElementById("errorMessage").getRenderer(TextRenderer.class).setText(e.getMessage());
//			} catch (MailNotValidatedException e) {
//				nifty.gotoScreen("validateMail");
//			}
//		} else {
//			screen.findElementById("errorMessage").getRenderer(TextRenderer.class).setText("Passwords don't match");
//		}
//
//	}
//
//	public void back() {
//		Client.logout();
//		nifty.gotoScreen("login");
//	}
//}
