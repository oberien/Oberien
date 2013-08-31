/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.gamesstates;

import controller.Options;
import controller.StateMap;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Layer;
import model.Model;
import model.Player;
import model.map.Coordinate;
import model.map.Map;
import model.map.MapList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import view.data.StartData;
import view.eventhandler.MouseEvents;
import view.renderer.*;

/**
 *
 * @author Bobthepeanut
 */
public class GameRunning extends BasicGameState {
	private StartData sd;
	private Map map;
	private StateMap statemap;
	private MouseEvents me;
	private MapRenderer mr;
	private HUDRenderer hudr;
	private UnitRenderer ur;
	private GroundRenderer gr;
	private DamageRenderer dr;
	
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
	private Coordinate[] sight;

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
			
			hudr = sd.getHudr();
			ur = sd.getUr();
			gr = sd.getGr();
			dr = sd.getDr();
			me = new MouseEvents();
			me.init();
			screenWidth = gc.getScreenWidth();
			screenHeight = gc.getScreenHeight();
			statemap = sd.getSm();
			
			sight = statemap.getSight();
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setAntiAlias(Options.antiAliasing);
		g.resetTransform();
		g.translate(-camX * scale, -camY * scale);
		g.scale(scale, scale);
		mr.draw(g);
		gr.draw(g, statemap, sight, mapcoord);
		ur.draw(g, statemap, unitMoving, statemap.getModel(mapcoord), statemap.getDirectionOf(mapcoord, unitMoving));
		dr.draw(g, dmgCoord1, dmg1, dmgCoord2, dmg2, attackMillis);
		g.resetTransform();
		hudr.draw(g, statemap, sbg, statemap.getModel(mapcoord));
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		//zooming/scaling
		int mw = Mouse.getDWheel();
		if ((scaleUp || mw > 0) && scale < 2) {
			float mouseMapX = mouseX / scale + camX;
			float mouseMapY = mouseY / scale + camY;
			scale += 0.05;
			camX = mouseMapX - mouseX / scale;
			camY = mouseMapY - mouseY / scale;
		}
		if ((scaleDown || mw < 0) && scale >= 0.3) {
			camX += gc.getScreenWidth() / 2 / scale - gc.getScreenWidth() / 2;
			camY += gc.getScreenHeight() / 2 / scale - gc.getScreenHeight() / 2;
			scale -= 0.05;
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
		int mapx = (int) (camX + mpoint.getX() / scale);
		int mapy = (int) (camY + mpoint.getY() / scale);
		Coordinate c = new Coordinate(mapx / 32, mapy / 32, Layer.Ground);
		Model m = statemap.getModel(c);
		//Mouse button down -> Unit gets selected/moves/attacks
		if (me.isMousePressed(0)) {
			//if a model is already selected
			if (mapcoord != null) {
				//if no model is on the field to move
				if (m == null) {
					//if model moved successfully
					if (statemap.move(mapcoord, c) == 1) {
						if (statemap.getEnemyModelPositionsInArea(statemap.getRange(c, StateMap.DIRECT_ATTACKRANGE)).length == 0) {
							mapcoord = null;
							statemap.actionDone(c);
						} else {
							mapcoord = c;
						}
						unitMoving = null;
					} else {
						mapcoord = null;
						unitMoving = null;
					}
				//if there is am EMEMY model on the field to move
				} else if (m.getPlayer().getTeam() != statemap.getCurrentPlayer().getTeam()) {
					int life1 = statemap.getModel(mapcoord).getLife();
					int life2 = m.getLife();
					int result = statemap.attack(mapcoord, c);
					if (result > 0) {
						if (statemap.getModel(mapcoord) != null) {
							dmgCoord1 = mapcoord;
							if (result == 1 || result == 4) {
								dmg1 = (life1 - statemap.getModel(mapcoord).getLife()) + "";
							} else if (result == 3 || result == 5) {
								dmg1 = "missed";
							} else if (result == 4) {
								dmg1 = "";
							}
						} else {
							dmgCoord1 = null;
						}

						if (statemap.getModel(c) != null) {
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
				} else if (mapcoord.equals(c)) {
					statemap.actionDone(c);
					mapcoord = null;
					unitMoving = null;
				}
			//if no model is selected, it will be selected if unit belongs to current player
			} else if (m != null && !m.isActionDone() && m.getPlayer() == statemap.getCurrentPlayer()) {
				mapcoord = c;
			//if model can't move there
			} else {
				mapcoord = null;
				unitMoving = null;
			}
			sight = statemap.getSight();
		//Mousebutton not down
		} else {
			//is a unit selected and has to be drawn at mouseposition?
			if (mapcoord != null && !statemap.getModel(mapcoord).isMoved()) {
				Coordinate[] mr = statemap.getRange(mapcoord, StateMap.MOVERANGE);
				for (Coordinate c1 : mr) {
					if (c1.equals(c)) {
						unitMoving = c;
					}
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

		//TODO here just testing purpose
		if (endTurn) {
			mapcoord = null;
			unitMoving = null;
			dmgCoord1 = null;
			dmgCoord2 = null;
			statemap.endTurn();
			endTurn = false;
			sight = statemap.getSight();
		}
	}

	@Override
	public void keyPressed(int key, char c) {
//    	System.out.println("Pressed: " + key + " " + c);
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
//    	System.out.println("Released: " + key + " " + c);
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
