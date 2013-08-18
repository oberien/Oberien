/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.renderer;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Bobthepeanut
 */
public class BufferedMapRenderer implements MapRenderer {
    private Image map;
    
    public BufferedMapRenderer(Image map) {
    	this.map = map;
    }

    @Override
    public void draw(Graphics g) throws SlickException {
    	g.drawImage(map, 0, 0);
    }
}
