package view.gamesstates;

import controller.Options;
import model.map.Map;
import model.map.MapList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import view.data.StartData;
import view.renderer.MapRenderer;

public abstract class MapState extends EventHandlingGameState {
	private final StartData sd;
	private Map map;
	private MapRenderer mr;

	private int screenWidth;
	private int screenHeight;
	private boolean scaleUp;
	private boolean scaleDown;
	private boolean moveLeft;
	private boolean moveRight;
	private boolean moveUp;
	private boolean moveDown;
	private int mouseX;
	private int mouseY;
	protected float scale = 1;
	protected float camX = 0;

	protected float camY = 0;

	public MapState(StartData sd) {
		this.sd = sd;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		if (MapList.getInstance().getCurrentMap() != null) {
			Options.applySettings();
			map = sd.getMap();
			mr = sd.getMr();
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setAntiAlias(Options.isAntiAliasing());
		g.resetTransform();
		g.translate(-camX * scale, -camY * scale);
		g.scale(scale, scale);
		mr.draw(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		//prepare all variables for EventHandling
		screenWidth = gc.getScreenWidth();
		screenHeight = gc.getScreenHeight();

		//handle all events
		handleEvents();

		//zooming/scaling
		if (scaleUp && scale < 2) {
			scale += 0.003 * delta;
		}
		if (scaleDown && scale >= 0.3) {
			scale -= 0.003 * delta;
		}

		//camera
		float moveSpeed = delta / scale;
		float minX = -500 / scale * ((float) screenWidth / screenHeight);
		float minY = -500 / scale * ((float) screenHeight / screenWidth);
		float maxX = map.getWidth() * 32 - minX - screenWidth / scale;
		float maxY = map.getHeight() * 32 - minY - screenHeight / scale;

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

	@Override
	public void mouseWheelMoved(int mw) {
		if (mw > 0 && scale < 2) {
			float mouseMapX = mouseX / scale + camX;
			float mouseMapY = mouseY / scale + camY;

			scale += 0.1;

			camX = mouseMapX - mouseX / scale;
			camY = mouseMapY - mouseY / scale;
		} else if (mw < 0 && scale >= 0.3) {
			camX += screenWidth / 2 / scale - screenWidth / 2;
			camY += screenHeight / 2 / scale - screenHeight / 2;

			scale -= 0.1;

			camX -= screenWidth / 2 / scale - screenWidth / 2;
			camY -= screenHeight / 2 / scale - screenHeight / 2;
		}
	}

	@Override
	public void keyPressed(int key, char c) {
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
	}

	@Override
	public void keyReleased(int key, char c) {
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
}
