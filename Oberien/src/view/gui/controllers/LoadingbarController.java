/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.gui.controllers;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.controls.Parameters;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.tools.SizeValue;

public class LoadingbarController implements Controller {

	private Nifty nifty;
	private Screen screen;
	private Element element;
	private Parameters parameter;

	@Override
	public void bind(Nifty nifty, Screen screen, Element element, Parameters parameter) {
		this.nifty = nifty;
		this.screen = screen;
		this.element = element;
		this.parameter = parameter;
	}

	@Override
	public void init(Parameters parameter) {
	}

	@Override
	public void onStartScreen() {
	}

	@Override
	public void onFocus(boolean getFocus) {
	}

	@Override
	public boolean inputEvent(NiftyInputEvent inputEvent) {
		return false;
	}

	public void setProgress(float progress) {
		final int MIN_WIDTH = 32;
		int pixelWidth = (int) (MIN_WIDTH + (element.getParent().getWidth() - MIN_WIDTH) * progress);
		element.setConstraintWidth(new SizeValue(pixelWidth + "px"));
		element.getParent().layoutElements();
	}
}
