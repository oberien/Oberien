/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.renderer;

import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import view.data.Globals;

/**
 * This class renders a grid on the tiles to be able to separate them from each
 * other
 */
public class GridRenderer {

	private int vID, cID;
	private IntBuffer vB;
	private int linesX, linesY;
	private int screenSizeX, screenSizeY;

	/**
	 * Inits this renderer.
	 *
	 * @param mapSizeX The absolute width of the map in pixels.
	 * @param mapSizeY the absolute height of the map in pixels.
	 * @param screenSizeX The width of the screen.
	 * @param screenSizeY The height of the screen.
	 */
	public void init(int mapSizeX, int mapSizeY, int screenSizeX, int screenSizeY) {
		System.out.println(mapSizeX);
		linesX = mapSizeX;
		linesY = mapSizeY;
		this.screenSizeX = screenSizeX;
		this.screenSizeY = screenSizeY;
		vB = BufferUtils.createIntBuffer((linesX + linesY) * 4 * 2);

		for (int i = 0; i < linesX; i++) {
			vB.put(0).put(i * Globals.TILE_SIZE - Globals.GRID_STRENGTH / 2);
			vB.put(screenSizeX).put(i * Globals.TILE_SIZE - Globals.GRID_STRENGTH / 2);
			vB.put(screenSizeX).put(i * Globals.TILE_SIZE + Globals.GRID_STRENGTH / 2);
			vB.put(0).put(i * Globals.TILE_SIZE + Globals.GRID_STRENGTH / 2);
		}

		for (int i = 0; i < linesY; i++) {
			vB.put(i * Globals.TILE_SIZE - Globals.GRID_STRENGTH / 2).put(0);
			vB.put(i * Globals.TILE_SIZE + Globals.GRID_STRENGTH / 2).put(0);
			vB.put(i * Globals.TILE_SIZE - Globals.GRID_STRENGTH / 2).put(screenSizeY);
			vB.put(i * Globals.TILE_SIZE + Globals.GRID_STRENGTH / 2).put(screenSizeY);
		}
		vB.flip();

		IntBuffer ib = BufferUtils.createIntBuffer(1);
		GL15.glGenBuffers(ib);
		vID = ib.get(0);
	}

	public void draw() {

		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

		GL11.glColor3f(0, 0, 0);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vB, GL15.GL_STATIC_DRAW);
		GL11.glVertexPointer(3, GL11.GL_FLOAT, 2 << 2, 0L);

		GL11.glDrawArrays(GL11.GL_QUADS, 0, (linesX + linesY) * 4);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

//		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
	}
}
