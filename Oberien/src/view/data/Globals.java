/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.data;

import org.newdawn.slick.Color;

/**
 * This class stores constants that are needed in different locations throughout
 * the code.
 */
public class Globals {

	/**
	 * The size of one tile in the tile spritesheet without the border.
	 */
	public static final int TILE_SIZE = 32;
	/**
	 * The border size of a tile in the tile spritesheet. It is specified per
	 * side, thus meaning that the tile's
	 * <code>x = TILE_SIZE + TILE_BORDER_SIZE * 2</code>
	 */
	public static final int TILE_BORDER_SIZE = 1;
	/**
	 * Needed because <code>Image.getWidth()</code> is bugged in Slick2d. Stores
	 * the width of the tile spritesheet.
	 */
	public static final int TILE_TEXTURE_X_SIZE = 256;
	/**
	 * Needed because <code>Image.getHeight()</code> is bugged in Slick2d.
	 * Stores the height of the tile spritesheet.
	 */
	public static final int TILE_TEXTURE_Y_SIZE = 128;
	/**
	 * Stores the strength of the lines in the grid rendered by the
	 * GridRenderer.
	 */
	public static final int GRID_STRENGTH = 2;
	/**
	 * Stores the color of the grid rendered by the GridRenderer.
	 */
	public static final Color GRID_COLOR = Color.black;
}
