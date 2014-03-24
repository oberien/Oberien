/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.renderer;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * This is a very simple Implementation of the MapRenderer. It works fine for
 * small maps, however it is very inefficient for larger maps and thus shouldn't
 * be used.
 */
public class SimpleMapRenderer implements MapRenderer {

	private final byte[][] data;
	private final Image[] tiles;
	/**Stores the placement information for the tiles (x, y) and their index (z) in the texture atlas.**/
	private Vector3f pos[];

	public SimpleMapRenderer(byte[][] data, Image[] tiles) {
		this.data = data;
		this.tiles = tiles;
	}

	@Override
	public void init() {
		int length = data.length;
		for (int ix = 0; ix < data.length; ix++) {
			length += data[ix].length;
		}
		pos = new Vector3f[length];
		
		int i = 0;
		for (int x = 0; x < data.length; x++) {
			float posx = x * tiles[0].getWidth();
			for (int y = 0; y < data[0].length; y++) {
				float posy = y * tiles[0].getHeight();
				pos[i] = new Vector3f(posx, posy, data[x][y]);
				i++;
			}
		}
	}

	@Override
	public void draw(Graphics mapg) throws SlickException {
		for (Vector3f po : pos) {
			tiles[(int) po.z].draw(po.x, po.y);
		}
	}
}
