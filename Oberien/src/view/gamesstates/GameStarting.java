/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.gamesstates;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.nulldevice.NullSoundDevice;
import de.lessvoid.nifty.renderer.lwjgl.input.LwjglInputSystem;
import de.lessvoid.nifty.renderer.lwjgl.render.LwjglRenderDevice;
import de.lessvoid.nifty.tools.TimeProvider;
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

import view.components.Panel;
import view.data.StartData;
import view.data.UIElements;

public class GameStarting extends BasicGameState {

	private Font f;
	private UnicodeFont uf;
	private StartData sd;
	private UIElements ui;
	private Panel panel;

	private int count = 0;

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

		ui = new UIElements();
		ui.loadLogo();

		Image logo = ui.getLogo();
		panel = new Panel(0, 0, gc.getWidth(), gc.getHeight());
		panel.setBackgroundImage(logo, gc.getWidth() / 2 - logo.getWidth() / 2, gc.getHeight() / 2 - logo.getHeight() / 2);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		panel.repaint(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
		if (count == 0) {
			ui.loadBackground();
		} else if (count == 1) {
			ui.loadExit();
		} else if (count == 2) {
			ui.loadSettings();
		} else if (count == 3) {
			ui.loadStartGame();
		} else if (count == 4) {
			ui.loadButton();
		} else if (count == 5) {
			LwjglInputSystem inSys = new LwjglInputSystem();
			try {
				inSys.startup();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			Nifty nifty = new Nifty(new LwjglRenderDevice(true),
					new NullSoundDevice(),
					inSys,
					new TimeProvider());
			nifty.fromXml("res/xml/main.xml", "start");
			nifty.gotoScreen("start");
			nifty.loadStyleFile("nifty-default-styles.xml");
			nifty.loadControlFile("nifty-default-controls.xml");
			sd.setNifty(nifty);
		} else {
			sd.setUI(ui);
			sbg.getState(getID() + 1).init(gc, sbg);
			sbg.enterState(getID() + 1);
		}
		count++;
	}
}
