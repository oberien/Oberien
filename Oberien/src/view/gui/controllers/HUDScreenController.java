/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.gui.controllers;

import controller.Controller;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import event.ModelEventListener;
import event.PlayerStatsListener;
import event.TurnChangedListener;
import model.Model;
import model.map.Coordinate;
import view.data.Globals;
import view.data.StartData;

public class HUDScreenController implements ScreenController, ModelEventListener, PlayerStatsListener, TurnChangedListener {

//	private int turn, money, energy, population;
	private String playername;
	private Nifty nifty;
	private Element turnText, playerName;
	private Loadingbar moneyBar, energyBar, populationBar;
	private Screen screen;
	private Element unitBox;
	private Controller c;

	private boolean changed;

	public HUDScreenController() {

	}

	@Override
	public void bind(Nifty nifty, Screen screen) {
		this.nifty = nifty;
		this.screen = screen;

		turnText = screen.findElementById("turnText");
		playerName = screen.findElementById("playerName");

		moneyBar = screen.findNiftyControl("moneyBar", Loadingbar.class);
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

//	public void updateTopHUD(int turn, int money, int energy, int population, String playername) {
//		if (turn != this.turn) {
//			this.turn = turn;
//			turnText.getRenderer(TextRenderer.class).setText("Turn " + turn);
//			changed = true;
//		}
//		if (money != this.money) {
//			this.money = money;
//			moneyBar.setProgress(1 / money * 100);
//		}
//		if (energy != this.energy) {
//			this.energy = energy;
//			energyBar.setProgress(1 / energy * 100);
//		}
//		if (population != this.population) {
//			this.population = population;
//			populationBar.setProgress(1 / population * 100);
//		}
//		if (!playername.equals(this.playername)) {
//			this.playername = playername;
//			playerName.getRenderer(TextRenderer.class).setText(playername);
//			changed = true;
//		}
//
//		if (changed) {
//			screen.layoutLayers();
//		}
//	}
//
//	public void updateBottomHUD() {
//		new ImageBuilder("img0") {
//			{
//				height("32px");
//				width("32px");
//				interactOnClick(playername);
//			}
//		}.build(nifty, screen, unitBox);
//	}

	public void registerControllerListeners(StartData sd) {
		c = sd.getController();
		c.addModelEventListener(this);
		c.addTurnChangedListener(this);
		c.addTurnChangedListener(this);
	}
	
	@Override
	public void modelMoved(Coordinate from, Coordinate to) {
	}

	@Override
	public void modelAttacked(Coordinate attacker, Coordinate defender) {
	}

	@Override
	public void modelIsBuild(Coordinate model, int x, int y, String name) {
	}

	@Override
	public void modelAddedToBuild(Coordinate builder, Coordinate model) {
	}

	@Override
	public void modelAdded(int x, int y, String name) {
	}

	@Override
	public void modelRemoved(Coordinate c, Model m) {
	}

	@Override
	public void moneyChanged(int money) {
		moneyBar.setProgress(1 / money * 100);
	}

	@Override
	public void energyChanged(int energy) {
		energyBar.setProgress(1 / energy * 100);
	}

	@Override
	public void populationChanged(int population) {
		populationBar.setProgress(1 / population * 100);
	}

	@Override
	public void roundChanged(int turn) {
		turnText.getRenderer(TextRenderer.class).setText(Integer.toString(turn));
	}

	@Override
	public void playernameChanged(String playername) {
		playerName.getRenderer(TextRenderer.class).setText(playername);
	}
}