package view.gamesstates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.SlickCallable;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import view.data.StartData;
import de.lessvoid.nifty.Nifty;

public class NiftyMenu extends BasicGameState {
	private StartData sd;
	private Nifty nifty;
	
	public NiftyMenu(StartData sd) {
		this.sd = sd;
	}
		
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.nifty = sd.getNifty();
	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		nifty.update();
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		SlickCallable.enterSafeBlock();
		nifty.render(false);
		SlickCallable.leaveSafeBlock();
	}
	
	@Override
	public int getID() {
		return 1;
	}
}