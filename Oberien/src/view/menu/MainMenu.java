/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.menu;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import view.data.StartData;
import view.event.ActionEvent;
import view.event.ActionListener;
import view.components.Button;

public class MainMenu extends MenuTempl implements ActionListener {
	private boolean modeSwitch = false, switchMenu = false, shouldExit = false;
	private String menuName = null;
	private Button start, exit, settings, startp, exitp, settingsp;
	private Font f;
	private UnicodeFont uf;
	private boolean startPressed, exitPressed, settingsPressed;
	
	public MainMenu(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	public void init(GameContainer gc, StartData sd) throws SlickException {
		f = sd.getFont();
		f = f.deriveFont(Font.BOLD, 20);
		uf = new UnicodeFont(f);
		uf.getEffects().add(new ColorEffect(java.awt.Color.white));
		uf.addAsciiGlyphs();
		uf.loadGlyphs();	   
		start = new Button(gc.getWidth()/2 - 500/2, gc.getHeight()/2 - 100, 500, 100, null, sd.getUI().getStartGame(), sd.getUI().getStartGamePressed());
		start.addActionListener(this);
		start.setActionCommand("start");
		add(start);
		exit = new Button(0, 0, sd.getUI().getExit().getWidth(), sd.getUI().getExit().getHeight(), null, sd.getUI().getExit(), sd.getUI().getExitPressed());
		exit.addActionListener(this);
		exit.setActionCommand("exit");
		add(exit);
		settings = new Button(gc.getWidth()/2 - 500/2, gc.getHeight()/2 - 250, 500, 100, null, sd.getUI().getSettings(), sd.getUI().getSettingsPressed());
		settings.addActionListener(this);
		settings.setActionCommand("settings");
		add(settings);
	}

	@Override
	public boolean getModeSwitch() {
		return modeSwitch;
	}

	@Override
	public boolean switchMenu() {
		return switchMenu;
	}

	@Override
	public String getSwitchMenu() {
		return menuName;
	}

	@Override
	public boolean shouldExit() {
		return shouldExit;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		if (ac.equals("start")) {
			switchMenu = true;
			menuName = "MapChooser";
		} else if (ac.equals("exit")) {
			shouldExit = true;
		} else if (ac.equals("settings")) {
			settingsPressed = false;
		}
	}
}
