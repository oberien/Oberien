package view.gamesstates;

import controller.Controller;
import de.lessvoid.nifty.Nifty;
import event.NiftyMenuUpdateAdapter;
import event.NiftyMenuUpdateListener;
import model.map.MapList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.SlickCallable;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import view.data.StartData;

public class NiftyMenu extends BasicGameState {

	private static StartData sd;
	private static StateBasedGame sbg;
	private Nifty nifty;

	private static NiftyMenuUpdateAdapter niftyMenuUpdateAdapter;

	public NiftyMenu(StartData sd) {
		this.sd = sd;
		niftyMenuUpdateAdapter = new NiftyMenuUpdateAdapter();
	}

	public static void addNiftyMenuUpdateListener(NiftyMenuUpdateListener l) {
		niftyMenuUpdateAdapter.addNiftyMenuUpdateListener(l);
	}

	public static void removeNiftyMenuUpdateListener(NiftyMenuUpdateListener l) {
		niftyMenuUpdateAdapter.removeNiftyMenuUpdateListener(l);
	}

	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.sbg = game;
		this.nifty = sd.getNifty();
	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		niftyMenuUpdateAdapter.update();
		nifty.update();
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		SlickCallable.enterSafeBlock();
		nifty.render(true);
		SlickCallable.leaveSafeBlock();
	}

	public static void startGame(Controller controller) {
		sd.setMap(MapList.getInstance().getCurrentMap());
		sd.setController(controller);
		sd.setState(controller.getState());
		sbg.enterState(2);
	}

	@Override
	public int getID() {
		return 1;
	}
}
