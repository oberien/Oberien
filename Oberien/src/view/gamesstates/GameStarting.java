/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.gamesstates;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import view.data.StartData;

/**
 *
 * @author Bobthepeanut
 */
public class GameStarting extends BasicGameState {
	private Font f;
	private UnicodeFont uf;
	private StartData sd;
	private Image[] ui;
	
	public GameStarting(StartData sd) {
		this.sd = sd;
	}

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new File("./res/fonts/digital_tech.ttf"));
			sd.setFont(f);
			f.deriveFont(Font.BOLD, 20);
		} catch (FontFormatException ex) {
			Logger.getLogger(GameStarting.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException e) {
			e.printStackTrace();
		}
		uf = new UnicodeFont(f);
		uf.getEffects().add(new ColorEffect(java.awt.Color.white));
		uf.addAsciiGlyphs();
		uf.loadGlyphs();
		
		/*ui = new Image[1];
		ui[0] = new Image("/res/imgs/ui/Button.png");
		sd.setUI(ui);*/
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
		sbg.enterState(getID() + 1);
	}
	
}
