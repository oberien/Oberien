/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.menus;

import java.awt.Font;
import model.map.MapList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import view.data.StartData;
import view.menus.elements.Button;

/**
 *
 * @author Bobthepeanut
 */
public class MapChooser extends MenuTempl {
	private UnicodeFont uf, hf;
	private String[] mapnames;
	private Button[] btns;
	private boolean switchState = false, exit = false;
	private int swidth;
	private int sheight;
	
	public void init(Font f, GameContainer gc) throws SlickException {		
		uf = new UnicodeFont(f.deriveFont(Font.BOLD, 15));
		hf = new UnicodeFont(f.deriveFont(Font.BOLD, 50));
		hf.getEffects().add(new ColorEffect(java.awt.Color.white));
		hf.addAsciiGlyphs();
		hf.loadGlyphs();
		uf.getEffects().add(new ColorEffect(java.awt.Color.white));
		uf.addAsciiGlyphs();
		uf.loadGlyphs();
		mapnames = MapList.getInstance().getMapNames();
		btns = new Button[mapnames.length];
		swidth = gc.getWidth();
		sheight = gc.getHeight();
		int width = 200;
		int height = 80;
		//the +4 is for gaps
		int btnsPerRow = swidth/(width + 4);
		int row = 1;
		int count = 0;
		for (int i = 0; i < mapnames.length; i++) {
			if (count >= btnsPerRow) {
				row++;
				count = 0;
			}
			btns[i] = new Button(count*width + 2, row * height + 80 + 2, width - 2, height - 2, Color.blue, mapnames[i], uf);
			count++;
		}
	}
	
	public void draw(Graphics g) {
		hf.drawString(swidth/2 - hf.getWidth("Choose a map")/2, 0, "Choose a map");
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
