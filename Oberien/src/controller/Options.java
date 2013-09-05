package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

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
	public static double loadingSpeed = 0.01;
	
	public static void save() {
		try {
			File f = new File("cfg/settings.properties");
			if (!f.exists()) {
				f.mkdirs();
				f.createNewFile();
			}
			Properties properties = new Properties();
			properties.setProperty("bufferMap", bufferMap + "");
			properties.setProperty("vsync", vsync + "");
			properties.setProperty("screenMode", screenMode + "");
			properties.setProperty("onlyUpdateWhenVisible", onlyUpdateWhenVisible + "");
			properties.setProperty("antiAliasing", antiAliasing + "");
			properties.setProperty("loadingSpeed", loadingSpeed + "");
			properties.store(new FileOutputStream("cfg/settings.properties"), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void load() {
		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream("cfg/bounds.properties"));
			bufferMap = Boolean.parseBoolean(properties.getProperty("bufferMap"));
			vsync = Boolean.parseBoolean(properties.getProperty("vsync"));
			screenMode = Integer.parseInt(properties.getProperty("screenMode"));
			onlyUpdateWhenVisible = Boolean.parseBoolean(properties.getProperty("onlyUpdateWhenVisible"));
			antiAliasing = Boolean.parseBoolean(properties.getProperty("antiAliaising"));
			loadingSpeed = Double.parseDouble(properties.getProperty("loadingSpeed"));
		} catch (FileNotFoundException e) {
			save();
		} catch (IOException e) {e.printStackTrace();}
	}
}
