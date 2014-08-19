/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view.gui.controllers;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.elements.events.NiftyMousePrimaryClickedEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class StartScreenController implements ScreenController {

	@Override
	public void bind(Nifty nifty, Screen screen) {
	}

	@Override
	public void onStartScreen() {
	}

	@Override
	public void onEndScreen() {
	}
	
	public void buttonClicked() {
		System.out.println("buttonClicked()");
	}
	
	@NiftyEventSubscriber(id="addPlayer")
	public void onClick(String id, NiftyMousePrimaryClickedEvent event) {
	System.out.println("element with id [" + id + "] clicked at [" + event.getMouseX() +
	", " + event.getMouseY() + "]");
	}
}
