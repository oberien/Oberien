/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.renderer;

import java.nio.FloatBuffer;
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
	private FloatBuffer cB;
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
		cB = BufferUtils.createFloatBuffer((linesX + linesY) * 4 * 3);

		for (int i = 0; i < (linesX + linesY) * 4; i++) {
			cB.put(Globals.GRID_COLOR.r).put(Globals.GRID_COLOR.g).put(Globals.GRID_COLOR.b);
		}
		cB.flip();
		
		for (int i = 0; i < linesX; i++) {
			vB.put(0).put(i * Globals.TILE_SIZE - Globals.GRID_STRENGTH / 2);
			vB.put(mapSizeX * Globals.TILE_SIZE).put(i * Globals.TILE_SIZE - Globals.GRID_STRENGTH / 2);
			vB.put(mapSizeX * Globals.TILE_SIZE).put(i * Globals.TILE_SIZE + Globals.GRID_STRENGTH / 2);
			vB.put(0).put(i * Globals.TILE_SIZE + Globals.GRID_STRENGTH / 2);
		}

		for (int i = 0; i < linesY; i++) {
			vB.put(i * Globals.TILE_SIZE - Globals.GRID_STRENGTH / 2).put(0);
			vB.put(i * Globals.TILE_SIZE + Globals.GRID_STRENGTH / 2).put(0);
			vB.put(i * Globals.TILE_SIZE - Globals.GRID_STRENGTH / 2).put(mapSizeY * Globals.TILE_SIZE);
			vB.put(i * Globals.TILE_SIZE + Globals.GRID_STRENGTH / 2).put(mapSizeY * Globals.TILE_SIZE);
		}
		vB.flip();

		IntBuffer ib = BufferUtils.createIntBuffer(2);
		GL15.glGenBuffers(ib);
		vID = ib.get(0);
		cID = ib.get(1);
	}

	public void draw() {

		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vB, GL15.GL_STATIC_DRAW);
		GL11.glVertexPointer(2, GL11.GL_INT, 2 * 4, 0L);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, cID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, cB, GL15.GL_STATIC_DRAW);
		GL11.glColorPointer(3, GL11.GL_FLOAT, 3 * 4, 0L);
		
		GL11.glDrawArrays(GL11.GL_QUADS, 0, (linesX + linesY) * 4);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
	}
}
