/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.menus;

import java.awt.Font;
import model.map.MapList;
import net.java.games.input.Component;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import view.data.StartData;
import view.gamesstates.Menu;
import view.menus.elements.Button;

/**
 *
 * @author Bobthepeanut
 */
public class MapChooser extends MenuTempl {
	private UnicodeFont uf;
	private String[] mapnames;
	private Button[] btns;
	private boolean switchState = false, exit = false;
	
	public void init(Font f, GameContainer gc) throws SlickException {
		f = f.deriveFont(Font.BOLD, 20);
		uf = new UnicodeFont(f);
		uf.getEffects().add(new ColorEffect(java.awt.Color.white));
		uf.addAsciiGlyphs();
		uf.loadGlyphs();
		mapnames = MapList.getInstance().getMapNames();
		btns = new Button[mapnames.length];
		int swidth = gc.getWidth();
		int sheight = gc.getHeight();
		int width = swidth/btns.length;
		int height = 80;
		for (int i = 0; i < mapnames.length; i++) {
			btns[i] = new Button(i*width, sheight/2 - height/2, width, height, Color.blue, mapnames[i], uf);
		}
	}
	
	public void draw(Graphics g) {
		for (int i = 0; i < btns.length; i++) {
			btns[i].draw(g);
		}
	}
	
	public void update(StartData sd, boolean mousePressed) {
		for (int i = 0; i < btns.length; i++) {
			if (btns[i].isClicked(mousePressed)) {
				sd.setMap(MapList.getInstance().getMap(mapnames[i]));
				switchState = true;
			}
		}
	}

	@Override
	public boolean getModeSwitch() {
		return switchState;
	}

	@Override
	public boolean switchMenu() {
		return false;
	}

	@Override
	public String getSwitchMenu() {
		return null;
	}

	@Override
	public boolean shouldExit() {
		return exit;
	}
}
