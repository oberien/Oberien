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
import org.newdawn.slick.state.BasicGameState;
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
import view.data.Globals;
import view.gui.controllers.HUDScreenController;

public class GameRunning extends BasicGameState {
	private StartData sd;
	private Map map;
	private Controller controller;
	private State state;
	private MouseEvents me;
	private MapRenderer mr;
	private HUDRenderer hudr;
	private UnitRenderer ur;
	private FoWRenderer fowr;
	private ActionGroundRenderer agr;
	private DamageRenderer dr;
	private HUDScreenController huc;
	
	private int screenWidth;
	private int screenHeight;
	private boolean scaleUp;
	private boolean scaleDown;
	private boolean moveLeft;
	private boolean moveRight;
	private boolean moveUp;
	private boolean moveDown;
	private boolean endTurn;
	private int mouseX;
	private int mouseY;
	private float scale = 1;
	private float camX = 0;
	private float camY = 0;
	private Coordinate mapcoord;
	private Coordinate unitMoving;
	private Coordinate dmgCoord1;
	private String dmg1;
	private Coordinate dmgCoord2;
	private String dmg2;
	private long attackMillis;
	private boolean buildModel;
	
	private MusicManager mm;

	public GameRunning(StartData sd) {
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
		if (MapList.getInstance().getCurrentMap() != null) {
			map = sd.getMap();
			mr = sd.getMr();
			mr.init();
			
			hudr = sd.getHudr();
			ur = sd.getUr();
			fowr = sd.getFowr();
			agr = sd.getAgr();
			dr = sd.getDr();
			me = new MouseEvents();
			me.init();
			screenWidth = gc.getScreenWidth();
			screenHeight = gc.getScreenHeight();
			controller = sd.getController();
			state = sd.getState();
					
			huc = Globals.getHUDController();
			mm = new MusicManager();
			mm.init();
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setAntiAlias(Options.isAntiAliasing());
		g.resetTransform();
		g.translate(-camX * scale, -camY * scale);
		g.scale(scale, scale);
		mr.draw(g);
		fowr.draw(g, state.getSight());
		boolean build;
		if (buildModel == true) {
			build = true;
		} else {
			build = hudr.getSelectedModel()!=null;
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
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (gc.getInput().isKeyDown(Keyboard.KEY_P)) {
			mm.playMusic();
		} else if(gc.getInput().isKeyDown(Keyboard.KEY_O)) {
			mm.stopMusic();
		}
		//zooming/scaling
		int mw = Mouse.getDWheel();
		if ((scaleUp || mw > 0) && scale < 2) {
			float mouseMapX = mouseX / scale + camX;
			float mouseMapY = mouseY / scale + camY;
			if (scaleUp) {
				scale += 0.003*delta;
			} else if (mw > 0) {
				scale += 0.1;
			}
			camX = mouseMapX - mouseX / scale;
			camY = mouseMapY - mouseY / scale;
		} else if ((scaleDown || mw < 0) && scale >= 0.3) {
			camX += gc.getScreenWidth() / 2 / scale - gc.getScreenWidth() / 2;
			camY += gc.getScreenHeight() / 2 / scale - gc.getScreenHeight() / 2;
			if (scaleDown) {
				scale -= 0.003*delta;
			} else if (mw < 0) {
				scale -= 0.1;
			}
			camX -= gc.getScreenWidth() / 2 / scale - gc.getScreenWidth() / 2;
			camY -= gc.getScreenHeight() / 2 / scale - gc.getScreenHeight() / 2;
		}

		//camera
		float moveSpeed = delta / scale;
		float minX = -500 / scale * ((float) gc.getScreenWidth() / gc.getScreenHeight());
		float minY = -500 / scale * ((float) gc.getScreenHeight() / gc.getScreenWidth());
		float maxX = map.getWidth() * 32 - minX - gc.getScreenWidth() / scale;
		float maxY = map.getHeight() * 32 - minY - gc.getScreenHeight() / scale;

		if (moveLeft && camX > minX) {
			camX -= moveSpeed;
		}
		if (moveUp && camY > minY) {
			camY -= moveSpeed;
		}
		if (moveRight && camX < maxX) {
			camX += moveSpeed;
		}
		if (moveDown && camY < maxY) {
			camY += moveSpeed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			gc.exit();
		}

		//Events
		//MouseEvents
		Point mpoint = me.getMousePos();
		
		hudr.update(me.isMousePressed(0), mpoint);
		
		int mapx = (int) (camX + mpoint.getX() / scale);
		int mapy = (int) (camY + mpoint.getY() / scale);
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
			if (mapcoord != null && model instanceof Unit && !((Unit)model).isMoved()) {
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
	public void keyPressed(int key, char c) {
//		System.out.println("Pressed: " + key + " " + c);
		if (c == '+') {
			scaleUp = true;
		}
		if (c == '-') {
			scaleDown = true;
		}
		if (key == Input.KEY_LEFT) {
			moveLeft = true;
		}
		if (key == Input.KEY_RIGHT) {
			moveRight = true;
		}
		if (key == Input.KEY_UP) {
			moveUp = true;
		}
		if (key == Input.KEY_DOWN) {
			moveDown = true;
		}
		if (key == Input.KEY_RETURN) {
			endTurn = true;
		}
	}

	@Override
	public void keyReleased(int key, char c) {
//		System.out.println("Released: " + key + " " + c);
		if (c == '+') {
			scaleUp = false;
		}
		if (c == '-') {
			scaleDown = false;
		}
		if (key == Input.KEY_LEFT) {
			moveLeft = false;
		}
		if (key == Input.KEY_RIGHT) {
			moveRight = false;
		}
		if (key == Input.KEY_UP) {
			moveUp = false;
		}
		if (key == Input.KEY_DOWN) {
			moveDown = false;
		}
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
//		System.out.println("mouse moved from " + oldx + ":" + oldy + " to " + newx + ":" + newy);
		mouseX = newx;
		mouseY = newy;
		if (newx < 3) {
			moveLeft = true;
		} else if (newx > screenWidth - 4) {
			moveRight = true;
		} else if (moveLeft && oldx < 3) {
			moveLeft = false;
		} else if (moveRight && oldx > screenWidth - 4) {
			moveRight = false;
		}

		if (newy < 3) {
			moveUp = true;
		} else if (newy > screenHeight - 4) {
			moveDown = true;
		} else if (moveUp && oldy < 3) {
			moveUp = false;
		} else if (moveDown && oldy > screenHeight - 4) {
			moveDown = false;
		}
	}
}
