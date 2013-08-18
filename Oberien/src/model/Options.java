package model;

public class Options {
	/**
	 * indicates wheather the map is buffered (faster, high RAM) or not (slower, low RAM)
	 */
	public static boolean bufferMap = true;
	/**
	 * indicates wheather vertical synchronisation is activated
	 */
	public static boolean vsync = false;
	/**
	 * 1 = fullscreen <br>
	 * 2 = windowed <br>
	 * 3 = borderless <br>
	 */
	public static int screenMode = 1;
	/**
	 * indicates wheather the game is only rendered when it is visible or not
	 */
	public static boolean onlyUpdateWhenVisible = true;
	/**
	 * indicates wheather anti aliasing is enabled or not
	 */
	public static boolean antiAliasing = false;
	/**
	 * sets the loading speed (0-1)<br>
	 * higher values result in less notifications but in a higher load speed
	 */
	public static float loadingSpeed = 0.001f;
}
