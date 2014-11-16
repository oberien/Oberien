/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.gui.controllers;

import controller.Controller;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import event.HUDModelClickedAdapter;
import event.ModelClickedListener;
import event.ModelEventListener;
import event.PlayerStatsListener;
import event.TurnChangedListener;
import java.util.ArrayList;
import model.BuildingModel;
import model.Model;
import model.ModelList;
import model.map.Coordinate;
import model.player.Player;
import view.data.Globals;
import view.data.StartData;
import view.gamesstates.GameRunning;

public class HUDScreenController extends HUDModelClickedAdapter implements ModelClickedListener, ScreenController, ModelEventListener, PlayerStatsListener, TurnChangedListener {

	private Nifty nifty;
	private Element turnText, playerName;
	private Loadingbar moneyBar, energyBar, populationBar;
	private Screen screen;
	private Element unitBox;
	private Element moneyBarText, energyBarText, populationBarText;
	private Controller c;
	private final ArrayList<Element> modelImageBoxes = new ArrayList<>();
	private final ArrayList<Element> subBoxes = new ArrayList<>();

	public HUDScreenController() {

	}

	@Override
	public void bind(Nifty nifty, Screen screen) {
		Globals.setHUDController(this);
		this.nifty = nifty;
		this.screen = screen;

		turnText = screen.findElementById("turnText");
		playerName = screen.findElementById("playerName");

		moneyBar = screen.findNiftyControl("moneyBar", Loadingbar.class);
		energyBar = screen.findNiftyControl("energyBar", Loadingbar.class);
		populationBar = screen.findNiftyControl("populationBar", Loadingbar.class);

		moneyBarText = screen.findElementById("moneyBarText");
		energyBarText = screen.findElementById("energyBarText");
		populationBarText = screen.findElementById("populationBarText");

		unitBox = screen.findElementById("unitBox");

		for (int i = 0; i < unitBox.getHeight(); i += Globals.TILE_SIZE) {
			subBoxes.add(
					new PanelBuilder("vertOrderPanel" + i / Globals.TILE_SIZE) {
						{
							height(Globals.TILE_SIZE + "px");
							width("100%");
							childLayout(ChildLayoutType.Horizontal);
							interactOnClick("unitBoxClick()");
						}
					}.build(nifty, screen, unitBox));
		}
	}

	@NiftyEventSubscriber(pattern = ".*ImageBox")
	public void unitImageBoxClicked(String name, ButtonClickedEvent e) {
		for (Model l : ModelList.getInstance().getAllModels()) {
			if ((l.getName() + "ImageBox").equals(name)) {
				HUDModelClicked(l);
			}
		}
	}

	public void unitBoxClick() {
		for (int i = modelImageBoxes.size() - 1; i >= 0; i--) {
			modelImageBoxes.remove(i);
		}
		modelImageBoxes.clear();
	}

	@Override
	public void onStartScreen() {
	}

	@Override
	public void onEndScreen() {
	}

	public void registerListeners(StartData sd, GameRunning gr) {
		c = sd.getController();
		c.addModelEventListener(this);
		c.addTurnChangedListener(this);
		c.addPlayerStatsListener(this);
		gr.addModelClickedEventListener(this);

		Player p = c.getState().getCurrentPlayer();
		moneyChanged(p.getMoney());
		energyChanged(p.getEnergy());
		populationChanged(p.getPopulation());
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
		moneyBar.setProgress(money / c.getState().getCurrentPlayer().getStorage());
		moneyBarText.getRenderer(TextRenderer.class).setText(money + "/" + c.getState().getCurrentPlayer().getStorage());
	}

	@Override
	public void energyChanged(int energy) {
		energyBar.setProgress(energy / c.getState().getCurrentPlayer().getStorage());
		energyBarText.getRenderer(TextRenderer.class).setText(energy + "/" + c.getState().getCurrentPlayer().getStorage());
	}

	@Override
	public void populationChanged(int population) {
		populationBar.setProgress(population / c.getState().getCurrentPlayer().getPopulationStorage());
		populationBarText.getRenderer(TextRenderer.class).setText(population + "/" + c.getState().getCurrentPlayer().getPopulationStorage());
	}

	@Override
	public void roundChanged(int turn) {
		turnText.getRenderer(TextRenderer.class).setText("Turn " + Integer.toString(turn));
	}

	@Override
	public void playernameChanged(String playername) {
		playerName.getRenderer(TextRenderer.class).setText(playername);
	}

	@Override
	public void modelClicked(Model m) {
		if (m == null) {
			unitBoxClick();
		} else if (m instanceof BuildingModel) {
			BuildingModel b = (BuildingModel) m;
			Model[] list = ModelList.getInstance().getModelsOfType(b.getBuilds());
			int a = 0;
			Element subBox = subBoxes.get(0);
			int i = 0;
			for (final Model e : list) {
				if (i * Globals.TILE_SIZE > unitBox.getWidth()) {
					a++;
					subBox = subBoxes.get(a);
				}
				modelImageBoxes.add(
						new ButtonBuilder(e.getName() + "ImageBox") {
							{
								name("buttonImage");
								filename("/res/imgs/units/" + e.getId() + ".2.png");
								height(Globals.TILE_SIZE + "px");
								width(Globals.TILE_SIZE + "px");
							}
						}.build(nifty, screen, subBox));

				i++;
			}
		}
	}
}
