/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.gui.controllers;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.NiftyControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class HUDScreenController implements ScreenController {

	private int turn, metal, energy, population;
	private String playername; 
	private Nifty nifty;
	private Element roundText, playerName;
	private NiftyControl metalBar, energyBar, populationBar;
	
	public HUDScreenController() {

	}

	@Override
	public void bind(Nifty nifty, Screen screen) {
		roundText = screen.findElementById("roundText");
		playerName = screen.findElementById("playerName");
		
//		metalBar = screen.findNiftyControl(playername, LoadingbarController.class);
	}

	@Override
	public void onStartScreen() {
	}

	@Override
	public void onEndScreen() {
	}

	public void updateTopHUD(int turn, int metal, int energy, int population, String playername) {
		
		this.turn = turn;
		this.metal = metal;
		this.energy = energy;
		this.population = population;
		this.playername = playername;
	}
}
