/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.gamesstates;

import controller.Controller;
import controller.State;
import de.lessvoid.nifty.Nifty;
import event.HUDModelClickedListener;
import event.ModelClickedAdapter;
import event.ModelClickedListener;
import model.AttackingModel;
import model.BuildingModel;
import model.Layer;
import model.Model;
import model.map.Coordinate;
import model.map.MapList;
import model.unit.Unit;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import view.data.StartData;
import view.music.MusicManager;
import view.renderer.*;

import java.util.Arrays;

public class GameRunning extends MapState implements HUDModelClickedListener {
	private GameContainer gc;

	private StartData sd;
	private Controller controller;
	private State state;

	private HUDRenderer hudr;
	private UnitRenderer ur;
	private ActionUnitRenderer aur;
	private FoWRenderer fowr;
	private ActionGroundRenderer agr;
	private DamageRenderer dr;

	private boolean endTurn;

	private Coordinate selectedModelCoordinate;
	private Model modelToBuild;
	private Coordinate unitActionCoordinate;

	private Coordinate dmgCoord1;
	private String dmg1;
	private Coordinate dmgCoord2;
	private String dmg2;
	private long attackMillis;

	private Nifty nifty;

	private MusicManager mm;

	private ModelClickedAdapter modelClickedAdapter;

	public GameRunning(StartData sd) {
		super(sd);
		this.sd = sd;
		dmg1 = "";
		dmg2 = "";

		modelClickedAdapter = new ModelClickedAdapter();
	}

	public void addModelClickedEventListener(ModelClickedListener l) {
		modelClickedAdapter.addModelClickedEventListener(l);
	}

	public void removeModelClickedEventListener(ModelClickedListener l) {
		modelClickedAdapter.removeModelClickedEventListener(l);
	}

