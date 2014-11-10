/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.gamesstates;

import java.util.Arrays;

import event.HUDModelClickedListener;
import event.ModelClickedAdapter;
import event.ModelClickedListener;
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
import controller.Controller;
import controller.State;
import de.lessvoid.nifty.Nifty;

public class GameRunning extends MapState implements HUDModelClickedListener {
	private GameContainer gc;

	private StartData sd;
	private Controller controller;
	private State state;

	private HUDRenderer hudr;
	private UnitRenderer ur;
	private FoWRenderer fowr;
	private ActionGroundRenderer agr;
	private DamageRenderer dr;

	private boolean endTurn;

	private Coordinate selectedModelCoordinate;
	private Model modelToBuild;
	private Coordinate unitMoving;

	private Coordinate dmgCoord1;
	private String dmg1;
	private Coordinate dmgCoord2;
	private String dmg2;
	private long attackMillis;
	private boolean buildModelBoolean;

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
		boolean build;
		if (buildModelBoolean) {
			build = true;
		} else {
			build = hudr.getSelectedModel() != null;
		}
		agr.draw(g, state, selectedModelCoordinate, build);
		Model model;
		if (hudr.getSelectedModel() != null) {
			model = hudr.getSelectedModel();
		} else {
			model = state.getModel(selectedModelCoordinate);
		}
		ur.draw(g, state, unitMoving, model, controller.getDirectionOf(selectedModelCoordinate, unitMoving), state.getCurrentPlayer().getColor());
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
			unitMoving = null;
			dmgCoord1 = null;
			dmgCoord2 = null;
			controller.endTurn();
			endTurn = false;
		}
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		super.mouseClicked(button, x, y, clickCount);
		int mapx = (int) (camX + x / scale);
		int mapy = (int) (camY + y / scale);
		Coordinate currentCoordinate = new Coordinate(mapx / 32, mapy / 32, Layer.Ground);
		Model currentModel = state.getModel(currentCoordinate);

		//selected model
		Model alreadySelectedModel = state.getModel(selectedModelCoordinate);

		//no model selected -> select model if appropriate
		if (alreadySelectedModel == null) {
			selectModel(currentModel, currentCoordinate);
			return;
		}

		// BuildingModel selected and modelToBuild is chosen in HUD
		if (selectedModelCoordinate instanceof BuildingModel && modelToBuild != null) {
			buildModel(selectedModelCoordinate, currentCoordinate, modelToBuild);
			return;
		}

		//if a model is already selected
		if (selectedModelCoordinate != null) {
			//if no model is on the field to move
			if (currentModel == null) {
				//if a model is selected to build
				if (modelToBuild != null) {


					//if model moved successfully
				} else if (controller.move(selectedModelCoordinate, currentCoordinate) == 1) {
					//if model can attack
					Coordinate[] range = state.getDirectAttackrange(currentCoordinate);
					if (range != null) {
						if (state.getEnemyModelPositionsInArea(range).length == 0) {
							selectedModelCoordinate = null;
							controller.setActionDone(currentCoordinate);
						} else {
							selectedModelCoordinate = currentCoordinate;
						}
					} else {
						//if model can build
						range = state.getBuildrange(currentCoordinate);
						if (range != null) {
							buildModelBoolean = true;
							selectedModelCoordinate = currentCoordinate;
						}
					}

					unitMoving = null;
				} else {
					selectedModelCoordinate = null;
					unitMoving = null;
				}
				//if there is an EMEMY model on the field to move
			} else if (currentModel.getPlayer().getTeam() != state.getCurrentPlayer().getTeam()) {
				int life1 = alreadySelectedModel.getLife();
				int life2 = currentModel.getLife();
				int result = controller.attack(selectedModelCoordinate, currentCoordinate);
				if (result > 0) {
					if (state.getModel(selectedModelCoordinate) != null) {
						dmgCoord1 = selectedModelCoordinate;
						if (result == 1 || result == 4) {
							dmg1 = (life1 - alreadySelectedModel.getLife()) + "";
						} else if (result == 3 || result == 5) {
							dmg1 = "missed";
						} else if (result == 4) {
							dmg1 = "";
						}
					} else {
						dmgCoord1 = null;
					}

					if (state.getModel(currentCoordinate) != null) {
						dmgCoord2 = currentCoordinate;
						if (result == 1 || result == 2 || result == 3) {
							dmg2 = (life2 - currentModel.getLife()) + "";
						} else if (result == 4 || result == 5) {
							dmg2 = "missed";
						}
					} else {
						dmgCoord2 = null;
					}
				}
				attackMillis = System.currentTimeMillis();
				selectedModelCoordinate = null;
				unitMoving = null;

				//if model belongs to current player AND model isn't finished to build yet AND selected model can build
			} else if (currentModel.getPlayer() == state.getCurrentPlayer() && currentModel.getTimeToBuild() != 0 && alreadySelectedModel instanceof BuildingModel) {
				controller.addModelToBuild(selectedModelCoordinate, currentCoordinate);
				buildModelBoolean = false;
				//if clicked onto itself
			} else if (selectedModelCoordinate.equals(currentCoordinate)) {
				controller.setActionDone(currentCoordinate);
				selectedModelCoordinate = null;
				unitMoving = null;
			}
			//if no model is selected, it will be selected if unit belongs to current player AND is finished building
		} else if (currentModel != null && !currentModel.isActionDone() && currentModel.getPlayer() == state.getCurrentPlayer() && currentModel.getTimeToBuild() == 0) {
			selectedModelCoordinate = currentCoordinate;
			//if model can't move there
		} else {
			selectedModelCoordinate = null;
			unitMoving = null;
		}
		System.out.println(selectedModelCoordinate);
	}

	private void selectModel(Model m, Coordinate c) {
		if (m != null
				&& !m.isActionDone()
				&& m.getPlayer() == state.getCurrentPlayer()
				&& m.getTimeToBuild() == 0) {
			selectedModelCoordinate = c;
			modelClickedAdapter.modelClicked(m);
		}
	}

	private void buildModel(Coordinate builder, Coordinate where, Model which) {
		controller.buildModel(builder, where.getX(), where.getY(), which.getName());
	}

	@Override
	public void mouseMoved(int x0, int y0, int x1, int y1) {
		super.mouseMoved(x0, y0, x1, y1);
		Model modelToBuild = hudr.getSelectedModel();
		Model model = state.getModel(selectedModelCoordinate);
		int mapx = (int) (camX + x1 / scale);
		int mapy = (int) (camY + y1 / scale);
		Coordinate c = new Coordinate(mapx / 32, mapy / 32, Layer.Ground);

		//modelToBuild is selected
		if (modelToBuild != null && !buildModelBoolean) {
			buildModelBoolean = true;
		}
		//is a unit selected and has to be drawn at mouseposition?
		if (selectedModelCoordinate != null && model instanceof Unit && !((Unit) model).isMoved()) {
			Coordinate[] range;
			//if selected unit is building
			if (buildModelBoolean) {
				range = state.getBuildrange(selectedModelCoordinate);
			} else {
				range = state.getMoverange(selectedModelCoordinate);
			}
			if (Arrays.asList(range).contains(c)) {
				unitMoving = c;
			}
		} else {
			unitMoving = null;
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
