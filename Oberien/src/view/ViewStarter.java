
package view;



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
