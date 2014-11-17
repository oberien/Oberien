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

import view.customgui.components.Label;
import view.customgui.components.Panel;
import view.data.StartData;
import view.renderer.*;
import view.renderer.MapRenderer;
import controller.Options;

public class GameLoading extends BasicGameState {

	private final StartData sd;
	private String[] loading;
	private Label[] labels;
	private Panel panel;
	private Font f;

	private Image tiles;
	private Image[][] units;
	private Image missing;
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
		missing = new Image("res/imgs/units/missing.png");
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

		units = new Image[1025][4];
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
		switch (currentPart) {
			case 0:
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
				break;
			case 1:
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
							units[i][d] = missing;
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
				break;
			case 2:
				sd.setMr(new MapRenderer(data, sd.getTiles()));
				loading[2] = "Created SimpleMapRenderer.";
				counter = 0;
				currentPart++;
				break;
			case 3:
				FoWRenderer fowr = new FoWRenderer(sd.getMap().getWidth(), sd.getMap().getHeight());
				sd.setFowr(fowr);
				loading[3] = "Created FogOfWarRenderer.";
				currentPart++;
				break;
			case 4:
				ActionGroundRenderer agr = new ActionGroundRenderer();
				sd.setAgr(agr);
				loading[3] = "Created ActionGroundRenderer.";
				currentPart++;
				break;
			case 5:
				UnitRenderer ur = new UnitRenderer(sd.getUnits(), sd.getFont());
				sd.setUr(ur);
				loading[5] = "Created UnitRenderer.";
				currentPart++;
				break;
			case 6:
				ActionUnitRenderer aur = new ActionUnitRenderer(sd.getUnits());
				sd.setAur(aur);
				loading[5] = "Created ActionUnitRenderer.";
				currentPart++;
				break;
			case 7:
				GridRenderer gr = new GridRenderer();
				gr.init(sd.getMap().getWidth(), sd.getMap().getHeight(), gc.getWidth(), gc.getHeight());
				sd.setGr(gr);
				loading[6] = "Created GridRenderer.";
				currentPart++;
				break;
			case 8:
				DamageRenderer dr = new DamageRenderer(sd.getFont());
				sd.setDr(dr);
				loading[7] = "Created DamageRenderer.";
				loading[8] = "Loading finished.";
				currentPart++;
				break;
			case 9:
				sbg.getState(getID() + 1).init(gc, sbg);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					ErrorLogger.logger.severe(e.getMessage());
				}
				sbg.enterState(getID() + 1);
				break;
		}
	}

	@Override
	public int getID() {
		return 2;
	}
}
