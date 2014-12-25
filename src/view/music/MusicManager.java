/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.music;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public class MusicManager {
	private Music m1;
	
	public void init() throws SlickException{
		m1 = new Music("/res/music/Searching.ogg", true);
	}
			
	public void playMusic() {
		m1.play();
	}
	
	public void stopMusic() {
		m1.stop();
	}
}
