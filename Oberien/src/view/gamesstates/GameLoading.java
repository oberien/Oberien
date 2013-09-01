/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.gamesstates;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import model.map.Map;
import model.map.MapList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import controller.Options;

import view.data.StartData;
import view.renderer.ActionGroundRenderer;
import view.renderer.BufferedMapRenderer;
import view.renderer.DamageRenderer;
import view.renderer.FoWRenderer;
import view.renderer.HUDRenderer;
import view.renderer.SimpleMapRenderer;
import view.renderer.UnitRenderer;

/**
 *
 * @author Bobthepeanut
 */
public class GameLoading extends BasicGameState {
	private StartData sd;
	private String[] loading;
	private Font f;
    private UnicodeFont uf;
    
    private Image[] tiles;
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
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
    	loading = new String[17];
		f = sd.getFont();
        f = f.deriveFont(Font.PLAIN, container.getScreenHeight()/loading.length-5);
        uf = new UnicodeFont(f);
        uf.getEffects().add(new ColorEffect(java.awt.Color.white));
        uf.addAsciiGlyphs();
        uf.loadGlyphs();
        /*String[] mapNames = MapList.getInstance().getMapNames();
        int sel = JOptionPane.showOptionDialog(null, "Choose your map", "Map", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, mapNames, 0);*/
        mapd = sd.getMap();
        //sd.setMap(map);
        
        tiles = new Image[256];
		units = new Image[513][4];
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
    	for (int i = 0; i < loading.length; i++) {
        	if (loading[i] == null) {
        		break;
        	}
        	uf.drawString(0, i*(gc.getScreenHeight()/loading.length-5), loading[i]);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    	if (currentPart == 0) {
			mapd = sd.getMap();
			data = mapd.getMap();
    		boolean nextPart = false;
    		int j = counter + (int)Math.ceil(tiles.length*Options.loadingSpeed);
    		for (int i = counter; i < j; i++) {
		        if (counter < tiles.length) {
		        	loading[0] = "Loading map tiles [" + counter + "/" + tiles.length + "]";
		            try {
		                tiles[counter] = new Image("res/imgs/tiles/" + counter + ".png");
		                counter++;
		            } catch (Exception ex) {
		            	nextPart = true;
		            }
		        } else {
		        	nextPart = true;
		        }
		        if (nextPart) {
		        	loading[0] = "Loading map tiles [" + counter + "/" + tiles.length + "] finished.";
	            	counter = 0;
	            	sd.setTiles(tiles);
	            	currentPart++;
	            	return;
		        }
    		}
    	} else if (currentPart == 1) {
    		boolean nextPart = false;
    		int j = counter + (int)Math.ceil(units.length*units[0].length*Options.loadingSpeed);
    		for (int x = counter; x < j; x++) {
	    		int i = counter/4;
	    		int d = counter%4;
	    		if (counter < units.length*units[0].length) {
	            	loading[1] = "Loading unit tiles [" + i + "/" + units.length*units[0].length + "]";
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
	    			loading[1] = "Loading unit tiles [" + counter + "/" + units.length*units[0].length + "] finished.";
	    			counter = 0;
	    			currentPart++;
	    			sd.setUnits(units);
	    			return;
	    		}
    		}
    	} else if (currentPart == 2) {
	        if (Options.bufferMap) {
	        	if (map == null) {
		        	try {
		        		map = new Image(data.length * tiles[0].getWidth(), data[0].length * tiles[0].getHeight());
		        		mapg = map.getGraphics();
		        	} catch (SlickException e) {
		        		loading[2] = "Creating BufferedMapRenderer - RAM too small -> switching to SimpleMapRenderer";
		        		Options.bufferMap = false;
		        		sd.setMr(new SimpleMapRenderer(data, sd.getTiles()));
		        		counter = 0;
			    		currentPart++;
		        	}
	        	} else {
	        		int j = counter + (int)Math.ceil(units.length*units[0].length*Options.loadingSpeed);
	        		for (int i = 0; i < j; i++) {
		        		int x = counter/data[0].length;
			            int y = counter%data[0].length;
			            if (counter < data.length*data[0].length) {
				            float posx = x * tiles[0].getWidth();
				            float posy = y * tiles[0].getHeight();
				            loading[2] = "Creating BufferedMapRenderer - draw tile [" + (counter+1) + "/" + (data.length*data[0].length) + "]";
				            byte b = data[x][y];
				            mapg.drawImage(tiles[b], posx, posy);
				            counter++;
		        		} else {
		        			mapg.flush();
				    		sd.setMr(new BufferedMapRenderer(map));
				    		loading[2] = "Creating BufferedMapRenderer - draw tile [" + (counter) + "/" + (data.length*data[0].length) + "] finished.";
				    		counter = 0;
				    		currentPart++;
				    		return;
		        		}
	        		}
	        		mapg.flush();
	        	}
	    	} else {
	    		loading[2] = "Created SimpleMapRenderer.";
	    		sd.setMr(new SimpleMapRenderer(data, sd.getTiles()));
	    		counter = 0;
	    		currentPart++;
	    	}
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
    		DamageRenderer dr = new DamageRenderer(sd.getFont());
    		sd.setDr(dr);
    		loading[6] = "Created DamageRenderer.";
    		loading[7] = "Loading finished.";
    		currentPart++;
    	} else if (currentPart == 8) {
    		int states = sbg.getStateCount();
    		/*for (int i = 1; i < states; i++) {
    			sbg.getState(i).init(gc, sbg);
    		}*/
			sbg.getState(3).init(gc, sbg);
	        try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {e.printStackTrace();}
	        sbg.enterState(getID() + 1);
    	}
    }
    
    @Override
    public int getID() {
        return 2;
    }
}
