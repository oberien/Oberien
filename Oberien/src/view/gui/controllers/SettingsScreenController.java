/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.gui.controllers;

import controller.Options;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.DropDownSelectionChangedEvent;
import de.lessvoid.nifty.controls.Slider;
import de.lessvoid.nifty.controls.SliderChangedEvent;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

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
				break;
			case 1:
				break;
			case 2:
				break;
			default:
				throw new RuntimeException("ScreenMode is out of Bounds");
		}
	}

	@Override
	public void onStartScreen() {
	}

	@Override
	public void onEndScreen() {
	}

	@NiftyEventSubscriber(pattern = ".*Volume")
	public void sliderUpdate(String id, SliderChangedEvent e) {
		if ("masterVolume".equals(id)) {
			masterLabel.getRenderer(TextRenderer.class)
					.setText(Integer.toString((int) masterControl.getValue()));
		} else if ("musicVolume".equals(id)) {
			musicLabel.getRenderer(TextRenderer.class)
					.setText(Integer.toString((int) musicControl.getValue()));
		} else {
			soundLabel.getRenderer(TextRenderer.class)
					.setText(Integer.toString((int) soundControl.getValue()));
		}
	}

	@NiftyEventSubscriber(id = "screenModeDropDown")
	public void dropDownUpdate(String id, DropDownSelectionChangedEvent ev) {
		System.out.println(ev.getSelection().toString());
	}
}
