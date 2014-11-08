/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.gamesstates;

import java.util.Arrays;

import model.BuildingModel;
import model.Layer;
import model.Model;
import model.map.Coordinate;
import model.map.Map;
import model.map.MapList;
import model.unit.Unit;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.StateBasedGame;

import view.data.StartData;
import view.eventhandler.MouseEvents;
import view.music.MusicManager;
import view.renderer.ActionGroundRenderer;
import view.renderer.DamageRenderer;
import view.renderer.FoWRenderer;
import view.renderer.HUDRenderer;
import view.renderer.MapRenderer;
import view.renderer.UnitRenderer;
import controller.Controller;
import controller.Options;
import controller.State;
import de.lessvoid.nifty.Nifty;
import org.newdawn.slick.opengl.SlickCallable;
import view.data.Globals;

public class GameRunning extends MapState {
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

	private Coordinate mapcoord;
	private Coordinate unitMoving;
	private Coordinate dmgCoord1;
	private String dmg1;
	private Coordinate dmgCoord2;
	private String dmg2;
	private long attackMillis;
	private boolean buildModel;

	private Nifty nifty;

	private MusicManager mm;

	public GameRunning(StartData sd) {
		super(sd);
		this.sd = sd;
		dmg1 = "";
		dmg2 = "";
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
		if (buildModel == true) {
			build = true;
		} else {
			build = hudr.getSelectedModel() != null;
		}
		agr.draw(g, state, mapcoord, build);
		Model model;
		if (hudr.getSelectedModel() != null) {
			model = hudr.getSelectedModel();
		} else {
			model = state.getModel(mapcoord);
		}
		ur.draw(g, state, unitMoving, model, controller.getDirectionOf(mapcoord, unitMoving), state.getCurrentPlayer().getColor());
		dr.draw(g, dmgCoord1, dmg1, dmgCoord2, dmg2, attackMillis);
		g.resetTransform();
		hudr.draw(g, state, sbg, state.getModel(mapcoord));
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
			mapcoord = null;
			unitMoving = null;
			dmgCoord1 = null;
			dmgCoord2 = null;
			controller.endTurn();
			endTurn = false;
		}
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		System.out.println(":D");
		super.mouseClicked(button, x, y, clickCount);
		int mapx = (int) (camX + x / scale);
		int mapy = (int) (camY + y / scale);
		Coordinate c = new Coordinate(mapx / 32, mapy / 32, Layer.Ground);
		//model of current field
		Model m = state.getModel(c);
		//selected model
		Model model = state.getModel(mapcoord);

		Model modelToBuild = hudr.getSelectedModel();
		//Mouse button down -> Unit gets selected/moves/attacks
		if (hudr.isMouseEventAvailable()) {
			//if a model is already selected
			if (mapcoord != null) {
				//if no model is on the field to move
				if (m == null) {
					//if a model is selected to build
					if (modelToBuild != null) {
						int id = controller.buildModel(mapcoord, c.getX(), c.getY(), modelToBuild.getName());
						if (id == 1) {
							model.setActionDone(true);
						} else {
							System.out.println("BuildError: " + id);
						}
						//TODO add messages when builderror occurs
						hudr.resetSelection();
						mapcoord = null;
						unitMoving = null;
						buildModel = false;

						//if model moved successfully
					} else if (controller.move(mapcoord, c) == 1) {
						//if model can attack
						Coordinate[] range = state.getDirectAttackrange(c);
						if (range != null) {
							if (state.getEnemyModelPositionsInArea(range).length == 0) {
								mapcoord = null;
								controller.setActionDone(c);
							} else {
								mapcoord = c;
							}
						} else {
							//if model can build
							range = state.getBuildrange(c);
							if (range != null) {
								buildModel = true;
								mapcoord = c;
							}
						}

						unitMoving = null;
					} else {
						mapcoord = null;
						unitMoving = null;
					}
					//if there is an EMEMY model on the field to move
				} else if (m.getPlayer().getTeam() != state.getCurrentPlayer().getTeam()) {
					int life1 = model.getLife();
					int life2 = m.getLife();
					int result = controller.attack(mapcoord, c);
					if (result > 0) {
						if (state.getModel(mapcoord) != null) {
							dmgCoord1 = mapcoord;
							if (result == 1 || result == 4) {
								dmg1 = (life1 - model.getLife()) + "";
							} else if (result == 3 || result == 5) {
								dmg1 = "missed";
							} else if (result == 4) {
								dmg1 = "";
							}
						} else {
							dmgCoord1 = null;
						}

						if (state.getModel(c) != null) {
							dmgCoord2 = c;
							if (result == 1 || result == 2 || result == 3) {
								dmg2 = (life2 - m.getLife()) + "";
							} else if (result == 4 || result == 5) {
								dmg2 = "missed";
							}
						} else {
							dmgCoord2 = null;
						}
					}
					attackMillis = System.currentTimeMillis();
					mapcoord = null;
					unitMoving = null;

					//if model belongs to current player AND model isn't finished to build yet AND selected model can build
				} else if (m.getPlayer() == state.getCurrentPlayer() && m.getTimeToBuild() != 0 && model instanceof BuildingModel) {
					controller.addModelToBuild(mapcoord, c);
					buildModel = false;
					//if clicked onto itself
				} else if (mapcoord.equals(c)) {
					controller.setActionDone(c);
					mapcoord = null;
					unitMoving = null;
				}
				//if no model is selected, it will be selected if unit belongs to current player AND is finished building
			} else if (m != null && !m.isActionDone() && m.getPlayer() == state.getCurrentPlayer() && m.getTimeToBuild() == 0) {
				mapcoord = c;
				//if model can't move there
			} else {
				mapcoord = null;
				unitMoving = null;
			}
			//Mousebutton not down
		} else {
			//modelToBuild is selected
			if (modelToBuild != null && !buildModel) {
				buildModel = true;
			}
			//is a unit selected and has to be drawn at mouseposition?
			if (mapcoord != null && model instanceof Unit && !((Unit) model).isMoved()) {
				Coordinate[] range;
				//if selected unit is building
				if (buildModel) {
					range = state.getBuildrange(mapcoord);
				} else {
					range = state.getMoverange(mapcoord);
				}
				if (Arrays.asList(range).contains(c)) {
					unitMoving = c;
				}
			} else {
				unitMoving = null;
			}
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
}
