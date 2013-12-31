/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.renderer;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SimpleMapRenderer implements MapRenderer {
    private byte[][] data;
    private Image[] tiles;
    
    public SimpleMapRenderer(byte[][] data, Image[] tiles) {
    	this.data = data;
    	this.tiles = tiles;
    }
    
    @Override
    public void draw(Graphics mapg) throws SlickException {
        for (int x = 0; x < data.length; x++) {
            float posx = x * tiles[1].getWidth();
            for (int y = 0; y < data[0].length; y++) {
                float posy = y * tiles[1].getHeight();     
                byte b = data[x][y];
                mapg.drawImage(tiles[b], posx, posy);
            }
            mapg.flush();
        }  
    }
}
