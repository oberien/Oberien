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
import view.event.ActionEvent;
import view.event.ActionListener;
import view.components.Button;

public class MapChooser extends MenuTempl implements ActionListener{
	private UnicodeFont uf, hf;
	private String[] mapnames;
	private Button[] btns;
	private boolean[] btnsPressed;
	private boolean switchState = false, exit = false;
	private int swidth;
	private int sheight;
	private StartData sd;
	
	public void init(Font f, GameContainer gc, StartData sd) throws SlickException {
		this.sd = sd;
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
		btnsPressed = new boolean[btns.length];
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
			btns[i] = new Button(count*width + 2, row * height + 80 + 2, width - 2, height - 2, null , sd.getUI().getButton(), sd.getUI().getButtonPressed(), mapnames[i], uf);
			btns[i].setActionCommand(i + "");
			btns[i].addActionListener(this);
			add(btns[i]);
			count++;
		}
	}
	
	public void paintComponent(Graphics g) {
		hf.drawString(swidth/2 - hf.getWidth("Choose a map")/2, 0, "Choose a map");
		super.paintComponent(g);
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		sd.setMap(MapList.getInstance().getMap(mapnames[Integer.parseInt(e.getActionCommand())]));
		switchState = true;
	}
}
