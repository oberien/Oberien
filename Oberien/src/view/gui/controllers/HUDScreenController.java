/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.gui.controllers;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class HUDScreenController implements ScreenController {

	private int turn, metal, energy, population;
	private String playername; 
	private Nifty nifty;
	
	public HUDScreenController() {

	}

	@Override
	public void bind(Nifty nifty, Screen screen) {
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
