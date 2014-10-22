/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.gui.controllers;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import view.data.Globals;

public class HUDScreenController implements ScreenController {
	
	private int turn, metal, energy, population;
	private String playername;
	private Nifty nifty;
	private Element turnText, playerName;
	private Loadingbar metalBar, energyBar, populationBar;
	private Screen screen;
	private Element unitBox;
	
	private boolean changed;
	
	public HUDScreenController() {
		
	}
	
	@Override
	public void bind(Nifty nifty, Screen screen) {
		this.nifty = nifty;
		this.screen = screen;
				
		turnText = screen.findElementById("turnText");
		playerName = screen.findElementById("playerName");
		
		metalBar = screen.findNiftyControl("metalBar", Loadingbar.class);
		energyBar = screen.findNiftyControl("energyBar", Loadingbar.class);
		populationBar = screen.findNiftyControl("populationBar", Loadingbar.class);
		
		unitBox = screen.findElementById("unitBox");
		
		Globals.setHUDController(this);
	}
	
	@Override
	public void onStartScreen() {
	}
	
	@Override
	public void onEndScreen() {
	}
	
	public void updateTopHUD(int turn, int metal, int energy, int population, String playername) {
		if (turn != this.turn) {
			this.turn = turn;
			turnText.getRenderer(TextRenderer.class).setText("Turn " + turn);
			changed = true;
		}
		if (metal != this.metal) {
			this.metal = metal;
			metalBar.setProgress(1 / metal * 100);
		}
		if (energy != this.energy) {
			this.energy = energy;
			energyBar.setProgress(1 / energy * 100);
		}
		if (population != this.population) {
			this.population = population;
			populationBar.setProgress(1 / population * 100);
		}
		if (!playername.equals(this.playername)) {
			this.playername = playername;
			playerName.getRenderer(TextRenderer.class).setText(playername);
			changed = true;
		}
		
		if (changed) {
			screen.layoutLayers();
		}
	}
	
	public void updateBottomHUD() {
		
	}
}
