/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.gui.controllers;

import controller.Options;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.*;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import logger.ErrorLogger;
import org.lwjgl.util.Dimension;
import org.newdawn.slick.SlickException;

public class SettingsScreenController implements ScreenController {

	private Nifty nifty;
	private Screen screen;
	private Slider masterControl, musicControl, soundControl;
	private Element masterLabel, musicLabel, soundLabel;
	private DropDown<String> screenModeDropDown;

	@Override
	public void bind(final Nifty nifty, final Screen screen) {
		this.nifty = nifty;
		this.screen = screen;

		masterControl = screen.findNiftyControl("masterVolume", Slider.class);
		musicControl = screen.findNiftyControl("musicVolume", Slider.class);
		soundControl = screen.findNiftyControl("soundVolume", Slider.class);
		masterControl.setValue(Options.getMasterVolume());
		musicControl.setValue(Options.getMusicVolume());
		soundControl.setValue(Options.getSoundVolume());
		masterLabel = screen.findElementById("masterLabel");
		musicLabel = screen.findElementById("musicLabel");
		soundLabel = screen.findElementById("soundLabel");
		masterLabel.getRenderer(TextRenderer.class)
				.setText(Integer.toString((int) masterControl.getValue()));
		musicLabel.getRenderer(TextRenderer.class)
				.setText(Integer.toString((int) musicControl.getValue()));
		soundLabel.getRenderer(TextRenderer.class)
				.setText(Integer.toString((int) soundControl.getValue()));

		screenModeDropDown = screen.findNiftyControl("screenModeDropDown", DropDown.class);
		screenModeDropDown.addItem("Borderless");
		screenModeDropDown.addItem("Windowed");
		screenModeDropDown.addItem("Fullscreen");

		switch (Options.getScreenMode()) {
			case 0:
				screenModeDropDown.selectItem("Fullscreen");
				break;
			case 1:
				screenModeDropDown.selectItem("Windowed");
				break;
			case 2:
				screenModeDropDown.selectItem("Borderless");
				break;
			default:
				throw new UnsupportedOperationException("ScreenMode is out of Bounds");
		}

		screen.findNiftyControl("VSyncCheckBox", CheckBox.class).setChecked(Options.isVsync());
		screen.findNiftyControl("resolutionXTextField", TextField.class).setText(Integer.toString(Options.getResolution().getWidth()));
		screen.findNiftyControl("resolutionYTextField", TextField.class).setText(Integer.toString(Options.getResolution().getHeight()));
	}

	@Override
	public void onStartScreen() {
	}

	@Override
	public void onEndScreen() {
	}

	@NiftyEventSubscriber(pattern = ".*TextField")
	public void textFieldUpdate(String id, TextFieldChangedEvent ev) {
		System.out.println(ev.getText());
		if (id.equals("resolutionXTextField")) {
			Options.setResolution(new Dimension(Integer.parseInt(ev.getText()), Options.getResolution().getHeight()));
		} else if (id.equals("resolutionYTextField")) {
			Options.setResolution(new Dimension(Options.getResolution().getWidth(), Integer.parseInt(ev.getText())));
		} else {
			throw new UnsupportedOperationException("TextField with id: " + id + " returned unexpected result: " + ev.getText());
		}
	}

	@NiftyEventSubscriber(id = "VSyncCheckBox")
	public void checkBoxUpdate(String id, CheckBoxStateChangedEvent ev) {
		Options.setVsync(ev.isChecked());
	}

	@NiftyEventSubscriber(pattern = ".*Volume")
	public void sliderUpdate(String id, SliderChangedEvent e) {
		if ("masterVolume".equals(id)) {
			masterLabel.getRenderer(TextRenderer.class)
					.setText(Integer.toString((int) masterControl.getValue()));
			Options.setMasterVolume((int) masterControl.getValue());
		} else if ("musicVolume".equals(id)) {
			musicLabel.getRenderer(TextRenderer.class)
					.setText(Integer.toString((int) musicControl.getValue()));
			Options.setMusicVolume((int) musicControl.getValue());
		} else {
			soundLabel.getRenderer(TextRenderer.class)
					.setText(Integer.toString((int) soundControl.getValue()));
			Options.setMasterVolume((int) soundControl.getValue());
		}
	}

	@NiftyEventSubscriber(id = "screenModeDropDown")
	public void dropDownUpdate(String id, DropDownSelectionChangedEvent ev) {
		String str = ev.getSelection().toString();
		if (str.equals("Windowed")) {
			Options.setScreenMode(1);
		} else if (str.equals("Fullscreen")) {
			Options.setScreenMode(0);
		} else if (str.equals("Borderless")) {
			Options.setScreenMode(2);
		} else {
			throw new UnsupportedOperationException("DropDown with id: " + id + " returned unexpected result: " + str);
		}
	}

	@NiftyEventSubscriber(id = "returnButton")
	public void mainMenu(String id, ButtonClickedEvent ev) {
		nifty.gotoScreen("start");
	}

	@NiftyEventSubscriber(id = "applyButton")
	public void applyClicked(String id, ButtonClickedEvent ev) {
		try {
			Options.applySettings();
		} catch (SlickException e) {
			ErrorLogger.logger.severe(e.getMessage());
		}
	}
}
