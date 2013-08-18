package view.data;

import java.awt.Font;

import org.newdawn.slick.Image;

import model.map.Map;
import view.renderer.*;

public class StartData {
	private Map map;
	private Image[] tiles;
    private Image[][] units;
    private MapRenderer mr;
    private GroundRenderer gr;
    private HUDRenderer hudr;
    private UnitRenderer ur;
    private DamageRenderer dr;
    private Font font;

	public GroundRenderer getGr() {
		return gr;
	}

	public void setGr(GroundRenderer gr) {
		this.gr = gr;
	}

	public HUDRenderer getHudr() {
		return hudr;
	}

	public void setHudr(HUDRenderer hudr) {
		this.hudr = hudr;
	}

	public UnitRenderer getUr() {
		return ur;
	}

	public void setUr(UnitRenderer ur) {
		this.ur = ur;
	}

	public Image[] getTiles() {
		return tiles;
	}

	public void setTiles(Image[] tiles) {
		this.tiles = tiles;
	}

	public Image[][] getUnits() {
		return units;
	}

	public void setUnits(Image[][] units) {
		this.units = units;
	}
	

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}
	
	public MapRenderer getMr() {
		return mr;
	}
	public void setMr(MapRenderer mr) {
		this.mr = mr;
	}

	public DamageRenderer getDr() {
		return dr;
	}

	public void setDr(DamageRenderer dr) {
		this.dr = dr;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}
}
