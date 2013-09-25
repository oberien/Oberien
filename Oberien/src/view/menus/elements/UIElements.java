/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.menus.elements;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Bobthepeanut
 */
public class UIElements {
	private Image exit;
	private Image exit_p;
	private Image settings;
	private Image settings_p;
	private Image startGame;
	private Image startGame_p;
	private Image bg, bg1;
	private Image logo;
	private Image button;
	private Image button_p;
	
	public void loadButton() throws SlickException {
		button = new Image("res/imgs/ui/button.png");
		button_p = new Image("res/imgs/ui/button-p.png");
	}
	
	public void loadLogo() throws SlickException {
		logo = new Image("res/imgs/ui/logo.png");
	}
	
	public void loadExit() throws SlickException {
		exit = new Image("/res/imgs/ui/exit.png");
		exit_p = new Image("/res/imgs/ui/exit-p.png"); 
	}
	
	public void loadSettings() throws SlickException {
		settings = new Image("/res/imgs/ui/settings.png");
		settings_p = new Image("/res/imgs/ui/settings-p.png");
	}
	
	public void loadStartGame() throws SlickException {
		startGame = new Image("/res/imgs/ui/start_game.png");
		startGame_p = new Image("/res/imgs/ui/start_game-p.png");
	}
	
	public void loadBackground() throws SlickException {
		bg = new Image("/res/imgs/ui/bg02.png");
		bg1 = new Image("/res/imgs/ui/bg03.png");
	}
	
	public void loadAll() throws SlickException {
		this.loadLogo();
		this.loadButton();
		this.loadExit();
		this.loadSettings();
		this.loadStartGame();
		this.loadBackground();
	}

	public Image getExit() {
		return exit;
	}

	public Image getExitPressed() {
		return exit_p;
	}

	public Image getSettings() {
		return settings;
	}

	public Image getBg1() {
		return bg1;
	}

	public Image getSettingsPressed() {
		return settings_p;
	}

	public Image getStartGame() {
		return startGame;
	}

	public Image getStartGamePressed() {
		return startGame_p;
	}

	public Image getBg() {
		return bg;
	}
	
	public Image getLogo() {
		return logo;
	}

	public Image getButton() {
		return button;
	}

	public Image getButtonPressed() {
		return button_p;
	}	
}
