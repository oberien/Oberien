/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.gui.controllers;

import controller.Options;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.SliderChangedEvent;
import de.lessvoid.nifty.controls.dropdown.DropDownControl;
import de.lessvoid.nifty.controls.slider.SliderControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.ArrayList;

public class SettingsScreenController implements ScreenController {

	private Nifty nifty;
	private Screen screen;
	private SliderControl masterControl, musicControl, soundControl;
	private Element masterLabel, musicLabel, soundLabel;
	private DropDown screenModeDropDown;

	@Override
	public void bind(final Nifty nifty, final Screen screen) {
		this.nifty = nifty;
		this.screen = screen;

		masterControl = screen.findControl("masterVolume", SliderControl.class);
		musicControl = screen.findControl("musicVolume", SliderControl.class);
		soundControl = screen.findControl("soundVolume", SliderControl.class);
		masterControl.setValue(Options.masterVolume);
		musicControl.setValue(Options.musicVolume);
		soundControl.setValue(Options.soundVolume);
		masterLabel = screen.findElementById("masterLabel");
		musicLabel = screen.findElementById("musicLabel");
		soundLabel = screen.findElementById("soundLabel");
		masterLabel.getRenderer(TextRenderer.class)
				.setText(Integer.toString((int) masterControl.getValue()));
		musicLabel.getRenderer(TextRenderer.class)
				.setText(Integer.toString((int) musicControl.getValue()));
		soundLabel.getRenderer(TextRenderer.class)
				.setText(Integer.toString((int) soundControl.getValue()));

		screenModeDropDown = screen.findControl("screenModeDropDown", DropDownControl.class);

		screenModeDropDown.addAllItems(new ArrayList<Element>() {
			{
				add(new TextBuilder() {
					{
						id("fullscreenLabel");
						text("Fullscreen");
						font("res/fonts/16/raven.fnt");
					}
				}.build(nifty, screen, screen.findElementById("screenModePanel")));
				add(new TextBuilder() {
					{
						id("windowedLabel");
						text("Windowed");
						font("res/fonts/16/raven.fnt");
					}
				}.build(nifty, screen, screen.findElementById("screenModePanel")));
				add(new TextBuilder() {
					{
						id("borderlessLabel");
						text("Borderless");
						font("res/fonts/16/raven.fnt");
					}
				}.build(nifty, screen, screen.findElementById("screenModePanel")));
			}
		});
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
}
