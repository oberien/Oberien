/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view.gui.controllers;

import controller.Options;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class StartScreenController implements ScreenController {
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

		public void multiplayer() {
			nifty.gotoScreen("multiplayer-login");
		}

		public void settings() {
			nifty.gotoScreen("settings");
		}

		public void exit() {
			Options.save();
			//TODO: Add GameContainer.exit() instead of this
			System.exit(0);
		}
}
