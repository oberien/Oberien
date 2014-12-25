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
//
//public class ValidateMailScreenController implements ScreenController {
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
//		screen.findNiftyControl("validationToken", TextField.class).setText("ValidationToken");
//	}
//
//	@Override
//	public void onEndScreen() {
//	}
//
//	public void clearTokenField() {
//		screen.findNiftyControl("validationToken", TextField.class).setText("");
//	}
//
//	public void validate() {
//		String token = screen.findNiftyControl("validationToken", TextField.class).getDisplayedText();
//		try {
//			Client.validateMail(token);
//			nifty.gotoScreen("chat");
//		} catch (IOException e) {
//			screen.findElementById("errorMessage").getRenderer(TextRenderer.class).setText("Connection to Server failed: " + e.getMessage());
//		} catch (ValidationException | MultiplayerException | MailNotValidatedException | LoginException e) {
//			screen.findElementById("errorMessage").getRenderer(TextRenderer.class).setText(e.getMessage());
//		}
//	}
//
//	public void back() {
//		nifty.gotoScreen("login");
//	}
//}
