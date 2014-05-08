
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
		game.setDisplayMode(game.getScreenWidth(), game.getScreenHeight(), true);
		if (Options.screenMode == 0) {
			game.setFullscreen(true);
		} else if (Options.screenMode == 1) {
			game.setFullscreen(false);
		}
		if (Options.screenMode == 2) {
			System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
		}
		game.setVSync(Options.vsync);
		game.setUpdateOnlyWhenVisible(Options.onlyUpdateWhenVisible);
		if (Options.fps != -1) {
			game.setTargetFrameRate(Options.fps);
		}
		game.start();
	}
	
	public static Input getInput() {
		return game.getInput();
	}
}
