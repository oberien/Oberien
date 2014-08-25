
package view;



import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import controller.Options;

public class ViewStarter {
	
	private static AppGameContainer game;
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws SlickException {
		Logger.getLogger("de.lessvoid").setLevel(Level.WARNING);
		game = new AppGameContainer(new View());
		Options.initOptions(game);
		Options.load();
		Options.applySettings();
		game.start();
	}
	
	public static Input getInput() {
		return game.getInput();
	}
}
