/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view.gui.controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import controller.Options;
import controller.multiplayer.LoginException;
import controller.multiplayer.MailNotValidatedException;
import controller.multiplayer.MultiplayerException;
import controller.multiplayer.ValidationException;
import controller.multiplayer.client.Client;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class StartScreenController implements ScreenController {
		Nifty nifty;
		Screen screen;
		private Element loginPopup;


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

		public void multiplayer() {
			loginPopup = nifty.createPopup("multiplayerlogin");
			nifty.showPopup(nifty.getCurrentScreen(), loginPopup.getId(), null);
			//nifty.gotoScreen("login");
		}

		public void settings() {
			nifty.setDebugOptionPanelColors(true);
			//nifty.gotoScreen("settings");
		}

		public void exit() {
			Options.save();
			//TODO: Add GameContainer.exit() instead of this
			System.exit(0);
		}
		

		public void clearUserField() {
			screen.findNiftyControl("username", TextField.class).setText("");
		}
		
		public void clearPwdField() {
			screen.findNiftyControl("password", TextField.class).setText("");
		}

		public void login() {
			// should actually run in parallel to the gui rendering, which would afford the opportunity to 
			// output a "Operation in Progress..." message
			String user = screen.findNiftyControl("username", TextField.class).getDisplayedText();
			String pwd = screen.findNiftyControl("password", TextField.class).getRealText();
			try {
				String username = Client.login(user, pwd).getUsername();
				System.out.println("chat");
				nifty.gotoScreen("chat");
			} catch(InvalidKeySpecException | NoSuchAlgorithmException e) {
				screen.findElementById("errorMessage").getRenderer(TextRenderer.class).setText("Internal Client Error: " + e.getMessage());
			} catch(IOException e) {
				screen.findElementById("errorMessage").getRenderer(TextRenderer.class).setText("Connection to Server failed: " + e.getMessage());
			} catch(ValidationException | MultiplayerException | LoginException e) {
				screen.findElementById("errorMessage").getRenderer(TextRenderer.class).setText(e.getMessage());
			} catch (MailNotValidatedException e) {
				System.out.println("validateMail");
				nifty.gotoScreen("validateMail");
			}
		}
		
		public void register() {
			nifty.gotoScreen("register");
		}
		
		public void backToMainMenu() {
			if(loginPopup != null){
				nifty.closePopup(loginPopup.getId());
				loginPopup = null;
			}
			nifty.gotoScreen("start");
		}
}
