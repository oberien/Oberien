package view.eventhandler.gamesetup;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.controls.Parameters;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;

public class AddPlayerButton implements Controller {
	Element element;
	
	@Override
	public void bind(Nifty nifty, Screen screen, Element element,
			Parameters parameter) {
		this.element = element;
	}

	@Override
	public void init(Parameters parameter) {}

	@Override
	public boolean inputEvent(NiftyInputEvent inputEvent) {
		return false;
	}

	@Override
	public void onFocus(boolean getFocus) {}

	@Override
	public void onStartScreen() {}
	
	public void buttonClicked() {
		System.out.println("buttonClicked() for element: " + element);
	}
}
