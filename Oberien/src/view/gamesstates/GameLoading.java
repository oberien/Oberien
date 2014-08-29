/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.gamesstates;

import java.awt.Font;

import logger.ErrorLogger;
import model.map.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import view.components.Label;
import view.components.Panel;
import view.data.StartData;
import view.renderer.ActionGroundRenderer;
import view.renderer.DamageRenderer;
import view.renderer.FoWRenderer;
import view.renderer.GridRenderer;
import view.renderer.HUDRenderer;
import view.renderer.SimpleMapRenderer;
import view.renderer.UnitRenderer;
import controller.Options;

public class GameLoading extends BasicGameState {

	private final StartData sd;
	private String[] loading;
	private Label[] labels;
	private Panel panel;
	private Font f;

	private Image tiles;
	private Image[][] units;
	private byte[][] data;
	private Image map;
	private Graphics mapg;

	private int currentPart;
	private int counter;

	private Map mapd;

	public GameLoading(StartData sd) {
		this.sd = sd;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		loading = new String[18];
		f = sd.getFont();
		f = f.deriveFont(Font.PLAIN, gc.getScreenHeight() / loading.length - 5);
		UnicodeFont uf = new UnicodeFont(f);
		uf.getEffects().add(new ColorEffect(java.awt.Color.white));
		uf.addAsciiGlyphs();
		uf.loadGlyphs();

		panel = new Panel(0, 0, gc.getWidth(), gc.getHeight());
		labels = new Label[loading.length];
		for (int i = 0; i < labels.length; i++) {
			labels[i] = new Label(0, i * (gc.getScreenHeight() / loading.length - 5), uf);
			panel.add(labels[i]);
		}

		mapd = sd.getMap();

		units = new Image[513][4];
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		for (int i = 0; i < loading.length; i++) {
			if (loading[i] == null) {
				break;
			}
			labels[i].setText(loading[i]);
		}
		panel.repaint(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (currentPart == 0) {
			mapd = sd.getMap();
			data = mapd.getMap();
//			boolean nextPart = false;
////			int j = counter + (int) Math.ceil(tiles.length * Options.loadingSpeed);
//			for (int i = counter; i < j; i++) {
//				if (counter < tiles.length) {
//					loading[0] = "Loading map tiles [" + counter + "/" + tiles.length + "]";
//					try {
//						tiles[counter] = new Image("res/imgs/tiles/" + counter + ".png");
//						counter++;
//					} catch (Exception ex) {
//						nextPart = true;
//					}
//				} else {
//					nextPart = true;
//				}
//				if (nextPart) {
//					loading[0] = "Loading map tiles [" + counter + "/" + tiles.length + "] finished.";
//					counter = 0;
//					sd.setTiles(tiles);
//					currentPart++;
//					return;
//				}
//			}
			loading[0] = "Loading tiles spritesheet";
			try {
				tiles = new Image("res/imgs/tiles/tiles.png");
			} catch (SlickException ex) {
				//Temporary solution
				//TODO: Implement correct error handling
				System.out.println("Error: " + tiles.getResourceReference() + " was not found. Aborting.");
				System.exit(1);
			}
			sd.setTiles(tiles);
			currentPart++;
		} else if (currentPart == 1) {
			boolean nextPart = false;
			int j = counter + (int) Math.ceil(units.length * units[0].length * Options.getLoadingSpeed());
			for (int x = counter; x < j; x++) {
				int i = counter / 4;
				int d = counter % 4;
				if (counter < units.length * units[0].length) {
					loading[1] = "Loading unit tiles [" + i + "/" + units.length * units[0].length + "]";
					try {
						units[i][d] = new Image("res/imgs/units/" + i + "." + d + ".png");
						counter++;
					} catch (Exception ex) {
						units[i][d] = new Image("res/imgs/units/missing.png");
						counter++;
					}
				} else {
					nextPart = true;
				}
				if (nextPart) {
					loading[1] = "Loading unit tiles [" + counter + "/" + units.length * units[0].length + "] finished.";
					counter = 0;
					currentPart++;
					sd.setUnits(units);
					return;
				}
			}
		} else if (currentPart == 2) {
//				if (map == null) {
//					try {
//						map = new Image(data.length * Globals.TILE_SIZE, data[0].length * Globals.TILE_SIZE);
//						mapg = map.getGraphics();
//					} catch (SlickException e) {
//						loading[2] = "Creating BufferedMapRenderer - RAM too small -> switching to SimpleMapRenderer";
//						Options.bufferMap = false;
//						sd.setMr(new ListingMapRenderer(data, sd.getTiles()));
//						counter = 0;
//						currentPart++;
//					}
//				} else {
//					int j = counter + (int) Math.ceil(units.length * units[0].length * Options.loadingSpeed);
//					for (int i = 0; i < j; i++) {
//						int x = counter / data[0].length;
//						int y = counter % data[0].length;
//						if (counter < data.length * data[0].length) {
//							float posx = x * tiles[0].getWidth();
//							float posy = y * tiles[0].getHeight();
//							loading[2] = "Creating BufferedMapRenderer - draw tile [" + (counter + 1) + "/" + (data.length * data[0].length) + "]";
//							byte b = data[x][y];
//							mapg.drawImage(tiles[b], posx, posy);
//							counter++;
//						} else {
//							mapg.flush();
//							sd.setMr(new BufferedMapRenderer(map));
//							loading[2] = "Creating BufferedMapRenderer - draw tile [" + (counter) + "/" + (data.length * data[0].length) + "] finished.";
//							counter = 0;
//							currentPart++;
//							return;
//						}
//					}
//					mapg.flush();
//				}
			loading[2] = "Created SimpleMapRenderer.";
			sd.setMr(new SimpleMapRenderer(data, sd.getTiles()));
			counter = 0;
			currentPart++;
		} else if (currentPart == 3) {
			FoWRenderer fowr = new FoWRenderer(sd.getMap().getWidth(), sd.getMap().getHeight());
			sd.setFowr(fowr);
			loading[3] = "Created FogOfWarRenderer.";
			currentPart++;
		} else if (currentPart == 4) {
			ActionGroundRenderer agr = new ActionGroundRenderer();
			sd.setAgr(agr);
			loading[3] = "Created ActionGroundRenderer.";
			currentPart++;
		} else if (currentPart == 5) {
			HUDRenderer hudr = new HUDRenderer(sd.getFont(), gc.getWidth(), sd.getUnits(), gc);
			sd.setHudr(hudr);
			loading[4] = "Created HUDRenderer.";
			currentPart++;
		} else if (currentPart == 6) {
			UnitRenderer ur = new UnitRenderer(sd.getUnits(), sd.getFont());
			sd.setUr(ur);
			loading[5] = "Created UnitRenderer.";
			currentPart++;
		} else if (currentPart == 7) {
			GridRenderer gr = new GridRenderer();
			gr.init(sd.getMap().getWidth(), sd.getMap().getHeight(), gc.getWidth(), gc.getHeight());
			sd.setGr(gr);
			loading[6] = "Created GridRenderer.";
			currentPart++;
		} else if (currentPart == 8) {
			DamageRenderer dr = new DamageRenderer(sd.getFont());
			sd.setDr(dr);
			loading[7] = "Created DamageRenderer.";
			loading[8] = "Loading finished.";
			currentPart++;
//		} else if (currentPart == 9) {
//			LwjglInputSystem inSys = new LwjglInputSystem();
//			try {
//				inSys.startup();
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//			Nifty nifty = new Nifty(new LwjglRenderDevice(),
//					new NullSoundDevice(),
//					inSys,
//					new TimeProvider());
//			nifty.fromXml("xml/main.xml", "start");
//			nifty.gotoScreen("start");
//			nifty.loadStyleFile("nifty-default-styles.xml");
//			nifty.loadControlFile("nifty-default-controls.xml");
//			sd.setNifty(nifty);
//			loading[8] = "Loaded and created and initialised Nifty.";
//			currentPart++;
		} else if (currentPart == 9) {
			int states = sbg.getStateCount();
			/*for (int i = 1; i < states; i++) {
			 sbg.getState(i).init(gc, sbg);
			 }*/
			sbg.getState(getID() + 1).init(gc, sbg);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				ErrorLogger.logger.severe(e.getMessage());
			}
			sbg.enterState(getID() + 1);
		}
	}

	@Override
	public int getID() {
		return 2;
	}
}
