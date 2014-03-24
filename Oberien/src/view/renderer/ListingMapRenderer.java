/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.renderer;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;

/**
 * This class is an advanced MapRenderer that renders with help of a render list. 
 * It isn't working properly at the moment.
 */
public class ListingMapRenderer implements MapRenderer {

	private final byte[][] data;
	private final Image[] tiles;
	private int list;
	private final SGL gl = Renderer.get();

	public ListingMapRenderer(byte[][] data, Image[] tiles) {
		this.data = data;
		this.tiles = tiles;
	}

	@Override
	public void init() {
		list = gl.glGenLists(1);
		gl.glNewList(list, SGL.GL_COMPILE);
		for (int x = 0; x < data.length; x++) {
			float posx = x * tiles[1].getWidth();
			for (int y = 0; y < data[0].length; y++) {
				float posy = y * tiles[1].getHeight();
				byte b = data[x][y];
				System.out.println(b);
				
				tiles[b].draw(posx, posy);
			}
		}
		gl.glEndList();
	}

	@Override
	public void draw(Graphics mapg) throws SlickException {
		Graphics.setCurrent(mapg);
		gl.glCallList(list);
	}
}
