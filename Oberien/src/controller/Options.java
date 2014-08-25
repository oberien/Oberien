package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Options {
	/**
	 * AppGameContainer the settings are applied to
	 */
	private static AppGameContainer game;
	/**
	 * indicates whether vertical synchronisation is activated
	 */
	private static boolean vsync = true;
	/**
	 * 0 = fullscreen <br>
	 * 1 = windowed <br>
	 * 2 = borderless <br>
	 */
	private static int screenMode = 1;
	/**
	 * indicates wheather the game is only rendered when it is visible or not
	 */
	private static boolean onlyUpdateWhenVisible = true;
	/**
	 * indicates wheather anti aliasing is enabled or not
	 */
	private static boolean antiAliasing = false;
	/**
	 * sets the loading speed (0-1)<br>
	 * higher values result in less notifications but in a higher load speed
	 */
	private static double loadingSpeed = 0.01;
	/**
	 * indicates the number of frames per second to be displayed<br>
	 * -1 = max FPS
	 */
	private static int fps = -1;
	
	/**
	 * Volume of music and sound, and the overall volume. Not implemented yet. 
	 */
	private static int masterVolume = 100, musicVolume = 100, soundVolume = 100;
	
	public static void initOptions(AppGameContainer agc) {
		game = agc;
	}
	
	public static void applySettings() throws SlickException {
		if (game == null) {
			throw new IllegalStateException("AppGameContainer in Options not initialized. Please use Options.init(AppGameContainer) first.");
		}
		//TODO: add width/height option
		game.setDisplayMode(game.getScreenWidth(), game.getScreenHeight(), true);
		if (Options.screenMode == 0) {
			game.setFullscreen(true);
		} else if (Options.screenMode == 1) {
			game.setFullscreen(false);
			if (Options.screenMode == 2) {
				System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
			}
		}
		game.setVSync(Options.vsync);
		game.setUpdateOnlyWhenVisible(Options.onlyUpdateWhenVisible);
		if (Options.fps != -1) {
			game.setTargetFrameRate(Options.fps);
		}
		//TODO: add option for showing fps
		game.setShowFPS(true);
	}
	
	public static void save() {
		try {
			File f = new File("cfg/settings.properties");
			if (!f.exists()) {
				new File("cfg").mkdirs();
				f.createNewFile();
			}
			Properties properties = new Properties();
			properties.setProperty("vsync", vsync + "");
			properties.setProperty("screenMode", screenMode + "");
			properties.setProperty("onlyUpdateWhenVisible", onlyUpdateWhenVisible + "");
			properties.setProperty("antiAliasing", antiAliasing + "");
			properties.setProperty("loadingSpeed", loadingSpeed + "");
			properties.setProperty("fps", fps + "");
			properties.setProperty("masterVolume", masterVolume + "");
			properties.setProperty("musicVolume", musicVolume + "");
			properties.setProperty("soundVolume", soundVolume + "");
			properties.store(new FileOutputStream("cfg/settings.properties"), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void load() {
		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream("cfg/bounds.properties"));
			vsync = Boolean.parseBoolean(properties.getProperty("vsync"));
			screenMode = Integer.parseInt(properties.getProperty("screenMode"));
			onlyUpdateWhenVisible = Boolean.parseBoolean(properties.getProperty("onlyUpdateWhenVisible"));
			antiAliasing = Boolean.parseBoolean(properties.getProperty("antiAliaising"));
			loadingSpeed = Double.parseDouble(properties.getProperty("loadingSpeed"));
			fps = Integer.parseInt(properties.getProperty("fps"));
			masterVolume = Integer.parseInt(properties.getProperty("masterVolume"));
			musicVolume = Integer.parseInt(properties.getProperty("musicVolume"));
			soundVolume = Integer.parseInt(properties.getProperty("soundVolume"));
		} catch (FileNotFoundException e) {
			save();
		} catch (IOException e) {e.printStackTrace();}
	}

	public static boolean isVsync() {
		return vsync;
	}

	public static void setVsync(boolean vsync) {
		Options.vsync = vsync;
	}

	public static int getScreenMode() {
		return screenMode;
	}

	public static void setScreenMode(int screenMode) {
		Options.screenMode = screenMode;
	}

	public static boolean isOnlyUpdateWhenVisible() {
		return onlyUpdateWhenVisible;
	}

	public static void setOnlyUpdateWhenVisible(boolean onlyUpdateWhenVisible) {
		Options.onlyUpdateWhenVisible = onlyUpdateWhenVisible;
	}

	public static boolean isAntiAliasing() {
		return antiAliasing;
	}

	public static void setAntiAliasing(boolean antiAliasing) {
		Options.antiAliasing = antiAliasing;
	}

	public static double getLoadingSpeed() {
		return loadingSpeed;
	}

	public static void setLoadingSpeed(double loadingSpeed) {
		Options.loadingSpeed = loadingSpeed;
	}

	public static int getFps() {
		return fps;
	}

	public static void setFps(int fps) {
		Options.fps = fps;
	}

	public static int getMasterVolume() {
		return masterVolume;
	}

	public static void setMasterVolume(int masterVolume) {
		Options.masterVolume = masterVolume;
	}

	public static int getMusicVolume() {
		return musicVolume;
	}

	public static void setMusicVolume(int musicVolume) {
		Options.musicVolume = musicVolume;
	}

	public static int getSoundVolume() {
		return soundVolume;
	}

	public static void setSoundVolume(int soundVolume) {
		Options.soundVolume = soundVolume;
	}

	public static void setGame(AppGameContainer game) {
		Options.game = game;
	}
}
