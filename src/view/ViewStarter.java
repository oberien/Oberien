
package view;


import controller.Options;
import logger.ErrorLogger;
import logger.EventLogger;
import logger.GameLogger;
import logger.TechLogger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class ViewStarter {
	
	private static AppGameContainer game;
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws SlickException {
		ErrorLogger.init();
		GameLogger.init();
		TechLogger.init();
		EventLogger.init();
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