	@Override
	public int getID() {
		return 4;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		super.init(gc, sbg);
		if (MapList.getInstance().getCurrentMap() != null) {
			this.gc = gc;

			hudr = sd.getHudr();
			ur = sd.getUr();
			aur = sd.getAur();
			fowr = sd.getFowr();
			agr = sd.getAgr();
			dr = sd.getDr();
			controller = sd.getController();
			state = sd.getState();

//			Globals.getHUDController().registerControllerListeners(sd);
			nifty = sd.getNifty();
			mm = new MusicManager();
			mm.init();
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		super.render(gc, sbg, g);

		fowr.draw(g, state.getSight());
		agr.draw(g, state, selectedModelCoordinate, modelToBuild != null);
		Model model;
		if (hudr.getSelectedModel() != null) {
			model = hudr.getSelectedModel();
		} else {
			model = state.getModel(selectedModelCoordinate);
		}
		ur.draw(g, state);
		aur.draw(g, model, unitActionCoordinate, controller.getDirectionOf(selectedModelCoordinate, unitActionCoordinate), state.getCurrentPlayer().getColor());
		dr.draw(g, dmgCoord1, dmg1, dmgCoord2, dmg2, attackMillis);
		g.resetTransform();
		hudr.draw(g, state, sbg, state.getModel(selectedModelCoordinate));
//		SlickCallable.enterSafeBlock();
//		nifty.render(false);
//		SlickCallable.leaveSafeBlock();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
//		nifty.update();
		super.update(gc, sbg, delta);

		//set damage coords to null when time > 2000ms
		if (System.currentTimeMillis() - attackMillis > 2000) {
			dmgCoord1 = null;
			dmgCoord2 = null;
		}

		//TODO create an ingame button to end turn
		if (endTurn) {
			selectedModelCoordinate = null;
			unitActionCoordinate = null;
			dmgCoord1 = null;
			dmgCoord2 = null;
			controller.endTurn();
			endTurn = false;
		}
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		super.mouseClicked(button, x, y, clickCount);

		boolean retur;

		int mapx = (int) (camX + x / scale);
		int mapy = (int) (camY + y / scale);
		Coordinate currentCoordinate = new Coordinate(mapx / 32, mapy / 32, Layer.Ground);
		Model currentModel = state.getModel(currentCoordinate);

		//selected model
		Model selectedModel = state.getModel(selectedModelCoordinate);

		//no model selected -> select model if appropriate
		if (selectedModel == null) {
			retur = selectModel(currentModel, currentCoordinate);
			if (retur) return;
		}

		// BuildingModel selected and modelToBuild is chosen in HUD
		if (selectedModelCoordinate instanceof BuildingModel && modelToBuild != null) {
			retur = buildModel(selectedModelCoordinate, currentCoordinate, modelToBuild);
			if (retur) return;
		}

		// BuildingModel selected and will be added to to build the model on currentPos
		if (selectedModelCoordinate instanceof BuildingModel && !currentModel.isBuilt()) {
			retur = addModelToBuild(selectedModelCoordinate, currentCoordinate);
			if (retur) return;
		}

		// AttackingModel is chosen and clicked coordinate is an enemy model
		if (selectedModelCoordinate instanceof AttackingModel && currentModel.getPlayer().getTeam() != state.getCurrentPlayer().getTeam()) {
			retur = attack(selectedModelCoordinate, currentCoordinate);
			if (retur) return;
		}

		// Clicked on itself -> set action done
		if (currentCoordinate.equals(selectedModelCoordinate)) {
			retur = setActionDone(currentCoordinate);
			if (retur) return;
		}

		// Unit is chosen and wants to move
		if (selectedModel instanceof Unit) {
			retur = move(selectedModelCoordinate, currentCoordinate);
			if (retur) return;
		}

		resetSelectedModels();
	}

	//TODO: ErrorHandling

	private boolean selectModel(Model m, Coordinate c) {
		if (m != null
				&& !m.isActionDone()
				&& m.getPlayer() == state.getCurrentPlayer()
				&& m.getTimeToBuild() == 0) {
			selectedModelCoordinate = c;
			modelClickedAdapter.modelClicked(m);
			return true;
		}
		return false;
	}

	private boolean buildModel(Coordinate builder, Coordinate where, Model which) {
		int result = controller.buildModel(builder, where.getX(), where.getY(), which.getName());
		boolean built = result > 0;
		if (built) {
			resetSelectedModels();
		}
		return built;
	}

	private boolean addModelToBuild(Coordinate builder, Coordinate modelCoordinate) {
		int result = controller.addModelToBuild(builder, modelCoordinate);
		boolean added = result > 0;
		if (added) {
			resetSelectedModels();
		}
		return added;
	}

	private boolean attack(Coordinate attackerCoordinate, Coordinate defenderCoordinate) {
		Model attacker = state.getModel(attackerCoordinate);
		Model defender = state.getModel(defenderCoordinate);
		int attackerLife = attacker.getLife();
		int defenderLife = defender.getLife();
		int result = controller.attack(attackerCoordinate, defenderCoordinate);
		if (result < 1) return false;

		attacker = state.getModel(attackerCoordinate);
		defender = state.getModel(defenderCoordinate);
		if (attacker != null) {
			dmgCoord1 = attackerCoordinate;
			if (result == 1 || result == 4) {
				dmg1 = (attackerLife - attacker.getLife()) + "";
			} else if (result == 3 || result == 5) {
				dmg1 = "missed";
			} else if (result == 2) {
				dmg1 = "";
			}
		} else {
			dmgCoord1 = null;
		}

		if (defender != null) {
			dmgCoord2 = defenderCoordinate;
			if (result == 1 || result == 2 || result == 3) {
				dmg2 = (defenderLife - defender.getLife()) + "";
			} else if (result == 4 || result == 5) {
				dmg2 = "missed";
			}
		} else {
			dmgCoord2 = null;
		}
		attackMillis = System.currentTimeMillis();
		resetSelectedModels();
		return true;
	}

	private boolean setActionDone(Coordinate c) {
		controller.setActionDone(c);
		resetSelectedModels();
		return true;
	}

	private boolean move(Coordinate from, Coordinate to) {
		int result = controller.move(from, to);
		boolean moved = result > 0;
		if (moved) {
			resetSelectedModels();
		}
		return moved;
	}

	private void resetSelectedModels() {
		selectedModelCoordinate = null;
		unitActionCoordinate = null;
		modelToBuild = null;
		modelClickedAdapter.modelClicked(null);
	}

	@Override
	public void mouseMoved(int x0, int y0, int x1, int y1) {
		super.mouseMoved(x0, y0, x1, y1);
		Model model = state.getModel(selectedModelCoordinate);
		int mapx = (int) (camX + x1 / scale);
		int mapy = (int) (camY + y1 / scale);
		Coordinate c = new Coordinate(mapx / 32, mapy / 32, Layer.Ground);

		Coordinate[] range = null;

		//BuildingModel and modelToBuild selected -> render model to build on hover
		if (selectedModelCoordinate != null && model instanceof BuildingModel && !model.isActionDone() && modelToBuild != null) {
			range = state.getBuildrange(selectedModelCoordinate);
		} else
		//is a unit selected and has to be drawn at mouseposition?
		if (selectedModelCoordinate != null && model instanceof Unit && !((Unit) model).isMoved()) {
			range = state.getMoverange(selectedModelCoordinate);
		}

		if (range != null && Arrays.asList(range).contains(c)) {
			unitActionCoordinate = c;
		}
	}

	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		if (key == Keyboard.KEY_P) {
			mm.playMusic();
		} else if (key == Keyboard.KEY_O) {
			mm.stopMusic();
		} else if (key == Input.KEY_RETURN) {
			endTurn = true;
		} else if (key == Keyboard.KEY_ESCAPE) {
			gc.exit();
		}
	}

	@Override
	public void HUDModelClicked(Model m) {
		modelToBuild = m;
	}
}
