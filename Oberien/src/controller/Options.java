package controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.lwjgl.util.Dimension;
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
	private static boolean vsyncChanged = false;
	/**
	 * 0 = fullscreen <br>
	 * 1 = windowed <br>
	 * 2 = borderless <br>
	 */
	private static int screenMode = 0;
	private static boolean screenModeChanged = false;
	/**
	 * width and height of the game
	 */
	private static Dimension resolution;
	private static boolean resolutionChanged = false;
	/**
	 * indicates wheather the game is only rendered when it is visible or not
	 */
	private static boolean onlyUpdateWhenVisible = true;
	private static boolean onlyUpdateWhenVisibleChanged = false;
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
	 * <= 0 => max FPS
	 */
	private static int fps = 0;
	private static boolean fpsChanged = false;
	/**
	 * indicates whether to show the fps in the top left corner or not
	 */
	private static boolean showFps = true;
	private static boolean showFpsChanged = false;
	
	/**
	 * Volume of music and sound, and the overall volume. Not implemented yet. 
	 */
	private static int masterVolume = 100, musicVolume = 100, soundVolume = 100;
	
	public static void initOptions(AppGameContainer agc) {
		game = agc;
		resolution = new Dimension(game.getScreenWidth(), game.getScreenHeight());
	}
	
	public static void applySettings() throws SlickException, IllegalStateException {
		if (game == null) {
			throw new IllegalStateException("AppGameContainer in Options not initialized. Please use Options.init(AppGameContainer) first.");
		}
		if (resolutionChanged) {
			game.setDisplayMode(resolution.getWidth(), resolution.getHeight(), screenMode == 0);
			resolutionChanged = false;
		}
		if (resolutionChanged || screenModeChanged) {
			if (Options.screenMode == 0) {
				game.setFullscreen(true);
			} else if (Options.screenMode == 1) {
				System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
				game.setFullscreen(true);
				game.setFullscreen(false);
			} else if (Options.screenMode == 2) {
				System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
				game.setFullscreen(true);
				game.setFullscreen(false);
			}
			screenModeChanged = false;
		}
		if (vsyncChanged) {
			game.setVSync(vsync);
			vsyncChanged = false;
		}
		if (onlyUpdateWhenVisibleChanged) {
			game.setUpdateOnlyWhenVisible(Options.onlyUpdateWhenVisible);
			onlyUpdateWhenVisibleChanged = false;
		}
		if (fpsChanged) {
			game.setTargetFrameRate(Options.fps);
			fpsChanged = false;
		}
		if (showFpsChanged) {
			game.setShowFPS(showFps);
			showFpsChanged = false;
		}
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
			properties.setProperty("resolution", resolution.getWidth() + ":" + resolution.getHeight());
			properties.setProperty("onlyUpdateWhenVisible", onlyUpdateWhenVisible + "");
			properties.setProperty("antiAliasing", antiAliasing + "");
			properties.setProperty("loadingSpeed", loadingSpeed + "");
			properties.setProperty("fps", fps + "");
			properties.setProperty("showFps", showFps + "");
			properties.setProperty("masterVolume", masterVolume + "");
			properties.setProperty("musicVolume", musicVolume + "");
			properties.setProperty("soundVolume", soundVolume + "");
			properties.store(new FileOutputStream("cfg/settings.properties"), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void load() {
		if (game == null) {
			throw new IllegalStateException("AppGameContainer in Options not initialized. Please use Options.init(AppGameContainer) first.");
		}
		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream("cfg/settings.properties"));
			vsync = Boolean.parseBoolean(properties.getProperty("vsync", "true"));
			screenMode = Integer.parseInt(properties.getProperty("screenMode", "0"));
			String[] res = properties.getProperty("resolution", game.getScreenWidth() + ":" + game.getScreenHeight()).split(":");
			resolution = new Dimension(Integer.parseInt(res[0]), Integer.parseInt(res[1]));
			onlyUpdateWhenVisible = Boolean.parseBoolean(properties.getProperty("onlyUpdateWhenVisible", "true"));
			antiAliasing = Boolean.parseBoolean(properties.getProperty("antiAliaising", "false"));
			loadingSpeed = Double.parseDouble(properties.getProperty("loadingSpeed", "0.01"));
			fps = Integer.parseInt(properties.getProperty("fps", "0"));
			showFps = Boolean.parseBoolean(properties.getProperty("showFps", "true"));
			masterVolume = Integer.parseInt(properties.getProperty("masterVolume", "100"));
			musicVolume = Integer.parseInt(properties.getProperty("musicVolume", "100"));
			soundVolume = Integer.parseInt(properties.getProperty("soundVolume", "100"));
			vsyncChanged = true;
			screenModeChanged = true;
			resolutionChanged = true;
			onlyUpdateWhenVisible = true;
			fpsChanged = true;
			showFpsChanged = true;
		} catch (FileNotFoundException e) {
			save();
		} catch (IOException e) {e.printStackTrace();}
	}

	public static boolean isVsync() {
		return vsync;
	}

	public static void setVsync(boolean vsync) {
		if (Options.vsync == vsync) {
			return;
		} else {
			Options.vsync = vsync;
			Options.vsyncChanged = true;
		}
	}

	public static int getScreenMode() {
		return screenMode;
	}

	public static void setScreenMode(int screenMode) {
		if (Options.screenMode == screenMode) {
			return;
		} else {
			Options.screenMode = screenMode;
			screenModeChanged = true;
		}
	}

	public static boolean isOnlyUpdateWhenVisible() {
		return onlyUpdateWhenVisible;
	}

	public static void setOnlyUpdateWhenVisible(boolean onlyUpdateWhenVisible) {
		if (Options.onlyUpdateWhenVisible == onlyUpdateWhenVisible) {
			return;
		} else {
			Options.onlyUpdateWhenVisible = onlyUpdateWhenVisible;
			onlyUpdateWhenVisibleChanged = true;
		}
	}

	public static boolean isAntiAliasing() {
		return antiAliasing;
	}

	public static void setAntiAliasing(boolean antiAliasing) {
		if (Options.antiAliasing == antiAliasing) {
			return;
		} else {
			Options.antiAliasing = antiAliasing;
		}
	}

	public static double getLoadingSpeed() {
		return loadingSpeed;
	}

	public static void setLoadingSpeed(double loadingSpeed) {
		if (Options.loadingSpeed == loadingSpeed) {
			return;
		} else {
			Options.loadingSpeed = loadingSpeed;
		}
	}

	public static int getFps() {
		return fps;
	}

	public static void setFps(int fps) {
		if (Options.fps == fps) {
			return;
		} else {
			Options.fps = fps;
			fpsChanged = false;
		}
	}

	public static int getMasterVolume() {
		return masterVolume;
	}

	public static void setMasterVolume(int masterVolume) {
		if (Options.masterVolume == masterVolume) {
			return;
		} else {
			Options.masterVolume = masterVolume;
		}
	}

	public static int getMusicVolume() {
		return musicVolume;
	}

	public static void setMusicVolume(int musicVolume) {
		if (Options.musicVolume == musicVolume) {
			return;
		} else {
			Options.musicVolume = musicVolume;
		}
	}

	public static int getSoundVolume() {
		return soundVolume;
	}

	public static void setSoundVolume(int soundVolume) {
		if (Options.soundVolume == soundVolume) {
			return;
		} else {
			Options.soundVolume = soundVolume;
		}
	}

	public static boolean isShowFps() {
		return showFps;
	}

	public static void setShowFps(boolean showFps) {
		if (Options.showFps == showFps) {
			return;
		} else {
			Options.showFps = showFps;
			showFpsChanged = true;
		}
	}

	public static Dimension getResolution() {
		return resolution;
	}

	public static void setResolution(Dimension resolution) {
		if (Options.resolution.equals(resolution)) {
			return;
		} else {
			Options.resolution = resolution;
			resolutionChanged = true;
		}
	}
}
