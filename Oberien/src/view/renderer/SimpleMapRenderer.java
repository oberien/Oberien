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
                    
                    switch (b){
                        case 0:
                        	mapg.drawImage(tiles[0], posx, posy);
                            break;
                        case 1: 
                        	mapg.drawImage(tiles[1], posx, posy);
                            break;
                        case 2:
                        	mapg.drawImage(tiles[2], posx, posy);
                            break;
                        case 3:
                        	mapg.drawImage(tiles[3], posx, posy);
                            break;
                        case 4:
                        	mapg.drawImage(tiles[4], posx, posy);
                            break;
                        case 5:
                        	mapg.drawImage(tiles[5], posx, posy);
                            break;
                        case 6:
                        	mapg.drawImage(tiles[6], posx, posy);
                            break;
                        case 7:
                        	mapg.drawImage(tiles[7], posx, posy);
                            break;
                        case 8:
                        	mapg.drawImage(tiles[8], posx, posy);
                            break;
                    }
                }
                mapg.flush();
            }  
    }
}
