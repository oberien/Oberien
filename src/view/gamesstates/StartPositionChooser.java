package view.gamesstates;

import controller.Controller;
import controller.Options;
import controller.State;
import model.Layer;
import model.map.Coordinate;
import model.map.Map;
import model.map.MapList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import view.data.StartData;
import view.renderer.FoWRenderer;
import view.renderer.GridRenderer;
import view.renderer.UnitRenderer;

import java.util.Arrays;

public class StartPositionChooser extends MapState {
	private GameContainer gc;

	private final StartData sd;
	private Controller controller;
	private State state;
	private Map map;
	private UnitRenderer ur;
	private FoWRenderer fowr;

	private GridRenderer gr;

	private int basex = -1, basey = -1;
	private boolean endTurn;

	public StartPositionChooser(StartData sd) {
		super(sd);
		this.sd = sd;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		super.init(gc, sbg);
		if (MapList.getInstance().getCurrentMap() != null) {
			Options.applySettings();

			this.gc = gc;

			controller = sd.getController();
			state = sd.getState();
			map = sd.getMap();
			ur = sd.getUr();
			fowr = sd.getFowr();
			gr = sd.getGr();
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		super.render(gc, sbg, g);
		fowr.draw(g, state.getSight());
		ur.draw(g, state);
//		gr.draw();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		super.update(gc, sbg, delta);

		if (endTurn) {
			endTurn = false;
			if (basex != -1 && basey != -1) {
				controller.endTurn();
				basex = -1;
				basey = -1;
				if (state.getRound() == 1) {
					sbg.getState(getID() + 1).init(gc, sbg);
					sbg.enterState(getID() + 1);
				}
			}
		}
	}

	@Override
	public int getID() {
		return 3;
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		super.mouseClicked(button, x, y, clickCount);
		int mapx = (int) (super.camX + x / super.scale);
		int mapy = (int) (super.camY + y / super.scale);

		if (basex != -1) {
			controller.removeModel(new Coordinate(basex, basey, Layer.Ground));
		}
		basex = mapx / 32;
		basey = mapy / 32;
		if (!Arrays.asList(map.getStartAreaOfTeam(state.getCurrentPlayer().getTeam())).contains(new Coordinate(basex, basey, Layer.Ground))) {
			basex = -1;
			basey = -1;
		}
		controller.addModel(basex, basey, "Base");
	}

	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		if (key == Input.KEY_ESCAPE) {
			gc.exit();
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		super.keyReleased(key, c);
		if (key == Input.KEY_RETURN) {
			endTurn = true;
		}
	}
}
