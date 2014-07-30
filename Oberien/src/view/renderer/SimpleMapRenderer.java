/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

/**
 * This is a very simple Implementation of the MapRenderer. It works fine for
 * small maps, however it is very inefficient for larger maps and thus shouldn't
 * be used.
 */
public class SimpleMapRenderer implements MapRenderer {

	private final byte[][] data;
	private final Image tiles;
	private int vID, tID;
	private FloatBuffer vB, tB;

	/**
	 * Creates a MapRenderer which once used to be simple but now is really,
	 * really complex.
	 *
	 * @param data the tiles indices in a two dimensional array of the form
	 * byte[x][y]
	 * @param tiles the images that are to be used
	 * @throws SlickException
	 * @Deprecated
	 */
	public SimpleMapRenderer(byte[][] data, Image[] tiles) throws SlickException {
		this.data = data;
		//TODO: Transfer this image into StartData and load it in gameLoading
		this.tiles = new Image("res/imgs/tiles/tiles.png");
		this.init();
	}

	/**
	 * Creates a MapRenderer with a set of tile data and an image spritesheet.
	 *
	 * @param data the tiles indices in a two dimensional array of the form
	 * byte[x][y]
	 * @param tiles the spritesheet containing the tiles.
	 */
	public SimpleMapRenderer(byte[][] data, Image tiles) {
		this.data = data;
		this.tiles = tiles;
		this.init();
	}

	@Override
	public void init() {
		vB = BufferUtils.createFloatBuffer(data[0].length * data.length * 3 * 4);
		tB = BufferUtils.createFloatBuffer(data[0].length * data.length * 2 * 4);

		for (int x = 0; x < data.length; x++) {
			for (int y = 0; y < data[x].length; y++) {

				vB.put(x * 32).put(y * 32).put(0);
				vB.put(x * 32 + 32).put(y * 32).put(0);
				vB.put(x * 32 + 32).put(y * 32 + 32).put(0);
				vB.put(x * 32).put(y * 32 + 32).put(0);

				Vector2f c0 = getTileCoordinates(data[x][y], 0);
				Vector2f c1 = getTileCoordinates(data[x][y], 1);
				Vector2f c2 = getTileCoordinates(data[x][y], 2);
				Vector2f c3 = getTileCoordinates(data[x][y], 3);

				tB.put(c0.x).put(c0.y);
				tB.put(c1.x).put(c1.y);
				tB.put(c2.x).put(c2.y);
				tB.put(c3.x).put(c3.y);
			}
		}
		vB.flip();
		tB.flip();

		IntBuffer ib = BufferUtils.createIntBuffer(2);
		GL15.glGenBuffers(ib);
		vID = ib.get(0);
		tID = ib.get(1);
	}

	@Override
	public void draw(Graphics mapg) throws SlickException {

		//leave your fingers off this code if you don't want to get hurt!
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tiles.getTexture().getTextureID());

		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vB, GL15.GL_STATIC_DRAW);
		GL11.glVertexPointer(3, GL11.GL_FLOAT, 3 << 2, 0L);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, tID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, tB, GL15.GL_STATIC_DRAW);
		GL11.glTexCoordPointer(3, GL11.GL_FLOAT, 2 << 2, 0L);

		GL11.glDrawArrays(GL11.GL_QUADS, 0, data.length * data[0].length * 4);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
	}

	/**
	 * This is a helper method to compute the position of a tile in a
	 * spritesheet. TODO: implement an own class for this functionality and
	 * generalize this method
	 *
	 * @param index The index of the tile to be fetched the coordinates from.
	 * Numbering goes from left to right, then from top to bottom.
	 * @param corner the corner that should be returned (0 = top left, 1 = top
	 * right, 2 = bottom right...)
	 * @return a Vector2f with the texture coordinates of the tile at the
	 * specified index
	 */
	private Vector2f getTileCoordinates(byte index, int corner) {
		//get the lower 3 bits in posX0 -> these determine the relative position in the row (0 means first, 1 second...)
		byte posX0 = (byte) (index & 0x3);
		//divide index by 4 to get the relative row position 
		byte posY0 = (byte) (index / 4);
		//Computation of the texture coordinates by determining if there must be length units added by checking what corner is requested (the magic code below).
		//CAUTION: TOUCH AT OWN RISK!
		float x = posX0 * 1f / 4f + 1f / 4f * ((((corner % 3) & 0x1) << 1 | (corner % 3) & 0x2) >> 1);
		float y = posY0 * 1f / 8f + 1f / 8f * ((corner & 0x2) >> 1);
		return new Vector2f(x, y);
	}
}
